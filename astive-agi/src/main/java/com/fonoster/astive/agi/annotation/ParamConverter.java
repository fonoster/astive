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
import com.fonoster.astive.agi.StringConverter;

/**
 * Define the converter to be use in a parameter. For instance, arguments
 * in a array must be converted into a comma separated values
 * before you sent to Asterisk. Example:
 *
 * <p> The array <code>arg[] = {"a", "b", "c"}</code> will be converted in
 * <code>"a,b,c"</code>.
 *
 * <p>The default converter is {@link StringConverter}. However, for future
 * commands new converters can be define extending the class {@link Converter}.
 *
 * @since 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = java.lang.annotation.ElementType.FIELD)
public @interface ParamConverter {
  /**
   * Get the converter to be use in a parameter. The default converter is
   * {@link StringConverter}. However, for new commands new converters
   * can be define.
   *
   * @return converter to be use in a parameter.
   */
  public Class<?> converter() default StringConverter.class;
}
