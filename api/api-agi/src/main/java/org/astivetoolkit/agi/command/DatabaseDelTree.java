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
 * Deletes a <code>family</code> or specific <code>keytree</code> within a
 * <code>family</code> in the Asterisk database.
 *
 * <p>Returns 1 if successful, 0 otherwise.
 *
 * @since 1.0.0
 * @see DatabaseDel
 */
@AgiCommand(command = "DATABASE DELTREE")
public class DatabaseDelTree implements Serializable {
  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 0xa1f5e4eeaf7df841L;

  /**
   * Database family.
   */
  @Parameter(optional = false)
  private String family;

  /**
   * Keytree within family.
   */
  @Parameter(position = 0x1)
  private String keyTree;

  /**
   * Create a new DatabaseDelTree object to delete a database family.
   *
   * @param family database family.
   */
  public DatabaseDelTree(final String family) {
    this.family = family;
  }

  /**
   * Create a new DatabaseDelTree object to delete a keytree within a database
   * family.
   *
   * @param family database family.
   * @param keyTree keytree to be deleted.
   */
  public DatabaseDelTree(final String family, final String keyTree) {
    this.family = family;
    this.keyTree = keyTree;
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
   * Get family keytree.
   *
   * @return family keytree.
   */
  public String getKeyTree() {
    return keyTree;
  }

  /**
   * Set database family.
   *
   * @param family database family.
   */
  public void setFamily(final String family) {
    this.family = family;
  }

  /**
   * Set family keytree.
   *
   * @param keyTree family keytree.
   */
  public void setKeyTree(final String keyTree) {
    this.keyTree = keyTree;
  }
}
