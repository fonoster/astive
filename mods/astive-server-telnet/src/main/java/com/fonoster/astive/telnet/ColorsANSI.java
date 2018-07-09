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
 * Contains all the ANSI colors constants.
 *
 * @since 1.0
 */
public class ColorsANSI {
    public static final String SANE = "\u001B[0m";
    public static final String BRIGHT = "\u001B[1m";
    public static final String BLACK = "\u001B[0;30m";
    public static final String RED = "\u001B[0;31m";
    public static final String GREEN = "\u001B[0;32m";
    public static final String YELLOW = "\u001B[0;33m";
    public static final String BLUE = "\u001B[0;34m";
    public static final String MAGENTA = "\u001B[0;35m";
    public static final String CYAN = "\u001B[0;36m";
    public static final String WHITE = "\u001B[0;37m";
    public static final String DARK_BLACK = "\u001B[1;30m";
    public static final String DARK_RED = "\u001B[1;31m";
    public static final String DARK_GREEN = "\u001B[1;32m";
    public static final String DARK_YELLOW = "\u001B[1;33m";
    public static final String DARK_BLUE = "\u001B[1;34m";
    public static final String DARK_MAGENTA = "\u001B[1;35m";
    public static final String DARK_CYAN = "\u001B[1;36m";
    public static final String DARK_WHITE = "\u001B[1;37m";
    public static final String BACKGROUND_BLACK = "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_MAGENTA = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";

    private ColorsANSI() {
    }
}
