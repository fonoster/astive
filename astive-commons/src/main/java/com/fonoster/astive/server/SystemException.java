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
package com.fonoster.astive.server;

/**
 * This exception is used whenever a critical action is perform by the system.
 *
 * @since 1.0
 */
public class SystemException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SystemException object with null as its detail message.
     */
    public SystemException() {
        super();
    }

    /**
     * Creates a new SystemException object with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by
     * the <code>Throwable.getMessage()</code> method).
     */
    public SystemException(final String message) {
        super(message);
    }
}
