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

import java.text.Format;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Help to localize messages.
 *
 * @since 1.0.0
 */
public final class AppLocale {
    // Define the bundle prefix.

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("Messages");
    // Define the message format.
    private static MessageFormat messageForm = new MessageFormat("");
    // Formats to be use in messages.
    private static Format[] formats = {null};

    /**
     * Get a localized message.
     *
     * @param key used to access a particular message.
     * @return localized message.
     */
    public static String getI18n(final String key) {
        try {
            return MESSAGES.getString(key);
        } catch (MissingResourceException ex) {
            return key;
        }
    }

    /**
     * Get a localized message with arguments. The message must be defined like
     * so:
     *
     * <p>myMessage=This is a message with arguments
     * <code>{0}</code>
     *
     * <p>Then
     * <code>{0}</code> will be substitute by
     * <code>args[0]</code>.
     *
     * @param key message key.
     * @param args arguments to be passed to the message.
     * @return localized message with arguments.
     */
    public static String getI18n(final String key, final Object[] args) {
        try {
            final String msgPatter = MESSAGES.getString(key);
            messageForm.setFormats(formats);
            messageForm.applyPattern(msgPatter);

            final String result = messageForm.format(args);

            return result;
        } catch (MissingResourceException ex) {
            return key;
        }
    }

    /**
     * Constructor class. This class only has a static methods, therefore can't
     * be instantiated.
     */
    private AppLocale() {
    }
}
