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
package com.fonoster.astive.examples.ws;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Yahoo Weather example.
 *
 * @since 1.0
 */
public class WeatherFormatter {
    private static Logger log = Logger.getLogger(WeatherFormatter.class);

    public String format(Weather weather) throws Exception {
        log.info("Formatting Weather Data");

        Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("output.vm"));
        VelocityContext context = new VelocityContext();
        context.put("weather", weather);

        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "", reader);

        return writer.toString();
    }
}
