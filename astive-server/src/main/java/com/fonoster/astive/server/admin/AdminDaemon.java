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
package com.fonoster.astive.server.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;
import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.server.AbstractAstiveServer;
import com.fonoster.astive.server.SystemException;
import com.fonoster.astive.server.appmanager.Deployer;
import com.fonoster.astive.server.appmanager.DeployerManager;
import com.fonoster.astive.server.monitor.ConnectionMonitor;
import com.fonoster.astive.server.security.AstPolicy;
import com.fonoster.astive.server.security.AstPolicyUtil;
import com.fonoster.astive.util.AppLocale;
import com.fonoster.astive.util.NetUtil;

/**
 * Provide remote access to the (@link DeployerManager) functionalities.
 *
 * @since 1.0
 * @see ConnectionMonitor
 * @see DeployerManager
 */
public class AdminDaemon extends ServerSocket implements Deployer, Runnable {
    private static final Logger LOG = Logger.getLogger(AdminDaemon.class);
    private AbstractAstiveServer server;
    private InetAddress bindAddr;
    private int backlog;
    private int port;

    public AdminDaemon(int port, int backlog, InetAddress bindAddr, AbstractAstiveServer server)
            throws IOException {
        super();
        this.port = port;
        this.backlog = backlog;
        this.bindAddr = bindAddr;
        this.server = server;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deploy(String app) throws AstiveException {
        DeployerManager.getInstance().deploy(app);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undeploy(String app) throws AstiveException {
        DeployerManager.getInstance().undeploy(app);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            if (!NetUtil.isPortAvailable(port)) {
                throw new RuntimeException(AppLocale.getI18n("unableToOpenPortError", new Object[]{port}));
            }

            InetSocketAddress inet = new InetSocketAddress(bindAddr, port);
            bind(inet, backlog);

            while (true) {
                Socket client = accept();

                StringBuilder sbr = new StringBuilder();
                sbr.append(client.getInetAddress().getHostAddress());
                sbr.append(":");
                sbr.append(port);

                SocketPermission sp = new SocketPermission(sbr.toString(), AstPolicy.DEFAULT_ACTION);

                if (!AstPolicyUtil.hasPermission(sp)) {
                    client.close();

                    continue;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                String commandStr = reader.readLine();

                AdminCommand command = AdminCommand.valueOf(commandStr);
                String arg = null;

                if (command.equals(AdminCommand.DEPLOY) || command.equals(AdminCommand.UNDEPLOY)) {
                    arg = reader.readLine();
                }

                if (command.equals(AdminCommand.DEPLOY)) {
                    try {
                        deploy(arg);
                        writer.println(AppLocale.getI18n("messageDone"));
                        LOG.info(AppLocale.getI18n("messageDone"));
                    } catch (AstiveException ex) {
                        writer.println(AppLocale.getI18n("errorUnableToDeployApp"));
                        LOG.warn(AppLocale.getI18n("errorUnableToDeployApp"));
                    }

                    writer.flush();

                    continue;
                }

                if (command.equals(AdminCommand.UNDEPLOY)) {
                    try {
                        undeploy(arg);
                        writer.println(AppLocale.getI18n("messageDone"));
                        LOG.info(AppLocale.getI18n("messageDone"));
                    } catch (AstiveException ex) {
                        writer.println(AppLocale.getI18n("errorUnableToUndeployApp"));
                        LOG.warn(AppLocale.getI18n("errorUnableToUndeployApp"));
                    }

                    writer.flush();

                    continue;
                }

                if (command.equals(AdminCommand.STOP)) {
                    try {
                        server.stop();
                    } catch (SystemException ex) {
                        LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new Object[]{ex.getMessage()}));
                    }

                    break;
                }

                if (command.equals(AdminCommand.HELP)) {
                    // XXX: no-yet-implemented
                    break;
                }

                if (command.equals(AdminCommand.VERSION)) {
                    // XXX: no-yet-implemented
                    break;
                }

                client.close();
            }
        } catch (IOException ex) {
            LOG.error(AppLocale.getI18n("errorUnableToCommunicateWithAdminDaemon",
                    new Object[]{ex.getMessage()}));
        }
    }
}
