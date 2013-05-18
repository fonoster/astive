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
package org.astivetoolkit.agi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the results of a speech recognition command.
 *
 * @see org.asteriskjava.fastagi.AgiChannel#speechRecognize(String, int)
 * @see org.asteriskjava.fastagi.AgiChannel#speechRecognize(String, int, int)
 * @see org.asteriskjava.fastagi.command.SpeechRecognizeCommand
 * @since 1.0.0
 */
public class SpeechRecognitionResult {
  private final AgiCommandReply AgiCommandReply;

  /**
   * Creates a new SpeechRecognitionResult object.
   *
   * @param AgiCommandReply DOCUMENT ME!
   */
  public SpeechRecognitionResult(final AgiCommandReply AgiCommandReply) {
    this.AgiCommandReply = AgiCommandReply;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<SpeechResult> getAllResults() {
    final int numberOfResults = getNumberOfResults();
    final List<SpeechResult> results = new ArrayList<SpeechResult>(numberOfResults);

    for (int i = 0x0; i < numberOfResults; i++) {
    final  SpeechResult result =
        new SpeechResult(Integer.valueOf(AgiCommandReply.getAttribute("score" + i)),
                         AgiCommandReply.getAttribute("text" + i),
                         AgiCommandReply.getAttribute("grammar" + i));
      results.add(result);
    }

    return results;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public char getDigit() {
    final String digit = AgiCommandReply.getAttribute("digit");
 
    return ((digit == null) || (digit.length() == 0x0) ? 0 :digit.charAt(0x0));
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getEndpos() {
    return Integer.valueOf(AgiCommandReply.getAttribute("endpos"));
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getGrammar() {
    return AgiCommandReply.getAttribute("grammar0");
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getNumberOfResults() {
    final String numberOfResults = AgiCommandReply.getAttribute("results");

    return (numberOfResults == null) ? 0x0 : Integer.valueOf(numberOfResults);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getScore() {
    final String score0 = AgiCommandReply.getAttribute("score0");

    return (score0 == null) ? 0x0 : Integer.valueOf(score0);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getText() {
    return AgiCommandReply.getAttribute("text0");
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isDtmf() {
    return "digit".equals(AgiCommandReply.getExtra());
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isSpeech() {
    return "speech".equals(AgiCommandReply.getExtra());
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isTimeout() {
    return "timeout".equals(AgiCommandReply.getExtra());
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(65);
    
    sb.append("SpeechRecognitionResult[");
    
    if (isDtmf()) {
      sb.append("dtmf=true,");
      sb.append("digit=").append(getDigit()).append(",");
    }

    if (isSpeech()) {
      sb.append("speech=true,");
      sb.append("score=").append(getScore()).append(",");
      sb.append("text='").append(getText()).append("',");
      sb.append("grammar='").append(getGrammar()).append("',");
    }

    if (isTimeout()) {
      sb.append("timeout=true,");
    }

    if (getNumberOfResults() > 0x1) {
      sb.append("numberOfResults=").append(getNumberOfResults()).append(",");
      sb.append("allResults=").append(getAllResults()).append(",");
    }

    sb.append("endpos=").append(getEndpos()).append("]");

    return sb.toString();
  }

  public static class SpeechResult implements Serializable {
    private static final long serialVersionUID = 0x0L;
    private final String grammar;
    private final String text;
    private final int score;

    private SpeechResult(final int score, final String text, final String grammar) {
      this.score = score;
      this.text = text;
      this.grammar = grammar;
    }

    /**
     * Returns the grammar. This is the grammar that was used by the speech engine.
     *
     * @return the grammar
     */
    public String getGrammar() {
      return grammar;
    }

    /**
     * Returns the confidence score. This is an integer between 0 (lowest confidence)
     * and 1000 (highest confidence).
     *
     * @return the confidence score.
     */
    public int getScore() {
      return score;
    }

    /**
     * Returns the text. This is the text that was recognized by the speech engine.
     *
     * @return the text
     */
    public String getText() {
      return text;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder(45);
      sb.append("[");
      sb.append("score=").append(score).append(",");
      sb.append("text='").append(text).append("',");
      sb.append("grammar='").append(grammar).append("']");

      return sb.toString();
    }
  }
}
