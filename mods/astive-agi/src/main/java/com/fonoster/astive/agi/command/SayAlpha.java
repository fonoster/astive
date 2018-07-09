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
 * Say a given character string, returning early if any of the given DTMF digits
 * are received on the channel.
 *
 * <p>Returns 0 if playback completes without a digit being pressed, or the ASCII
 * numerical value of the digit if one was pressed or -1 on error/hangup.
 *
 * @since 1.0
 */
@AgiCommand(command = "SAY ALPHA")
public class SayAlpha implements Serializable {
  private static final long serialVersionUID = -2496223335807892306L;
  @Parameter(position = 1, optional = false)
  private String escapeDigits;
  @Parameter(optional = false)
  private String text;

  /**
   * Create a new SayAlpha object with the text to "say".
   *
   * @param text text to say.
   */
  public SayAlpha(String text) {
    this.text = text;
    this.escapeDigits = "";
  }

  /**
   * Create a new SayAlpha object with the text to "say" and escape digits.
   *
   * @param text text to say.
   * @param escapeDigits escape digits.
   */
  public SayAlpha(String text, String escapeDigits) {
    this.text = text;
    this.escapeDigits = escapeDigits;
  }

  /**
   * Get the digits used to interrupt the audio.
   *
   * @return digits used to interrupt the audio.
   */
  public String getEscapeDigits() {
    return escapeDigits;
  }

  /**
   * Get text to say.
   *
   * @return text to say.
   */
  public String getText() {
    return text;
  }

  /**
   * Set the digits to be use to interrupt the audio.
   *
   * @param escapeDigits digits to be use to interrupt the audio.
   */
  public void setEscapeDigits(String escapeDigits) {
    this.escapeDigits = escapeDigits;
  }

  /**
   * Set text to say.
   *
   * @param text text to say.
   */
  public void setText(String text) {
    this.text = text;
  }
}
