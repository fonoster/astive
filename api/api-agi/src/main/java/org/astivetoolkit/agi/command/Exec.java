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
import org.astivetoolkit.agi.annotation.ParamConverter;
import org.astivetoolkit.agi.annotation.Parameter;
import org.astivetoolkit.agi.annotation.Separator;

/**
 * Executes <code>application</code> with given <code>options</code>.
 *
 * <p>Returns whatever the application returns, or -2 on failure to find
 * application.
 * @since 1.0.0
 */
@AgiCommand(command = "EXEC")
public class Exec implements Serializable {
  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 0xa5f26d38c9382610L;

  /**
   * Application to execute.
   */
  @Parameter(optional = false)
  private String application;

  /**
   * Application options.
   */
  @Parameter(position = 0x1)
  @ParamConverter
  @Separator
  private String[] options;

  /**
   * Create a new Exec object.
   *
   * @param application application to execute.
   */
  public Exec(final String application) {
    this.application = application;
  }

  /**
   * Create a new Exec object with options.
   *
   * @param application application to execute.
   * @param options application options.
   */
  public Exec(final String application, final String... options) {
    this.application = application;
    this.options = options;
  }

  /**
   * Get application to execute.
   *
   * @return application to execute.
   */
  public String getApplication() {
    return application;
  }

  /**
   * Get application options.
   *
   * @return application options.
   */
  public String[] getOptions() {
    return options;
  }

  /**
   * Set application to execute.
   *
   * @param application application to execute.
   */
  public void setApplication(final String application) {
    this.application = application;
  }

  /**
   * Set application options.
   *
   * @param options applications options
   */
  public void setOptions(String... options) {
    this.options = options;
  }
}
