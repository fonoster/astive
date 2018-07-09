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
package com.fonoster.astive.astivlet;

import java.net.InetAddress;
import java.util.ArrayList;
import com.fonoster.astive.agi.AgiRequest;
import com.fonoster.astive.agi.fastagi.FastAgiConnection;

/**
 * Extends the {@link AgiRequest} object to provide request information for
 * network(FastAgi) request.
 *
 * @see AstivletResponse
 * @since 1.0
 */
public class AstivletRequest extends AgiRequest {
    private InetAddress localAddress;
    private InetAddress remoteAddress;
    private int localPort;
    private int remotePort;

    /**
     * Creates a new AstivletRequest object.
     *
     * @param lines to construct client request.
     * @param client represent client connection.
     */
    public AstivletRequest(ArrayList<String> lines, FastAgiConnection client) {
        super(lines);
        localAddress = client.getSocket().getLocalAddress();
        remoteAddress = client.getSocket().getLocalAddress();
        localPort = client.getSocket().getLocalPort();
        remotePort = client.getSocket().getPort();
    }

    /**
     * Returns the Internet Protocol (IP) address for the server receiving the
     * request.
     *
     * @return a <code>InetAddress</code> containing the IP address in which the
     * request was received.
     */
    public InetAddress getLocalAddress() {
        return localAddress;
    }

    /**
     * Returns the Internet Protocol (IP) port number for the server/service
     * receiving the request.
     *
     * @return the service port number.
     */
    public int getLocalPort() {
        return localPort;
    }

    /**
     * Returns the Internet Protocol (IP) address of the client or last proxy
     * that sent the request.
     *
     * @return an <code>InetAddress</code> containing the IP address of the
     * client that sent the request.
     */
    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * Returns the Internet Protocol (IP) source port of the client or last
     * proxy that sent the request.
     *
     * @return port number on remote client or last proxy.
     */
    public int getRemotePort() {
        return remotePort;
    }
}
