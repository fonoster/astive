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
package com.fonoster.astive.server.appmanager;

import com.fonoster.astive.AstiveException;

/**
 *
 * @since 1.0
 */
public interface Deployer {

    /**
     * Adds app to the server index.
     *
     * @param file app path
     * @throws AstiveException
     */
    void deploy(String file) throws AstiveException;

    /**
     * Discharge app from the server.
     *
     * @param deploymentId app to undeployed.
     * @throws AstiveException if app don't exist.
     */
    void undeploy(String deploymentId) throws AstiveException;
}
