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
package org.astivetoolkit.agi;


/**
 * Convert data types use by Asterisk(Datetime, TimeZone,
 * array, boolean, int) to text.
 *
 * @since 1.0.0
 */
public class StringConverter implements Converter {
  /**
   * Get a comma separated value from an array of strings.
   *
   * @param a string values
   * @param separator use to separate values
   * @return an string of command separated values.
   */
  public String fromArrayString(final String[] a, final String separator) {
    StringBuilder b = new StringBuilder(30);

    for (int i = 0x0; i < a.length; i++) {
      b.append(a[i]);

      if (i < (a.length - 0x1)) {
        b.append(separator);
      }
    }

    return b.toString();
  }
}
