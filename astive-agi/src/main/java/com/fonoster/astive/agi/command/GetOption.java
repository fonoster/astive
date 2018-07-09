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
 * Behaves similar to {@link StreamFile} but used with a timeout option.
 *
 * @since 1.0
 */
@AgiCommand(command = "GET OPTION")
public class GetOption implements Serializable {
  private static final long serialVersionUID = -5788144017301928558L;
  @Parameter(position = 2)
  private Integer timeout;
  @Parameter(position = 1, optional = false)
  private String escapeDigits;
  @Parameter(optional = false)
  private String file;

  /**
   * Create a new GetOption object with audio an escape digits. With this
   * constructor the command wait until a digit in escapeDigits is press.
   *
   * @param file audio to stream.
   * @param escapeDigits digits to interrupt the audio.
   */
  public GetOption(String file, String escapeDigits) {
    this.file = file;
    this.escapeDigits = escapeDigits;
    this.timeout = -1;
  }

  /**
   * Create a new GetOption object with audio an escape digits. With this
   * constructor the command wait until a digit in escapeDigits is press.
   *
   * @param file audio to stream.
   * @param escapeDigits digits to interrupt the audio.
   * @param timeout time in milliseconds to wait for DTMF digit.
   */
  public GetOption(String file, String escapeDigits, Integer timeout) {
    this.file = file;
    this.escapeDigits = escapeDigits;
    this.timeout = timeout;
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
   * Get audio to be send to <code>channel</code>.
   *
   * @return audio to be send to <code>channel</code>.
   */
  public String getFile() {
    return file;
  }

  /**
   * Get timeout(in milliseconds).
   *
   * @return time in milliseconds.
   */
  public Integer getTimeout() {
    return timeout;
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
   * Set audio to be send to <code>channel</code>. Keep in mind that
   * the audio must not contain any extension.
   *
   * @param file audio to be send to <code>channel</code>
   */
  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Set timeout(in milliseconds).
   *
   * @param timeout time in milliseconds
   */
  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }
}
