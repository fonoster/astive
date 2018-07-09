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
package com.fonoster.astive.util;

import java.util.regex.Pattern;

/**
 * Use to check if a URL pattern is valid.
 *
 * @since 1.0.0
 */
public final class URLValidator {

    /**
     * Use for exact match.
     */
    public static final Pattern SLASH_SLASH_ASTIVLET =
            Pattern.compile("^\\/[-a-zA-Z0-9][-a-zA-Z0-9+&@#/%?=~_|!:,.;]");
    /**
     * Match an string starting with '/' and ending with '/*'.
     */
    public static final Pattern SLASH_SLASH_ASTERISK = Pattern.compile("^\\/[-a-zA-Z0-9_](\\/\\*)");
    /**
     * Match an string with form '*.extension'.
     */
    public static final Pattern ASTERISK_EXTENSION =
            Pattern.compile("^\\*.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]");

    private URLValidator() {
    }

    /**
     * Check if an URL match with the following criteria.
     *
     * A string beginning with a '/' character and ending with a '/*' suffix is
     * used for path mapping. A string beginning with a '*.' prefix is used as
     * an extension mapping. A string containing only the '/' character
     * indicates the "default" servlet of the application. In this case the
     * servlet path is the request URI minus the context path and the path info
     * is null. All other strings are used for exact matches only.
     *
     * @param url URL to validate.
     * @return if match with a valid pattern.
     */
    static public Boolean isValidURL(final String url) {

        return (url.trim().equals("/")
                || SLASH_SLASH_ASTIVLET.matcher(url).find()
                || SLASH_SLASH_ASTERISK.matcher(url).find()
                || ASTERISK_EXTENSION.matcher(url).find());

    }
}
