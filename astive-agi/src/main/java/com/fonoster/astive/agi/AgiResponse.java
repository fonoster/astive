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

import java.util.Date;
import java.util.TimeZone;

/**
 * API with all commands supported by the AGI protocol, since Asterisk version
 * 1.0.
 *
 * @since 1.0
 */
public interface AgiResponse {

    /**
     * Answers channel if not already in answer state.
     *
     * @throws AgiException if the command can't be sent to Asterisk (for
     * example because the channel has been hung up).
     */
    void answer() throws AgiException;

    /**
     * Interrupts expected flow of Async AGI commands and returns control to
     * previous source (typically, the PBX dialplan).
     *
     * @throws AgiException
     */
    void asyncAgiBreak() throws AgiException;

    /**
     * Returns the status of the current channel.
     *
     * @param channel the channel to check.
     * @return the current channel status.
     * @see ChannelStatus
     */
    ChannelStatus getChannelStatus() throws AgiException;

    /**
     * Returns the status of the specified
     * <code>channel</code>. If no channel name is given then returns the status
     * of the current channel.
     *
     * @param channel the channel to check.
     * @return the current channel status.
     * @see ChannelStatus
     */
    ChannelStatus getChannelStatus(String channel) throws AgiException;

    /**
     * Sends audio file on channel and allows the listener to control the
     * stream.
     *
     * @param file the file extension must not be included in the filename.
     * @throws AgiException
     */
    void controlStreamFile(String file) throws AgiException;

    /**
     * Sends audio file on channel and allows the listener to control the
     * stream.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char controlStreamFile(String file, String escapeDigits)
            throws AgiException;

    /**
     * Sends audio file on channel and allows the listener to control the
     * stream.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits allow users to finish stream.
     * @param offset silence time(in milliseconds) before play stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char controlStreamFile(String file, String escapeDigits, int offset)
            throws AgiException;

    /**
     * Sends audio file on channel and allows the listener to control the
     * stream.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits allow users to finish stream.
     * @param offset silence time(in milliseconds) before play stream.
     * @param forwardDigit allow user to move stream forward. (Defaults to *)
     * @param rewindDigit start over the stream. (Defaults to #)
     * @param pauseDigit allow user to stop stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char controlStreamFile(String file, String escapeDigits, int offset, char forwardDigit,
            char rewindDigit, char pauseDigit)
            throws AgiException;

    /**
     * Deletes an entry in the Asterisk database for a given family and key.
     *
     * @param family the database family.
     * @param key the key of entry to be remove.
     * @throws AgiException
     */
    void databaseDel(String family, String key) throws AgiException;

    /**
     * Deletes a family within a family in the Asterisk database
     *
     * @param family the family.
     * @throws AgiException
     */
    void databaseDelTree(String family) throws AgiException;

    /**
     * Deletes a family or specific keytree within a family in the Asterisk
     * database
     *
     * @param family the family to be deleted.
     * @param keyTree the specific keytree for the family.
     * @throws AgiException
     */
    void databaseDelTree(String family, String keyTree) throws AgiException;

    /**
     * Retrieves an entry in the Asterisk database for a given family and key.
     *
     * @param family the database family.
     * @param key the entry key.
     * @return a database value.
     * @throws AgiException
     */
    String databaseGet(String family, String key) throws AgiException;

    /**
     * Adds or updates an entry in the Asterisk database for a given family,
     * key, and value.
     *
     * @param family the database family.
     * @param key the entry key.
     * @param value the value for the parameter.
     * @throws AgiException
     */
    void databasePut(String family, String key, String value)
            throws AgiException;

    /**
     * Executes application without options.
     *
     * @param application the application to be executed.
     * @throws AgiException
     */
    void exec(String application) throws AgiException;

    /**
     * Executes application with given options.
     *
     * @param application the application to be executed.
     * @param args array of options needed by the application.
     * @throws AgiException
     */
    void exec(String application, String... args) throws AgiException;

