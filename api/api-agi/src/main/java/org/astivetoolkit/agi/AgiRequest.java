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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.astivetoolkit.agi.annotation.RequestField;

/**
 * Contain information about the channel the script is invoked on.
 * This is the first package sent by a <code>channel</code> in AGI protocol.
 *
 * @since 1.0.0
 */
public class AgiRequest {
  // A usual logging class
  private static final Logger LOG = Logger.getLogger(AgiRequest.class);
  private static Map<String, String> parameters;

  /**
   * Collection of a pair key/value element with information about
   * the <code>channel</code>.
   */
  private ArrayList<String> lines;

  /**
   * Whether this Agi is passed audio (EAGI - Enhanced AGI).
   */
  @RequestField("agi_enhanced")
  private Boolean enhanced;

  /**
   * Whether or not this lines was sent throw a network(fastagi).
   */
  @RequestField("agi_network")
  private Boolean network;

  /**
   * Channel type(e.g. ZAP, SIP, H323, IAX...).
   */
  @RequestField("agi_type")
  private ChannelType channelType;

  /**
   * Use to map Agi field with AgiRequest variables.
   */
  private HashMap<String, String> fieldsMap;

  /**
   * ANI2 (Info digits) also called Originating line information or OLI.
   * Possible codes are listed can be founded at:
   *
   * <br>http://www.nanpa.com/number_resource_info/ani_ii_assignments.html
   */
  @RequestField("agi_callingani2")
  private Integer callingAni2;

  /**
   * Caller ID presentation for incoming calls (PRI channels).
   * Class {@link PresentationType} can be use to decode this value.
   */
  @RequestField("agi_callingpres")
  private Integer callingPres;

  /**
   * Transit Network Selector (PRI channels).
   * Note: Will be a great contribution if you send us a link or document
   * with an in deep explanation about this parameter.
   */
  @RequestField("agi_callingtns")
  private Integer callingTns;

  /**
   * Caller Type of Number (PRI channels).
   * Class {@link TonType} can be use to decode this value.
   */
  @RequestField("agi_callington")
  private Integer callingTon;

  /**
   * Account code (if specified).
   */
  @RequestField("agi_accountcode")
  private String accountCode;

  /**
   * Current call unique identifier
   */
  @RequestField("agi_uniqueid")
  private String callId;

  /**
   * Caller Id Number only(e.g. "123").
   */
  @RequestField("agi_callerid")
  private String callerId;

  /**
   * Caller Id Name only(e.g. "John Doe")
   */
  @RequestField("agi_calleridname")
  private String callerIdName;

  /**
   * Current channel name.
   */
  @RequestField("agi_channel")
  private String channel;

  /**
   * Current context.
   */
  @RequestField("agi_context")
  private String context;

  /**
   * Dialed Number Identifier.
   */
  @RequestField("agi_dnid")
  private String dnId;

  /**
   * Extension that was called (e.g. 300).
   */
  @RequestField("agi_extension")
  private String extension;

  /**
   * Current language. Language for the current <code>channel</code>.
   */
  @RequestField("agi_language")
  private String language;

  /**
   * Current priority in the dialplan.
   */
  @RequestField("agi_priority")
  private String priority;

  /**
   * Redirected Dial Number ID Service.
   */
  @RequestField("agi_rdnis")
  private String rdNis;

  /**
   * Name of the Agi script that is being called.
   */
  @RequestField("agi_request")
  private String requestURL;

  /**
   * Remote Agi script(fastagi script).
   */
  @RequestField("agi_network_script")
  private String script;

  /**
   *
   */
  @RequestField("agi_threadid")
  private String threadId;

  /**
   * Version of the AGI version(Asterisk Version).
   */
  @RequestField("agi_version")
  private String version; // TODO: Investigate, how to get asterisk version...

  /**
   * Create a new AgiRequest object with the info sent by a new
   * <code>channel</code>.
   *
   * @param lines array of a pairs key/value elements with information about
   * the <code>channel</code>.
   */
  public AgiRequest(final ArrayList<String> lines) {
    this.lines = lines;

    fieldsMap = new HashMap<String, String>();

    for (String line : lines) {
      if (line.split(":").length < 2) {
        continue;
      }

      // WARNING: Ensure it work for the parameter agi_request
      String key = line.split(":")[0].trim();
      String value = line.split(":")[1].trim();

      if (key.equals("agi_request") && (line.split(":").length == 3)) {
        value = value + ":" + line.split(":")[2].trim();
      }

      fieldsMap.put(key, value);
    }

    try {
      fillFields();
    } catch (IllegalArgumentException ex) {
      LOG.warn(ex.getMessage());
    } catch (IllegalAccessException ex) {
      LOG.warn(ex.getMessage());
    }

    // Get parameters.
    // Note: The URL needs to be validated prior to do the next process.
    // TODO: Improve this by using regex        
    parameters = new HashMap<String, String>();

    String params = null;

    if (script.split("\\?").length > 1) {
      params = script.split("\\?")[1];
    }
 

    if (params != null) {
      if (params.split("&").length > 1) {
        String[] p = params.split("&");

        for (String s : p) {
          String key = s.split("=")[0].trim();
          String val = s.split("=")[1].trim();
          parameters.put(key, val);
        }
      }
    }
  }

