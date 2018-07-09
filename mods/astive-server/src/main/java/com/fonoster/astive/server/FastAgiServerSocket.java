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
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.agi.DefaultAgiServerSettings;
import com.fonoster.astive.agi.fastagi.FastAgiConnection;
import com.fonoster.astive.util.AppLocale;

/**
 * This object receive connection from a FastAGI client.
 * 
 * @since 1.0
 * @see Service
 */
public class FastAgiServerSocket extends ServerSocket implements Service, DefaultAgiServerSettings {
    private static final Logger LOG = Logger.getLogger(FastAgiServerSocket.class);
    private InetAddress bindAddr;
    private int backlog;
    private int port;

    /**
     * Creates a new FastAgiServerSocket object.
     *
     * @param port
     * @param backlog
     * @param bindAddr
     *
     * @throws IOException
     */
    public FastAgiServerSocket(int port, int backlog, InetAddress bindAddr)
            throws IOException {
        super();

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("port", new Object[]{port}));
            LOG.debug(AppLocale.getI18n("backlog", new Object[]{backlog}));
            LOG.debug(AppLocale.getI18n("bindAddr", new Object[]{bindAddr}));
        }

        this.port = port;
        this.backlog = backlog;
        this.bindAddr = bindAddr;
    }

    public FastAgiConnection acceptConnection() throws IOException {
        return new FastAgiConnection(accept());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return !isClosed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws SystemException, AstiveException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageStartingFastAgiServerSocket"));
        }

        try {
            InetSocketAddress inet = new InetSocketAddress(bindAddr, port);
            bind(inet, backlog);
        } catch (IOException ex) {
            throw new SystemException(
                    AppLocale.getI18n("errorCantStartFastAgiServerSocket",
                    new Object[]{bindAddr.getHostAddress(), port}));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageDone"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() throws SystemException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageStoppingFastAgiServerSocket"));
        }

        try {
            close();            
        } catch (IOException ex) {
            throw new SystemException(AppLocale.getI18n("errorCantStopFastAgiServerSocket",
                    new Object[]{ex.getMessage()}));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageDone"));
        }
    }
}
