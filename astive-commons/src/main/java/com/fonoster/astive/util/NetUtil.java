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
package com.fonoster.astive.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Contains a collection of methods helpful for network operations.
 *
 * @since 1.0.0
 */
public final class NetUtil {

    private NetUtil() {
    }

    /**
     * Check if a port is available. This method will attempt to open the
     * <code>port</code> to check if is been used by another process.
     *
     * @param port a valid port (if is in the range in between 0 and 65535,
     * inclusive).
     * @return whether or not the port is available.
     */
    public static boolean isPortAvailable(final int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;

        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);

            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /*
                     * should not be thrown
                     */
                }
            }
        }

        return false;
    }
}
