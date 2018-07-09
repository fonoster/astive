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

import com.fonoster.astive.menu.MenuItem;

/**
 * A PositionChangeEvent is triggered when the {@link MenuNavigator} move from
 * one {@link MenuItem} to another.
 *
 * @since 1.0.0
 * @see DigitsEvent
 * @see PositionChangeListener
 */
public class PositionChangeEvent extends DigitsEvent {

    private Object newObject;
    private int position;

    /**
     * Create new PositionChangeEvent.
     *
     * @param source the object that originated the event.
     * @param newObject then new {@link MenuItem}.
     * @param position the position of the new option in the menu.
     */
    public PositionChangeEvent(final Object source, final Object newObject,
            final int position) {
        super(source, ((MenuItem) source).getDigits());
        this.newObject = newObject;
        this.position = position;
    }

    /**
     * Returns the current {@link MenuItem}.
     *
     * @return the new menu item.
     */
    public Object getNewObject() {
        return newObject;
    }

    /**
     * Returns the new position.
     *
     * @return the position of the new option in the menu.
     */
    public int getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[position = " + getPosition() + "]";
    }
}
