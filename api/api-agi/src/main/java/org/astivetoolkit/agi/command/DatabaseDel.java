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
package org.astivetoolkit.agi.command;

import java.io.Serializable;
import org.astivetoolkit.agi.annotation.AgiCommand;
import org.astivetoolkit.agi.annotation.Parameter;

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
 * @since 1.0.0
 * @see DatabasePut
 */
@AgiCommand(command = "DATABASE DEL")
public class DatabaseDel implements Serializable {
  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 0xa2a28d494ab2c757L;

  /**
   * Arbitrary name use to group a collection of values defined by a key.
   */
  @Parameter(optional = false)
  private String family;

  /**
   * Element of database.
   */
  @Parameter(position = 0x1, optional = false)
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
