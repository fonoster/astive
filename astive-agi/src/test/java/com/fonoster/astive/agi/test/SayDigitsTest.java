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
import com.fonoster.astive.agi.command.SayDigits;

/**
 * Test case for command {@link com.fonoster.astive.agi.command.SayDigits}.
 * 
 * @since 1.0
 */
public class SayDigitsTest extends TestCase {
  /**
   * Creates a new SayDigitsTest object.
   *
   * @param testName {@inheritDoc}
   */
  public SayDigitsTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */  
  public void testCommand() throws AgiException {
    String digits = "123";
    String escapeDigits;

    // Testing first constructor
    StringBuilder b = new StringBuilder("SAY DIGITS");
    b.append(" ");
    b.append("\"");
    b.append(digits);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append("\"");

    SayDigits command = new SayDigits(digits);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing second constructor
    escapeDigits = "123";
    b = new StringBuilder("SAY DIGITS");
    b.append(" ");
    b.append("\"");
    b.append(digits);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(escapeDigits);
    b.append("\"");

    command = new SayDigits(digits, escapeDigits);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
