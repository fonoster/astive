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
package org.astivetoolkit.agi.command.test;

import org.astivetoolkit.agi.command.RecordFile;
import org.astivetoolkit.agi.AgiException;
import org.astivetoolkit.agi.CommandProcessor;
import junit.framework.TestCase;

/**
 * DOCUMENT ME
 */
public class RecordFileTest extends TestCase {
  /**
   * Creates a new RecordFileTest object.
   *
   * @param testName DOCUMENT ME!
   */
  public RecordFileTest(String testName) {
    super(testName);
  }

  /**
   * DOCUMENT ME!
   *
   * @throws AgiException DOCUMENT ME!
   */
  public void testCommand() throws AgiException {
    String filename = "abc";
    String format = "mp3";
    String escapeDigits = "";
    int timeout = 0xffffffff;
    int offset = 0x0;
    String beep = "BEEP";
    int silence = 0x0;

    // Testing first constructor
    StringBuilder b = new StringBuilder("RECORD FILE");
    b.append(" ");
    b.append("\"");
    b.append(filename);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(format);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(escapeDigits);
    b.append("\"");
    b.append(" ");
    b.append(timeout);

    RecordFile command = new RecordFile(filename, format);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));

    // Testing 3th constructor
    b.append(" ");
    b.append(offset);
    b.append(" ");
    b.append("\"");
    b.append(beep);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append("s=");
    b.append(silence);
    b.append("\"");
    command = new RecordFile(filename, format, escapeDigits, timeout, offset, true, silence);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}