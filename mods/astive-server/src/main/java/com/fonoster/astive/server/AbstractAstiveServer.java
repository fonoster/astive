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

import java.io.IOException;
import java.net.InetAddress;
import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.Version;
import com.fonoster.astive.util.AppLocale;

/**
 *
 * @since 1.0
 * @see Service
 * @see FastAgiServerSocket
 * @see AstiveServer
 * @see SimpleAstiveServer
 */
public abstract class AbstractAstiveServer extends FastAgiServerSocket implements Service {
    public static final String ASTIVE_HOME = System.getenv("ASTIVE_HOME");
    public static final String ASTIVE_APPS = System.getenv("ASTIVE_HOME") + "/apps/";
    private InetAddress bindAddr;
    private int backlog;
    private int port;
    private static final Logger LOG = Logger.getLogger(AbstractAstiveServer.class);

    public AbstractAstiveServer() throws SystemException, IOException {
        // Using the default agi asterisk port
        super(DEFAULT_AGI_SERVER_PORT, DEFAULT_AGI_SERVER_BACKLOG,
                InetAddress.getByName(DEFAULT_AGI_SERVER_BIND_ADDR));
        this.port = DEFAULT_AGI_SERVER_PORT;
        this.backlog = DEFAULT_AGI_SERVER_BACKLOG;
    }

    public AbstractAstiveServer(int port) throws SystemException, IOException {
        super(port, DEFAULT_AGI_SERVER_BACKLOG, InetAddress.getByName(DEFAULT_AGI_SERVER_BIND_ADDR));
    }

    public AbstractAstiveServer(int port, int backlog) throws SystemException, IOException {
        super(port, backlog, InetAddress.getLocalHost());
    }

    public AbstractAstiveServer(int port, int backlog, InetAddress bindAddr)
            throws SystemException, IOException {
        super(port, backlog, bindAddr);
        this.bindAddr = bindAddr;
        this.port = port;
        this.backlog = backlog;
    }

    /**
     * @return the backlog
     */
    public int getBacklog() {
        return backlog;
    }

    /**
     * @return server bindAddr
     */
    public InetAddress getBindAddr() {
        return bindAddr;
    }

    /**
     * @return server port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return server version.
     */
    public String getVersion() {
        StringBuilder sb =
                new StringBuilder(AppLocale.getI18n("astivedVersion",
                new String[]{Version.VERSION, Version.BUILD_TIME}));

        return sb.toString();
    }

    protected abstract void launchConnectionMonitor() throws AstiveException;

    @Override
    public void start() throws SystemException, AstiveException {
        super.start();
        launchConnectionMonitor();
    }

    @Override
    public void stop() throws SystemException {
        try {
            super.stop();
            System.exit(0);
        } catch (Exception ex) {
            LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new Object[]{ex.getMessage()}));
            System.exit(1);
        }
    }
}
