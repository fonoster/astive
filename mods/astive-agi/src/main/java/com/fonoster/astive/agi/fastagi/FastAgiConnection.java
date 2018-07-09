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
package com.fonoster.astive.agi.fastagi;

import java.io.*;
import java.net.Socket;
import com.fonoster.astive.agi.Connection;

/**
 * Create a communication channel from Asterisk to the Fastagi Server.
 *
 * @since 1.0
 * @see Connection
 */
public class FastAgiConnection implements Connection {
  private BufferedReader reader;
  private PrintWriter writer;
  private Socket socket;

  /**
   * Create a new FastAgiConnection object with host and port as parameters.
   *
   * @param host to connect.
   * @param port to connect.
   * @throws IOException if socket is not open.
   */
  public FastAgiConnection(String host, int port) throws IOException {
    socket = new Socket(host, port);
    setIO();
  }

  /**
   * Create a new FastAgiConnection object with a socket as parameter.
   *
   * @param socket create by other class.
   * @throws IOException if socket is not open.
   */
  public FastAgiConnection(Socket socket) throws IOException {
    this.socket = socket;
    setIO();
  }

  /**
   * Close the current connection.
   *
   * @throws IOException if socket was already closed.
   */
  public void close() throws IOException {
    socket.close();
  }

  /**
   * Get socket object for this connection.
   *
   * @return socket for this connection.
   */
  public Socket getSocket() {
    return socket;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosed() {
    return socket.isClosed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String readLine() throws IOException {
    return reader.readLine();
  }

  /**
   * Sets the variables reader and writer.
   *
   * @throws IOException if socket is not open.
   */
  private void setIO() throws IOException {
    reader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
    writer = new PrintWriter(new OutputStreamWriter(getSocket().getOutputStream()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(String str) throws IOException {
    writer.println(str);
    writer.flush();
  }
}
