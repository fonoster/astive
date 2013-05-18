/*
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.util;

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
  private static Format[] formats = { null };

  /**
   * Constructor class. This class only has a static methods, therefore can't
   * be instantiated.
   */
  private AppLocale() {
  }

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
   * <p>myMessage=This is a message with arguments <code>{0}</code>
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
}
