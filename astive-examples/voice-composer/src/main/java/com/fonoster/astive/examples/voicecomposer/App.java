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
package com.fonoster.astive.examples.voicecomposer;

import static java.lang.System.out;
import java.util.Date;
import java.util.TimeZone;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.astivlet.AstivletRequest;
import com.fonoster.astive.astivlet.AstivletResponse;
import com.fonoster.astive.menu.*;
import com.fonoster.astive.menu.event.DigitsEvent;
import com.fonoster.astive.menu.event.DigitsListener;
import com.fonoster.astive.menu.event.KeyEvent;
import com.fonoster.astive.menu.event.KeyListener;

/**
 * VoiceComposer example.
 *
 * @since 1.0
 */
public class App extends Astivlet {

    @Override
    public void service(AstivletRequest request, AstivletResponse response) {

        Date today = new Date();
        VoiceComposition vc = VoiceComposer
                .withEscapeDigits("12345")                
                .withTimeZone(TimeZone.getDefault())
                .streamFile("date")
                .streamFile("is")                                
                .addSilence(1)
                .sayDate(today)
                .addSilence(1)
                .withEscapeDigits("")
                .streamFile("new-york")
                .sayAlpha("newyork").create();
        
        Menu m = new Menu();
        m.setGreetingsFile("welcome");
        m.setMaxDigits(3);
        m.setInterDigitsTimeout(1000);
        m.addVoiceComposition(vc);
        m.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent evt) {
                out.print("Key: ");
                out.println(evt.getKey());
            }
        });
        
        m.addDigitsListener(new DigitsListener() {

            @Override
            public void digitsEnter(DigitsEvent evt) {
                out.print("Digits: ");
                out.println(evt.getDigits());                
            }
        });
                
        MenuNavigator mn = new MenuNavigator(response);
        
        try {
            mn.run(m);
        } catch (AgiException ex) {
            out.println(ex.getMessage());
        }
    }
}
