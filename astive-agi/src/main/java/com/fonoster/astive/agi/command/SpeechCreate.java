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
 * Create a speech object to be used by the other Speech AGI commands.
 *
 * @since 1.0
 * @see SpeechDestroy
 */
@AgiCommand(command = "SPEECH CREATE")
public class SpeechCreate implements Serializable {
  private static final long serialVersionUID = -8343359487319664122L;
  @Parameter(optional = false)
  private String engine;

  /**
   * Create a new SpeechCreate object.
   *
   * @param engine the name of the speech engine to use for subsequent
   * Speech AGI commands.
   */
  public SpeechCreate(String engine) {
    this.engine = engine;
  }

  /**
   * Get engine name.
   *
   * @return engine name.
   */
  public String getEngine() {
    return engine;
  }

  /**
   * Set engine name.
   *
   * @param engine engine name.
   */
  public void setEngine(String engine) {
    this.engine = engine;
  }
}
