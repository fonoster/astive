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
package com.fonoster.astive.server.utils;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.fonoster.astive.Version;
import com.fonoster.astive.server.ServiceProperties;
import com.fonoster.astive.util.AppLocale;

/**
 * Print the start info.
 *
 * @since 1.0.0
 */
public class InitOutput {

    private static final Logger LOG = Logger.getLogger(InitOutput.class);

    /**
     * Print the start info.
     *
     * @param properties info to be printed.
     */
    public void printInit(ArrayList<ServiceProperties> properties) {
        StringBuilder sb =
                new StringBuilder(AppLocale.getI18n("astivedInitHeader",
                new String[]{Version.VERSION, Version.BUILD_TIME}));

        for (ServiceProperties param : properties) {
            if (param.isDisabled()) {
                continue;
            }

            // This is not elegant but work !
            if (param.getServiceName().length() > 7) {
                sb.append(param.getServiceName());
                sb.append("\t");
            } else {
                sb.append(param.getServiceName());
                sb.append("\t\t");
            }

            sb.append(param.getBindAddr().getHostAddress());
            sb.append("\t");

            if (param.isUnableToOpen()) {
                sb.append(AppLocale.getI18n("errorUnableToOpen"));
            } else {
                sb.append(param.getPort());
            }

            sb.append("\n");
        }

        sb.append(AppLocale.getI18n("astivedInitFooter"));
        LOG.info(sb.toString());
    }
}
