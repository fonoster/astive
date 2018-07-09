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
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import com.fonoster.astive.server.AbstractAstiveServer;
import com.fonoster.astive.util.AppLocale;

/**
 *
 * @since 1.0
 */
public class AdminDaemonClient extends Socket {
    private static final Logger LOG = Logger.getLogger(AdminDaemonClient.class);
    private BufferedReader reader;
    private PrintWriter writer;

    {
        DOMConfigurator.configure(AbstractAstiveServer.ASTIVE_HOME + "/conf/log4j.xml");
    }

    public AdminDaemonClient(InetAddress addr, int port)
            throws IOException {
        super(addr, port);
        reader = new BufferedReader(new InputStreamReader(getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(getOutputStream()));
    }

    public void deploy(String app) throws IOException {
        send(AdminCommand.DEPLOY, app);
        // Server response
        out.println(getReader().readLine());
    }

    public void undeploy(String app) throws IOException {
        send(AdminCommand.UNDEPLOY, app);
        // Server response
        out.println(getReader().readLine());
    }

    public void stop() throws IOException {
        send(AdminCommand.STOP, null);
        if (LOG.isInfoEnabled()) {
            LOG.info(AppLocale.getI18n("messageDone"));
        }
    }

    private void send(AdminCommand cmd, String arg) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageSendingCmd", new Object[]{cmd, arg}));
        }

        getWriter().println(cmd.toString());

        if ((arg != null) && !arg.isEmpty()) {
            getWriter().println(arg);
        }

        getWriter().flush();

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageDone"));
        }
    }

    private BufferedReader getReader() throws IOException {
        return reader;
    }

    private PrintWriter getWriter() throws IOException {
        return writer;
    }
}
