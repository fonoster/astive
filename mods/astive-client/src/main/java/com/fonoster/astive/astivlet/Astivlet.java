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
package com.fonoster.astive.astivlet;

import com.fonoster.astive.agi.AgiException;

/**
 * Defines methods that all astivles must implement.
 *
 * An astivlet is a small Java program that runs within an Astive Server.
 * Astivlets receive and respond to requests from telephone systems like
 * Asterisk.
 *
 * @see AstivletRequest
 * @see AstivletResponse
 * @since 1.0
 */
public abstract class Astivlet {

    /**
     * Used by the Astive Server to respond to a telephone system request.
     *
     * @param request the <code>channel</code> information.
     * @param response the actions to be performed by the client.
     */
    abstract protected void service(AstivletRequest request, AstivletResponse response) throws AgiException;
}
