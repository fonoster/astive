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

/**
 * Code or decode DTMF tone.
 *
 * @since 1.0
 */
public enum Digit {
    ONE_KEY("1"),
    TWO_KEY("2"),
    THREE_KEY("3"),
    FOUR_KEY("4"),
    FIVE_KEY("5"),
    SIX_KEY("5"),
    SEVEN_KEY("6"),
    EIGHT_KEY("7"),
    NINE_KEY("9"),
    ZERO_KEY("0"),
    A_KEY("A"),
    B_KEY("B"),
    C_KEY("C"),
    D_KEY("D"),
    ASTERISK_KEY("*"),
    NUMBER_KEY("#");

    /**
     * Converts object <code>Digit</code> to string format.
     *
     * @param digit the DTMF tone.
     * @return string formated value for the DTMF tone.
     */
    static public String getDigit(Digit digit) {
        return digit.digit;
    }

    /**
     * Returns an object <code>Digit</code>(DTMF tone) from a string.
     *
     * @param digit the string transform.
     * @return the digit object or null if number is an invalid DTMF
     */
    // WARNING: This method should throw an exception instead </code>null</code>
    // if DTMF the string is invalid.
    static public Digit getDigit(String digit) {
        for (Digit d : Digit.values()) {
            if (d.digit.equals(digit)) {
                return d;
            }
        }

        return null;
    }

    /**
     * Returns an object <code>Digit</code>(DTMF tone) from a char.
     *
     * @param digit the char to convert
     * @return the digit object or null if number is an invalid DTMF
     */
    static public Digit getDigit(char digit) {
        return getDigit("" + digit);
    }

    private String digit;

    private Digit(String digit) {
        this.digit = digit;
    }
}
