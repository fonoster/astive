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
 * Sends the given text on a <code>channel</code>. Most channels do not support
 * the transmission of text.
 *
 * <p>Returns 0 if text is sent, or if the channel does not support text
 * transmission. Returns -1 only on error/hangup.
 *
 * @since 1.0
 */
@AgiCommand(command = "SEND TEXT")
public class SendText implements Serializable {
  private static final long serialVersionUID = -323324435661044014L;
  @Parameter(optional = false)
  private String text;

  /**
   * Create a new SendText object.
   *
   * @param text text to send.
   */
  public SendText(String text) {
    this.text = text;
  }

  /**
   * Get text to send.
   *
   * @return text to send.
   */
  public String getText() {
    return text;
  }

  /**
   * Set text to send.
   *
   * @param text text to send.
   */
  public void setText(String text) {
    this.text = text;
  }
}
