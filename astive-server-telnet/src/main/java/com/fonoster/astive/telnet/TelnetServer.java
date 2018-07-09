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
package com.fonoster.astive.telnet;

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
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.fonoster.astive.server.security.AstPolicy;
import com.fonoster.astive.server.security.AstPolicyUtil;
import com.fonoster.astive.util.AppLocale;
import com.fonoster.astive.util.NetUtil;

/**
 * Default implementation of Astive Server - Telnet service.
 *
 * @since 1.0
 */
public abstract class TelnetServer extends ServerSocket implements Runnable {
    private static final Logger LOG = Logger.getLogger(TelnetServer.class);
    private static String promptSymbol = ColorsANSI.BRIGHT + "[astive]$ " + ColorsANSI.SANE;
    private InetAddress bindAddr;
    private int backlog;
    private int port;

    /**
     * Create a new TelnetServer with bindAddr, backlog and port not
     * initialized.
     *
     * @throws IOException when unable to perform IO operations.
     */
    public TelnetServer() throws IOException {
    }

    /**
     * Create a new TelnetServer with port, backlog and bindAddr.
     *
     * @param port port to where the service should bound.
     * @param backlog maximum connections in queue. After that all connections
     * will be dropped.
     * @param bindAddr address to where the service should be bound.
     * @throws IOException when unable to perform IO operations.
     */
    public TelnetServer(int port, int backlog, InetAddress bindAddr)
            throws IOException {
        super();
        this.port = port;
        this.backlog = backlog;
        this.bindAddr = bindAddr;
    }

    /**
     * Returns a list with all applications deployed into the server.
     *
     * @return a list of string with the applications running into the server.
     */
    public abstract List<String> lookup();

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

                writer.println(AppLocale.getI18n("messageTelnetIntro"));
                writer.println(AppLocale.getI18n("messageTelnetHelp"));
                writer.print(promptSymbol);
                writer.flush();

                while (true) {
                    String commandStr = reader.readLine();

                    TelnetCommand command = TelnetCommand.get(commandStr);

                    if (command == null) {
                        writer.println(AppLocale.getI18n("messageTelnetCommandNotFound"));
                        writer.print(promptSymbol);
                        writer.flush();

                        continue;
                    }

                    if (command.equals(TelnetCommand.STOP)) {
                        stop();

                        break;
                    }

                    if (command.equals(TelnetCommand.SYSTEM)) {
                        writer.println(AppLocale.getI18n("messageTelnetCommandNotImpl"));
                        writer.print(promptSymbol);
                        writer.flush();

                        continue;
                    }

                    if (command.equals(TelnetCommand.HELP)) {
                        StringBuilder sb = new StringBuilder(65);
                        sb.append("stop");
                        sb.append("\n");
                        sb.append(" ");
                        sb.append("system");
                        sb.append("\n");
                        sb.append(" ");
                        sb.append("help");
                        sb.append("\n");
                        sb.append(" ");
                        sb.append("lookup");
                        sb.append("\n");
                        sb.append(" ");
                        sb.append("exit");
                        sb.append("\n");
                        sb.append(" ");
                        sb.append("version");
                        writer.println(sb.toString());
                        writer.print(promptSymbol);
                        writer.flush();

                        continue;
                    }

                    if (command.equals(TelnetCommand.LOOKUP)) {
                        StringBuilder sb = new StringBuilder(30);
                        Iterator<String> i = lookup().iterator();

                        while (i.hasNext()) {
                            String appInfo = i.next();
                            sb.append(appInfo);

                            if (i.hasNext()) {
                                sb.append("\n");
                            }
                        }

                        if (sb.toString().isEmpty()) {
                            writer.println(AppLocale.getI18n("messageTelnetEmptyResults"));
                        } else {
                            writer.println(sb.toString());
                        }
                        writer.print(promptSymbol);
                        writer.flush();

                        continue;
                    }

                    if (command.equals(TelnetCommand.EXIT)) {
                        client.close();

                        break;
                    }

                    if (command.equals(TelnetCommand.VERSION)) {
                        writer.println(version());
                        writer.print(promptSymbol);
                        writer.flush();

                        continue;
                    }
                }
            }
        } catch (IOException ex) {
            LOG.error(AppLocale.getI18n("errorUnableToCommunicateWithAdminDaemon",
                    new Object[]{ex.getMessage()}));
        }
    }

    /**
     * Stop the TelnetServer
     */
    public abstract void stop();

    /**
     * List all
     * <code>server</code> configurations.
     */
    public abstract String system();

    /**
     * Returns the version of the running server.
     *
     * @return server instance version.
     */
    public abstract String version();
}
