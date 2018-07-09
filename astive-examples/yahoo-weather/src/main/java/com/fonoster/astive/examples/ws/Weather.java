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

/**
 * Yahoo Weather example.
 *
 * @since 1.0
 */
public class Weather {
    private String city;
    private String region;
    private String country;
    private String condition;
    private String temp;
    private String chill;
    private String humidity;

    public Weather() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getChill() {
        return chill;
    }

    public void setChill(String chill) {
        this.chill = chill;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("weather info:");
        sb.append("country: ");
        sb.append(getCountry());
        sb.append("\n");
        sb.append("city: ");
        sb.append(getCity());
        sb.append("\n");
        sb.append("region: ");
        sb.append(getRegion());
        sb.append("\n");
        sb.append("condition: ");
        sb.append(getCondition());
        sb.append("\n");
        sb.append("chill: ");
        sb.append(getChill());
        sb.append("\n");
        sb.append("temp: ");
        sb.append(getTemp());
        sb.append("\n");
        sb.append("humidity: ");
        sb.append(getHumidity());
        sb.append("\n");

        return sb.toString();
    }
}
