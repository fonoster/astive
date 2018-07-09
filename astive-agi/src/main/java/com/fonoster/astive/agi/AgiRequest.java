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

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;
import com.fonoster.astive.agi.annotation.RequestField;

/**
 * Contains the data sent by the requesting<code>channel</code> as well as the
 * queryParameters passed from the dialplan.
 *
 * @since 1.0
 */
public class AgiRequest {
  private static final Logger LOG = Logger.getLogger(AgiRequest.class);
  // Collection of a pair key/value element with information about
  // the <code>channel</code>.
  private static Map<String, String> queryParameters;
  private static String[] arguments;
  // Use to map Agi field with AgiRequest variables.
  private HashMap<String, String> fieldsMap;

  private ArrayList<String> lines;
  @RequestField("agi_type")
  private ChannelType channelType;
  @RequestField("agi_accountcode")
  private String accountCode;
  @RequestField("agi_uniqueid")
  private String callId;
  @RequestField("agi_callerid")
  private String callerId;
  @RequestField("agi_calleridname")
  private String callerIdName;
  @RequestField("agi_channel")
  private String channel;
  @RequestField("agi_context")
  private String context;
  @RequestField("agi_dnid")
  private String dnId;
  @RequestField("agi_extension")
  private String extension;
  @RequestField("agi_language")
  private String language;
  @RequestField("agi_priority")
  private String priority;
  @RequestField("agi_rdnis")
  private String rdNis;
  @RequestField("agi_request")
  private String requestURL;
  @RequestField("agi_network_script")
  private String script;
  @RequestField("agi_threadid")
  private String threadId;
  @RequestField("agi_version")
  private String version;
  @RequestField("agi_enhanced")
  private boolean enhanced;
  @RequestField("agi_network")
  private boolean network;
  @RequestField("agi_callingani2")
  private int callingAni2;
  @RequestField("agi_callingpres")
  private int callingPres;
  @RequestField("agi_callingtns")
  private int callingTns;
  @RequestField("agi_callington")
  private int callingTon;

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

    List<String> args = new ArrayList<String>();

    for (String line : lines) {

      String key = line.split(":")[0].trim();


      if (line.split(":").length < 2) {
        fieldsMap.put(line.split(":")[0].trim(), null);
        continue;
      }


      String value = line.split(":")[1].trim();

      if (key.equals("agi_request") && (line.split(":").length == 3)) {
        value = value + ":" + line.split(":")[2].trim();
      }

      if(key.matches("agi_arg_(.*)")) {
        args.add(value);
      }

      fieldsMap.put(key, value);
    }

    arguments = args.toArray(new String[args.size()]);

    try {
      fillFields();
    } catch (IllegalArgumentException ex) {
      LOG.warn(ex.getMessage());
    } catch (IllegalAccessException ex) {
      LOG.warn(ex.getMessage());
    }

    queryParameters = new HashMap<String, String>();

