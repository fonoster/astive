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

import java.io.IOException;

/**
 * Provide read/write capabilities for few objects.
 *
 * @since 1.0
 */
public interface Connection {
  /**
   * Whether or not the channel is closed.
   *
   * @return true for a closed channel, false otherwise.
   */
  abstract boolean isClosed();

  /**
   * Reads a line of text from I/O channel.
   *
   * @return line of text from I/O channel.
   * @throws IOException if unable to perform input operation.
   */
  abstract String readLine() throws IOException;

  /**
   * Sends a given String to I/O channel.
   *
   * @param line string to send.
   * @throws IOException if unable to perform output operation.
   */
  abstract void write(String str) throws IOException;
}
