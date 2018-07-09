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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the results of a speech recognition command.
 * 
 * @since 1.0
 */
public class SpeechRecognitionResult {
  private final AgiCommandReply AgiCommandReply;

  public SpeechRecognitionResult(final AgiCommandReply AgiCommandReply) {
    this.AgiCommandReply = AgiCommandReply;
  }

  public List<SpeechResult> getAllResults() {
    final int numberOfResults = getNumberOfResults();
    final List<SpeechResult> results = new ArrayList<SpeechResult>(numberOfResults);

    for (int i = 0; i < numberOfResults; i++) {
    final  SpeechResult result =
        new SpeechResult(Integer.valueOf(AgiCommandReply.getAttribute("score" + i)),
                         AgiCommandReply.getAttribute("text" + i),
                         AgiCommandReply.getAttribute("grammar" + i));
      results.add(result);
    }

    return results;
  }

  public char getDigit() {
    final String digit = AgiCommandReply.getAttribute("digit");
 
    return ((digit == null) || (digit.length() == 0) ? 0 :digit.charAt(0));
  }

  public int getEndpos() {
    return Integer.valueOf(AgiCommandReply.getAttribute("endpos"));
  }

  public String getGrammar() {
    return AgiCommandReply.getAttribute("grammar0");
  }

  public int getNumberOfResults() {
    final String numberOfResults = AgiCommandReply.getAttribute("results");

    return (numberOfResults == null) ? 0 : Integer.valueOf(numberOfResults);
  }

  public int getScore() {
    final String score0 = AgiCommandReply.getAttribute("score0");

    return (score0 == null) ? 0 : Integer.valueOf(score0);
  }

  public String getText() {
    return AgiCommandReply.getAttribute("text0");
  }

  public boolean isDtmf() {
    return "digit".equals(AgiCommandReply.getExtra());
  }

  public boolean isSpeech() {
    return "speech".equals(AgiCommandReply.getExtra());
  }

  public boolean isTimeout() {
    return "timeout".equals(AgiCommandReply.getExtra());
  }

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

    if (getNumberOfResults() > 1) {
      sb.append("numberOfResults=").append(getNumberOfResults()).append(",");
      sb.append("allResults=").append(getAllResults()).append(",");
    }

    sb.append("endpos=").append(getEndpos()).append("]");

    return sb.toString();
  }

  public static class SpeechResult implements Serializable {
    private static final long serialVersionUID = 0L;
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
