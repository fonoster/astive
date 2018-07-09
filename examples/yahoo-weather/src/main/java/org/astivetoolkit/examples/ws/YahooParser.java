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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

/**
 * Yahoo Weather example.
 *
 * @since 1.0
 */
public class YahooParser {
    private static Logger log = Logger.getLogger(YahooParser.class);

    public Weather parse(InputStream inputStream) throws DocumentException {
        Weather weather = new Weather();

        log.info("Creating XML Reader");

        SAXReader xmlReader = createXmlReader();
        Document doc = xmlReader.read(inputStream);

        log.info("Parsing XML Response");

        weather.setCity(doc.valueOf("/rss/channel/yweather:location/@city"));
        weather.setRegion(doc.valueOf("/rss/channel/yweather:location/@region"));
        weather.setCountry(doc.valueOf(
                "/rss/channel/yweather:location/@country"));
        weather.setCondition(doc.valueOf(
                "/rss/channel/item/yweather:condition/@text"));
        weather.setTemp(doc.valueOf(
                "/rss/channel/item/yweather:condition/@temp"));
        weather.setChill(doc.valueOf("/rss/channel/yweather:wind/@chill"));
        weather.setHumidity(doc.valueOf(
                "/rss/channel/yweather:atmosphere/@humidity"));

        return weather;
    }

    private SAXReader createXmlReader() {
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("yweather", "http://xml.weather.yahoo.com/ns/rss/1.0");

        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        SAXReader xmlReader = new SAXReader();
        xmlReader.setDocumentFactory(factory);

        return xmlReader;
    }
}
