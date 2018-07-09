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
import com.fonoster.astive.agi.command.DatabaseDelTree;

/**
 * Test case for command {@link com.fonoster.astive.agi.command.DatabaseDel}.
 * 
 * @since 1.0
 */
public class DatabaseDelTreeTest extends TestCase {
  /**
   * Creates a new DatabaseDelTreeTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public DatabaseDelTreeTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String family = "familyDb";
    String keyTree = "keyTreeDb";

    // Testing first constructor
    StringBuilder b = new StringBuilder("DATABASE DELTREE");
    b.append(" ");
    b.append("\"");
    b.append(family);
    b.append("\"");

    DatabaseDelTree command = new DatabaseDelTree(family);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing second constructor        
    b.append(" ");
    b.append("\"");
    b.append(keyTree);
    b.append("\"");
    command = new DatabaseDelTree(family, keyTree);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
