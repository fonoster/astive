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
 * Set an engine-specific setting in a key/value format.
 *
 * @since 1.0
 */
@AgiCommand(command = "SPEECH SET")
public class SpeechSet implements Serializable { 
  private static final long serialVersionUID = -1479993108651095058L;
  @Parameter(optional = false)
  private String name;
  @Parameter(position = 1, optional = false)
  private String value;

  /**
   * Create a new SpeechSet object.
   *
   * @param name property name.
   * @param value propert value.
   */
  public SpeechSet(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Get property name.
   *
   * @return property name.
   */
  public String getName() {
    return name;
  }

  /**
   * Get property value.
   *
   * @return property value.
   */
  public String getValue() {
    return value;
  }

  /**
   * Set property name.
   *
   * @param name property name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set property value.
   *
   * @param value property value.
   */
  public void setValue(String value) {
    this.value = value;
  }
}
