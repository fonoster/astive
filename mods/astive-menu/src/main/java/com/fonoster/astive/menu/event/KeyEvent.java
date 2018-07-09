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
import com.fonoster.astive.menu.Digit;

/**
 * A KeyEvent is triggered for every key pressed by a <code>Subject</code>.
 *
 * @since 1.0.0
 * @see KeyListener
 * @see DigitsEvent
 */
public class KeyEvent extends EventObject {

    private Digit key;

    /**
     * Creates a new KeyEvent object.
     *
     * @param source the object that originated the event.
     * @param key the key pressed by a <code>Subject</code>.
     */
    public KeyEvent(final Object source, final Digit key) {
        super(source);
        this.source = source;
        this.key = key;
    }

    /**
     * Returns the key pressed by <code>Subject</code>.
     *
     * @return the key pressed by a <code>Subject</code>.
     */
    public Digit getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[key = " + getKey() + "]";
    }
}
