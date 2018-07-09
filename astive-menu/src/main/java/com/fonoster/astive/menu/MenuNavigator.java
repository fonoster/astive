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
package com.fonoster.astive.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.fonoster.astive.agi.AgiException;
import com.fonoster.astive.agi.AgiResponse;
import com.fonoster.astive.agi.CommandProcessor;
import com.fonoster.astive.menu.action.Action;
import com.fonoster.astive.menu.event.ActionEvent;
import com.fonoster.astive.menu.event.AuthenticationEvent;
import com.fonoster.astive.menu.event.DigitsEvent;
import com.fonoster.astive.menu.event.FailEvent;
import com.fonoster.astive.menu.event.InterDigitsTimeoutEvent;
import com.fonoster.astive.menu.event.KeyEvent;
import com.fonoster.astive.menu.event.MaxFailureEvent;
import com.fonoster.astive.menu.event.MaxTimeoutEvent;
import com.fonoster.astive.menu.event.PositionChangeEvent;

/**
 * The engine of Menu API. It eliminate the necessity of the loops commonly
 * needed by AGI, when creating user iteration/navigation.
 *
 * @since 1.0
 */
public class MenuNavigator {
    private static final Logger LOG = Logger.getLogger(MenuNavigator.class);
    private AgiResponse agiResponse;
    private Menu currentMenu;
    private boolean answered;
    private boolean autoAnswer;

    /**
     * Creates a new instance of MenuNavigator.
     *
     * @param agiResponse allows user iteration with telephony system.
     */
    public MenuNavigator(AgiResponse agiResponse) {
        this.agiResponse = agiResponse;
        autoAnswer = true;
        answered = false;
    }

    private void authenticate(MenuItem item) {
        if (item.getMustAuthenticate()) {
            if (!item.getAuthenticator().isAuthenticated()) {
                AuthenticationEvent evt = new AuthenticationEvent(item, item.getAuthenticator());
                item.getAuthenticator().signIn();

                if (item.getAuthenticator().isAuthenticated()) {
                    item.fireAuthenticationEvent_onSuccess(null);
                } else {
                    item.fireAuthenticationEvent_onFailure(evt);
                }
            }
        }
    }

    private boolean checkMaxFailure(Menu menu, String opt) {
        if (menu.getFailuresCount() >= menu.getMaxFailures()) {
            MaxFailureEvent event = new MaxFailureEvent(menu, opt, menu.getMaxFailures());
            menu.fireMaxFailureEvent_maxFailurePerform(event);

            // do break the flow
            return true;
        }

        return false;
    }

    private boolean checkMaxTimeout(Menu menu, String opt) {
        if (menu.getTimeoutCount() >= menu.getMaxTimeouts()) {
            MaxTimeoutEvent event = new MaxTimeoutEvent(menu, opt, menu.getMaxTimeouts());
            menu.fireMaxTimeoutEvent_maxTimeoutPerform(event);

            // do break the flow
            return true;
        }

        return false;
    }

    private ArrayList<String> getChildsKeys(ArrayList<MenuItem> menuChilds) {
        ArrayList<String> result = new ArrayList<String>();

        for (MenuItem child : menuChilds) {
            final String digits = ((MenuItem) child).getDigits();
            result.add(digits);
        }

        return result;
    }

    private Menu getCurrentMenu() {
        return currentMenu;
    }

