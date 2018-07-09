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
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation use to define the value expected by Asterisk on a boolean value.
 * For instance the command {@link RecordFile} expect a "BEEP" when the parameter
 * beep is set to true.
 *
 * <p>This annotation should be used in combination with {@link Parameter} and
 * {@link ParamConverter}.
 *
 * @since 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = java.lang.annotation.ElementType.FIELD)
public @interface BooleanChoose {
  /**
   * Get string value for a true.
   *
   * @return string value for a true. The default value is "ON".
   */
  public String valueOnTrue() default "ON";
  /**
   * Get string value for a false.
   *
   * @return string value for a false. The default value is "OFF".
   */
  public String valueOnFalse() default "OFF";
}
