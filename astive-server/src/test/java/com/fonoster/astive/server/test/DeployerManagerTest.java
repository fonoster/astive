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

import java.io.File;
import junit.framework.TestCase;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.server.AstObj;
import com.fonoster.astive.server.appmanager.DeployerManager;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;

/**
 * Test case for {@link DeployerManager}.
 *
 * @since 1.0
 */
public class DeployerManagerTest extends TestCase {

    /**
     * Creates a new DeployerManagerTest object.
     */
    public DeployerManagerTest() {
    }

    /**
     * Test method.
     */
    public void testDeployerManager() throws AstiveException {
        File file = MavenTestingUtils.getProjectFile("/src/test/resources/helloworld.jar");
        AstObj astObj = new AstObj("helloworld.jar", file.getAbsolutePath());
        Astivlet ast = astObj.getAstivletByURLPattern("/examples/HelloWorld");
        DeployerManager dm = DeployerManager.getInstance();

        assertNotNull(ast);
        assertEquals("Should be not apps at this point", dm.getApps().size(), 0);

        dm.addApp(astObj);

        assertEquals("Should be one app at this point", dm.getApps().size(), 1);
        assertNotNull(dm.getApp(astObj.getDeploymentId()));
        dm.removeApp(astObj);

        assertEquals("Should be 0 apps again", dm.getApps().size(), 0);
    }
}
