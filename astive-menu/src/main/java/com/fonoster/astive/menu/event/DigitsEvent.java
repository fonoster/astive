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
package com.fonoster.astive.menu.event;

import java.util.EventObject;

/**
 * A DigitsEvent is triggered when an user press the digits of a
 * {@link MenuItem} or {@link Menu}.
 *
 * @since 1.0.0
 * @see DigitsListener
 */
public class DigitsEvent extends EventObject {

    private String digits;

    /**
     * Creates a new DigitsEvent object.
     *
     * @param source the object that originated the event.
     * @param digits the digits pressed.
     */
    public DigitsEvent(final Object source, final String digits) {
        super(source);
        this.digits = digits;
    }

    /**
     * Returns the digits pressed.
     *
     * @return the digits pressed by the user.
     */
    public String getDigits() {
        return digits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[digits = " + getDigits() + "]";
    }
}
