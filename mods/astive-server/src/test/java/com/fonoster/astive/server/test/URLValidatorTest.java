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
package com.fonoster.astive.server.test;

import junit.framework.TestCase;
import com.fonoster.astive.util.URLValidator;

/**
 * Test case for URLValidator.
 * 
 * @since 1.0
 */
public class URLValidatorTest extends TestCase {

    /**
     * Creates a new URLValidatorTest object.
     *
     * @param testName {@inheritDoc}.
     */
    public URLValidatorTest(String testName) {
        super(testName);
    }

    /**
     * Test method.
     */
    public void testValidateURLs() {
        assertTrue(URLValidator.isValidURL("/"));
        assertFalse(URLValidator.isValidURL("/*"));
        assertFalse(URLValidator.isValidURL("/2"));
        assertTrue(URLValidator.isValidURL("/myproject"));
        assertFalse(URLValidator.isValidURL("/_abc"));
        assertTrue(URLValidator.isValidURL("/myproject/"));
        assertTrue(URLValidator.isValidURL("/myproject/*"));
        assertTrue(URLValidator.isValidURL("/myproject/myAstivlet"));
        assertTrue(URLValidator.isValidURL("*.ast"));
        assertTrue(URLValidator.isValidURL("*.ast2"));
        assertTrue(URLValidator.isValidURL("*.ast?%$"));
        assertFalse(URLValidator.isValidURL("abc.ast?%$"));
    }
}
