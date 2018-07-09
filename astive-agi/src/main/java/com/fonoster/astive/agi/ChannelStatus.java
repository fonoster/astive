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
 * Enumerator that handle the status of a <code>channel</code>.
 *
 * @since 1.0
 */
public enum ChannelStatus {
  /**
   * Channel is in an unknown status.
   */
  UNKNOWN_STATUS(-1),
  /**
   * Channel is down and available.
   */
  CHANNEL_IS_DOWN_AND_AVAILABLE(0),
  /**
   * Channel is down but reserved.
   */
  CHANNEL_IS_DOWN_BUT_RESERVED(1),
  /**
   * Channel is off hook.
   */
  CHANNEL_IS_OFF_HOOK(2),
  /**
   * Digits have been dialed.
   */
  DIGITS_HAVE_BEEN_DIALED(3),
  /**
   * Line is ringing.
   */
  LINE_IS_RINGING(4),
  /**
   * Remote end is ringing.
   */
  REMOTE_END_IS_RINGING(5),
  /**
   * Line is up.
   */
  LINE_IS_UP(6),
  /**
   * Line is busy
   */
  LINE_IS_BUSY(7);
  
  // Int value of this type.
  private int code;

  // Create a new ChannelStatus object with status code as parameter. This
  // class is an enumerator, therefore can't be instantiated directly.
  private ChannelStatus(int code) {
    this.code = code;
  }

  /**
   * Returns enumerator for channel status code.
   *
   * @param code channel status code
   * @return ChannelStatus enumerator
   */
  public static ChannelStatus get(int code) {
    for (ChannelStatus cs : ChannelStatus.values()) {
      if (cs.code == code) {
        return cs;
      }
    }

    return null;
  }

  /**
   * Get the int value of this type.
   *
   * @return value for the enumerator element.
   */
  public int getCode() {
    return code;
  }
}
