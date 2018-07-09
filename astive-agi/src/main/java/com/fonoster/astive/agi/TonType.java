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
 * Enumerator that map the ton type for an incoming call.
 *
 * @since 1.0
 */
public enum TonType {UNKNOWN(0, "Unknown", "Unknown"),
  INTERNATIONAL(1, "International", "International"),
  NATIONAL(2, "National", "National"),
  NETWORK_SPECIFIC(3, "NetworkSpecific", "Network specific"),
  SUBSCRIBER_NUMBER(4, "SubscriberNumber", "Subscriber number"),
  ALPHA_NUMERIC(5, "AlphaNumberic", "Alpha numeric"),
  ABBREVIATED(6, "Abbreviated", "Abbreviated");

  private int value;
  private String name;
  private String literal;
  
  private TonType(final int value, final String name, final String literal) {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  public static TonType get(final int value) {
    return null;
  }

  public static TonType getByName(final String name) {
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
