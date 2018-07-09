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
package com.fonoster.astive.agi;

import com.fonoster.astive.util.AppLocale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle communication with Asterisk, at the lowest level.
 *
 * @since 1.0
 */
public class AgiCommandHandler {
    private Connection conn;

    /**
     * Construct a new handler to manage communication with Asterisk, at the
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
        ArrayList<String> lines = new ArrayList<String>();
        String line;

        try {
            while (!(line = conn.readLine()).isEmpty()) {
                lines.add(line);
            }
        } catch (IOException ex) {
            throw new AgiException(AppLocale.getI18n("unableToCommunicateWithAsteriskError",
                    new Object[]{ex.getMessage()}));
        }

        return (new AgiRequest(lines));
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
            throw new AgiException(AppLocale.getI18n("errorConnectionClosed"));
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
                throw new AgiException(AppLocale.getI18n("errorUnableToCommunicateWithAsterisk"));
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
