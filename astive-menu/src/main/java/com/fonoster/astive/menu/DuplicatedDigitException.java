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
package com.fonoster.astive.menu;

import com.fonoster.astive.util.AppLocale;

/**
 * This exception is trigger when user attempts to insert more than one menu or
 * menu item into the same menu level using the same </code>digit</code>.
 *
 * @since 1.0
 */
public class DuplicatedDigitException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final String MSG = AppLocale
            .getI18n("errorDuplicatedKeyInMenu");

    /**
     * Creates a new instance of DuplicatedDigitException.
     */
    public DuplicatedDigitException() {
        super(DuplicatedDigitException.MSG);
    }
}
