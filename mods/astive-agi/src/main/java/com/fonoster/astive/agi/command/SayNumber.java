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
 * Say a given number, returning early if any of the given DTMF digits are
 * received on the channel.
 *
 * <p>Returns 0 if playback completes without a digit being pressed, or the ASCII
 * numerical value of the digit if one was pressed or -1 on error/hangup.
 *
 * @since 1.0
 */
@AgiCommand(command = "SAY NUMBER")
public class SayNumber implements Serializable {
  private static final long serialVersionUID = -5608428370844564593L;
  @Parameter(optional = false)
  private Integer number;
  @Parameter(position = 1, optional = false)
  private String escapeDigits;

  /**
   * Create a new SayNumber object with the number to say.
   *
   * @param number number to say.
   */
  public SayNumber(Integer number) {
    this.number = number;
    this.escapeDigits = "";
  }

  /**
   * Create a new SayNumber object with the number to say and escape digits.
   *
   * @param number number to say.
   * @param escapeDigits escape digits.
   */
  public SayNumber(Integer number, String escapeDigits) {
    this.number = number;
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
   * Get number to say.
   *
   * @return number to say.
   */
  public Integer getNumber() {
    return number;
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
   * Set number to say.
   *
   * @param number number to say.
   */
  public void setNumber(Integer number) {
    this.number = number;
  }
}
