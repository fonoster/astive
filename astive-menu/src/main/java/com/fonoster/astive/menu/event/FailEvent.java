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
 * A FailEvent is triggered when a <code>Subject</code> attempts to enter an
 * invalid menu option.
 *
 * @since 1.0.0
 * @see FailListener
 * @see DigitsEvent
 */
public class FailEvent extends DigitsEvent {

    private int failCount;

    /**
     * Creates a new FailEvent object.
     *
     * @param source the object that originated the event.
     * @param digits the digits pressed.
     * @param failCount the amount of times the <code>Subject</code> has pressed
     * an invalid menu option.
     */
    public FailEvent(final Object source, final String digits, final int failCount) {
        super(source, digits);
        this.failCount = failCount;
    }

    /**
     * Returns the fail count.
     *
     * @return the amount of times the <code>Subject</code> has pressed an
     * invalid option.
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[digits = " + getDigits() + ", failCount = " + getFailCount() + "]";
    }
}
