/*
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.util;

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
   * Check if a port is available. This method will attempt to open the <code>port</code>
   * to check if is been used by another process.
   *
   * @param port a valid port (if is in the range in between 0 and 65535, inclusive).
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
