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
package com.fonoster.astive.agi.command;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;
import com.fonoster.astive.agi.annotation.AgiCommand;
import com.fonoster.astive.agi.annotation.ParamConverter;
import com.fonoster.astive.agi.annotation.Parameter;

/**
 * Say a given time, returning early if any of the given DTMF digits are received
 * on the channel.
 *
 * <p>Returns 0 if playback completes without a digit being pressed, or the
 * ASCII numerical value of the digit if one was pressed or -1 on error/hangup.
 *
 * @since 1.0
 */
@AgiCommand(command = "SAY DATETIME")
public class SayDatetime implements Serializable {
  private static final long serialVersionUID = 767818324021690725L;
  public static final String DEFAULT_FORMAT = "ABdY 'digits/at' IMp";
  @Parameter(optional = false)
  @ParamConverter
  private Date date;
  @Parameter(position = 1, optional = false)
  private String escapeDigits;
  @Parameter(position = 2)
  private String format;
  @Parameter(position = 3)
  @ParamConverter
  private TimeZone timeZone;

  /**
   * Create a new SayDatetime object with the date to say.
   *
   * @param date datetime to say.
   */
  public SayDatetime(Date date) {
    this.date = date;
    this.escapeDigits = "";
  }

  /**
   * Create a new SayDate object with the date to say and escape digits.
   *
   * @param date datetime to say.
   * @param escapeDigits escape digits.
   */
  public SayDatetime(Date date, String escapeDigits) {
    this.date = date;
    this.escapeDigits = escapeDigits;
  }

  /**
   * Create a new SayDate object with the date to say and escape digits and
   * specific format.
   *
   * @param date datetime to say.
   * @param escapeDigits escape digits.
   * @param format format to say date. By default Asterisk use
   * "ABdY 'digits/at' IMp".
   */
  public SayDatetime(Date date, String escapeDigits, String format) {
    this.date = date;
    this.escapeDigits = escapeDigits;
    this.format = format;
  }

  /**
   * Create a new SayDate object with the date to say and escape digits and
   * specific format and timezone.
   *
   * @param date datetime to say.
   * @param escapeDigits escape digits.
   * @param format format to say date. By default Asterisk use
   * "ABdY 'digits/at' IMp".
   * @param timeZone Timezone.
   */
  public SayDatetime(Date date, String escapeDigits, String format, TimeZone timeZone) {
    this.date = date;
    this.escapeDigits = escapeDigits;
    this.format = format;
    this.timeZone = timeZone;
  }

  /**
   * Get datetime to say.
   *
   * @return datetime to say.
   */
  public Date getDate() {
    return date;
  }

  /**
   * Get the digits used to interrupt the audio.
   *
   * @return digits used to interrupt the audio.
   */
  public String getEscapeDigits() {
    return escapeDigits;
  }

  /**
   * Get datetime format.
   *
   * @return datetime format.
   */
  public String getFormat() {
    return format;
  }

  /**
   * Get timezone for datetime.
   *
   * @return timezone for datetime.
   */
  public TimeZone getTimeZone() {
    return timeZone;
  }

  /**
   * Set datetime to say.
   *
   * @param date
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * Set the digits to be use to interrupt the audio.
   *
   * @param escapeDigits digits to be use to interrupt the audio.
   */
  public void setEscapeDigits(String escapeDigits) {
    this.escapeDigits = escapeDigits;
  }

  /**
   * Set datetime format.
   *
   * @param format datetime format.
   */
  public void setFormat(String format) {
    this.format = format;
  }

  /**
   * Set timezone for datetime.
   *
   * @param timeZone timezone for datetime.
   */
  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }
}
