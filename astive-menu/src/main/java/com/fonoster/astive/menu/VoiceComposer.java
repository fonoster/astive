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

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import com.fonoster.astive.agi.command.SayAlpha;
import com.fonoster.astive.agi.command.SayDate;
import com.fonoster.astive.agi.command.SayDatetime;
import com.fonoster.astive.agi.command.SayDigits;
import com.fonoster.astive.agi.command.SayNumber;
import com.fonoster.astive.agi.command.SayPhonetic;
import com.fonoster.astive.agi.command.SayTime;
import com.fonoster.astive.agi.command.StreamFile;

/**
 * Helps build voice compositions - which is a group of commands executed in
 * sequence.
 *
 * <p>The use of the functions {@link #withTimeZone(TimeZone)},
 * {@link #withFormat(String)} and {@link #withEscapeDigits(String)} only affect
 * subsequent commands.
 *
 * @since 1.0
 * @see VoiceComposition
 * @see MenuItem
 */
public class VoiceComposer {
    private static ArrayList<Object> commands;
    private static String escapeDigits;
    private static String format;
    private static TimeZone timeZone;
    private static VoiceComposer instance = new VoiceComposer();

    /**
     * Creates a new {@link VoiceComposition}.
     *
     * @return the voice composition with the sequence of commands.
     */
    public static VoiceComposition create() {
        VoiceComposition vc = new VoiceComposition(VoiceComposer.commands);
        VoiceComposer.reset();

        return vc;
    }

    /**
     * Appends a silence to the sequence.
     *
     * @param silence time in milliseconds for silence.
     * @return a reference to this object.
     */
    public static VoiceComposer addSilence(int silence) {
        if (hasEscapeDigits()) {
            commands.add(new StreamFile("silence/" + silence, VoiceComposer.escapeDigits));
        } else {
            commands.add(new StreamFile("silence/" + silence));
        }

        return instance;
    }

    /**
     * Appends a command {@link SayAlpha} to the sequence.
     *
     * @param text the text to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayAlpha(final String text) {
        if (hasEscapeDigits()) {
            commands.add(new SayAlpha(text, VoiceComposer.escapeDigits));
        } else {
            commands.add(new SayAlpha(text));
        }

        return instance;
    }

    /**
     * Appends a command {@link SayDate} to the sequence.
     *
     * @param date the date to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayDate(final Date date) {
        if (hasEscapeDigits()) {
            commands.add(new SayDate(date, VoiceComposer.escapeDigits));
        } else {
            commands.add(new SayDate(date));
        }

        return instance;
    }

    /**
     * Appends a command {@link SayDatetime} to the sequence.
     *
     * @param datetime the datetime to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayDatetime(final Date datetime) {

        SayDatetime command = new SayDatetime(datetime);

        if (hasEscapeDigits()) {
            command.setEscapeDigits(VoiceComposer.escapeDigits);
        }

        if (hasFormat()) {
            command.setFormat(VoiceComposer.format);
        }

        if (hasTimeZone()) {
            command.setTimeZone(VoiceComposer.timeZone);
        }

        return instance;
    }

    /**
     * Appends a command {@link SayDigits} to the sequence.
     *
     * @param digits the digits to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayDigits(final String digits) {
        if (hasEscapeDigits()) {
            commands.add(new SayDigits(digits, VoiceComposer.escapeDigits));
        } else {
            commands.add(new SayDigits(digits));
        }

        return instance;
    }

    /**
     * Appends a command {@link SayNumber} to the sequence.
     *
     * @param number the number to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayNumber(int number) {
        if (hasEscapeDigits()) {
            commands.add(new SayNumber(number, VoiceComposer.escapeDigits));
        } else {
            commands.add(new SayNumber(number));
        }

        return instance;
    }

    /**
     * Appends a command {@link SayPhonetic} to the sequence.
     *
     * @param text the text to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayPhonetic(String text) {
        if (hasEscapeDigits()) {
            commands.add(new SayPhonetic(text, VoiceComposer.escapeDigits));
        } else {
            commands.add(new SayPhonetic(text));
        }

        return instance;
    }

    /**
     * Appends a command {@link SayTime} to the sequence.
     *
     * @param time the time to say.
     * @return a reference to this object.
     */
    public static VoiceComposer sayTime(Date time) {
        if (hasEscapeDigits()) {
            commands.add(new SayTime(time, VoiceComposer.escapeDigits));
        } else {
            commands.add(new SayTime(time));
        }

        return instance;
    }

    /**
     * Appends a command {@link StreamFile} to the sequence.
     *
     * @param file the file extension must not be included in the filename.
     * @return a reference to this object.
     */
    public static VoiceComposer streamFile(String file) {
        if (hasEscapeDigits()) {
            commands.add(new StreamFile(file, VoiceComposer.escapeDigits));
        } else {
            commands.add(new StreamFile(file));
        }

        return instance;
    }

    /**
     * Adds escape digits to the subsequent commands.
     *
     * @param escapeDigits allow users to finish stream.
     * @return a reference to this object.
     */
    public static VoiceComposer withEscapeDigits(String escapeDigits) {
        VoiceComposer.escapeDigits = escapeDigits;

        return instance;
    }

    /**
     * Adds format to the subsequent commands. Not all commands use this
     * parameter.
     *
     * @param format the format
     * @return a reference to this object.
     */
    public static VoiceComposer withFormat(String format) {
        VoiceComposer.format = format;

        return instance;
    }

    /**
     * Adds time zone to the subsequent commands. Not all commands use this
     * parameter.
     *
     * @param timeZone the time zone.
     * @return a reference to this object.
     */
    public static VoiceComposer withTimeZone(TimeZone timeZone) {
        VoiceComposer.timeZone = timeZone;

        return instance;
    }

    private static boolean hasEscapeDigits() {
        return (escapeDigits != null) && (escapeDigits.length() != 0);
    }

    private static boolean hasFormat() {
        return (VoiceComposer.format != null);
    }

    private static boolean hasTimeZone() {
        return (VoiceComposer.timeZone != null);
    }

    private static void reset() {
        VoiceComposer.escapeDigits = "";
        VoiceComposer.commands = new ArrayList<Object>();
        VoiceComposer.format = "";
        VoiceComposer.timeZone = null;
    }

    private VoiceComposer() {
        commands = new ArrayList<Object>();
    }
}
