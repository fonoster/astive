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
package org.astivetoolkit.server;


/**
 * This exception is used whenever a critical action is perform by the system.
 *
 * @since 1.0.0
 */
public class SystemException extends Exception {
 
	private static final long serialVersionUID = 1L;

/**
   * Creates a new SystemException object with null as its detail message.
   */
  public SystemException() {
	  super();
  }

  /**
   * Creates a new SystemException object with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the
   * <code>Throwable.getMessage()</code> method).
   */
  public SystemException(final String message) {
    super(message);
  }
}
