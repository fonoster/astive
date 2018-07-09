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
package com.fonoster.astive.server;

import java.net.InetAddress;
import java.util.List;

/**
 * Contains all parameters needed by a final implementation of {@link Service}.
 *
 * @see Service
 * @since 1.0
 */
public interface ServiceProperties {

    /**
     * Returns the maximum queue length for incoming connection indications.
     *
     * @return maximum number of pending connections on the socket.
     */
    int getBacklog();

    /**
     * Returns the address to where the
     * <code>service</code> is bound, if any.
     *
     * @return the local address the <code>service</code> is bound to, or
     * <code>null</code> for the <code>anyLocal</code> address.
     */
    InetAddress getBindAddr();

    /**
     * Returns the address list from where connections are accept.
     *
     * @return by default <code>service</code> accept connections only from *
     * the <code>local</code> machine.
     */
    List<String> getOnlyFrom();

    /**
     * Returns the
     * <code>service</code> port.
     *
     * @return port to where <code>service</code> is bound.
     */
    int getPort();

    /**
     * Returns the fully qualified class name for the final implementation of
     * {@link Service}.
     *
     * @return the final implementation of a particular <code>service</code>.
     */
    String getServer();

    /**
     * Returns the name of
     * <code>service</code>.
     *
     * @return a descriptive name for a <code>service</code> (ex.: Telnet,
     * Admin)...
     */
    String getServiceName();

    /**
     * Indicates whether or not the
     * <code>service</code> is disabled.
     *
     * @return true if <code>service</code> is disabled or true otherwise.
     */
    boolean isDisabled();

    /**
     * Indicates whether or not the
     * <code>service</code> was bound to a IP/Port.
     *
     * @return true if <code>service</code> can't be bound to a port(ex.: port
     * occupied by another process).
     */
    boolean isUnableToOpen();

    /**
     * Define the maximum queue length for incoming connection indications.
     *
     * @param backlog provided should be greater than 0.
     * @see ServerSocket
     */
    void setBacklog(int backlog);

    /**
     * Address to where the
     * <code>service</code> should be bound.
     *
     * @param bindAddr should be a valid address where the code>service</code>
     * is running.
     */
    void setBindAddr(InetAddress bindAddr);

    /**
     * Set this parameter to
     * <code>true</code> to indicate
     *
     * @param disabled useful for services no mandatory(ex.:Telnet)
     */
    void setDisabled(boolean disabled);

    /**
     * Used to indicate the list of address from where the service can be
     * accessed.
     *
     * @param onlyFrom by default a service can only be accessed from the local
     * machine.
     */
    void setOnlyFrom(List<String> onlyFrom);

    /**
     * A valid port to where the service is bound.
     *
     * @param port is valid if is in the range in between 0 and 65535,
     * inclusive.
     */
    void setPort(int port);

    /**
     * Use to define the final implementation of the service.
     *
     * @param server has to be a fully qualified name class.
     */
    void setServer(String server);

    /**
     * Define the name for a particular service, for display purposes.
     *
     * @param serviceName useful to describe the function of the service.
     */
    void setServiceName(String serviceName);

    /**
     * Set to true if can't bound
     * <code>service</code> to a particular IP/Port.
     *
     * @param unableToOpen is set to false if bound process ends normally.
     */
    void setUnableToOpen(boolean unableToOpen);
}