  /**
   * Set a variables by using reflection.
   */
  private void fillFields() throws IllegalArgumentException, IllegalAccessException {
    for (String af : fieldsMap.keySet()) {
      for (Field f : AgiRequest.class.getDeclaredFields()) {
        RequestField rf;

        if (f.isAnnotationPresent(RequestField.class)) {
          rf = f.getAnnotation(RequestField.class);
        } else {
          continue;
        }

        if (rf.value().equals(af)) {
          // There is an exeption for the variables:
          // channelType, enhanced and network
          if (f.getName().equals("network")) {
            if (fieldsMap.get(af).equals("yes")) {
              f.set(this, Boolean.TRUE);
            } else {
              f.set(this, Boolean.FALSE);
            }

            break;
          } else if (f.getName().equals("enhanced")) {
            if (fieldsMap.get(af).equals("1.0")) {
              f.set(this, Boolean.TRUE);
            } else {
              f.set(this, Boolean.FALSE);
            }

            break;
          } else if (f.getType().equals(ChannelType.class)) {
            f.set(this, ChannelType.get(fieldsMap.get(af)));

            break;
          } else if (f.getType().equals(Integer.class)) {
            f.set(this, Integer.valueOf(fieldsMap.get(af)));

            break;
          } else {
            f.set(this, fieldsMap.get(af));

            break;
          }
        }
      }
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getAccountCode() {
    return accountCode;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getCallId() {
    return callId;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getCallerId() {
    return callerId;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getCallerIdName() {
    return callerIdName;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCallingAni2() {
    return callingAni2.intValue();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCallingPres() {
    return callingPres.intValue();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCallingTns() {
    return callingTns.intValue();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getCallingTon() {
    return callingTon.intValue();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getChannel() {
    return channel;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public ChannelType getChannelType() {
    return channelType;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getContext() {
    return context;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getDnId() {
    return dnId;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getExtension() {
    return extension;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Get a list with the requestURL parameters.
   */
  public ArrayList<String> getLines() {
    return lines;
  }

  /**
   * DOCUMENT ME!
   *
   * @param parameter DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getParameter(String parameter) {
    return parameters.get(parameter);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getPriority() {
    return priority;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getRdNis() {
    return rdNis;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getRequestURL() {
    return requestURL;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getScript() {
    return script;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getThreadId() {
    return threadId;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getVersion() {
    return version;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Boolean isEnhanced() {
    return enhanced;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Boolean isNetwork() {
    return network;
  }

  /**
   * Return all elements key/value of this lines.
   *
   * @return all elements key/value of this lines.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("agi_network:");

    if (isNetwork()) {
      sb.append("yes");
    } else {
      sb.append("no");
    }

    sb.append("\n");

    sb.append("agi_network_script:");
    sb.append(getRequestURL());
    sb.append("\n");

    sb.append("agi_request:");
    sb.append(getScript());
    sb.append("\n");

    sb.append("agi_channel:");
    sb.append(getChannel());
    sb.append("\n");

    sb.append("agi_language:");
    sb.append(getLanguage());
    sb.append("\n");

    sb.append("agi_type:");
    sb.append(getChannelType());
    sb.append("\n");

    sb.append("agi_uniqueid:");
    sb.append(getCallId());
    sb.append("\n");

    sb.append("agi_version:");
    sb.append(getVersion());
    sb.append("\n");

    sb.append("agi_callerid:");
    sb.append(getCallerId());
    sb.append("\n");

    sb.append("agi_calleridname:");
    sb.append(getCallerIdName());
    sb.append("\n");

    sb.append("agi_callingpres:");
    sb.append(getCallingPres());
    sb.append("\n");

    sb.append("agi_callingani2:");
    sb.append(getCallingAni2());
    sb.append("\n");

    sb.append("agi_callingtns:");
    sb.append(getCallingTns());
    sb.append("\n");

    sb.append("agi_dnid:");
    sb.append(getDnId());
    sb.append("\n");

    sb.append("agi_rdnis:");
    sb.append(getRdNis());
    sb.append("\n");

    sb.append("agi_context:");
    sb.append(getContext());
    sb.append("\n");

    sb.append("agi_extension:");
    sb.append(getExtension());
    sb.append("\n");

    sb.append("agi_priority:");
    sb.append(getPriority());
    sb.append("\n");

    sb.append("agi_enhanced:");
    sb.append(isEnhanced());
    sb.append("\n");

    sb.append("agi_accountcode:");
    sb.append(getAccountCode());
    sb.append("\n");

    sb.append("agi_threadid:");
    sb.append(getThreadId());

    return sb.toString();
  }
}
