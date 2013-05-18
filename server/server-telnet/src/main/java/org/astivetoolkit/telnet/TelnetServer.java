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
package org.astivetoolkit.telnet;


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
import org.astivetoolkit.server.security.AstPolicy;
import org.astivetoolkit.server.security.AstPolicyUtil;
import org.astivetoolkit.util.AppLocale;
import org.astivetoolkit.util.NetUtil;

/**
 * Default implementation of Astive Server - Telnet service.
 *
 * @since 1.0.0
 */
public abstract class TelnetServer extends ServerSocket implements Runnable {
  private static final Logger LOG = Logger.getLogger(TelnetServer.class);
  private static String promptSymbol = ColorsANSI.BRIGHT + "[astive]$ " + ColorsANSI.SANE;
  private InetAddress bindAddr;
  private int backlog;
  private int port;

  /**
   * Create a new TelnetServer with bindAddr, backlog and port not initialized.
   * @throws IOException when unable to perform IO operations.
   */
  public TelnetServer() throws IOException {
  }

  /**
   * Create a new TelnetServer with port, backlog and bindAddr.
   *
   * @param port port to where the service should bound.
   * @param backlog maximun connections in queue. After that all connections
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
   * Return a list with all applications deployed into the server.
   *
   * @return a list of string with the applications running into the server.
   */
  public abstract List<String> lookup();

  /**
   * DOCUMENT ME!
   */
  @Override
  public void run() {
    try {
      if (!NetUtil.isPortAvailable(port)) {
        throw new RuntimeException(AppLocale.getI18n("unableToOpenPort", new Object[] { port }));
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

        writer.println(AppLocale.getI18n("telnetIntro"));
        writer.println(AppLocale.getI18n("telnetHelp"));
        writer.print(promptSymbol);
        writer.flush();

        while (true) {
          String commandStr = reader.readLine();

          TelnetCommand command = TelnetCommand.get(commandStr);

          if (command == null) {
            writer.println(AppLocale.getI18n("telnetCommandNotFound"));
            writer.print(promptSymbol);
            writer.flush();

            continue;
          }

          if (command.equals(TelnetCommand.STOP)) {
            stop();

            break;
          }

          if (command.equals(TelnetCommand.SYSTEM)) {
            writer.println(AppLocale.getI18n("telnetCommandNotImpl"));
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

            // TODO: Print a message if is empty?
            writer.println(sb.toString());
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
      LOG.error(AppLocale.getI18n("unableToPerformIOWithAdminDaemon",
                                  new Object[] { ex.getMessage() }));
    }
  }

  /**
   * Stop the TelnetServer
   */
  public abstract void stop();

  /**
   * List all <code>server</code> configurations.
   */
  public abstract String system();

  /**
   * Return the version of the running server.
   *
   * @return server instance version.
   */
  public abstract String version();
}
