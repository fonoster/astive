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
 * Sets the context for continuation upon exiting the application.
 *
 * @since 1.0
 */
@AgiCommand(command = "SET CONTEXT")
public class SetContext implements Serializable {
  private static final long serialVersionUID = -518908570483537151L;
  @Parameter(optional = false)
  private String context;

  /**
   * Create a new SetContext object.
   *
   * @param context channel context
   */
  public SetContext(String context) {
    this.context = context;
  }

  /**
   * Get context.
   *
   * @return context.
   */
  public String getContext() {
    return context;
  }

  /**
   * Set desired context for channel.
   *
   * @param context desired context for channel.
   */
  public void setContext(String context) {
    this.context = context;
  }
}
