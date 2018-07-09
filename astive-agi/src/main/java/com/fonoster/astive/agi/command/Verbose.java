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
 * Sends <code>message</code> to the console via verbose message system.
 * <code>level</code> is the verbose level (1-4). Always returns 1.
 *
 * @since 1.0
 */
@AgiCommand(command = "VERBOSE")
public class Verbose implements Serializable {
  private static final long serialVersionUID = 5179054735596539116L;
  @Parameter(position = 1, optional = false)
  private Integer level;
  @Parameter(optional = false)
  private String message;

  /**
   * Create a new Verbose object.
   *
   * @param message message to send.
   * @param level verbosity level(1-4).
   */
  public Verbose(String message, Integer level) {
    this.message = message;
    this.level = level;
  }

  /**
   * Get verbosity level.
   *
   * @return level verbosity level
   */
  public Integer getLevel() {
    return level;
  }

  /**
   * Get message to send.
   *
   * @return message to send.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Set verbosity level.
   *
   * @param level verbosity level of verbosity(1-4).
   */
  public void setLevel(Integer level) {
    this.level = level;
  }

  /**
   * Set message to send.
   *
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
