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
package org.astivetoolkit.agi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.astivetoolkit.util.AppLocale;

/**
 * Handle communication with Asterisk, in the lowest level.
 *
 * @since 1.0.0
 */
public class AgiCommandHandler {
  private AgiRequest agiRequest;
  private Connection conn;

  /**
   * Construct a new handler to manage communication with Asterisk, in the
   * lowest level.
   *
   * @param conn an object that implement {@link Connection} interface.
   */
  public AgiCommandHandler(Connection conn) {
    this.conn = conn;
  }

  /**
   * Get the initial package sent by the AGI protocol.
   *
   * @return info about client request, such as script to be executed, channel
   * name etc... {@link AgiRequest}
   * @throws AgiException if is unable to perform I/O with Asterisk.
   */
  public AgiRequest getAgiRequest() throws AgiException {
    if (agiRequest != null) {
      return agiRequest;
    }

    ArrayList<String> lines = new ArrayList<String>();
    String line;

    try {
      while (!(line = conn.readLine()).isEmpty()) {
        lines.add(line);
      }
    } catch (IOException ex) {
      throw new AgiException(AppLocale.getI18n("unableToPerformIOWithAsterisk",
                                               new Object[] { ex.getMessage() }));
    }

    return (agiRequest = new AgiRequest(lines));
  }

  /**
   * Get the last reply sent by Asterisk as result of an command.
   *
   * @return reply to a command
   * @throws AgiException if the connection has been closed.
   */
  private AgiCommandReply readAgiCommandReply() throws AgiException {
    AgiCommandReply reply;
    List<String> lines;
    String line = null;

    lines = new ArrayList<String>();

    try {
      line = conn.readLine();
    } catch (IOException e) {
      throw new AgiException(AppLocale.getI18n("theConnectionHasBeenClosed"));
    }

    if (line == null) {
      throw new AgiException();
    }

    if (line.startsWith("HANGUP")) {
      if (line.length() > 6) {
        line = line.substring(6);
      } else {
        return readAgiCommandReply();
      }
    }

    lines.add(line);

    if (line.startsWith(Integer.toString(AgiCommandReply.SC_INVALID_COMMAND_SYNTAX))) {
      try {
        while ((line = conn.readLine()) != null) {
          lines.add(line);

          if (line.startsWith(Integer.toString(AgiCommandReply.SC_INVALID_COMMAND_SYNTAX))) {
            break;
          }
        }
      } catch (IOException ex) {
        throw new AgiException(AppLocale.getI18n("unableToReadReplyFromAsterisk"));
      }
    }

    reply = new AgiCommandReply(lines);

    // Special handling for gosub, see AJ-257
    if (reply.getStatus() == AgiCommandReply.SC_TRYING) {
      return readAgiCommandReply();
    } else {
      return reply;
    }
  }

  /**
   * Send a command to Asterisk
   * <code>channel</code>.
   *
   * @param command command to send.
   * @return the reply for the sent command.
   * @throws AgiException if is unable to perform I/O operations.
   */
  public AgiCommandReply sendAgiCommand(Object command)
                                 throws AgiException {
    try {
      if (command instanceof String) {
        conn.write((String) command);
      } else {
        conn.write(CommandProcessor.buildCommand(command));
      }
    } catch (IOException ex) {
      throw new AgiException();
    }

    return readAgiCommandReply();
  }
}
