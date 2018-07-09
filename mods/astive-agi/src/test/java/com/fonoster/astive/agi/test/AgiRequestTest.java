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

import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.AgiRequest;
import com.fonoster.astive.agi.ChannelType;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Test case for {@link com.fonoster.astive.agi.AgiRequest}.
 * 
 * @since 1.0
 */
public class AgiRequestTest extends TestCase {
  /**
   * Create a new AgiRequestTest object with the name of the test.
   *
   * @param testName {@inheritDoc}.
   */
  public AgiRequestTest(String testName) {
    super(testName);
  }

  /**
   * This test ensure that AgiRequest is parsing Asterisk request properly.
   *
   * @throws AgiException if missing a line thats part of the standard request.
   */
  public void testAgiRequest() throws AgiException {
    ArrayList<String> lines = new ArrayList();
    lines.add("agi_network:yes");
    lines.add("agi_network_script:/myAstivlet?ext=2");
    lines.add("agi_request:myAstivlet");
    lines.add("agi_channel:SIP/John-00000002");
    lines.add("agi_language:en");
    lines.add("agi_type:SIP");
    lines.add("agi_uniqueid:1325988028.2");
    lines.add("agi_callerid:john");
    lines.add("agi_calleridname:John Doe");
    lines.add("agi_callingpres:0");
    lines.add("agi_callingani2:0");
    lines.add("agi_callington:0");
    lines.add("agi_callingtns:0");
    lines.add("agi_dnid:101");
    lines.add("agi_rdnis:unknown");
    lines.add("agi_context:my-context");
    lines.add("agi_extension:101");
    lines.add("agi_priority:1");
    lines.add("agi_enhanced:0.0");
    lines.add("agi_accountcode:");
    lines.add("agi_arg_1:John");
    lines.add("agi_arg_2:Doe");
    lines.add("agi_arg_3:101");
    lines.add("\n");

    AgiRequest ar = new AgiRequest(lines);

    assertTrue(ar.isNetwork());
    assertEquals(ar.getScript(), "/myAstivlet?ext=2");
    assertEquals(ar.getRequestURL(), "myAstivlet");
    assertEquals(ar.getChannel(), "SIP/John-00000002");
    assertEquals(ar.getLanguage(), "en");
    assertEquals(ar.getChannelType(), ChannelType.SIP);
    assertEquals(ar.getCallId(), "1325988028.2");
    assertEquals(ar.getCallerId(), "john");
    assertEquals(ar.getCallerIdName(), "John Doe");
    assertEquals(ar.getCallingPres(), 0);
    assertEquals(ar.getCallingAni2(), 0);
    assertEquals(ar.getCallingTon(), 0);
    assertEquals(ar.getCallingTns(), 0);
    assertEquals(ar.getDnId(), "101");
    assertEquals(ar.getRdNis(), "unknown");
    assertEquals(ar.getContext(), "my-context");
    assertEquals(ar.getExtension(), "101");
    assertEquals(ar.getPriority(), "1");
    assertFalse(ar.isEnhanced());
    assertNull(ar.getAccountCode());
    assertEquals("2", ar.getQueryParameter("ext"));
    assertNull(ar.getQueryParameter("uid"));
    assertEquals("John",ar.getArguments()[0]);
  }
}
