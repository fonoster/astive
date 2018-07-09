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

/**
 * A MaxTimeoutEvent is triggered if the <code>Subject</code> skip iteration
 * with the {@link Menu} as many time as {@link Menu#getMaxTimeout()}.
 *
 * @since 1.0.0
 * @see MaxTimeoutListener
 * @see DigitsEvent
 */
public class MaxTimeoutEvent extends DigitsEvent {

    private int maxTimeout;

    /**
     * Create a new MaxTimeoutEvent object.
     *
     * @param source the object that originated the event.
     * @param digits the digits pressed.
     * @param maxTimeout maximum time that the <code>Subject</code> is allow to
     * skip menu iteration.
     */
    public MaxTimeoutEvent(final Object source, final String digits,
            final int maxTimeout) {
        super(source, digits);
        this.maxTimeout = maxTimeout;
    }

    /**
     * Returns the maximum time that <code>Subject</code> can skip iteration
     * with the menu.
     *
     * @return the maximum time that the menu allows the <code>Subject</code> to
     * avoid iteration.
     */
    public int getMaxTimeout() {
        return maxTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[digits = " + getDigits() + ", maxTimeout = " + getMaxTimeout() + "]";
    }
}
