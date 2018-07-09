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
 * Hangs up the specified channel. If no channel name is given, hangs up the
 * current <code>channel</code>.
 *
 * @since 1.0
 */
@AgiCommand(command = "HANGUP")
public class Hangup implements Serializable {
  private static final long serialVersionUID = -7294601691881839635L;
  @Parameter(optional = false)
  private String channel;

  /**
   * Create a new Hangup object.
   */
  public Hangup() {
  }

  /**
   * Create a new Hangup object with channel.
   *
   * @param channel channel name to hangup or null for current channel.
   */
  public Hangup(String channel) {
    this.channel = channel;
  }

  /**
   * Channel name or null for current channel.
   *
   * @return channel name.
   */
  public String getChannel() {
    return channel;
  }

  /**
   * Set channel name. Null for current channel.
   *
   * @param channel channel name.
   */
  public void setChannel(String channel) {
    this.channel = channel;
  }
}
