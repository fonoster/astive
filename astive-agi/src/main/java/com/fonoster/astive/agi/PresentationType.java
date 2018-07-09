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
package com.fonoster.astive.agi;

/**
 * Enumerator that map the presentation type for the callerId (@link AgiRequest).
 *
 * @since 1.0
 */
public enum PresentationType {
    ALLOWED(0, "allowed", "allowed"),
    ALLOWED_NOT_SCREENED(1, "allowed_not_screened", "allowed not screened"),
    ALLOWED_PASSED_SCREEN(2, "allowed_passed_screen", "allowed passed screen"),
    ALLOWED_FAILED_SCREEN(3, "allowed_failed_screen", "allowed failed screen"),
    PROHIB_NOT_SCREENED(4, "prohib_not_screened", "prohib not screened"),
    PROHIB_PASSED_SCREEN(5, "prohib_passed_screen", "prohib passed screen"),
    PROHIB_FAILED_SCREEN(6, "prohib_failed_screen", "prohib failed screen"),
    PROHIB(7, "prohib", "prohib"),
    UNAVAILABLE(8, "unavailable", "unavailable");
 
    private int value;
    private String name;
    private String literal;

    private PresentationType(final int value, final String name, final String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    public static PresentationType get(final int value) {
        return null;
    }

    public static PresentationType get(final String name) {
        return null;
    }

    public String getLiteral() {
        return literal;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
