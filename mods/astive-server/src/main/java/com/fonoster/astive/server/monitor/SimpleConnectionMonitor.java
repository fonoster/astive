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
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.astivlet.AstivletRequest;
import com.fonoster.astive.astivlet.AstivletResponse;
import com.fonoster.astive.server.AstivletProcessor;
import com.fonoster.astive.server.ConnectionManager;
import com.fonoster.astive.server.FastAgiConnectionManager;
import com.fonoster.astive.server.FastAgiServerSocket;
import com.fonoster.astive.util.AppLocale;

import java.io.IOException;
import java.util.concurrent.*;

/**
 *
 * @since 1.0
 * @see ConnectionMonitor
 */
public class SimpleConnectionMonitor implements ConnectionMonitor {
    private static final Logger LOG = Logger.getLogger(SimpleConnectionMonitor.class);
    private Astivlet astivlet;
    private ConnectionManager manager;
    private ThreadPoolExecutor threadPoolExecutor;
    private FastAgiServerSocket server;

    /**
     * Creates a new SimpleConnectionMonitor object.
     *
     * @param server the server.
     * @param astivlet the application.
     */
    public SimpleConnectionMonitor(FastAgiServerSocket server, Astivlet astivlet, int threads) {
        long keepAliveTime = 0L;

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageStartingConnectionMonitor"));
        }

        this.server = server;
        this.astivlet = astivlet;
        this.manager = new FastAgiConnectionManager();

        BlockingQueue<Runnable> threadPool = new LinkedBlockingQueue<>();

        threadPoolExecutor = new ThreadPoolExecutor(threads, threads,
                keepAliveTime, TimeUnit.MILLISECONDS,
                threadPool);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processConnection(final Connection conn)
            throws AstiveException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageProcessingCall"));
        }

        try {
            FastAgiConnection fastConn = (FastAgiConnection) conn;
            AgiCommandHandler cHandler = new AgiCommandHandler(fastConn);
            FastAgiResponse response = new FastAgiResponse(cHandler);
            AstivletRequest aRequest = new AstivletRequest(cHandler.getAgiRequest().getLines(), fastConn);
            AstivletResponse aResponse = new AstivletResponse(response);
            String requestAppName = aRequest.getRequestURL();

            if (LOG.isDebugEnabled()) {
                LOG.debug(AppLocale.getI18n("messageExecApp", new Object[]{requestAppName}));
            }

            AstivletProcessor.invokeAstivlet(getAstivlet(), aRequest, aResponse);

            if (LOG.isDebugEnabled()) {
                LOG.debug(AppLocale.getI18n("messageDone"));
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
        while (true) {
            final FastAgiConnection conn;

            try {
                conn = server.acceptConnection();

                if (threadPoolExecutor.getMaximumPoolSize() <= threadPoolExecutor.getActiveCount()) {
                    conn.close();
                    continue;
                }

                Callable task = new Callable() {

                    @Override
                    public Object call() throws AstiveException {
                        manager.add(conn);

                        try {
                            processConnection(conn);
                        } catch (AstiveException e) {
                            throw new AstiveException(e);
                        }

                        try {
                            manager.remove(conn);
                        } catch (IOException ex) {
                            LOG.error(AppLocale.getI18n("errorConnectionClosed",
                                    new Object[]{ex.getMessage()}));
                            throw new AstiveException(AppLocale.getI18n("errorConnectionClosed",
                                    new Object[]{ex.getMessage()}));
                        }
                        return null;
                    }
                };

                LOG.debug(AppLocale.getI18n("messageTaskCount") + threadPoolExecutor.getActiveCount());

                threadPoolExecutor.submit(task);
            } catch (IOException ex) {
                LOG.error(AppLocale.getI18n("errorConnectionClosed", new Object[]{ex.getMessage()}));
            }
        }
    }

    /**
     * Set the {@link com.fonoster.astive.astivlet.Astivlet} that will be
     * performed.
     *
     * @param astivlet the astivlet to perform.
     */
    public void setAstivlet(Astivlet astivlet) {
        this.astivlet = astivlet;
    }

    /**
     * Returns the {@link com.fonoster.astive.astivlet.Astivlet} that will be
     * performed.
     *
     * @return the astivlet to perform.
     */
    public Astivlet getAstivlet() {
        return astivlet;
    }

}