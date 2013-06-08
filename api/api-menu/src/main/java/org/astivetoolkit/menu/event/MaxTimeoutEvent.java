/* 
 * Copyright (C) 2010-2013 by PhonyTive LLC (http://phonytive.com)
 * http://astivetoolkit.org
 *
 * This file is part of Astive Toolkit(ATK)
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
package org.astivetoolkit.menu.event;


/**
 *
 * @since 1.0.0
 * @see MaxTimeoutListener
 * @see DigitsEvent
 */
public class MaxTimeoutEvent extends DigitsEvent {
  /**
   * DOCUMENT ME!
   */
  protected int maxTimeout;

  /** <p>Creates a new instance of MaxTimeoutEvent</p> */
  public MaxTimeoutEvent(Object source, String digit, int maxTimeout) {
    super(source, digit);
    this.maxTimeout = maxTimeout;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getMaxTimeout() {
    return maxTimeout;
  }

  /**
   * DOCUMENT ME!
   *
   * @param maxTimeout DOCUMENT ME!
   */
  public void setMaxTimeout(int maxTimeout) {
    this.maxTimeout = maxTimeout;
  }
}