    private String getData(String file, int milliSecondsWatting, int maxDigits,
            AgiResponse agiResponse, MenuItem item, char c)
            throws AgiException {
        Menu menu;
        String result = new String();

        if (item.getParent() != null) {
            menu = ((Menu) item.getParent());
        } else {
            menu = getCurrentMenu();
        }

        // Has not press any key
        if (c == 0) {
            // WARNING: Not sure about using all keys...
            c = agiResponse.streamFile(file, "0123456789*#");

            if (c == 0 /*
                     * && milliSecondsWatting == 0
                     */) {
                try {
                    Thread.sleep(milliSecondsWatting);
                } catch (InterruptedException ex) {
                    LOG.warn(ex.getMessage());
                }

                return "(timeout)";
            }
        } else {
            result = "" + c;
            c = agiResponse.waitForDigit(menu.getInterDigitsTimeout());
        }

        KeyEvent evt;

        if (c != 0) {
            evt = new KeyEvent(item, Digit.getDigit(c));
            item.fireKeyEvent_keyTyped(evt);
            result += ("" + c);
        }

        while (true) {
            if ((result.length() == maxDigits) || (c == '#')) {
                return result;
            }

            c = agiResponse.waitForDigit(menu.getInterDigitsTimeout());

            if (c != 0) {
                result += ("" + c);
                evt = new KeyEvent(item, Digit.getDigit(c));
                item.fireKeyEvent_keyTyped(evt);
            } else {
                InterDigitsTimeoutEvent event =
                        new InterDigitsTimeoutEvent(item, result, menu.getInterDigitsTimeout());
                menu.fireInterDigitsTimeoutListener_timeoutPerform(event);

                break;
            }
        }

        return result;
    }

    private MenuItem getMenuItem(Menu menu, String digits) {
        for (MenuItem item : menu.getChilds()) {
            if (item.getDigits().equals(digits)) {
                return item;
            }
        }

        return null;
    }

