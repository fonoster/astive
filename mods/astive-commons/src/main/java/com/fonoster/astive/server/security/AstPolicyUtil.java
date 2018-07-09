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
package com.fonoster.astive.server.security;

import java.security.Permission;
import java.security.PermissionCollection;

/**
 * Utility use to verified whether or not a permission is part of the collection
 * of permissions store in {@link AstPolicy}.
 *
 * @see AstPolicy
 * @since 1.0
 */
public final class AstPolicyUtil {
    private static PermissionCollection p = AstPolicy.getInstance().getPermissions();

    private AstPolicyUtil() {
    }

    /**
     * Verified if a particular permission is granted in {@link AstPolicy}
     *
     * @param permission to be checked.
     * @return true if permission is store in {@link AstPolicy}, false
     * otherwise.
     */
    static public boolean hasPermission(final Permission permission) {
        return p.implies(permission);
    }
}
