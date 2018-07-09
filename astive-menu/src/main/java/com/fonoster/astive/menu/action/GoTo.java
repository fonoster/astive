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
package com.fonoster.astive.menu.action;

import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.AgiResponse;
import com.fonoster.astive.menu.Menu;
import com.fonoster.astive.menu.MenuNavigator;

/**
 * Use this action to jump from one {@link Menu} to another.
 *
 * @since 1.0
 * @see GoExt
 */
public class GoTo implements Action {
    private AgiResponse agiResponse;
    private Menu menu;

    /**
     * Creates a new GoTo object.
     *
     * @param agiResponse agi response.
     * @param menu menu where to jump.
     */
    public GoTo(AgiResponse agiResponse, Menu menu) {
        this.agiResponse = agiResponse;
        this.menu = menu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doAction() {
        try {
            MenuNavigator e = new MenuNavigator(agiResponse);
            e.run(menu);
        } catch (AgiException ex) {
            // Manage this exception
        }
    }
}
