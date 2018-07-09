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

import noNamespace.AppDocument;
import noNamespace.AppType;
import noNamespace.AstivletMappingType;
import noNamespace.AstivletType;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.util.AppLocale;
import com.fonoster.astive.util.URLValidator;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JarResources;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.proxy.CglibProxyProvider;
import org.xeustechnologies.jcl.proxy.ProxyProviderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convenient class to manage Astive apps jars.
 *
 * @since 1.0
 */
public final class AstObj {
    private static final Logger LOG = Logger.getLogger(AstObj.class);
    private static final String ASTIVE_DEPLOYMENT_DESCRIPTOR = "app.xml";
    private AppType app;
    private JarClassLoader jcl;
    private JclObjectFactory factory;
    private Map<String, Astivlet> astivlets;
    /**
     * Application identifier.
     */
    private String deploymentId;

    /**
     * Creates a new AstObj object.
     *
     * @param getDeploymentId application identifier.
     */
    public AstObj(String deploymentId, String jarFile) throws AstiveException {
        this.deploymentId = deploymentId;
        astivlets = new HashMap();

        try {
            // Should be in AstiveServer class
            ProxyProviderFactory.setDefaultProxyProvider(new CglibProxyProvider());
            // Creating a class loader for this app obj.
            jcl = new JarClassLoader();
            jcl.add(jarFile);
            factory = JclObjectFactory.getInstance(true);

            // Getting the descriptor
            JarResources jar = new JarResources();
            jar.loadJar(jarFile);

            byte[] appXml = jar.getResource(ASTIVE_DEPLOYMENT_DESCRIPTOR);
            AppDocument doc = AppDocument.Factory.parse(new String(appXml));

            if (doc.validate() == false) {
                throw new AstiveException(AppLocale.getI18n("errorInvalidDescriptor"));
            }

            app = doc.getApp();

            AstivletType[] ats = app.getAstivletArray();

            // Validate URL's patterns
            for (AstivletType at : ats) {
                List<String> urls = getURLs(at.getAstivletId());

                // Ignore the Astivlet
                if (urls.isEmpty()) {
                    continue;
                }

                for (String url : urls) {
                    if (!URLValidator.isValidURL(url)) {
                        throw new AstiveException(AppLocale.getI18n("invalidURL"));
                    }
                    Astivlet ast = getAstivletByURLPattern(url);

                    if (ast != null) { // URL Pattern already exist
                        throw new AstiveException(AppLocale.getI18n("patternAlreadyDefine"));
                    }
                    astivlets.put(url, getAstivletByClass(at.getClass1()));
                }
            }
        } catch (XmlException ex) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        }
    }

    /**
     * Get an instance of the class in the class loader.
     *
     * @param clazz class to return
     * @return an instance of requested class.
     */
    public Astivlet getAstivletByClass(String clazz) {
        return (Astivlet) factory.create(jcl, clazz);
    }

    /**
     * Get an instance of the class in the class loader.
     *
     * @param astivletId
     * @return an instance of the class loaded class.
     */
    public Astivlet getAstivletById(String astivletId) {
        AstivletType[] ats = app.getAstivletArray();

        for (AstivletType at : ats) {
            if (at.getAstivletId().equals(astivletId)) {
                return getAstivletByClass(at.getClass1());
            }
        }

        return null;
    }

    /**
     * Get an instance of the class in the class loader.
     *
     * @param urlPattern find the Astivlet matching with this pattern.
     * @return an instance of the class loaded class.
     */
    public Astivlet getAstivletByURLPattern(String urlPattern) {
        return astivlets.get(urlPattern);
    }

    /**
     * Get application identifier.
     *
     * @return application identifier.
     */
    public String getDeploymentId() {
        return deploymentId;
    }

    /**
     * Returns meta data related to de app.
     *
     * @return app meta-data.
     */
    public AppType getInfo() {
        return app;
    }

    /**
     * Get all URL for this app.
     *
     * @return a list with all URL's patterns in this object instance.
     */
    public List<String> getURLPatterns() {
        AstivletMappingType[] amts = app.getAstivletMappingArray();
        ArrayList<String> amtList = new ArrayList();

        for (AstivletMappingType amt : amts) {
            amtList.add(amt.getUrlPattern());
        }

        return amtList;
    }

    /**
     * Get URL.
     *
     * @param astivletId astivlet identifier.
     * @return an url.
     */
    private List getURLs(String astivletId) {
        List<String> urlList = new ArrayList();
        AstivletMappingType[] amts = app.getAstivletMappingArray();

        for (AstivletMappingType amt : amts) {
            if (amt.getAstivletId().equals(astivletId)) {
                urlList.add(amt.getUrlPattern());
            }
        }

        return urlList;
    }
}
