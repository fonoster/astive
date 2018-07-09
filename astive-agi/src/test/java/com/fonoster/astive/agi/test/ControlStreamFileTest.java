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
import com.fonoster.astive.agi.command.ControlStreamFile;

/**
 * Test case for command {@link com.fonoster.astive.agi.command.ControlStreamFile}.
 * 
 * @since 1.0
 */
public class ControlStreamFileTest extends TestCase {
  /**
   * Creates a new ControlStreamFileTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public ControlStreamFileTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String file = "tt-monkeys";
    String escapeDigits = "";
    int offset = -1;
    char forwardDigit = '*';
    char rewindDigit = '#';
    char pauseDigit = '9';

    // Testing first constructor 
    StringBuilder b = new StringBuilder("CONTROL STREAM FILE");
    b.append(" ");
    b.append("\"");
    b.append(file);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(escapeDigits);
    b.append("\"");
    b.append(" ");
    b.append(offset);

    ControlStreamFile command = new ControlStreamFile(file);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing second constructor
    command = new ControlStreamFile(file, escapeDigits);
    assertEquals(b.toString(), (CommandProcessor.buildCommand(command)));

    // Testing third constructor
    command = new ControlStreamFile(file, escapeDigits, offset);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing 4th constructor
    b.append(" ");
    b.append("\"");
    b.append(forwardDigit);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(rewindDigit);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(pauseDigit);
    b.append("\"");
    command = new ControlStreamFile(file, escapeDigits, offset, forwardDigit, rewindDigit,
                                    pauseDigit);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
