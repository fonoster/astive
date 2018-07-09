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
package com.fonoster.astive.agi.test;

import junit.framework.TestCase;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.CommandProcessor;
import com.fonoster.astive.agi.command.GetData;

/**
 * Test case for command {@link com.fonoster.astive.agi.command.GetData}.
 * 
 * @since 1.0
 */
public class GetDataTest extends TestCase {
  /**
   * Creates a new GetDataTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public GetDataTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String file = "tt-monkeys";
    int timeout = 0;
    int maxDigits = 1024;

    // Test first constructor
    StringBuilder b = new StringBuilder("GET DATA");
    b.append(" ");
    b.append("\"");
    b.append(file);
    b.append("\"");
    b.append(" ");
    b.append(timeout);
    b.append(" ");
    b.append(maxDigits);

    GetData command = new GetData(file);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing the second constructor
    command = new GetData(file, timeout);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing the third and last constructor
    command = new GetData(file, timeout, maxDigits);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
