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

import java.util.List;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.astivlet.Astivlet;

/**
 * Help manage Astive app objects.
 *
 * @since 1.0
 */
public interface AstDB {

    /**
     * Use to add apps to the database index. The final implementation must
     * ensure that a new app don't override old app URL's.
     *
     * @param astObj object to add.
     */
    public void addApp(AstObj astObj) throws AstiveException;

    /**
     *
     * @param deploymentId
     * @return
     */
    public Boolean appExist(String deploymentId);

    /**
     * Get an Astive app object.
     *
     * @return Astive app object
     */
    public AstObj getApp(String deploymentId) throws AstiveException;

    /**
     * List apps in the db index.
     *
     * @return app list.
     */
    public List<AstObj> getApps() throws AstiveException;

    /**
     * Get astivlet by using it url.
     *
     * @param urlStr url of requested astivlet.
     * @return requested astivlet.
     */
    public Astivlet getAstivlet(String urlStr) throws AstiveException;

    /**
     * Get a list the URL's index.
     *
     * @return URL's index list.
     */
    public String[] getAstivletsURLs() throws AstiveException;

    /**
     * Remove app from index. The final implementation must ensure remove
     * astivlets from de index of apps.
     *
     * @param astObj app to remove.
     */
    public void removeApp(AstObj astObj) throws AstiveException;
}
