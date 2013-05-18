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
 * Enum that handle the hangup cause for a <code>channel</code>.
 *
 * @since 1.0.0
 */
public enum HangupCause { // TODO: Define all enum elements below this comment.
  UNKNOWN(0xffffffff);

  /**
   * Int value of this type.
   */
  private int code;

  /**
   * Create a new {@link HangupCause} object. This class is an enum,
   * therefore can't be instantiated directly.
   *
   * @param code hangup cause int.
   */
  private HangupCause(final int code) {
    this.code = code;
  }

  /**
   * Get int code of hangup cause.
   *
   * @return code of hangup cause.
   */
  public int getCode() {
    return code;
  }
}
