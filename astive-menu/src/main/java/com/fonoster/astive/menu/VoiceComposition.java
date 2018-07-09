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

import java.util.Collections;
import java.util.List;

/**
 * Registers multiple commands that will be sent in sequence. This
 * class needs to be use in conjunction with {@link Menu}.
 *
 * @since 1.0
 */
public class VoiceComposition {
    private List<Object> commands;

    /**
     * Create a new VoiceComposition object.
     *
     * @param commands to be execute in sequence.
     */
    public VoiceComposition(List<Object> commands) {
        this.commands = commands;
    }

    /**
     * @return a sequence of commands to be executed.
     */
    public List<Object> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    /**
     * @param commands to be sent in sequence to the client.
     */
    public void setCommands(List<Object> commands) {
        this.commands = commands;
    }
}
