/* 
 * Copyright (C) 2017 by Fonoster Inc (http://fonoster.com)
 * http://github.com/fonoster/astive
 *
 * This file is part of Astive
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fonoster.astive.server;

import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.util.AppLocale;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Final implementation for AstDB.
 *
 * @since 1.0
 */
public class MyAstDB implements AstDB {
    private static final Logger LOG = Logger.getLogger(MyAstDB.class);
    private static final MyAstDB INSTANCE = new MyAstDB();
    private static Map<String, AstObj> apps;
    private static Map<String, Astivlet> astivletIndex;

    /**
     * Creates a new MyAstDB object.
     */
    private MyAstDB() {
        apps = Collections.synchronizedMap(new HashMap<String, AstObj>());
        astivletIndex = Collections.synchronizedMap(new HashMap<String, Astivlet>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addApp(AstObj astObj) throws AstiveException {
        // Ensure astObj url's are not in astivletIndex
        for (String urls : astObj.getURLPatterns()) {
            if (urlExist(urls)) {
                throw new AstiveException(AppLocale.getI18n("url already exist"));
            }
        }

        // everithing is good then add the app.
        apps.put(astObj.getDeploymentId(), astObj);

        // add astivlets to the db index.
        for (String url : astObj.getURLPatterns()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Adding url = " + url + " class = " + astObj.getAstivletByURLPattern(url));
            }

            astivletIndex.put(url, astObj.getAstivletByURLPattern(url));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean appExist(String deploymentId) {
        if (getApp(deploymentId) != null) {
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AstObj getApp(String deploymentId) {
        return apps.get(deploymentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AstObj> getApps() {
        ArrayList a = new ArrayList();

        for (AstObj ao : apps.values()) {
            a.add(ao);
        }

        return a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Astivlet getAstivlet(String urlStr) {
        // Check for specific url
        Astivlet ast;

        if ((ast = astivletIndex.get(urlStr)) != null) {
            return ast;
        }

        // Match parent paths and extentions
        int lCnt = 0;
        String bestMatch = null;

        for (String url : astivletIndex.keySet()) {
            Pattern curPattern = Pattern.compile(url);
            Boolean match = curPattern.matcher(urlStr).find();

            if (match) {
                if (url.length() > lCnt) {
                    bestMatch = url;
                    lCnt = url.length();
                }
            }
        }

        if (bestMatch != null) {
            if ((ast = astivletIndex.get(bestMatch)) != null) {
                return ast;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getAstivletsURLs() {
        return (String[]) astivletIndex.keySet().toArray();
    }

    /**
     * {@inheritDoc}
     */
    public static AstDB getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeApp(AstObj astObj) {
        // Ensure to remove astivlets from the index.
        for (String url : astObj.getURLPatterns()) {
            astivletIndex.remove(url);
        }

        apps.remove(astObj.getDeploymentId());
    }

    /**
     * Check if URL already exist.
     *
     * @param url URL to check
     * @return true if URL already exist or false otherwise.
     */
    private Boolean urlExist(String url) {
        Set<String> urls = astivletIndex.keySet();

        for (String curUrl : urls) {
            if (curUrl.equals(url)) {
                return true;
            }
        }

        return false;
    }
}
