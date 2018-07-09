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
package com.fonoster.astive.agi.command;

import java.io.Serializable;
import com.fonoster.astive.agi.annotation.AgiCommand;
import com.fonoster.astive.agi.annotation.Parameter;

/**
 * Retrieves an entry in the Asterisk database for a given <code>family</code>
 * <code>and key</code>
 * <p>Returns 0 if key is not set. Returns 1 if key is set and returns
 * the variable in parenthesis.
 *
 * Eg.: return code: 200 result=1 (testvariable)
 *
 * @since 1.0
 * @see DatabasePut
 */
@AgiCommand(command = "DATABASE GET")
public class DatabaseGet implements Serializable {
  private static final long serialVersionUID = 5937402997584970009L;
  @Parameter(optional = false)
  private String family;
  @Parameter(position = 1, optional = false)
  private String key;

  /**
   * Create a new DatabaseGet object with family and key as parameter.
   *
   * @param family database family.
   * @param key family element.
   */
  public DatabaseGet(final String family, final String key) {
    this.family = family;
    this.key = key;
  }

  /**
   * Get database family.
   *
   * @return database family.
   */
  public String getFamily() {
    return family;
  }

  /**
   * Get family element.
   *
   * @return family element.
   */
  public String getKey() {
    return key;
  }

  /**
   * Set database family.
   *
   * @param family database family
   */
  public void setFamily(final String family) {
    this.family = family;
  }

  /**
   * Set family element.
   *
   * @param key family element.
   */
  public void setKey(final String key) {
    this.key = key;
  }
}
