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
import com.fonoster.astive.agi.command.GoSub;

/**
 * Test case for command {@link com.fonoster.astive.agi.command.GoSub}.
 * 
 * @since 1.0
 */
public class GoSubTest extends TestCase {
  /**
   * Creates a new GoSubTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public GoSubTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String context = "test-context";
    String extension = "100";
    String priority = "1";
    String opt = "\"1,abc,amor\"";
    String[] options = { "1", "abc", "amor" };

    // Testing first constructor
    StringBuilder b = new StringBuilder("GOSUB");
    b.append(" ");
    b.append("\"");
    b.append(context);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(extension);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(priority);
    b.append("\"");

    GoSub command = new GoSub(context, extension, priority);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing second constructor
    b.append(" ");
    b.append(opt);
    command = new GoSub(context, extension, priority, options);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
