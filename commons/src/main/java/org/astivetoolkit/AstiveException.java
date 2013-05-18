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
package org.astivetoolkit;


/**
 *
 * @since 1.0.0
 * @see AgiException
 */
public class AstiveException extends Exception {
  
	private static final long serialVersionUID = 1L;

/**
   * Creates a new AstiveException object with the original exception as
   * parameter to be nested as part of this exception.
   *
   * @param exception used to provide further info about the original exception.
   */
  public AstiveException(final Exception exception) {
    super(exception);
  }

  /**
   * Creates a new AstiveException object.
   *
   * @param msg further info about the exception.
   */
  public AstiveException(final String msg) {
    super(msg);
  }

  /**
   * Creates a new AstiveException object.
   */
  public AstiveException() {
	  super();
  }
}
