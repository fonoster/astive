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
package com.fonoster.astive.astivlet;

import java.util.Date;
import java.util.TimeZone;
import com.fonoster.astive.agi.AgiCommandReply;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.AgiResponse;
import com.fonoster.astive.agi.ChannelStatus;
import com.fonoster.astive.agi.SpeechRecognitionResult;

/**
 * Extends the {@link AgiResponse} interface to provide astivlets, with the
 * capability of response to client requests.
 *
 * @since 1.0
 * @see AstivletRequest
 */
public class AstivletResponse implements AgiResponse {
    private AgiResponse response;

    /**
     * Creates a new AstivletRequest object with
     * <code>null</code> response.
     */
    public AstivletResponse() {
    }

    /**
     * Creates a new AstivletRequest object with response as parameter.
     *
     * @param response an object that implements the interface {
     * @AgiResponse}
     */
    public AstivletResponse(AgiResponse response) {
        this.response = response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void answer() throws AgiException {
        response.answer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void asyncAgiBreak() throws AgiException {
        response.asyncAgiBreak();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlStreamFile(String file) throws AgiException {
        response.controlStreamFile(file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char controlStreamFile(String file, String escapeDigits)
            throws AgiException {
        return response.controlStreamFile(file, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char controlStreamFile(String file, String escapeDigits, int offset)
            throws AgiException {
        return response.controlStreamFile(file, escapeDigits, offset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char controlStreamFile(String file, String escapeDigits, int offset, char forwardDigit,
            char rewindDigit, char pauseDigit)
            throws AgiException {
        return response.controlStreamFile(file, escapeDigits, offset, forwardDigit, rewindDigit,
                pauseDigit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void databaseDel(String family, String key) throws AgiException {
        response.databaseDel(family, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void databaseDelTree(String family) throws AgiException {
        response.databaseDelTree(family);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void databaseDelTree(String family, String keyTree)
            throws AgiException {
        response.databaseDelTree(family, keyTree);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String databaseGet(String family, String key)
            throws AgiException {
        return response.databaseGet(family, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void databasePut(String family, String key, String value)
            throws AgiException {
        response.databasePut(family, key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(String application) throws AgiException {
        response.exec(application);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(String application, String... args)
            throws AgiException {
        response.exec(application, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChannelStatus getChannelStatus() throws AgiException {
        return response.getChannelStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChannelStatus getChannelStatus(String channel)
            throws AgiException {
        return response.getChannelStatus(channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getData(String file) throws AgiException {
        return response.getData(file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getData(String file, int timeout) throws AgiException {
        return response.getData(file, timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getData(String file, int timeout, int maxDigits)
            throws AgiException {
        return response.getData(file, timeout, maxDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFullVariable(String variable) throws AgiException {
        return response.getFullVariable(variable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFullVariable(String variable, String channel)
            throws AgiException {
        return response.getFullVariable(variable, channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getOption(String file, String escapeDigits)
            throws AgiException {
        return response.getOption(file, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getOption(String file, String escapeDigits, int timeout)
            throws AgiException {
        return response.getOption(file, escapeDigits, timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVariable(String variable) throws AgiException {
        return response.getVariable(variable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gosub(String context, String extension, String priority)
            throws AgiException {
        response.gosub(context, extension, priority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gosub(String context, String extension, String priority, String... arguments)
            throws AgiException {
        response.gosub(context, extension, priority, arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hangup() throws AgiException {
        response.hangup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void musicOnHold(boolean on) throws AgiException {
        response.musicOnHold(on);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void musicOnHold(boolean on, String musicClass)
            throws AgiException {
        response.musicOnHold(on, musicClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void noop() throws AgiException {
        response.noop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char receiveChar() throws AgiException {
        return response.receiveChar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char receiveChar(int timeout) throws AgiException {
        return response.receiveChar(timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String receiveText() throws AgiException {
        return response.receiveText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String receiveText(int timeout) throws AgiException {
        return response.receiveText(timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void recordFile(String file, String format) throws AgiException {
        response.recordFile(file, format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char recordFile(String file, String format, String escapeDigits)
            throws AgiException {
        return response.recordFile(file, format, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char recordFile(String file, String format, String escapeDigits, int timeout)
            throws AgiException {
        return response.recordFile(file, format, escapeDigits, timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char recordFile(String file, String format, String escapeDigits, int timeout, int offset,
            boolean beep, int maxSilence)
            throws AgiException {
        return response.recordFile(file, format, escapeDigits, timeout, offset, beep, maxSilence);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayAlpha(String text) throws AgiException {
        response.sayAlpha(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayAlpha(String text, String escapeDigits)
            throws AgiException {
        return response.sayAlpha(text, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayDate(Date date) throws AgiException {
        response.sayDate(date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayDate(Date date, String escapeDigits) throws AgiException {
        return response.sayDate(date, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayDatetime(Date datetime) throws AgiException {
        response.sayDatetime(datetime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayDatetime(Date datetime, String escapeDigits)
            throws AgiException {
        return response.sayDatetime(datetime, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayDatetime(Date datetime, String escapeDigits, String format)
            throws AgiException {
        return response.sayDatetime(datetime, escapeDigits, format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayDatetime(Date datetime, String escapeDigits, String format, TimeZone timeZone)
            throws AgiException {
        return response.sayDatetime(datetime, escapeDigits, format, timeZone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayDigits(String digits) throws AgiException {
        response.sayDigits(digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayDigits(String digits, String escapeDigits)
            throws AgiException {
        return response.sayDigits(digits, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayNumber(int number) throws AgiException {
        response.sayNumber(number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayNumber(int number, String escapeDigits)
            throws AgiException {
        return response.sayNumber(number, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayPhonetic(String text) throws AgiException {
        response.sayPhonetic(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayPhonetic(String text, String escapeDigits)
            throws AgiException {
        return response.sayPhonetic(text, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sayTime(Date time) throws AgiException {
        response.sayTime(time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char sayTime(Date time, String escapeDigits) throws AgiException {
        return response.sayTime(time, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AgiCommandReply sendAgiCommand(String cmd) throws AgiException {
        return response.sendAgiCommand(cmd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendImage(String image) throws AgiException {
        response.sendImage(image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendText(String text) throws AgiException {
        response.sendText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAutoHangup(int time) throws AgiException {
        response.setAutoHangup(time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCallerId(String callerId) throws AgiException {
        response.setCallerId(callerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContext(String context) throws AgiException {
        response.setContext(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtension(String extension) throws AgiException {
        response.setExtension(extension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPriority(String priority) throws AgiException {
        response.setPriority(priority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTddMode(boolean on) throws AgiException {
        response.setTddMode(on);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechActivateGrammar(String name) throws AgiException {
        response.speechActivateGrammar(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechCreate() throws AgiException {
        response.speechCreate("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechCreate(String engine) throws AgiException {
        response.speechCreate(engine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechDeactivateGrammar(String name) throws AgiException {
        response.speechDeactivateGrammar(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechDestroy() throws AgiException {
        response.speechDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechLoadGrammar(String name, String path)
            throws AgiException {
        response.speechLoadGrammar(name, path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpeechRecognitionResult speechRecognize(String prompt, int timeout)
            throws AgiException {
        return response.speechRecognize(prompt, timeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechSet(String name, String value) throws AgiException {
        response.speechSet(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void speechUnloadGrammar(String name) throws AgiException {
        response.speechUnloadGrammar(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void streamFile(String file) throws AgiException {
        response.streamFile(file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char streamFile(String file, String escapeDigits)
            throws AgiException {
        return response.streamFile(file, escapeDigits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char streamFile(String file, String escapeDigits, int offset)
            throws AgiException {
        return response.streamFile(file, escapeDigits, offset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verbose(String message, int level) throws AgiException {
        response.verbose(message, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char waitForDigit(int interDigitsTimeout) throws AgiException {
        return response.waitForDigit(interDigitsTimeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hangup(String channel) throws AgiException {
        response.hangup(channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVar(String variable, String value) throws AgiException {
        response.setVar(variable, value);
    }
}
