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
 * Deletes an entry in the Asterisk database for a given family and key
 * <p>Returns 1 if successful, 0 otherwise.
 *
 * <p><code>Family</code> is an arbitrary name use to group a collection of
 * values defined by a <code>key</code>. Example:
 *
 * <p>For a database family with name <code>cidname</code> and element key
 * 8453178070, the resulting command will be:
 *
 * <code>database del cidname 8453178070</code>
 *
 * @since 1.0
 * @see DatabasePut
 */
@AgiCommand(command = "DATABASE DEL")
public class DatabaseDel implements Serializable {
  private static final long serialVersionUID = -6727659547412740265L;
  @Parameter(optional = false)
  private String family;
  @Parameter(position = 1, optional = false)
  private String key;

  /**
   * Create a new DatabaseDel object.
   *
   * @param family database family
   * @param key identifier of object in database.
   */
  public DatabaseDel(final String family, final String key) {
    this.family = family;
    this.key = key;
  }

  /**
   * Get database family for the element to be deleted.
   *
   * @return database family.
   */
  public String getFamily() {
    return family;
  }

  /**
   * Get element to deleted.
   *
   * @return element to delete.
   */
  public String getKey() {
    return key;
  }

  /**
   * Set database family with the element to be deleted.
   *
   * @param family
   */
  public void setFamily(final String family) {
    this.family = family;
  }

  /**
   * Set element to deleted.
   *
   * @param key element to delete.
   */
  public void setKey(final String key) {
    this.key = key;
  }
}
