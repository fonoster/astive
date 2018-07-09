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
 * Receives a character of text on a <code>channel</code>. Most channels do
 * not support the reception of text.
 *
 * <p>Returns the decimal value of the character if one is
 * received, or 0 if the channel does not support text reception. Returns
 * -1 only on error/hangup.
 *
 * @since 1.0
 */
@AgiCommand(command = "RECEIVE CHAR")
public class ReceiveChar implements Serializable {
  private static final long serialVersionUID = -4988177721603502302L; 
  @Parameter
  private Integer timeout;

  /**
   * Create a new ReceiveChar object.
   */
  public ReceiveChar() {
    timeout = 0;
  }

  /**
   * Create a new ReceiveChar object with timeout.
   *
   * @param timeout time in milliseconds to wait for char.
   */
  public ReceiveChar(Integer timeout) {
    this.timeout = timeout;
  }

  /**
   * Get timeout in milliseconds to wait for char.
   *
   * @return timeout in milliseconds.
   */
  public Integer getTimeout() {
    return timeout;
  }

  /**
   * Set Timeout in milliseconds to wait for char.
   *
   * @param timeout timeout in milliseconds.
   */
  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }
}
