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
 * A TimeoutEvent is triggered when the maximum time(as define in {@link Menu})
 * waiting for user iteration is reached.
 *
 * @since 1.0.0
 * @see TimeoutListener
 */
public class TimeoutEvent extends DigitsEvent {

    private int timeout;

    /**
     * Create a new TimeoutEvent object.
     *
     * @param source the object that originated the event.
     * @param digits the digits pressed.
     * @param maxTimeout maximum time waiting for user iteration.
     */
    public TimeoutEvent(final Object source, final String digits, final int timeout) {
        super(source, digits);
        this.timeout = timeout;
    }

    /**
     * Returns maximum time waiting for user iteration.
     *
     * @return maximum time (as define in the {@link Menu}) waiting for user
     * iteration.
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[digits = " + getDigits() + ", timeout = " + getTimeout() +"]" ;
    }
}
