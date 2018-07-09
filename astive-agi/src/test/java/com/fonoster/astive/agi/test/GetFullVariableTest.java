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
import com.fonoster.astive.agi.command.GetFullVariable;

/**
 * Test case for command {@link com.fonoster.astive.agi.command.GetFullVariable}.
 * 
 * @since 1.0
 */
public class GetFullVariableTest extends TestCase {
  /**
   * Creates a new GetFullVariableTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public GetFullVariableTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String variable = "tt-monkeys";
    String channel = "SIP/Sanders";

    // Test first constructor
    StringBuilder b = new StringBuilder("GET FULL VARIABLE");
    b.append(" ");
    b.append("\"");
    b.append(variable);
    b.append("\"");

    GetFullVariable command = new GetFullVariable(variable);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing the second constructor
    b.append(" ");
    b.append("\"");
    b.append(channel);
    b.append("\"");
    command = new GetFullVariable(variable, channel);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
