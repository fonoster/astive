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
package com.fonoster.astive.telnet;

/**
 * Enum that contains all possible commands to be executed by the TelnetServer.
 *
 * @since 1.0
 */
public enum TelnetCommand {
    /**
     * Stop server.
     */
    STOP("stop"),
    /**
     * List
     * <code>server</code> configurations.
     */
    SYSTEM("system"),
    /**
     * Show all applications deployed into the
     * <code>server</code>.
     */
    LOOKUP("lookup"),
    /**
     * Show help for commands.
     */
    HELP("help"),
    /**
     * Get server version.
     */
    VERSION("version"),
    /**
     * Close client connection.
     */
    EXIT("exit");
    /**
     * String value for this enum.
     */
    private String command;

    /**
     * Create a new TelnetCommand object with
     * <code>command</code> as parameter. This class is an enum, therefore can't
     * be instantiated directly.
     */
    private TelnetCommand(String command) {
        this.command = command;
    }

    /**
     * Get command enum.
     *
     * @param command command as text.
     * @return command as enum or <code>null</code> if command not present in
     * enum.
     */
    static public TelnetCommand get(String command) {
        for (TelnetCommand tc : TelnetCommand.values()) {
            if (tc.command.equals(command)) {
                return tc;
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
