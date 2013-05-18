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
 * Enum that map the presentation type for the callerId (@link AgiRequest).
 *
 * @since 1.0.0
 */
public enum PresentationType {ALLOWED(0x0, "allowed", "allowed"),
  ALLOWED_NOT_SCREENED(0x1, "allowed_not_screened", "allowed not screened"),
  ALLOWED_PASSED_SCREEN(0x2, "allowed_passed_screen", "allowed passed screen"),
  ALLOWED_FAILED_SCREEN(0x3, "allowed_failed_screen", "allowed failed screen"),
  PROHIB_NOT_SCREENED(0x4, "prohib_not_screened", "prohib not screened"),
  PROHIB_PASSED_SCREEN(0x5, "prohib_passed_screen", "prohib passed screen"),
  PROHIB_FAILED_SCREEN(0x6, "prohib_failed_screen", "prohib failed screen"),
  PROHIB(0x7, "prohib", "prohib"),
  UNAVAILABLE(0x8, "unavailable", "unavailable");

  /**
   * PresentationType value.
   */
  private int value;

  /**
   * PresentationType name
   */
  private String name;

  /**
   * PresentationType description.
   */
  private String literal;

  /**ADOC_COMME */
  private PresentationType(final int value, final String name, final String literal) {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * DOCUMENT ME!
   *
   * @param value DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static PresentationType get(final int value) {
    return null;
  }

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static PresentationType get(final String name) {
    return null;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getLiteral() {
    return literal;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getName() {
    return name;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getValue() {
    return value;
  }
}
