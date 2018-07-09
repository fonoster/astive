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

import org.apache.log4j.Logger;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.astivlet.AstivletRequest;
import com.fonoster.astive.astivlet.AstivletResponse;

/**
 * Yahoo Weather example.
 *
 * @since 1.0
 */
public class App extends Astivlet {
    private static final Logger LOG = Logger.getLogger(App.class);
    private int maxFail = 3;
    private String zip;
    private String enterZip = "enter-zip";
    private String invalidZipCode = "invalid-zip-code";
    private String pleaseWait = "please-wait";
    private String newZip = "new-zip";
    private String forZipcode = "for-zip-code";
    private String theHumidity = "the-humidity";
    private String theTemperature = "the-temp";

    @Override
    public void service(AstivletRequest request, AstivletResponse response) {
        try {
            response.answer();

            int failCount = 0;

            while (true) {
                // Wait three seconds in between digits
                zip = response.getData(enterZip, 3000, 5);
                // Wait ws response.
                response.streamFile(pleaseWait);

                if ((zip != null) && (zip.length() == 5)) {
                    // Invoking Yahoo Weather api.
                    Weather weather = WeatherAPI.getWeather(zip);

                    // For the Zip code: ...
                    response.streamFile(forZipcode);
                    response.sayDigits(zip);
                    response.streamFile("silence/2");

                    // The temperature is
                    response.streamFile(theTemperature);
                    response.sayDigits(weather.getTemp());
                    response.streamFile("silence/2");

                    // The humidity is                    
                    response.streamFile(theHumidity);
                    response.sayDigits(weather.getHumidity());
                    response.streamFile("silence/2");
                } else {
                    response.streamFile(invalidZipCode);
                    failCount++;

                    if (failCount == maxFail) {
                        break;
                    }

                    continue;
                }

                // The only available option is '1'                
                String answer = response.getData(newZip, 5000, 1);                                                       

                if (answer.equals("1")) {
                    failCount = 0;

                    continue;
                } else {
                    break;
                }
            }

            // Ends the call
            response.hangup();
        } catch (AgiException ex) {
            LOG.error(ex.getMessage());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
