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

import com.fonoster.astive.agi.annotation.*;
import com.fonoster.astive.util.AppLocale;

import java.lang.reflect.Field;
import java.util.*;

/**
 * This class is a helper. Can be use to extract meta information from Agi
 * commands classes and then construct commands in string format.
 *
 * @since 1.0
 */
public final class CommandProcessor {

    /**
     * Build a command from a class marked with {@link AgiCommand}.
     *
     * @param o object to in command generation.
     * @return command as string.
     * @throws AgiException
     */
    public static String buildCommand(Object o) throws AgiException {
        AgiCommand command = o.getClass().getAnnotation(AgiCommand.class);

        if (command == null) {
            throw new AgiException(AppLocale.getI18n("notACommandObject"));
        }

        final List<Field> parameters = getParameters(o);

        StringBuilder cmd = new StringBuilder(command.command());

        if (parameters == null || parameters.isEmpty()) {
            return command.command();
        }

        for (Field field : parameters) {
            Parameter p = field.getAnnotation(Parameter.class);
            Object param;

            try {
                param = field.get(o);
            } catch (IllegalArgumentException ex) {
                throw new AgiException(ex);
            } catch (IllegalAccessException ex) {
                throw new AgiException(ex);
            }

            if (param instanceof String || param instanceof Character) {
                if (param instanceof Character) {
                    Character c = (Character) param;

                    if (!c.toString().trim().isEmpty()) {
                        cmd.append(" ");
                        cmd.append("\"");
                        cmd.append(c);
                        cmd.append("\"");
                    }
                } else {
                    cmd.append(" ");
                    cmd.append("\"");
                    cmd.append(param.toString().trim());
                    cmd.append("\"");
                }
            } else if (param.getClass().isArray()) {
                if (!field.isAnnotationPresent(ParamConverter.class)) {
                    throw new AgiException(AppLocale.getI18n("cantFoundAnnotation",
                            new Object[]{ParamConverter.class}));
                } else if (!field.isAnnotationPresent(Separator.class)) {
                    throw new AgiException(AppLocale.getI18n("cantFoundAnnotation",
                            new Object[]{Separator.class}));
                }

                ParamConverter pc = field.getAnnotation(ParamConverter.class);
                Separator separator = field.getAnnotation(Separator.class);
                StringConverter sc;

                try {
                    sc = (StringConverter) pc.converter().newInstance();

                    String[] paramArr = (String[]) param;
                    String paramStr = sc.fromArrayString(paramArr, separator.value());

                    cmd.append(" ");
                    cmd.append("\"");
                    cmd.append(paramStr);
                    cmd.append("\"");
                } catch (InstantiationException ex) {
                    throw new AgiException(ex.getMessage());
                } catch (IllegalAccessException ex) {
                    throw new AgiException(ex.getMessage());
                }
                
            } else if (param instanceof Boolean) {
                if (!field.isAnnotationPresent(ParamConverter.class)) {
                    throw new AgiException(AppLocale.getI18n("cantFoundAnnotation"));
                } else if (!field.isAnnotationPresent(BooleanChoose.class)) {
                    throw new AgiException(AppLocale.getI18n("cantFoundAnnotation",
                            new Object[]{Separator.class}));
                }

                BooleanChoose bc = field.getAnnotation(BooleanChoose.class);

                String t = bc.valueOnFalse();
                Boolean b = (Boolean) param;

                if (b == true) {
                    t = bc.valueOnTrue();
                }

                if (!t.isEmpty()) {
                    cmd.append(" ");
                    cmd.append("\"");
                    cmd.append(t);
                    cmd.append("\"");
                }
            } else if (param instanceof Date) {
                if (!field.isAnnotationPresent(ParamConverter.class)) {
                    throw new AgiException(AppLocale.getI18n("cantFoundAnnotation"));
                }

                Date date = (Date) param;
                long seconds = date.getTime() / 1000;
                cmd.append(" ");
                cmd.append("\"");
                cmd.append(seconds);
                cmd.append("\"");
            } else if (param instanceof TimeZone) {
                if (!field.isAnnotationPresent(ParamConverter.class)) {
                    throw new AgiException(AppLocale.getI18n("cantFoundAnnotation"));
                }

                TimeZone tz = (TimeZone) param;
                String id = tz.getID();
                cmd.append(" ");
                cmd.append("\"");
                cmd.append(id);
                cmd.append("\"");
            } else {
                if (!p.prefix().isEmpty()) {
                    cmd.append(" ");
                    cmd.append("\"");
                    cmd.append(p.prefix());
                    cmd.append(param);
                    cmd.append("\"");
                } else {
                    cmd.append(" ");
                    cmd.append(param);
                }
            }
        }

        return cmd.toString().trim();
    }

    // Get all fields marked with annotations {@link Parameter}    
    // @param o object to analyze.
    private static List getParameters(Object o) throws AgiException {
        List parameters = new ArrayList();
        Field[] fs = o.getClass().getDeclaredFields();
        HashMap map = new HashMap();

        for (Field f : fs) {
            if (f.getAnnotation(Parameter.class) != null) {
                map.put(f.getAnnotation(Parameter.class).position(), f);
            }
        }

        Object[] keysArr = map.keySet().toArray();
        Arrays.sort(keysArr);

        for (Object i : keysArr) {
            Field f = (Field) map.get(i);
            f.setAccessible(true);

            Object c;

            try {
                c = f.get(o);
            } catch (IllegalArgumentException ex) {
                throw new AgiException(ex);
            } catch (IllegalAccessException ex) {
                throw new AgiException(ex);
            }

            // This avoid automatic cast to string
            if (c != null) {
                parameters.add(map.get(i));
            } else {
                break;
            }
        }

        return parameters;
    }

    private CommandProcessor() {
    }
}
