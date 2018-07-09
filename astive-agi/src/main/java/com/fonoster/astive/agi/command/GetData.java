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

import com.fonoster.astive.agi.annotation.AgiCommand;
import com.fonoster.astive.agi.annotation.Parameter;

import java.io.Serializable;

/**
 * Stream the given <code>file</code>, and receive DTMF data.
 *
 * <p>Returns the digits received from the channel at the other end.
 *
 * @since 1.0
 */
@AgiCommand(command = "GET DATA")
public class GetData implements Serializable {
  private static final long serialVersionUID = -6316397364689291658L;
  private static final int DEFAULT_MAX_DIGITS = 1024;
  private static final int DEFAULT_TIMEOUT = 0;
  @Parameter(position = 2)
  private Integer maxDigits;
  @Parameter(position = 1)
  private Integer timeout;
  @Parameter(optional = false)
  private String file;

  /**
   * Create a new GetData object with file as parameter.
   *
   * @param file audio to stream.
   */
  public GetData(String file) {
    this.file = file;
    this.timeout = DEFAULT_TIMEOUT;
    this.maxDigits = DEFAULT_MAX_DIGITS;
  }

  /**
   * Create a new GetData object with timeout.
   *
   * @param file audio to stream.
   * @param timeout time in milliseconds to wait for a new DTMF digit.
   */
  public GetData(String file, Integer timeout) {
    this.file = file;
    this.timeout = timeout;
    this.maxDigits = DEFAULT_MAX_DIGITS;
  }

  /**
   * Create a new GetData object with timeout and maxDigits.
   *
   * @param file audio to stream.
   * @param timeout time in milliseconds to wait for a new DTMF digit.
   * @param maxDigits maximum amount digits accepted.
   */
  public GetData(String file, Integer timeout, Integer maxDigits) {
    this.file = file;
    this.timeout = timeout;
    this.maxDigits = maxDigits;
  }

  /**
   * Get audio file.
   *
   * @return audio file.
   */
  public String getFile() {
    return file;
  }

  /**
   * Get maximum amount of digits.
   *
   * @return maximum amount of digits.
   */
  public Integer getMaxDigits() {
    return maxDigits;
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
   * Set audio file
   *
   * @param file audio file.
   */
  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Set maximum amount of digits.
   *
   * @param maxDigits maximum amount of digits.
   */
  public void setMaxDigits(Integer maxDigits) {
    this.maxDigits = maxDigits;
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
