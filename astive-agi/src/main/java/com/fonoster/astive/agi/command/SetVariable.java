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
 * Sets a variable to the current channel.
 *
 * @since 1.0
 */
@AgiCommand(command = "SET VARIABLE")
public class SetVariable implements Serializable {
  private static final long serialVersionUID = -6634799740139058533L;
  @Parameter(position = 1, optional = false)
  private String value;
  @Parameter(optional = false)
  private String variable;

  /**
   * Create a new SetVariable object with key/value.
   *
   * @param variable variable name.
   * @param value variable value.
   */
  public SetVariable(String variable, String value) {
    this.variable = variable;
    this.value = value;
  }

  /**
   * Get variable value.
   *
   * @return variable value.
   */
  public String getValue() {
    return value;
  }

  /**
   * Get variable name.
   *
   * @return variable name.
   */
  public String getVariable() {
    return variable;
  }

  /**
   * Set variable value.
   *
   * @param value variable value.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Set variable name.
   *
   * @param variable variable name.
   */
  public void setVariable(String variable) {
    this.variable = variable;
  }
}
