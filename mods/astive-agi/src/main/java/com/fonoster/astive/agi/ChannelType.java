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
 * Enum that handle the status of a <code>channel</code>.
 *
 * @since 1.0
 */
public enum ChannelType {
  /**
   * Channel type is unknown.
   */
  UNKNOWN("UNKNOWN"),
  /**
   * Channel type ZAP.
   */
  ZAP("ZAP"),
  /**
   * Channel type SIP
   */
  SIP("SIP"),
  /**
   * Channel type IAX
   */
  IAX("IAX"),
  /**
   * Channel type H.323
   */
  H323("H.323");
  
  // Type of channel as string. 
  private String name;

  // Create a new ChannelStatus object with status code as parameter. This
  // class is an enum, therefore can't be instantiated directly.
  private ChannelType(String name) {
    this.name = name;
  }

  /**
   * Returns enum for channel status code.
   *
   * @param code channel status code
   * @return ChannelStatus enum
   */
  public static ChannelType get(String name) {
    for (ChannelType ct : ChannelType.values()) {
      if (ct.toString().equalsIgnoreCase(name)) {
        return ChannelType.valueOf(name);
      }
    }

    return ChannelType.UNKNOWN;
  }

  /**
   * Get the name for this channel type.
   *
   * @return the name for this channel type.
   */
  public String getName() {
    return name;
  }
}