    /**
     * Stream the given
     * <code>file</code>, and receive DTMF data.
     *
     * @param file the file extension must not be included in the filename.
     * @return the digits received from the channel at the other end if any.
     */
    String getData(String file) throws AgiException;

    /**
     * Stream the given
     * <code>file</code>, and receive DTMF data.
     *
     * @param file the file extension must not be included in the filename.
     * @param timeout is the timeout(in milliseconds) to wait for user input. 0
     * means standard timeout value, with -1 the commands wait for ever.
     * @return the digits received from the channel at the other end if any.
     */
    String getData(String file, int timeout) throws AgiException;

    /**
     * Stream the given
     * <code>file</code>, and receive DTMF data.
     *
     * @param file the file extension must not be included in the filename.
     * @param timeout is the timeout(in milliseconds) to wait for user input. 0
     * means standard timeout value, with -1 the commands wait for ever.
     * @param maxDigits the maximum amount of digits that the user can enter.
     * @return the digits received from the channel at the other end if any.
     */
    String getData(String file, int timeout, int maxDigits)
            throws AgiException;

    /**
     * Evaluates a channel expression in the current
     * <code>channel</code>.
     *
     * @param variable the variable must be set and channel exist.
     * @return the value of the variable.
     * @throws AgiException
     */
    String getFullVariable(String variable) throws AgiException;

    /**
     * Evaluates a channel expression in the specified
     * <code>channel</code>.
     *
     * @param variable the variable to retrieve must be set and channel exist.
     * @param channel specific channel.
     * @return the value of variable.
     * @throws AgiException
     */
    String getFullVariable(String variable, String channel)
            throws AgiException;

    /**
     * Behaves similar to STREAM FILE but used with a timeout option.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits contains the digits that the user is expected to
     * press.
     * @return the key press by the user if any.
     */
    char getOption(String file, String escapeDigits) throws AgiException;

    /**
     * Behaves similar to STREAM FILE but used with a timeout option.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits allow users to finish stream.
     * @param timeout the timeout in milliseconds to wait if none of the defined
     * escape digits was presses while streaming.
     * @return the key press by the user if any.
     */
    char getOption(String file, String escapeDigits, int timeout)
            throws AgiException;

    /**
     * Gets a channel variable.
     *
     * @param variable the variable set in the current channel.
     * @return the value of variable if was previously set.
     * @throws AgiException
     */
    String getVariable(String variable) throws AgiException;

    /**
     * Cause the channel to execute the specified dialplan subroutine, returning
     * to the dialplan with execution of a Return().
     *
     * @param context context of the subroutine.
     * @param extension extension within context.
     * @param priority specific priority.
     * @throws AgiException
     */
    void gosub(String context, String extension, String priority)
            throws AgiException;

    /**
     * Cause the channel to execute the specified dialplan subroutine, returning
     * to the dialplan with execution of a Return().
     *
     * @param context context of the subroutine.
     * @param extension extension within context.
     * @param priority specific priority.
     * @param arguments optional arguments
     * @throws AgiException
     */
    void gosub(String context, String extension, String priority, String... arguments)
            throws AgiException;

    /**
     * Hangs up the current channel.
     */
    void hangup() throws AgiException;

    /**
     * Hangs up the specified channel.
     *
     * @param channel specific channel to hangup.
     */
    void hangup(String channel) throws AgiException;

    /**
     * Does nothing.
     *
     * @throws AgiException
     */
    void noop() throws AgiException;

    /**
     * Receives a character of text on a <code>channel</code>. Most channels do
     * not support the reception of text.
     *
     * @return the character if one is received.
     * @throws AgiException if channel doesn't support this operation.
     */
    char receiveChar() throws AgiException;

    /**
     * Receives one character from channels supporting it.
     *
     * @param timeout maximum time to wait for input in milliseconds or 0 for
     * infinite.
     * @return the character if one is received.
     * @throws AgiException if channel doesn't support this operation.
     */
    char receiveChar(int timeout) throws AgiException;

