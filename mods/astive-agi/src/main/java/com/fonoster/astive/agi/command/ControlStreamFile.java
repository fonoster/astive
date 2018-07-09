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
import com.fonoster.astive.agi.annotation.AgiCommand;
import com.fonoster.astive.agi.annotation.Parameter;

/**
 * Send the given file, allowing playback to be controlled by the given digits,
 * if any.
 *
 * <p>Returns '0' if playback completes without a digit being pressed, or the ASCII
 * numerical value of the digit if one was pressed, or '-1' on error or if the
 * <code>channel</code> was disconnected.
 *
 * @since 1.0
 * @see StreamFile
 */
@AgiCommand(command = "CONTROL STREAM FILE")
public class ControlStreamFile implements Serializable {
  private static final long serialVersionUID = 3190091457969146433L;
  // Silence time in milliseconds after audio finished. The default value
  // is '0' (no silence).
  @Parameter(position = 2, optional = false)
  private Integer offset;
  @Parameter(position = 1, optional = false)
  private String escapeDigits;
  @Parameter(optional = false)
  private String file;
  @Parameter(position = 3)
  private char forwardDigit;
  @Parameter(position = 5)
  private char pauseDigit;
  @Parameter(position = 4)
  private char rewindDigit;

  /**
   * Create a new ControlStreamFile object for a file. Audio can not be
   * interrupted when use this constructor.
   *
   * @param file audio to play.
   */
  public ControlStreamFile(final String file) {
    this.file = file;
    escapeDigits = "";
    offset = -1;
  }

  /**
   * Create a new ControlStreamFile object that can interrupt the audio by press
   * a digit present in escapeDigits param.
   *
   * @param file audio to play.
   * @param escapeDigits digits to interrupt the audio.
   */
  public ControlStreamFile(final String file, final String escapeDigits) {
    this.file = file;
    this.escapeDigits = escapeDigits;
    offset = -1;
  }

  /**
   * Create a new ControlStreamFile object that can interrupt the audio by
   * press a digit present in escapeDigits. The offset(in milliseconds)
   * is provided then the audio will seek to sample offset before play starts.
   *
   * @param file audio to play.
   * @param escapeDigits digits to interrupt the audio.
   * @param offset is provided then the audio will seek to sample offset before play starts.
   */
  public ControlStreamFile(final String file, final String escapeDigits, final Integer offset) {
    this.file = file;
    this.escapeDigits = escapeDigits;
    this.offset = offset;
  }

  /**
   * Create a new ControlStreamFile object that can interrupt. Can interrupt
   * the audio by press a digit present in escapeDigits. The offset(in milliseconds)
   * indicate a silence time after audio finished. Also, digits can be use to
   * control the stream.
   *
   * @param file audio to play.
   * @param escapeDigits digits to interrupt the audio.
   * @param offset is provided then the audio will seek to sample offset before play starts.
   * @param forwardDigit move audio forward.
   * @param rewindDigit repeat the audio.
   * @param pauseDigit stop the steam.
   */
  public ControlStreamFile(final String file, final String escapeDigits, final Integer offset, final char forwardDigit,
                          final char rewindDigit, final char pauseDigit) {
    this.file = file;
    this.escapeDigits = escapeDigits;
    this.offset = offset;
    this.forwardDigit = forwardDigit;
    this.rewindDigit = rewindDigit;
    this.pauseDigit = pauseDigit;
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
   * Get audio to be send to <code>channel</code>.
   *
   * @return audio to be send to <code>channel</code>.
   */
  public String getFile() {
    return file;
  }

  /**
   * Get the digit that move audio forward.
   *
   * @return digit that move audio forward or null if none is provided.
   */
  public char getForwardDigit() {
    return forwardDigit;
  }

  /**
   * Get time in milliseconds for silence after audio finished.
   *
   * @return time in milliseconds for silence after audio finished.
   */
  public Integer getOffset() {
    return offset;
  }

  /**
   * Get the digit to pause audio.
   *
   * @return digit to pause audio.
   */
  public char getPauseDigit() {
    return pauseDigit;
  }

  /**
   * Get the digit to pause audio.
   *
   * @return digit to pause audio.
   */
  public char getRewindDigit() {
    return rewindDigit;
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
   * Set audio to be send to <code>channel</code>. Keep in mind that
   * the audio must not contain any extension.
   *
   * @param file audio to be send to <code>channel</code>
   */
  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Set the digit that move audio forward.
   *
   * @param forwardDigit digit that move audio forward. If this is set null
   * any subsequent parameter will be ignore.
   */
  public void setForwardDigit(char forwardDigit) {
    this.forwardDigit = forwardDigit;
  }

  /**
   * Set time in milliseconds for silence after audio finished.
   *
   * @param offset time in milliseconds after audio finished. If this is set null
   * any subsequent parameter will be ignore.
   */
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   * Set the digit to pause audio. If this is set null any subsequent
   * parameter will be ignore.
   *
   * @param pauseDigit digit to pause audio.
   */
  public void setPauseDigit(char pauseDigit) {
    this.pauseDigit = pauseDigit;
  }

  /**
   * Set the digit to rewind audio.
   *
   * @param rewindDigit digit to rewind audio.
   */
  public void setRewindDigit(char rewindDigit) {
    this.rewindDigit = rewindDigit;
  }
}