    // XXX: Im not sure about this criteria
    private ArrayList<String> getSortedChildsKeys(ArrayList<MenuItem> menuChilds) {
        ArrayList result = new ArrayList();
        Comparator comparator =
                new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int c1 = ((MenuItem) o1).getPriority();
                int c2 = ((MenuItem) o2).getPriority();

                return (new Integer(c1)).compareTo(new Integer(c2));
            }
        ;
        };

    Object[] opts = new Object[menuChilds.size()];
        int cnt = 0;

        for (MenuItem child : menuChilds) {
            opts[cnt++] = child;
        }

        // Sorting the items
        Arrays.sort(opts, comparator);

        for (int i = 0; i < opts.length; i++) {
            String digits = ((MenuItem) opts[i]).getDigits();
            result.add(digits);
        }

        return result;
    }

    private Boolean isAutoAnswer() {
        return autoAnswer;
    }

    /**
     * Start the menu execution.
     *
     * @param menu and object containing all menu and menu items.
     */
    public void run(Menu menu) throws AgiException {
        String digits = null;
        setCurrentMenu(menu);

        // If channel is closed by customer the player must be auto-halt.
        if (agiResponse.getChannelStatus().getCode() == -1) {
            return;
        }

        // WARNING: To be reviewed
        if ((answered == false) && (isAutoAnswer() == true)) {
            agiResponse.answer();
            answered = true;
        }

        ArrayList<String> childsKeys;

        // Sort elements
        if (!menu.isSortChildsByDigits()) {
            childsKeys = getChildsKeys(menu.getChilds());
        } else {
            childsKeys = getSortedChildsKeys(menu.getChilds());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Total menu options: " + menu.getChilds().size());
        }

        if (((menu.isGreetingsPlayed() == false) || menu.isPlayGreetingsAllways())
                && (menu.getGreetingsFile() != null) && !menu.getGreetingsFile().isEmpty()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Playing menu intro: " + menu.getGreetingsFile());
            }

            char c = 0;
            digits = getData(menu.getGreetingsFile(), 0, menu.getMaxDigits(), agiResponse, menu, c);
            menu.setGreetingsPlayed(true);
        }

        if ((digits == null) || digits.equals("(timeout)")) {
            List<VoiceComposition> voiceCompositions = menu.getVoiceCompositions();
            Iterator<VoiceComposition> vcIterator = voiceCompositions.iterator();
            char c = 0;

            while (vcIterator.hasNext()) {
                VoiceComposition cVc = vcIterator.next();
                Iterator<Object> commands = cVc.getCommands().iterator();

                while (commands.hasNext()) {
                    Object o = commands.next();
                    String cmd = CommandProcessor.buildCommand(o);
                    c = agiResponse.sendAgiCommand(cmd).getResultCodeAsChar();

                    if (c != 0) {
                        KeyEvent evt = new KeyEvent(menu, Digit.getDigit(c));
                        menu.fireKeyEvent_keyTyped(evt);

                        digits = getData(menu.getFile(), 0, menu.getMaxDigits(), agiResponse, menu, c);

                        break;
                    }
                }

                if (c != 0) {
                    break;
                }
            }
        }

        if ((digits == null) || digits.equals("(timeout)")) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Playing menu options");
            }

            int pos = 0;

            for (String opts : childsKeys) {
                MenuItem option = getMenuItem(menu, opts);
                int millisecondsWatting = menu.getInterDigitsTimeout();

                if (((menu.getChilds().size() - 1) >= 0)
                        && menu.getChilds().get(menu.getChilds().size() - 1).equals(option)) {
                    millisecondsWatting = menu.getLastDigitsTimeout();
                }

                if ((digits == null) || digits.equals("(timeout)")) {
                    // Waiting time in between the item file and digits item file.
                    int msw;

                    msw = millisecondsWatting;

                    if ((option.getFile() != null) && !option.getFile().isEmpty()) {
                        char c = 0;
                        digits = getData(option.getFile(), msw, menu.getMaxDigits(), agiResponse, menu, c);
                    }
                }

                MenuItem oldOption = option;

                if (pos > 0) {
                    oldOption = getMenuItem(menu, childsKeys.get(pos - 1));
                }

                PositionChangeEvent evt = new PositionChangeEvent(oldOption, option, pos - 1);
                menu.firePositionChangeEvent_positionChange(evt);
                pos++;

                if (LOG.isDebugEnabled()) {
                    LOG.debug("pos = " + pos);
                }
            }
        }

        if ((digits != null) && !digits.equals("(timeout)")) {
            // Do break menu
            if (LOG.isDebugEnabled()) {
                LOG.debug("Enter digits is: " + digits);
            }

            // WARNING: Should this event be only at Menu level?
            DigitsEvent evt = new DigitsEvent((Object) menu, digits);
            menu.fireDigitsEvent_digitsEnter(evt);
        }

        // Selected none option
        if ((digits == null) || digits.equals("(timeout)")) {
            // XXX: Not only timeout, but also for # sign.
            if (digits == null) {
                FailEvent evt = new FailEvent(menu, digits, menu.getFailuresCount());
                menu.fireFailureListener_failurePerform(evt);
            } else if (digits.equals("(timeout)")) {
                // WARNNING:
                //menu.fireTimeoutListener_timeoutPerform(null);
            }

            // Try again
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unregistered option");
            }

            menu.incrementFailuresCount();

            if (!checkMaxFailure(menu, digits) && !checkMaxTimeout(menu, digits)) {
                run(menu);

                return;
            }

            if ((menu.getExitFile() != null) && !menu.getExitFile().isEmpty()) {
                agiResponse.streamFile(menu.getExitFile());
            }

            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Getting Menu/MenuItem for digits: " + digits);
        }

        MenuItem selectedOption = getMenuItem(menu, digits);

        if (selectedOption != null) {
            menu.resetFailuresCount();
            menu.resetTimeoutCount();

            Action action = selectedOption.getAction();

            if (action != null) {
                authenticate(selectedOption);
                selectedOption.getAction().doAction();
            }

            ActionEvent evt = new ActionEvent(selectedOption, digits);
            selectedOption.fireActionEvent_processAction(evt);

            if (selectedOption instanceof Menu) {
                run((Menu) selectedOption);
            } else {
                run(menu);
            }

            return;
        } else {
            // Invalid option
            if ((menu.getInvalidDigitsFile() != null) && !menu.getInvalidDigitsFile().isEmpty()) {
                agiResponse.streamFile(menu.getInvalidDigitsFile());
            }

            menu.incrementFailuresCount();

            if (!checkMaxFailure(menu, digits) && !checkMaxTimeout(menu, digits)) {
                run(menu);

                return;
            }

            if ((menu.getExitFile() != null) && !menu.getExitFile().isEmpty()) {
                agiResponse.streamFile(menu.getExitFile());
            }

            return;
        }
    }

    private void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