    /**
     * Receives a string of text on a channel. Most channels do not support the
     * reception of text.
     *
     * @return the text receive by the channel.
     * @throws AgiException if channel doesn't support this operation.
     */
    String receiveText() throws AgiException;

    /**
     * Receives a string of text on a channel. Most channels do not support the
     * reception of text.
     *
     * @param timeout maximum time to wait for input in milliseconds or 0 for
     * infinite.
     * @return the text receive by the channel.
     * @throws AgiException if channel doesn't support this operation.
     */
    String receiveText(int timeout) throws AgiException;

    /**
     * Record to a file until a given DTMF digit in the sequence is received.
     *
     * @param file the file extension must not be included in the filename.
     * @param format the format of the file to be recorded. For instance:
     * <code>wav</code>.
     * @throws AgiException
     */
    void recordFile(String file, String format) throws AgiException;

    /**
     * Record to a file until a given DTMF digit in the sequence is received.
     *
     * @param file the file extension must not be included in the filename.
     * @param format the format of the file to be recorded. For instance:
     * <code>wav</code>
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char recordFile(String file, String format, String escapeDigits)
            throws AgiException;

    /**
     * Record to a file until a given DTMF digit in the sequence is received.
     *
     * @param file the file extension must not be included in the filename.
     * @param format the format of the file to be recorded. For instance:
     * <code>wav</code>
     * @param escapeDigits allow users to finish stream.
     * @param timeout the maximum record time in milliseconds, or -1 for no
     * timeout.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char recordFile(String file, String format, String escapeDigits, int timeout)
            throws AgiException;

    /**
     * Record to a file until a given DTMF digit in the sequence is received.
     *
     * @param file the file extension must not be included in the filename.
     * @param format the format of the file to be recorded, for instance "wav".
     * @param escapeDigits allow users to finish stream.
     * @param timeout the maximum record time in milliseconds, or -1 for no
     * timeout.
     * @param offset the offset samples to skip.
     * @param beep <code>true</code> if a beep should be played before
     * recording.
     * @param maxSilence The amount of silence (in seconds) to allow before
     * returning despite the lack of DTMF digits or reaching timeout.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char recordFile(String file, String format, String escapeDigits, int timeout, int offset,
            boolean beep, int maxSilence) throws AgiException;

    /**
     * Says the given character string.
     *
     * @param text the text to say.
     * @throws AgiException
     */
    void sayAlpha(String text) throws AgiException;

    /**
     * Says the given character string, returning early if any of the given DTMF
     * number are received on the channel.
     *
     * @param text the text to say.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayAlpha(String text, String escapeDigits) throws AgiException;

    /**
     * Says the given date.
     *
     * @param date the date to say
     * @throws AgiException
     */
    void sayDate(Date date) throws AgiException;

    /**
     * Says the given date, returning early if any of the given DTMF number are
     * received on the channel.
     *
     * @param date the date to say.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayDate(Date date, String escapeDigits) throws AgiException;

    /**
     * Says a given time as specified by the format given.
     *
     * @param datetime the date and to to say.
     * @throws AgiException
     */
    void sayDatetime(Date datetime) throws AgiException;

    /**
     * Say a given time, returning early if any of the given DTMF digits are
     * received on the channel.
     *
     * @param datetime the number of seconds elapsed since 00:00:00 on January
     * 1, 1970, Coordinated Universal Time (UTC).
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayDatetime(Date datetime, String escapeDigits)
            throws AgiException;

    /**
     * Say a given time, returning early if any of the given DTMF digits are
     * received on the channel.
     *
     * @param datetime the number of seconds elapsed since 00:00:00 on January
     * 1, 1970, Coordinated Universal Time (UTC).
     * @param escapeDigits allow users to finish stream.
     * @param format the format the time should be said in. See voicemail.conf
     * (defaults to ABdY 'digits/at' IMp).
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayDatetime(Date datetime, String escapeDigits, String format)
            throws AgiException;

    /**
     * Say a given time, returning early if any of the given DTMF digits are
     * received on the channel.
     *
     * @param datetime the number of seconds elapsed since 00:00:00 on January
     * 1, 1970, Coordinated Universal Time (UTC).
     * @param escapeDigits allow users to finish stream.
     * @param format the format the time should be said in. See voicemail.conf
     * (defaults to ABdY 'digits/at' IMp).
     * @param timeZone acceptable values can be found in /usr/share/zoneinfo
     * Defaults to machine default.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayDatetime(Date datetime, String escapeDigits, String format, TimeZone timeZone)
            throws AgiException;

    /**
     * Say a given digit string.
     *
     * @param digits the digits to say.
     */
    void sayDigits(String digits) throws AgiException;

