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
package org.astivetoolkit.server.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.xmlbeans.XmlException;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.junit.Ignore;
import junit.framework.TestCase;
import noNamespace.AppDocument;
import noNamespace.AppType;

/**
 * Test AppXml.
 *
 * @since 1.0.0
 */
public class AppXmlTest extends TestCase {
  /**
   * Creates a new AppXmlTest object.
   *
   * @param testName DOCUMENT ME!
   */
  public AppXmlTest(String testName) {
    super(testName);
  }

  /**
   * DOCUMENT ME!
   *
   * @throws XmlException DOCUMENT ME!
   * @throws IOException DOCUMENT ME!
   */
  public void testHello() throws XmlException, IOException {
    File file = MavenTestingUtils.getProjectFile("/src/test/resources/app_test.xml");

    AppDocument doc = AppDocument.Factory.parse(file);
    assertTrue(doc.validate());

    AppType app = doc.getApp();
    assertFalse(app.getName().isEmpty());
    assertFalse(app.getName().isEmpty());
    assertFalse(app.getDescription().isEmpty());
    assertEquals(app.getAstivletArray().length, 0x2);
    assertEquals(app.getAstivletMappingArray().length, 0x2);
  }
}