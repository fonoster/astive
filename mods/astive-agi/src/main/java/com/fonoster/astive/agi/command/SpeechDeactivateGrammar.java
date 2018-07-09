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
 * Deactivates the specified grammar on the speech object.
 *
 * @since 1.0
 */
@AgiCommand(command = "SPEECH DEACTIVATE GRAMMAR")
public class SpeechDeactivateGrammar implements Serializable {
  private static final long serialVersionUID = 6890496911801730598L;
  @Parameter
  private String name;

  /**
   * Create a new SpeechDeactivateGrammar object.
   *
   * @param name grammar name.
   */
  public SpeechDeactivateGrammar(String name) {
    this.name = name;
  }

  /**
   * Get grammar name.
   *
   * @return grammar name.
   */
  public String getName() {
    return name;
  }

  /**
   * Set grammar name.
   *
   * @param name grammar name.
   */
  public void setName(String name) {
    this.name = name;
  }
}