    List<NameValuePair> params = null;
    try {
        params = URLEncodedUtils.parse(new URI(script), "UTF-8");
        for (NameValuePair param : params) {
            queryParameters.put(param.getName(), param.getValue());
        }
    } catch (URISyntaxException ex) {
        LOG.warn(ex.getMessage());
    }


  }

  // Set a variables by using reflection.
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
          // There is an exception for the variables:
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
          } else if (f.getType().equals(int.class)) {
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
   * Gets the identifier for the origin
   * <code>channel</code>.
   *
   * @return the account code of the origin channel.
   */
  public String getAccountCode() {
    return accountCode;
  }

  /**
   * An ID that help identify a particular call in the telephone system and
   * later in the database(Call Detail Record).
   *
   * @return the unique ID for the call.
   */
  public String getCallId() {
    return callId;
  }

  /**
   * Returns the ID of the origin caller.
   *
   * @return the caller ID number (or "unknown").
   */
  public String getCallerId() {
    return callerId;
  }

  /**
   * Geet the caller id name only(for example: "John Doe")
   *
   * @return the caller Id Name (or "unknown").
   */
  public String getCallerIdName() {
    return callerIdName;
  }

  /**
   * ANI2 (Info digits) also called Originating line information or OLI.
   * <p>Possible codes are listed can be founded at:
   * http://www.nanpa.com/number_resource_info/ani_ii_assignments.html
   *
   * @return a code with information about the line.
   */
  public int getCallingAni2() {
    return callingAni2;
  }

  /**
   * Caller ID presentation for incoming calls (PRI channels).
   *
   * @return a number that can be decode using {@link PresentationType}.
   */
  public int getCallingPres() {
    return callingPres;
  }

  /**
   * An optional 4 digit number (Transit Network Selector) used in PRI
   * Channels.
   * <p>
   * See http://www.voip-info.org/wiki/view/Asterisk+Detailed+Variable+List
   *
   * @return a code with the TNS (transit network selector).
   */
  public int getCallingTns() {
    return callingTns;
  }

  /**
   * Caller Type of Number (PRI channels).
   *
   * @return a number that can be decode using (@link TonType).
   */
  public int getCallingTon() {
    return callingTon;
  }

  /**
   * The originating channel (your phone).
   *
   * @return the calling phone.
   */
  public String getChannel() {
    return channel;
  }

  /**
   * Helps identify the type of channel(define in {@link ChannelType}).
   *
   * @return the channel type (for instance ZAP, SIP, H323, IAX).
   */
  public ChannelType getChannelType() {
    return channelType;
  }

  /**
   * Returns the
   * <code>context</code> that was use to place the call.
   *
   * @return the current context.
   */
  public String getContext() {
    return context;
  }

  /**
   * Returns Dialed Number Identifier(DnId).
   *
   * @return the dialed number id.
   */
  public String getDnId() {
    return dnId;
  }

  /**
   * Returns the called number, found in
   * <code>extension.conf</code>.
   *
   * @return the called number.
   */
  public String getExtension() {
    return extension;
  }

  /**
   * Language for the current
   * <code>channel</code>.
   *
   * @return the language code(for example 'en').
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns an array with all the queryParameters.
   *
   * @return the array with all queryParameters that conform this request.
   */
  public ArrayList<String> getLines() {
    return lines;
  }

  /**
   * Returns a parameter by parameter name.
   *
   * @param parameter parameter name.
   * @return the value of the parameter.
   */
  public String getQueryParameter(String parameter) {
    return queryParameters.get(parameter);
  }

  /**
    * Returns the arguments passed to the astivlet.
    *
    * @return an array with the arguments of this application
    */
  public String[] getArguments() {
        return arguments;
  }

  /**
   * The priority it was executed as in the dialplan.
   *
   * @return current priority in the dialplan.
   */
  public String getPriority() {
    return priority;
  }

  /**
   * Returns the Redirected Dial Number ID Service(RDNIS).
   *
   * @return the referring DNIS number.
   */
  public String getRdNis() {
    return rdNis;
  }

  /**
   * Name of the AGI script that is being called.
   *
   * @return the script file name.
   */
  public String getRequestURL() {
    return requestURL;
  }

  /**
   * Returns the name remote AGI script.
   *
   * @return the name of the fastagi script.
   */
  public String getScript() {
    return script;
  }

  /**
   * Thread ID of the AGI script (since Asterisk 1.6)
   *
   * @return the thread identifier.
   */
  public String getThreadId() {
    return threadId;
  }

  /**
   * Returns the version of Asterisk (since Asterisk 1.6).
   *
   * @return the asterisk version.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Whether this AGI is passed audio (EAGI - Enhanced AGI).
   *
   * @return true if started as an EAGI script, false otherwise.
   */
  public boolean isEnhanced() {
    return enhanced;
  }

  /**
   * Whether or not this request was sent through a network.
   *
   * @return true only when using fastagi.
   */
  public boolean isNetwork() {
    return network;
  }

  /**
   * Returns representation of this request in string format.
   *
   * @return the request in string format.
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
