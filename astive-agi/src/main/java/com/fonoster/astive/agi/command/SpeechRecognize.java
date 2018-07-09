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
 * Plays back given <code>prompt</code> while listening for speech and DTMF.
 *
 * @since 1.0
 */
@AgiCommand(command = "SPEECH RECOGNIZE")
public class SpeechRecognize implements Serializable {
  private static final long serialVersionUID = 6871133010336237807L;
  @Parameter(position = 2)
  private Integer offset;
  @Parameter(position = 1, optional = false)
  private Integer timeout;
  @Parameter(optional = false)
  private String prompt;

  /**
   * Creates a new SpeechRecognizeCommand object that plays the given prompt
   * and listens for for speech and DTMF.
   *
   * @param prompt the prompt to play.
   * @param timeout the maximum recognition time in milliseconds.
   */
  public SpeechRecognize(String prompt, Integer timeout) {
    this.prompt = prompt;
    this.timeout = timeout;
  }

  /**
   * Creates a new SpeechRecognizeCommand object that plays the given prompt
   * and listens for for speech and DTMF.
   *
   * @param prompt the prompt to play.
   * @param timeout the maximum recognition time in milliseconds.
   * @param offset the offset samples to skip when playing the prompt.
   */
  public SpeechRecognize(String prompt, Integer timeout, Integer offset) {
    this.prompt = prompt;
    this.timeout = timeout;
    this.offset = offset;
  }

  /**
   * Returns the offset samples to skip when playing the prompt.
   *
   * @return the offset samples to skip when playing the prompt.
   */
  public Integer getOffset() {
    return offset;
  }

  /**
   * Get prompt to play.
   *
   * @return prompt to play.
   */
  public String getPrompt() {
    return prompt;
  }

  /**
   * Returns the maximum recognition time in milliseconds.
   *
   * @return the maximum recognition time in milliseconds.
   */
  public Integer getTimeout() {
    return timeout;
  }

  /**
   * Sets the offset samples to skip when playing the prompt.
   *
   * @param offset the offset samples to skip when playing the prompt.
   */
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   * Set prompt to play.
   *
   * @param prompt prompt to play.
   */
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  /**
   * Sets the maximum recognition time in milliseconds.
   *
   * @param timeout the maximum recognition time in milliseconds,
   * or -1 for no timeout.
   */
  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }
}