    /**
     * Say a given digit string returning early if any of the given DTMF number
     * are received on the channel.
     *
     * @param digits the digits to say.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayDigits(String digits, String escapeDigits) throws AgiException;

    /**
     * Says a given number.
     *
     * @param number the number to say.
     * @throws AgiException
     */
    void sayNumber(int number) throws AgiException;

    /**
     * Say a given number, returning early if any of the given DTMF digits are
     * received on the channel.
     *
     * @param number the number to say.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayNumber(int number, String escapeDigits) throws AgiException;

    /**
     * Says the given character string with phonetics.
     *
     * @param text the text to say.
     * @throws AgiException
     */
    void sayPhonetic(String text) throws AgiException;

    /**
     * Says the given character string with phonetics, returning early if any of
     * the given DTMF number are received on the channel.
     *
     * @param text the text to say.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayPhonetic(String text, String escapeDigits) throws AgiException;

    /**
     * Says a given time.
     *
     * @param time the number of seconds elapsed since 00:00:00 on January 1,
     * 1970. Coordinated Universal Time (UTC).
     * @throws AgiException
     */
    void sayTime(Date time) throws AgiException;

    /**
     * Say a given time, returning early if any of the given DTMF digits are
     * received on the channel.
     *
     * @param time the number of seconds elapsed since 00:00:00 on January 1,
     * 1970. Coordinated Universal Time (UTC).
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char sayTime(Date time, String escapeDigits) throws AgiException;

    /**
     * Sends the given image on a channel. Most channels do not support the
     * transmission of images.
     *
     * @param image the image to send.
     * @throws AgiException
     */
    void sendImage(String image) throws AgiException;

    /**
     * Sends the given text on a channel. Most channels do not support the
     * transmission of text.
     *
     * @param text the text to send.
     * @throws AgiException
     */
    void sendText(String text) throws AgiException;

    /**
     * Cause the channel to automatically hangup at <code>time</code> seconds in
     * the future.
     *
     * @param time the seconds to hangup channel in the future.
     * @throws AgiException
     */
    void setAutoHangup(int time) throws AgiException;

    /**
     * Changes the <code>callerid</code> of the current channel.
     *
     * @param callerId the new caller id number.
     * @throws AgiException
     */
    void setCallerId(String callerId) throws AgiException;

    /**
     * Sets the context for continuation upon exiting the application.
     *
     * @param context the desired context.
     * @throws AgiException
     */
    void setContext(String context) throws AgiException;

    /**
     * Changes the extension for continuation upon exiting the application.
     *
     * @param extension the new extension.
     * @throws AgiException
     */
    void setExtension(String extension) throws AgiException;

    /**
     * Enables/Disables the music on hold generator, using the default music on
     * hold class.
     *
     * @param on true enables the music on hold.
     * @throws AgiException
     */
    void musicOnHold(boolean on) throws AgiException;

    /**
     * Enables/Disables the music on hold generator for an specific music class.
     *
     * @param on true enables the music on hold.
     * @param musicClass the music class to enable/disable.
     * @throws AgiException
     */
    void musicOnHold(boolean on, String musicClass) throws AgiException;

    /**
     * Changes the priority for continuation upon exiting the application.
     *
     * @param priority the priority must be a valid priority or label.
     * @throws AgiException
     */
    void setPriority(String priority) throws AgiException;

