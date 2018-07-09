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
import com.fonoster.astive.agi.annotation.BooleanChoose;
import com.fonoster.astive.agi.annotation.ParamConverter;
import com.fonoster.astive.agi.annotation.Parameter;

/**
 * Enables/Disables the music on hold generator. If <code>class</code> is
 * not specified, then the <code>default</code> music on hold class will be used.
 * Always returns 0.
 *
 * @since 1.0
 */
@AgiCommand(command = "SET MUSIC")
public class SetMusic implements Serializable {
  private static final long serialVersionUID = -5450998646107584670L;
  @Parameter(optional = false)
  @ParamConverter
  @BooleanChoose
  private Boolean on;
  @Parameter(position = 1)
  private String classStr;

  /**
   * Create a new SetMusic object.
   *
   * @param on <code>true</code> to turn music on hold on, or <code>false</code>
   * to turn music on hold off.
   */
  public SetMusic(Boolean on) {
    this.on = on;
  }

  /**
   * Create a new SetMusic object.
   *
   * @param on <code>true</code> to turn music on hold on, or <code>false</code>
   * to turn musicOnHold off.
   * @param classStr class to use as musicOnHold
   */
  public SetMusic(Boolean on, String classStr) {
    this.on = on;
    this.classStr = classStr;
  }

  /**
   * Get class for music on hold, or null for default.
   *
   * @return class for music on hold.
   */
  public String getClassStr() {
    return classStr;
  }

  /**
   * Returns whether music on hold is enable or disable.
   *
   * @return <code>true</code> if music on hold is on, or <code>false</code>
   * if is off.
   */
  public Boolean isOn() {
    return on;
  }

  /**
   * Set class for music on hold, or null for default.
   *
   * @param classStr class for music on hold.
   */
  public void setClassStr(String classStr) {
    this.classStr = classStr;
  }

  /**
   * Enables/Disables the music on hold.
   *
   * @param on <code>true</code> to enable music on hold, or <code>false</code>
   * to disable it.
   */
  public void setOn(Boolean on) {
    this.on = on;
  }
}
