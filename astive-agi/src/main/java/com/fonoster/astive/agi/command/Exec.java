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
import com.fonoster.astive.agi.annotation.ParamConverter;
import com.fonoster.astive.agi.annotation.Parameter;
import com.fonoster.astive.agi.annotation.Separator;

/**
 * Executes <code>application</code> with given <code>options</code>.
 *
 * <p>Returns whatever the application returns, or -2 on failure to find
 * application.
 * @since 1.0
 */
@AgiCommand(command = "EXEC")
public class Exec implements Serializable {
  private static final long serialVersionUID = -6489004022425967088L;
  @Parameter(optional = false)
  private String application;
  @Parameter(position = 1)
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
