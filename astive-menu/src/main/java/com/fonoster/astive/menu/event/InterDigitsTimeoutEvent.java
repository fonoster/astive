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
 * The InterDigitsTimeoutEvent is triggered when the <code>Subject</code> fails
 * to press the next key on time.
 *
 * @since 1.0.0
 * @see InterDigitsTimeoutListener
 * @see DigitsEvent
 */
public class InterDigitsTimeoutEvent extends DigitsEvent {

    private int timeout;

    /**
     * Create a new InterDigitsTimeoutEvent object.
     *
     * @param source the object that originated the event.
     * @param digits the digits pressed.
     * @param timeout to prevent this event the <code>Subject</code>(user)'s
     * should press the next digit quickly.
     */
    public InterDigitsTimeoutEvent(final Object source, final String digits, final int timeout) {
        super(source, digits);
        this.timeout = timeout;
    }

    /**
     * Returns the timeout
     *
     * @return to prevent this event the <code>Subject</code>(user)'s should
     * press the next digit quickly.
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[digits = " + getDigits() + ", timeout = " + getTimeout() + "]";
    }
}
