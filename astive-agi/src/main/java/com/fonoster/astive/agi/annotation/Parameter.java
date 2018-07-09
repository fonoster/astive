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
package com.fonoster.astive.agi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation use to mark an field as a command parameter.
 *
 * <p>Keep in mind, that if a parameter is marked as optional and set as null
 * any subsequent parameter will be ignored.
 *
 * @since 1.0
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = java.lang.annotation.ElementType.FIELD)
public @interface Parameter {
  /**
   * Get the position of this parameter in a command. The position should be
   * repeated or an AgiException will be thrown.
   *
   * @return position of this parameter in a command. Default position is 0.
   */
  public int position() default 0;
  /**
   * Get the prefix of the parameter or and empty string is none is defined.
   *
   * @return prefix of the parameter.
   */
  public String prefix() default "";
  /**
   * Returns whether or not a parameter is optional.
   *
   * @return true if parameter is option or false otherwise.
   */
  public boolean optional() default true;
}
