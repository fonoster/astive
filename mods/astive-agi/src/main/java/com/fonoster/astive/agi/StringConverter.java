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
package com.fonoster.astive.agi;


/**
 * Convert data types use by Asterisk(Datetime, TimeZone,
 * array, boolean, int) to text.
 *
 * @since 1.0
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

    for (int i = 0; i < a.length; i++) {
      b.append(a[i]);

      if (i < (a.length - 1)) {
        b.append(separator);
      }
    }

    return b.toString();
  }
}
