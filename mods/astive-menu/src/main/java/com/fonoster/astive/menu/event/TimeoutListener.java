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
 * The listener interface for receiving {@link TimeoutListener}.
 *
 * @since 1.0.0
 * @see TimeoutEvent
 */
public interface TimeoutListener {

    /**
     * Callback triggered when the maximum time to press the digits is reached.
     *
     * @param evt the event submitted.
     */
    abstract void timeoutPerform(TimeoutEvent evt);
}
