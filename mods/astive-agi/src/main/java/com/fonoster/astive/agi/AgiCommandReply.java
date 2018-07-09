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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @since 1.0
 */
public class AgiCommandReply {
    public static final int SC_TRYING = 100;
    public static final int SC_SUCCESS = 200;
    public static final int SC_INVALID_OR_UNKNOWN_COMMAND = 510;
    public static final int SC_DEAD_CHANNEL = 511;
    public static final int SC_INVALID_COMMAND_SYNTAX = 520;

    private static final Pattern STATUS_PATTERN = Pattern.compile("^(\\d{3})[ -]");
    private static final Pattern RESULT_PATTERN = Pattern.compile("^200 result=(\\S+)");
    private static final Pattern PARENTHESIS_PATTERN =
            Pattern.compile("^200 result=\\S* +\\((.*)\\)");
    private static final Pattern ADDITIONAL_ATTRIBUTES_PATTERN =
            Pattern.compile("^200 result=\\S* +(\\(.*\\) )?(.+)$");
    private static final Pattern SYNOPSIS_PATTERN = Pattern.compile("^\\s*Usage:\\s*(.*)\\s*$");
    private static final String END_OF_PROPER_USAGE = "520 End of proper usage.";
    private Integer status;
    private List<String> lines;
    private Map<String, String> attributes;
    private String extra;
    private String firstLine;
    private String result;
    // In case of status == 520 (invalid command syntax) this attribute contains
    // the synopsis of the command.
    private String synopsis;
    // In case of status == 520 (invalid command syntax) this attribute contains
    //the usage of the command.
    private String usage;
    private boolean extraCreated;

    /**
     * Creates a new AgiCommandReply object.
     */
    public AgiCommandReply() {
        super();
        this.status = null;
    }

    /**
     * Creates a new AgiCommandReply object.
     *
     * @param lines
     */
    public AgiCommandReply(List<String> lines) {
        this();

        if (lines != null) {
            this.lines = new ArrayList<String>(lines);

            if (!lines.isEmpty()) {
                firstLine = lines.get(0);
            }
        }
    }

    public String getAttribute(String name) {
        if (getStatus() != SC_SUCCESS) {
            return null;
        }

        if ("result".equalsIgnoreCase(name)) {
            return getResult();
        }

        return getAttributes().get(name.toLowerCase(Locale.ENGLISH));
    }

    protected Map<String, String> getAttributes() {
        if (attributes != null) {
            return Collections.unmodifiableMap(attributes);
        }

        attributes = new HashMap<String, String>();

        final Matcher matcher = ADDITIONAL_ATTRIBUTES_PATTERN.matcher(firstLine);

        if (matcher.find()) {
            attributes.putAll(parseAttributes(matcher.group(2)));
        }

        return Collections.unmodifiableMap(attributes);
    }

    public String getExtra() {
        if (getStatus() != SC_SUCCESS) {
            return null;
        }

        if (extraCreated) {
            return extra;
        }

        final Matcher matcher = PARENTHESIS_PATTERN.matcher(firstLine);

        if (matcher.find()) {
            extra = matcher.group(1);
        }

        extraCreated = true;

        return extra;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public String getResult() {
        if (result != null) {
            return result;
        }

        final Matcher matcher = RESULT_PATTERN.matcher(firstLine);

        if (matcher.find()) {
            result = matcher.group(1);
        } else {
            result = "";
        }

        return result;
    }

    public int getResultCode() {
        result = getResult();

        if (result == null) {
            return -1;
        }

        try {
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public char getResultCodeAsChar() {
        int resultCode;

        resultCode = getResultCode();

        if (resultCode < 0) {
            return 0;
        }

        return (char) resultCode;
    }

    public int getStatus() {
        if (status != null) {
            return status;
        }

        final Matcher matcher = STATUS_PATTERN.matcher(firstLine);

        if (matcher.find()) {
            status = Integer.parseInt(matcher.group(1));

            return status;
        }

        return -1;
    }

    public String getSynopsis() {
        if (getStatus() != SC_INVALID_COMMAND_SYNTAX) {
            return null;
        }

        if ((synopsis == null) && (lines.size() > 1)) {
            final String secondLine;
            final Matcher synopsisMatcher;

            secondLine = lines.get(1);
            synopsisMatcher = SYNOPSIS_PATTERN.matcher(secondLine);

            if (synopsisMatcher.find()) {
                synopsis = synopsisMatcher.group(1);
            }
        }

        return synopsis;
    }

    /**
     * Returns the usage of the command sent if Asterisk expected a different
     * syntax (getStatus() == SC_INVALID_COMMAND_SYNTAX).
     *
     * @return the usage of the command sent, <code>null</code> if there were no
     * syntax errors.
     */
    public String getUsage() {
        if (usage == null) {
            StringBuilder usageSB;

            usageSB = new StringBuilder();

            for (int i = 2; i < lines.size(); i++) {
                String line;

                line = lines.get(i);

                if (END_OF_PROPER_USAGE.equals(line)) {
                    break;
                }

                usageSB.append(line.trim());
                usageSB.append(" ");
            }

            usage = usageSB.toString().trim();
        }

        return usage;
    }

    private Map<String, String> parseAttributes(String s) {
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        Map<String, String> map = new HashMap<String, String>();

        boolean inKey = true;
        boolean inQuotes = false;
        char previousChar = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if ((c == '=') && inKey) {
                inKey = false;
                inQuotes = false;
            } else if (((c == ' ') && !inKey && !inQuotes)) {
                map.put(keyBuilder.toString().toLowerCase(Locale.ENGLISH), valueBuilder.toString());
                keyBuilder.delete(0, keyBuilder.length());
                valueBuilder.delete(0, valueBuilder.length());
                inKey = true;
            } else if ((c == '"') && !inKey) {
                if (previousChar == '\\') {
                    valueBuilder.deleteCharAt(valueBuilder.length() - 1);
                    valueBuilder.append(c);
                } else {
                    inQuotes = !inQuotes;
                }
            } else {
                if (inKey) {
                    keyBuilder.append(c);
                } else {
                    valueBuilder.append(c);
                }
            }

            previousChar = c;
        }

        if (keyBuilder.length() > 0) {
            map.put(keyBuilder.toString().toLowerCase(Locale.ENGLISH), valueBuilder.toString());
        }

        return map;
    }

    @Override
    public String toString() {
        StringBuilder sb;

        sb = new StringBuilder("AgiReply[");
        sb.append("status=").append(getStatus()).append(",");

        if (status == SC_SUCCESS) {
            sb.append("result='").append(getResult()).append("',");
            sb.append("extra='").append(getExtra()).append("',");
            sb.append("attributes=").append(getAttributes()).append(",");
        }

        if (status == SC_INVALID_COMMAND_SYNTAX) {
            sb.append("synopsis='").append(getSynopsis()).append("',");
        }

        sb.append("systemHashcode=").append(System.identityHashCode(this));
        sb.append("]");

        return sb.toString();
    }
}
