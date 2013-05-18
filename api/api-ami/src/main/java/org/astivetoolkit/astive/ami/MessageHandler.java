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
package org.astivetoolkit.astive.ami;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.astivetoolkit.ami.event.ManagerEvent;
import org.astivetoolkit.util.AppLocale;

/**
 *
 * @since 1.0.0
 */
class MessageHandler implements Runnable {
  private final BufferedReader reader;
  private final Manager manager;
  private final PrintWriter writer;
  private final ResponseQuee responseQuee;
  private final String protocolVersion;
  private boolean threadDone = false;

  /**
   * Creates a new MessageHandler object.
   *
   * @param manager DOCUMENT ME!
   * @param client DOCUMENT ME!
   *
   * @throws AmiException DOCUMENT ME!
   */
  MessageHandler(Manager manager, Socket client) throws AmiException {
    try {
      this.manager = manager;
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
      // Get manager version
      protocolVersion = reader.readLine();
      responseQuee = new ResponseQuee();
    } catch (IOException ex) {
      // send the msg
      throw new AmiException();
    }
  }

  /**
   * DOCUMENT ME!
   */
  public void checkInPacket() {
    ArrayList<String> lines = new ArrayList<String>();

    try {
      String s;

      while (!(s = getReader().readLine()).isEmpty()) {
        lines.add(s);
      }
    } catch (IOException ex) {
      // TODO: Report AmiException            
    }

    if (lines.isEmpty()) {
      return;
    }

    try {
      Message msg = new Message(lines);

      if (msg.getType().equals(MessageType.RESPONSE)) {
        String actionId = msg.getParameter("ActionID");

        synchronized (responseQuee) {
          responseQuee.pushMessage(actionId, msg);
          // Notify the method getMessage that this object is available
          responseQuee.notify();
        }
      } else if (msg.getType().equals(MessageType.EVENT)) {
        // Dispatching events to the manager
        // TODO: Think about the manager as event source?
        ManagerEvent event = ManagerEventFactory.getInstance().getEvent(manager, msg);
        manager.fireEvent(event);
      } else {
        // TODO: Unexpected messageType
        throw new AmiException(AppLocale.getI18n("unhandledResponsePacket"));
      }
    } catch (AmiException ex) {
      //
    }
  }

  /**
   * DOCUMENT ME!
   */
  public void done() {
    threadDone = true;
  }

  /**
   * DOCUMENT ME!
   *
   * @param actionId DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws AmiException DOCUMENT ME!
   */
  public Message getMessage(long actionId) throws AmiException {
    synchronized (responseQuee) {
      while (!responseQuee.messageExist("" + actionId)) {
        try {
          responseQuee.wait();
        } catch (InterruptedException ex) {
        }
      }
    }

    return responseQuee.pullMessage("" + actionId);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getProtocolVersion() {
    return protocolVersion;
  }

  private BufferedReader getReader() {
    return reader;
  }

  private PrintWriter getWriter() {
    return writer;
  }

  /**
   * DOCUMENT ME!
   */
  @Override
  public void run() {
    while (!threadDone) {
      checkInPacket();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param msg DOCUMENT ME!
   */
  public void sendMessage(Message msg) {
    getWriter().println(msg);
    getWriter().flush();
  }
}
