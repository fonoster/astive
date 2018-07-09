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
package com.fonoster.astive.menu.action;

import org.apache.log4j.Logger;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.AgiResponse;

/**
 * Use this action to jump from one context/extension/priority to another.
 *
 * @since 1.0
 * @see Action
 */
public class GoExt implements Action {
    private static final Logger LOG = Logger.getLogger(GoExt.class);
    private AgiResponse agiResponse;
    private String context;
    private String extension;
    private String priority;

    /**
     * Creates a new instance of GoExt
     */
    public GoExt(AgiResponse agiResponse, String context, String extension, String priority) {
        this.agiResponse = agiResponse;
        this.context = context;
        this.extension = extension;
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doAction() {
        try {
            agiResponse.setContext(context);
            agiResponse.setExtension(extension);
            agiResponse.setPriority(priority);
        } catch (AgiException ex) {
            LOG.warn(ex.getMessage());
        }
    }
}
