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

import java.net.SocketPermission;
import junit.framework.TestCase;
import com.fonoster.astive.server.security.AstPolicy;
import com.fonoster.astive.server.security.AstPolicyUtil;

/**
 * This test attempts to validate the security manager implemented by
 * {@link Service} and the objects {@link AstPolicy} and {@link AstPolicyUtil}.
 *
 * @see AstPolicy
 * @see AstPolicyUtil
 * @since 1.0
 */
public class AstPolicyTest extends TestCase {

    /**
     * Create a new AstPolicyTest object with the name of the test.
     *
     * @param testName name of the test.
     */
    public AstPolicyTest(String testName) {
        super(testName);
    }

    /**
     * This test add a permission to the collection in {@link AstPolicy}, an
     * then check if that permission is avaliable using
     * {@link AstPolicyUtil.hasPermission()}.
     */
    public void testAstPolicy() {
        AstPolicy ast = AstPolicy.getInstance();
        ast.addPermission(new SocketPermission("127.0.0.1:4444", AstPolicy.DEFAULT_ACTION));

        SocketPermission sp1 = new SocketPermission("127.0.0.1:4444", AstPolicy.DEFAULT_ACTION);
        SocketPermission sp2 = new SocketPermission("127.0.0.2:4444", AstPolicy.DEFAULT_ACTION);

        assertTrue(AstPolicyUtil.hasPermission(sp1));
        assertFalse(AstPolicyUtil.hasPermission(sp2));
    }
}
