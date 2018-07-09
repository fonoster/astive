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
import com.fonoster.astive.agi.annotation.ParamConverter;
import com.fonoster.astive.agi.annotation.Parameter;
import com.fonoster.astive.agi.annotation.Separator;

/**
 * Cause the channel to execute the specified dialplan subroutine, returning
 * to the dialplan with execution of a Return().
 *
 * @since 1.0
 */
@AgiCommand(command = "GOSUB")
public class GoSub implements Serializable {
  private static final long serialVersionUID = -9201675010003633043L;
  @Parameter(optional = false)
  private String context;
  @Parameter(position = 1, optional = false)
  private String extension;
  @Parameter(position = 2, optional = false)
  private String priority;
  @Parameter(position = 3)
  @ParamConverter
  @Separator
  private String[] arguments;

  /**
   * Create a new GoSub object.
   *
   * @param context context of the called subroutine.
   * @param extension extension in the called context.
   * @param priority priority of the called extension.
   */
  public GoSub(String context, String extension, String priority) {
    this.context = context;
    this.extension = extension;
    this.priority = priority;
  }

  /**
   * Create a new GoSub object.
   *
   * @param context context of the called subroutine.
   * @param extension extension in the called context.
   * @param priority priority of the called extension.
   * @param arguments optional list of arguments to be passed to the
   * subroutine.
   */
  public GoSub(String context, String extension, String priority, String... arguments) {
    this.context = context;
    this.extension = extension;
    this.priority = priority;
    this.arguments = arguments;
  }

  /**
   * Get subroutine arguments.
   *
   * @return subroutine arguments.
   */
  public String[] getArguments() {
    return arguments;
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
   * Get extension.
   *
   * @return extension.
   */
  public String getExtension() {
    return extension;
  }

  /**
   * Get priority.
   *
   * @return priority.
   */
  public String getPriority() {
    return priority;
  }

  /**
   * Set subroutine arguments.
   *
   * @param arguments subroutine arguments.
   */
  public void setArguments(String[] arguments) {
    this.arguments = arguments;
  }

  /**
   * Set context.
   *
   * @param context context.
   */
  public void setContext(String context) {
    this.context = context;
  }

  /**
   * Set extension.
   *
   * @param extension extension.
   */
  public void setExtension(String extension) {
    this.extension = extension;
  }

  /**
   * Set priority.
   *
   * @param priority priority.
   */
  public void setPriority(String priority) {
    this.priority = priority;
  }
}
