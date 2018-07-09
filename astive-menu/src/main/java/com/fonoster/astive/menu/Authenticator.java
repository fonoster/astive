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
package com.fonoster.astive.menu;

/**
 * Provides access control restricted menus.
 *
 * @since 1.0
 */
public abstract class Authenticator {
    private boolean authenticated = false;
    private int maxAuth = 3;

    /**
     * Creates a new Authenticator object.
     */
    public Authenticator() {
    }

    /**
     * Maximum attempts allowed to authenticate.
     *
     * @return maximum attempts of authentication before hang up.
     */
    public int getMaxAuth() {
        return maxAuth;
    }

    /**
     * The authentication variable is modified by the final implementation
     * of this class.
     *
     * @return true if authentication was successful false otherwise.
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets authentication to true or false. The authentication variable is
     * modified by the final implementation of this class.
     *
     * @param authenticated set to true to allow client to enter restricted
     * menus, or false to denied access.
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Overwrites the maximum(default) attempts to authenticate.
     *
     * @param maxAuth new maximum attempts to authenticate.
     */
    public void setMaxAuth(int maxAuth) {
        this.maxAuth = maxAuth;
    }

    /**
     * Use by final implementation to set the variable
     * <code>authenticated</code>.
     */
    public abstract void signIn();

    /**
     * Set's the variable authenticated to false.
     */
    public void signOut() {
        setAuthenticated(false);
    }
}
