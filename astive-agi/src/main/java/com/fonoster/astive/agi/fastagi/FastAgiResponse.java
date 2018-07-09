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

import java.util.Date;
import java.util.TimeZone;
import com.fonoster.astive.agi.*;
import com.fonoster.astive.agi.command.*;
import com.fonoster.astive.util.AppLocale;

/**
 * Final implementation of {@link AgiResponse} use for fast agi servers.
 *
 * @since 1.0
 * @see AgiResponse
 * @see AstivletResponse
 */
public class FastAgiResponse implements AgiResponse {
  private AgiCommandHandler cHandler;

  /**
   * Create a new FastAgiResponse with a {@link AgiCommandHandler} as a
   * parameter.
   *
   * @param cHandler command handler.
   */
  public FastAgiResponse(AgiCommandHandler cHandler) {
    this.cHandler = cHandler;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void answer() throws AgiException {
    Answer command = new Answer();
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void asyncAgiBreak() throws AgiException {
    AsyncAgiBreak command = new AsyncAgiBreak();
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void controlStreamFile(String file) throws AgiException {
    ControlStreamFile command = new ControlStreamFile(file);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorUnsupportedCommand"));
    } else if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char controlStreamFile(String file, String escapeDigits)
                         throws AgiException {
    ControlStreamFile command = new ControlStreamFile(file);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char controlStreamFile(String file, String escapeDigits, int offset)
                         throws AgiException {
    ControlStreamFile command = new ControlStreamFile(file, escapeDigits, offset);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char controlStreamFile(String file, String escapeDigits, int offset, char forwardDigit,
                                char rewindDigit, char pauseDigit)
                         throws AgiException {
    ControlStreamFile command =
      new ControlStreamFile(file, escapeDigits, offset, forwardDigit, rewindDigit, pauseDigit);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void databaseDel(String family, String key) throws AgiException {
    DatabaseDel command = new DatabaseDel(family, key);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void databaseDelTree(String family) throws AgiException {
    DatabaseDelTree command = new DatabaseDelTree(family);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void databaseDelTree(String family, String keyTree)
                       throws AgiException {
    DatabaseDelTree command = new DatabaseDelTree(family, keyTree);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String databaseGet(String family, String key)
                     throws AgiException {
    DatabaseGet command = new DatabaseGet(family, key);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void databasePut(String family, String key, String value)
                   throws AgiException {
    DatabasePut command = new DatabasePut(family, key, value);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void exec(String application) throws AgiException {
    Exec command = new Exec(application);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -2) {
        throw new AgiException(AppLocale.getI18n("errorFailureToFindApp"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void exec(String application, String... args)
            throws AgiException {
    Exec command = new Exec(application, args);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -2) {
        throw new AgiException(AppLocale.getI18n("errorFailureToFindApp"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChannelStatus getChannelStatus() throws AgiException {
    GetChannelStatus command = new GetChannelStatus();
    int code = cHandler.sendAgiCommand(command).getResultCode();

    return ChannelStatus.get(code);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChannelStatus getChannelStatus(String channel)
                                 throws AgiException {
    GetChannelStatus command = new GetChannelStatus(channel);
    int code = cHandler.sendAgiCommand(command).getStatus();

    return ChannelStatus.get(code);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getData(String file) throws AgiException {
    GetData command = new GetData(file);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getData(String file, int timeout) throws AgiException {
    GetData command = new GetData(file, timeout);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getData(String file, int timeout, int maxDigits)
                 throws AgiException {
    GetData command = new GetData(file, timeout, maxDigits);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFullVariable(String variable) throws AgiException {
    GetFullVariable command = new GetFullVariable(variable);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFullVariable(String variable, String channel)
                         throws AgiException {
    GetFullVariable command = new GetFullVariable(variable, channel);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char getOption(String file, String escapeDigits)
                 throws AgiException {
    GetOption command = new GetOption(file, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char getOption(String file, String escapeDigits, int timeout)
                 throws AgiException {
    GetOption command = new GetOption(file, escapeDigits, timeout);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVariable(String variable) throws AgiException {
    GetVariable command = new GetVariable(variable);

    return cHandler.sendAgiCommand(command).getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void gosub(String context, String extension, String priority)
             throws AgiException {
    GoSub command = new GoSub(context, extension, priority);

    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void gosub(String context, String extension, String priority, String... arguments)
             throws AgiException {
    GoSub command = new GoSub(context, extension, priority, arguments);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hangup() throws AgiException {
    Hangup command = new Hangup();
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void musicOnHold(boolean on) throws AgiException {
    SetMusic command = new SetMusic(on);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void musicOnHold(boolean on, String musicClass)
                   throws AgiException {
    SetMusic command = new SetMusic(on, musicClass);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void noop() throws AgiException {
    Noop command = new Noop();
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char receiveChar() throws AgiException {
    ReceiveChar command = new ReceiveChar();

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorUnsupportedChannel"));
    } else if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char receiveChar(int timeout) throws AgiException {
    ReceiveChar command = new ReceiveChar(timeout);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorUnsupportedChannel"));
    } else if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String receiveText() throws AgiException {
    ReceiveText command = new ReceiveText();

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String receiveText(int timeout) throws AgiException {
    ReceiveText command = new ReceiveText(timeout);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recordFile(String file, String format) throws AgiException {
    RecordFile command = new RecordFile(file, format);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char recordFile(String file, String format, String escapeDigits)
                  throws AgiException {
    RecordFile command = new RecordFile(file, format, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char recordFile(String file, String format, String escapeDigits, int timeout)
                  throws AgiException {
    RecordFile command = new RecordFile(file, format, escapeDigits, timeout);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char recordFile(String file, String format, String escapeDigits, int timeout, int offset,
                         boolean beep, int maxSilence)
                  throws AgiException {
    RecordFile command =
      new RecordFile(file, format, escapeDigits, timeout, offset, beep, maxSilence);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayAlpha(String text) throws AgiException {
    SayAlpha command = new SayAlpha(text);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayAlpha(String text, String escapeDigits)
                throws AgiException {
    SayAlpha command = new SayAlpha(text);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayDate(Date date) throws AgiException {
    SayDate command = new SayDate(date);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayDate(Date date, String escapeDigits) throws AgiException {
    SayDate command = new SayDate(date, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayDatetime(Date datetime) throws AgiException {
    SayDatetime command = new SayDatetime(datetime);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayDatetime(Date datetime, String escapeDigits)
                   throws AgiException {
    SayDatetime command = new SayDatetime(datetime, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayDatetime(Date datetime, String escapeDigits, String format)
                   throws AgiException {
    SayDatetime command = new SayDatetime(datetime, escapeDigits, format);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayDatetime(Date datetime, String escapeDigits, String format, TimeZone timeZone)
                   throws AgiException {
    SayDatetime command = new SayDatetime(datetime, escapeDigits, format, timeZone);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayDigits(String digits) throws AgiException {
    SayDigits command = new SayDigits(digits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayDigits(String digits, String escapeDigits)
                 throws AgiException {
    SayDigits command = new SayDigits(digits, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayNumber(int number) throws AgiException {
    SayNumber command = new SayNumber(number);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayNumber(int number, String escapeDigits)
                 throws AgiException {
    SayNumber command = new SayNumber(number, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayPhonetic(String text) throws AgiException {
    SayPhonetic command = new SayPhonetic(text);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayPhonetic(String text, String escapeDigits)
                   throws AgiException {
    SayPhonetic command = new SayPhonetic(text, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sayTime(Date time) throws AgiException {
    SayTime command = new SayTime(time);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char sayTime(Date time, String escapeDigits) throws AgiException {
    SayTime command = new SayTime(time, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendImage(String image) throws AgiException {
    SendImage command = new SendImage(image);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    // This makes no sense:
    // Returns 0 if image is sent, or if the channel does not support image
    // transmission(official documentation for command 'SEND IMAGE').
    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorUnsupportedChannel"));
    } else if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendText(String text) throws AgiException {
    SendText command = new SendText(text);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    // This makes no sense:
    // Returns 0 if image is sent, or if the channel does not support image
    // transmission(official documentation for command 'SEND TEXT').
    if(acr.getResultCode() == 0) {
        throw new AgiException(AppLocale.getI18n("errorUnsupportedChannel"));
    } else if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAutoHangup(int time) throws AgiException {
    SetAutoHangup command = new SetAutoHangup(time);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCallerId(String callerId) throws AgiException {
    SetCallerId command = new SetCallerId(callerId);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContext(String context) throws AgiException {
    SetContext command = new SetContext(context);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setExtension(String extension) throws AgiException {
    SetExtension command = new SetExtension(extension);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPriority(String priority) throws AgiException {
    SetPriority command = new SetPriority(priority);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTddMode(boolean on) throws AgiException {
    TddMode command = new TddMode(on);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelNotTddCapable"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechActivateGrammar(String name) throws AgiException {
    SpeechActivateGrammar command = new SpeechActivateGrammar(name);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechCreate() throws AgiException {
    speechCreate("");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechCreate(String engine) throws AgiException {
    SpeechCreate command = new SpeechCreate(engine);
    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if (acr.getResultCode() != 1) {
      if ((engine == null) || "".equals(engine)) {
        throw new AgiException(AppLocale.getI18n("errorCantCreateSpeechObjectForDefaultEngine"));
      } else {
        throw new AgiException(AppLocale.getI18n("errorCantCreateSpeechObject", new Object[] { engine }));
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechDeactivateGrammar(String name) throws AgiException {
    SpeechDeactivateGrammar command = new SpeechDeactivateGrammar(name);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechDestroy() throws AgiException {
    SpeechDestroy command = new SpeechDestroy();
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechLoadGrammar(String name, String path)
                         throws AgiException {
    SpeechLoadGrammar command = new SpeechLoadGrammar(name, path);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SpeechRecognitionResult speechRecognize(String prompt, int timeout)
                                          throws AgiException {
    SpeechRecognize command = new SpeechRecognize(prompt, timeout);
    AgiCommandReply reply = cHandler.sendAgiCommand(command);
    SpeechRecognitionResult result = new SpeechRecognitionResult(reply);

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechSet(String name, String value) throws AgiException {
    SpeechSet command = new SpeechSet(name, value);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void speechUnloadGrammar(String name) throws AgiException {
    SpeechUnloadGrammar command = new SpeechUnloadGrammar(name);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void streamFile(String file) throws AgiException {
    StreamFile command = new StreamFile(file);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char streamFile(String file, String escapeDigits)
                  throws AgiException {
    StreamFile command = new StreamFile(file, escapeDigits);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char streamFile(String file, String escapeDigits, int offset)
                  throws AgiException {
    StreamFile command = new StreamFile(file, escapeDigits, offset);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void verbose(String message, int level) throws AgiException {
    Verbose command = new Verbose(message, level);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char waitForDigit(int interDigitsTimeout) throws AgiException {
    WaitForDigit command = new WaitForDigit(interDigitsTimeout);

    AgiCommandReply acr = cHandler.sendAgiCommand(command);

    if(acr.getResultCode() == -1) {
        throw new AgiException(AppLocale.getI18n("errorChannelErrorOrDisconnected"));
    }

    return acr.getResultCodeAsChar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hangup(String channel) throws AgiException {
    Hangup command = new Hangup(channel);
    cHandler.sendAgiCommand(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVar(String variable, String value) throws AgiException {
    SetVariable command = new SetVariable(variable, value);
    cHandler.sendAgiCommand(command);
  }

  @Override
  public AgiCommandReply sendAgiCommand(String cmd) throws AgiException {
    return cHandler.sendAgiCommand(cmd);
  }
}
