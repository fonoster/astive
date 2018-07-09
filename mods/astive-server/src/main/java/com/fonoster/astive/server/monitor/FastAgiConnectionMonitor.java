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
package com.fonoster.astive.server.monitor;

import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.agi.AgiCommandHandler;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.Connection;
import com.fonoster.astive.agi.fastagi.FastAgiConnection;
import com.fonoster.astive.agi.fastagi.FastAgiResponse;
import com.fonoster.astive.astivlet.AstivletRequest;
import com.fonoster.astive.astivlet.AstivletResponse;
import com.fonoster.astive.server.AstivletProcessor;
import com.fonoster.astive.server.ConnectionManager;
import com.fonoster.astive.server.FastAgiConnectionManager;
import com.fonoster.astive.server.FastAgiServerSocket;
import com.fonoster.astive.server.security.AstPolicy;
import com.fonoster.astive.server.security.AstPolicyUtil;
import com.fonoster.astive.util.AppLocale;

import java.io.IOException;
import java.net.SocketPermission;
import java.util.concurrent.*;

/**
 *
 * @since 1.0
 * @see ConnectionMonitor
 */
public class FastAgiConnectionMonitor implements ConnectionMonitor {
    private static final Logger LOG = Logger.getLogger(FastAgiConnectionMonitor.class);
    private ConnectionManager manager;
    private FastAgiServerSocket server;
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * Creates a new FastAgiConnectionMonitor object.
     *
     * @param server the server to monitor.
     * @param threads maximum threads allow for the server.
     */
    public FastAgiConnectionMonitor(FastAgiServerSocket server, int threads) {
        long keepAliveTime = 0L;

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageStartingConnectionMonitor"));
        }

        this.server = server;
        manager = new FastAgiConnectionManager();

        BlockingQueue<Runnable> threadPool = new LinkedBlockingQueue<>();

        threadPoolExecutor = new ThreadPoolExecutor(threads, threads,
                keepAliveTime, TimeUnit.MILLISECONDS,
                threadPool);

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageDone"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processConnection(final Connection conn)
            throws AstiveException {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(AppLocale.getI18n("messageProcessingCall"));
            }

            FastAgiConnection fastConn = (FastAgiConnection) conn;

            String sbr = fastConn.getSocket().getInetAddress().getHostAddress() +
                    ":" +
                    fastConn.getSocket().getPort();

            SocketPermission sp = new SocketPermission(sbr, AstPolicy.DEFAULT_ACTION);

            if (AstPolicyUtil.hasPermission(sp)) {
                AgiCommandHandler cHandler = new AgiCommandHandler(conn);
                FastAgiResponse response = new FastAgiResponse(cHandler);
                AstivletRequest aRequest =
                        new AstivletRequest(cHandler.getAgiRequest().getLines(), fastConn);
                AstivletResponse aResponse = new AstivletResponse(response);

                AstivletProcessor.invokeAstivlet(aRequest, aResponse);

                if (LOG.isDebugEnabled()) {
                    LOG.debug(AppLocale.getI18n("messageDone"));
                }
            } else {
                LOG.error(AppLocale.getI18n("errorUnableToPlaceCallCheckNetPermissions"));

                try {
                    fastConn.getSocket().close();
                } catch (IOException ex) {
                    // Drop connection an report exception
                    throw new AstiveException(ex);
                }
            }
        } catch (AgiException ex) {
            LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new Object[]{ex.getMessage()}));
            throw new AstiveException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            final FastAgiConnection conn;

            try {
                conn = server.acceptConnection();

                if (threadPoolExecutor.getMaximumPoolSize() <= threadPoolExecutor.getActiveCount()) {
                    conn.close();
                    continue;
                }

                Callable task = new Callable() {

                    @Override
                    public Object call() throws Exception {

                        manager.add(conn);

                        try {
                            processConnection(conn);
                        } catch (AstiveException ex) {
                            LOG.warn(ex.getMessage());
                            throw new AstiveException(ex);
                        }

                        try {
                            manager.remove(conn);
                        } catch (IOException ex) {
                            LOG.error(AppLocale.getI18n("errorConnectionClosed",
                                    new Object[]{ex.getMessage()}));
                            throw new AstiveException(ex);
                        }
                        return null;
                    }
                };

                LOG.debug(AppLocale.getI18n("messageTaskCount") + threadPoolExecutor.getActiveCount());

                threadPoolExecutor.submit(task);
                
            } catch (IOException ex) {
                if(!server.isRunning()) {
                    LOG.debug(AppLocale.getI18n("messageStoppingFastAgiConnectionMonitor"));
                    Thread.currentThread().interrupt();
                    LOG.debug(AppLocale.getI18n("messageDone"));
                }
            }
        }
    }
}
