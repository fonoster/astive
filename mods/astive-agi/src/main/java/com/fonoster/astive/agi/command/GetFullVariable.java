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
 * Returns 0 if <code>variablename</code> is not set or channel does not exist.
 *
 * <p>Returns 1 if variablename is set and returns the variable in parenthesis.
 * Understands complex variable names and builtin variables, unlike GET VARIABLE.
 *
 * <p>Ex.: return code: 200 result=1 (testvariable)
 *
 * @since 1.0
 */
@AgiCommand(command = "GET FULL VARIABLE")
public class GetFullVariable implements Serializable {
  private static final long serialVersionUID = -8554768673812519898L;
  @Parameter(position = 1)
  private String channel;
  @Parameter(optional = false)
  private String variable;

  /**
   * Create a new GetFullVariable object to get the variable on the current
   * <code>channel</code>.
   *
   * @param variable channel variable.
   */
  public GetFullVariable(String variable) {
    this.variable = variable;
  }

  /**
   * Create a new GetFullVariable object to get the variable on
   * <code>channel</code>.
   *
   * @param variable channel variable.
   * @param channel channel name.
   */
  public GetFullVariable(String variable, String channel) {
    this.variable = variable;
    this.channel = channel;
  }

  /**
   * Get channel name or null for current channel.
   *
   * @return channel name.
   */
  public String getChannel() {
    return channel;
  }

  /**
   * Get channel variable.
   *
   * @return channel variable.
   */
  public String getVariable() {
    return variable;
  }

  /**
   * Set channel name. Use null for current channel.
   *
   * @param channel channel name.
   */
  public void setChannel(String channel) {
    this.channel = channel;
  }

  /**
   * Set channel variable.
   *
   * @param variable channel variable.
   */
  public void setVariable(String variable) {
    this.variable = variable;
  }
}
