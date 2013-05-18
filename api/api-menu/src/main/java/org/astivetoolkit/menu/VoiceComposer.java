/*
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.menu;

import org.astivetoolkit.agi.command.SayAlpha;
import org.astivetoolkit.agi.command.SayPhonetic;
import org.astivetoolkit.agi.command.StreamFile;
import org.astivetoolkit.agi.command.SayDate;
import org.astivetoolkit.agi.command.SayNumber;
import org.astivetoolkit.agi.command.SayDigits;
import org.astivetoolkit.agi.command.SayTime;
import org.astivetoolkit.agi.command.SayDatetime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @since 1.0.0
 */
public class VoiceComposer {
  private static ArrayList<Object> commands;
  private static String escapeDigits;
  private static String format;
  private static TimeZone timeZone;
  private static VoiceComposer instance = new VoiceComposer();
  private static int offset;

  // hide the constructor
  private VoiceComposer() {
    commands = new ArrayList<Object>();
  }

  /**
   * DOCUMENT ME!
   *
   * @param silence DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static VoiceComposition create() {
    VoiceComposition vc = new VoiceComposition(VoiceComposer.commands);
    VoiceComposer.reset();

    return vc;
  }

  private static boolean hasEscapeDigits() {
    return (escapeDigits != null) && (escapeDigits.length() != 0x0);
  }

  private static boolean hasFormat() {
    return (VoiceComposer.format != null) ;
  }

  private static boolean hasOffset() {
     return (VoiceComposer.offset == 0x0);
  }

  private static boolean hasTimeZone() {
    return (VoiceComposer.timeZone != null);    
  }

  private static void reset() {
    VoiceComposer.escapeDigits = "";
    VoiceComposer.commands = new ArrayList<Object>();
    VoiceComposer.format = "";
    VoiceComposer.timeZone = null;
    VoiceComposer.offset = 0x0;
  }

  /**
   * DOCUMENT ME!
   *
   * @param text DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param date DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param datetime DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static VoiceComposer sayDatetime(final Date datetime) {
    if (hasEscapeDigits()) {
      commands.add(new SayDatetime(datetime, VoiceComposer.escapeDigits));
    } else {
      commands.add(new SayDatetime(datetime));
    }

    return instance;
  }

  /**
   * DOCUMENT ME!
   *
   * @param digits DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param number DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param text DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param time DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param file DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @param escapeDigits DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static VoiceComposer withEscapeDigits(String escapeDigits) {
    VoiceComposer.escapeDigits = escapeDigits;

    return instance;
  }

  /**
   * DOCUMENT ME!
   *
   * @param format DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static VoiceComposer withFormat(String format) {
    VoiceComposer.format = format;

    return instance;
  }

  /**
   * DOCUMENT ME!
   *
   * @param offset DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static VoiceComposer withOffset(int offset) {
    VoiceComposer.offset = offset;

    return instance;
  }

  /**
   * DOCUMENT ME!
   *
   * @param timeZone DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static VoiceComposer withTimeZone(TimeZone timeZone) {
    VoiceComposer.timeZone = timeZone;

    return instance;
  }
}
