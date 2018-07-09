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
package com.fonoster.astive.server.admin;

/**
 *
 * @since 1.0
 */
public enum AdminCommand {
    /**
     * Start server.
     */
    START("start"),
    /**
     * Stop server.
     */
    STOP("stop"),
    /**
     * Deploy an application.
     */
    DEPLOY("deploy"),
    /**
     * Undeploy an application.
     */
    UNDEPLOY("undeploy"),
    /**
     * Show help for commands
     */
    HELP("help"),
    /**
     *
     */
    VERSION("version");
 
    // String value for this enum.    
    private String command;

    // Create a new AdminCommand object with status code as parameter. This
    // class is an enum, therefore can't be instantiated directly.
    private AdminCommand(String command) {
        this.command = command;
    }

    /**
     * Get command enum.
     *
     * @param command command as text.
     * @return command as enum
     */
    static public AdminCommand get(String command) {
        for (AdminCommand ac : AdminCommand.values()) {
            if (ac.command.equals(command)) {
                return ac;
            }
        }

        return null;
    }

    /**
     * Get command as text.
     *
     * @param command command as enum.
     * @return command as text.
     */
    public String getCommand() {
        return command;
    }
}
