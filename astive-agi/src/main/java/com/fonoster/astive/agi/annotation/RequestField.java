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
 * Used by AgiRequest to identify Agi fields.
 *
 * @since 1.0
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = java.lang.annotation.ElementType.FIELD)
public @interface RequestField {
  public String value();
}