    /**
     * Sets a variable to the current channel.
     *
     * @param variable the variable to be set.
     * @param value the value for the variable.
     * @throws AgiException
     */
    void setVar(String variable, String value) throws AgiException;

    /**
     * Activates the specified grammar on the speech object.
     *
     * @param name the grammar name.
     * @throws AgiException
     */
    void speechActivateGrammar(String name) throws AgiException;

    /**
     * Creates a speech object that uses the default speech engine. The speech
     * object is used by the other speech methods and must be created before
     * they are called.
     *
     * @throws AgiException
     */
    void speechCreate() throws AgiException;

    /**
     * Create a speech object to be used by the other Speech AGI commands.
     *
     * @param engine the name of the speech engine.
     * @throws AgiException
     */
    void speechCreate(String engine) throws AgiException;

    /**
     * Deactivates the specified grammar on the speech object.
     *
     * @param name the grammar name.
     * @throws AgiException
     */
    void speechDeactivateGrammar(String name) throws AgiException;

    /**
     * Destroys the current speech object.
     *
     * @throws AgiException
     */
    void speechDestroy() throws AgiException;

    /**
     * Loads the specified grammar as the specified name.
     *
     * @param name the name of the grammar.
     * @param path the path to the grammar to load.
     * @throws AgiException
     */
    void speechLoadGrammar(String name, String path) throws AgiException;

    /**
     * Plays back given prompt while listening for speech and dtmf.
     *
     * @param prompt the name of the file to stream, must not include extension.
     * @param timeout the timeout in milliseconds to wait for user input.<p>
     * 0 means standard timeout value, -1 means "ludicrous time" (essentially
     * never times out).
     * @return the recognition result
     * @throws AgiException
     */
    SpeechRecognitionResult speechRecognize(String prompt, int timeout)
            throws AgiException;

    /**
     * Sets the speech engine setting indicated by name to the given value.
     *
     * @param name the name of the setting to set.
     * @param value the value to set.
     * @throws AgiException
     */
    void speechSet(String name, String value) throws AgiException;

    /**
     * Unloads the specified grammar.
     *
     * @param name the grammar.
     * @throws AgiException
     */
    void speechUnloadGrammar(String name) throws AgiException;

    /**
     * Stream the given file.
     *
     * @param file the file extension must not be included in the filename.
     * @throws AgiException
     */
    void streamFile(String file) throws AgiException;

    /**
     * Send the given file, allowing playback to be interrupted by the given
     * digits, if any.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits allow users to finish stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char streamFile(String file, String escapeDigits) throws AgiException;

    /**
     * Send the given file, allowing playback to be interrupted by the given
     * digits, if any.
     *
     * @param file the file extension must not be included in the filename.
     * @param escapeDigits allow users to finish stream.
     * @param offset silence time(in milliseconds) before play stream.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char streamFile(String file, String escapeDigits, int offset)
            throws AgiException;

    /**
     * Enable/Disable TDD transmission/reception on a channel.
     *
     * @param on true to enable TDD false otherwise.
     * @throws AgiException
     */
    void setTddMode(boolean on) throws AgiException;

    /**
     * Sends message to the console via verbose message system.
     *
     * @param message the message to send.
     * @param level the verbose level (1-4).
     * @throws AgiException
     */
    void verbose(String message, int level) throws AgiException;

    /**
     * Waits up to <code>timeout</code> milliseconds for channel to receive a
     * DTMF digit.
     *
     * @param timeout maximum will wait for digit.
     * @return the key press by the user if any.
     * @throws AgiException
     */
    char waitForDigit(int timeout) throws AgiException;

    /**
     * Sends a command to asterisk and returns the corresponding reply.
     *
     * @param cmd the command to send.
     * @return the reply of the asterisk server.
     * @throws AgiException
     */
    AgiCommandReply sendAgiCommand(String cmd) throws AgiException;
}
