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
import com.fonoster.astive.menu.action.Action;
import com.fonoster.astive.menu.event.FailEvent;
import com.fonoster.astive.menu.event.FailureListener;
import com.fonoster.astive.menu.event.InterDigitsTimeoutEvent;
import com.fonoster.astive.menu.event.InterDigitsTimeoutListener;
import com.fonoster.astive.menu.event.MaxFailureEvent;
import com.fonoster.astive.menu.event.MaxFailureListener;
import com.fonoster.astive.menu.event.MaxTimeoutEvent;
import com.fonoster.astive.menu.event.MaxTimeoutListener;
import com.fonoster.astive.menu.event.PositionChangeEvent;
import com.fonoster.astive.menu.event.PositionChangeListener;
import com.fonoster.astive.menu.event.TimeoutEvent;
import com.fonoster.astive.menu.event.TimeoutListener;

/**
 *
 * @since 1.0
 * @see MenuItem
 */
public class Menu extends MenuItem {
    private ArrayList<MenuItem> childs = new ArrayList<MenuItem>();
    private ArrayList<FailureListener> failListenerList = new ArrayList<FailureListener>();
    private ArrayList<InterDigitsTimeoutListener> interDigitsTimeoutListenerList = new ArrayList<InterDigitsTimeoutListener>();
    private ArrayList<MaxFailureListener> maxFailureListenerList = new ArrayList<MaxFailureListener>();
    private ArrayList<MaxTimeoutListener> maxTimeoutListenerList = new ArrayList<MaxTimeoutListener>();
    private ArrayList<PositionChangeListener> positionChangeListenerList = new ArrayList<PositionChangeListener>();
    private ArrayList<TimeoutListener> timeoutListenerList = new ArrayList<TimeoutListener>();
    private String exitFile;
    private String greetingsFile;
    private String invalidDigitsFile;
    private boolean canInterruptGreetings = true;
    private boolean greetingsPlayed = false;
    private boolean playGreetingsAllways = false;
    private boolean sortChildsByDigits = true;
    private int failuresCount;
    private int interDigitsTimeout = 500;
    private int lastDigitsTimeout = 1500;
    private int maxDigits = 1;
    private int maxFailures = 3;
    private int maxTimeouts = 3;
    private int timeoutCount;

    /**
     * {@inheritDoc}
     */
    public Menu() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public Menu(final String digits, final String file) {
        super(digits, file);
    }

    /**
     * {@inheritDoc}
     */
    public Menu(final String digits, final String file, final Action action) {
        super(digits, file, action);
    }

    /**
     * {@inheritDoc}
     */
    public Menu(final String digits, final String file, final Action action,
            final int priority) {
        super(digits, file, action, priority);
    }

    /**
     * Registers a new menu item.
     *
     * @param item the new menu item.
     */
    public void addChild(MenuItem item) {
        item.parent = this;
        childs.add(item);
    }

    /**
     * Registers the specified <code>fail listener</code> to receive fail events
     * from this <code>menu</code>.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void addFailListener(FailureListener listener) {
        failListenerList.add(listener);
    }

    /**
     * Registers the specified <code>integer digit timeout listener</code> to
     * receive inter digits events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void addInterDigitsTimeoutListener(InterDigitsTimeoutListener listener) {
        interDigitsTimeoutListenerList.add(listener);
    }

    /**
     * Registers the specified <code>max failure listener</code> to receive max
     * failure events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void addMaxFailureListener(MaxFailureListener listener) {
        maxFailureListenerList.add(listener);
    }

    /**
     * Registers the specified <code>max timeout listener</code> to receive max
     * timeout events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void addMaxTimeoutListener(MaxTimeoutListener listener) {
        maxTimeoutListenerList.add(listener);
    }

    /**
     * Registers the specified <code>position change listener</code> to receive
     * position change events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void addPositionChangeListener(PositionChangeListener listener) {
        positionChangeListenerList.add(listener);
    }

    /**
     * Registers the specified <code>timeout listener</code> to receive position
     * timeout events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void addTimeoutListener(TimeoutListener listener) {
        timeoutListenerList.add(listener);
    }

    /**
     * Returns an array of all the child's registered on this menu.
     *
     * @return all of this menu child's or an empty array if no voice
     * compositions are currently registered.
     */
    public ArrayList<MenuItem> getChilds() {
        return childs;
    }

    /**
     * Returns the goodbye file.
     *
     * @return the <code>file</code> to be stream, without extension.
     */
    public String getExitFile() {
        return exitFile;
    }

    /**
     * Returns the greetings file.
     *
     * @return the <code>file</code> to be streamed, without extension.
     */
    public String getGreetingsFile() {
        return greetingsFile;
    }

    /**
     * Returns the maximum time in between digits.
     *
     * @return the maximum time allow in between digits.
     */
    public int getInterDigitsTimeout() {
        return interDigitsTimeout;
    }

    /**
     * Returns the <code>invalid digits</code> file.
     *
     * @return the <code>file</code> to be streamed, without extension.
     */
    public String getInvalidDigitsFile() {
        return invalidDigitsFile;
    }

    /**
     * Returns the maximum timeout for the last menu item in the menu.
     *
     * @return the maximum time that a <code>Subject</code> have, to press the
     * digits before the file for the last menu item finish.
     */
    public int getLastDigitsTimeout() {
        return lastDigitsTimeout;
    }

    /**
     * Returns the maximum digits allow for menu items in this menu.
     *
     * @return maximum digits for menu items.
     */
    public int getMaxDigits() {
        return maxDigits;
    }

    /**
     * Returns the maximum failure that a <code>Subject</code> is allow to.
     *
     * @return the maximum attempts that a <code>Subject</code>(user) is allow
     * to introduce invalid digits.
     */
    public int getMaxFailures() {
        return maxFailures;
    }

    /**
     * Returns whether or not the greetings can be interrupted by user
     * iteration.
     *
     * @return true if the greetings can be skipped.
     */
    public boolean isCanInterruptGreetings() {
        return canInterruptGreetings;
    }

    /**
     * Whether or not the greetings has been played.
     *
     * @return true if greetings was played already.
     */
    public boolean isGreetingsPlayed() {
        return greetingsPlayed;
    }

    /**
     * Whether or not greetings will be played every cycle of the menu.
     *
     * @return true if greetings will be played every time.
     */
    public boolean isPlayGreetingsAllways() {
        return playGreetingsAllways;
    }

    /**
     * Indicates if childs will be sort using the digits as criteria.
     *
     * @return true if menu items will be sorted.
     */
    public boolean isSortChildsByDigits() {
        return sortChildsByDigits;
    }

    /**
     * Removes the specified <code>inter digits listener</code> so it no longer
     * receives inter digits events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void removeInterDigitsTimeoutListener(InterDigitsTimeoutListener listener) {
        interDigitsTimeoutListenerList.remove(listener);
    }

    /**
     * Removes the specified <code>max failure listener</code> so it no longer
     * receives max failure events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void removeMaxFailureListener(MaxFailureListener listener) {
        maxFailureListenerList.remove(listener);
    }

    /**
     * Removes the specified <code>max timeout listener</code> so it no longer
     * receives max timeout events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void removeMaxTimeoutListener(MaxTimeoutListener listener) {
        maxTimeoutListenerList.remove(listener);
    }

    /**
     * Removes the specified <code>max failure listener</code> so it no longer
     * receives max failure events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void removePositionChangeListener(PositionChangeListener listener) {
        positionChangeListenerList.remove(listener);
    }

    /**
     * Removes the specified <code>timeout listener</code> so it no longer
     * receives timeout events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void removeTimeoutListener(TimeoutListener listener) {
        timeoutListenerList.remove(listener);
    }

    /**
     * Removes the specified <code>failure listener</code> so it no longer
     * receives failure events from this menu.
     *
     * @param listener the object that receives a notification when an event of
     * the specified type occurs.
     */
    public void removeFailureListener(FailureListener listener) {
        failListenerList.remove(listener);
    }

    /**
     * Restarts the the failures count, to prevent {@link MaxFailureEvent}.
     */
    public void resetFailuresCount() {
        setFailuresCount(0);
    }

    /**
     * Restarts the timeout count, to prevent {@link MaxTimeoutEvent}.
     */
    public void resetTimeoutCount() {
        setTimeoutCount(0);
    }

    /**
     * Establish whether or not the greetings can be interrupted by user
     * iteration.
     *
     * @param canInterruptGreetings 'true' will allow users to skip the
     * greetings.
     */
    public void setCanInterruptGreetings(boolean canInterruptGreetings) {
        this.canInterruptGreetings = canInterruptGreetings;
    }

    /**
     * Registers an array of menu items.
     *
     * @param childs an array of memu items.
     */
    public void setChilds(ArrayList<MenuItem> childs) {
        this.childs = childs;
    }

    /**
     * Sets the goodbye sound.
     *
     * @param exitFile the file extension must not be included in the filename.
     */
    public void setExitFile(String exitFile) {
        this.exitFile = exitFile;
    }

    /**
     * Sets the greetings sound.
     *
     * @param greetingsFile the file extension must not be included in the
     * filename.
     */
    public void setGreetingsFile(String greetingsFile) {
        this.greetingsFile = greetingsFile;
    }

    /**
     * Sets the maximum time allow in between digits.
     *
     * @param interDigitsTimeout the maximum time allow in between digits.
     */
    public void setInterDigitsTimeout(int interDigitsTimeout) {
        this.interDigitsTimeout = interDigitsTimeout;
    }

    /**
     * Sets the file to stream if invalid digits are introduce by a
     * <code>Subject</code>.
     *
     * @param greetingsFile the file extension must not be included in the
     * filename.
     */
    public void setInvalidDigitFile(String invalidDigitFile) {
        this.invalidDigitsFile = invalidDigitFile;
    }

    /**
     * Sets maximum time waiting for user interaction.
     *
     * @param lastDigitsTimeout the maximum time waiting for user interaction
     * after the audio of last menu item finished.
     */
    public void setLastDigitsTimeout(int lastDigitsTimeout) {
        this.lastDigitsTimeout = lastDigitsTimeout;
    }

    /**
     * Maximum digits length for menu items.
     *
     * @param maxDigits maximum length.
     */
    public void setMaxDigits(int maxDigits) {
        this.maxDigits = maxDigits;
    }

    /**
     * Defines the maximum amount failures that a <code>Subject</code> is allow
     * to.
     *
     * @param maxFailures the maximum amount of attempts to select invalid
     * options.
     */
    public void setMaxFailures(int maxFailures) {
        this.maxFailures = maxFailures;
    }

    /**
     * Defines the maximum amount of timeouts that <code>Subject</code> is allow
     * to.
     *
     * @param maxFailures the maximum amount of time that user is allow to skip
     * menu item selection.
     */
    public void setMaxTimeouts(int maxTimeouts) {
        this.maxTimeouts = maxTimeouts;
    }

    /**
     * Whether or not greetings will be played every menu cycle.
     *
     * @param playGreetingsAllways by default is set to false.
     */
    public void setPlayGreetingsAllways(boolean playGreetingsAllways) {
        this.playGreetingsAllways = playGreetingsAllways;
    }

    /**
     * Whether or not will sort menu items by digits.
     *
     * @param sortChildsByDigits is true by default.
     */
    public void setSortChildsByDigits(boolean sortChildsByDigits) {
        this.sortChildsByDigits = sortChildsByDigits;
    }

    /**
     * Returns the maximum timeout.
     *
     * @return the maximum consecutive timeouts that a <code>Subject</code> is
     * allow to.
     */
    public int getMaxTimeouts() {
        return maxTimeouts;
    }

    /**
     * Returns the current timeout count.
     *
     * @return the number of consecutive timeouts.
     */
    protected int getTimeoutCount() {
        return timeoutCount;
    }

    /**
     * Use by {@link MenuNavigator} to increment the consecutive timeout count.
     */
    protected void incremenTimeoutCount() {
        setTimeoutCount(getTimeoutCount() + 1);
    }

    /**
     * Use by {@link MenuNavigator} to increment the consecutive failures count.
     */
    protected void incrementFailuresCount() {
        setFailuresCount(getFailuresCount() + 1);
    }

    private void setTimeoutCount(int timeoutCount) {
        this.timeoutCount = timeoutCount;
    }

    /**
     * Checks for duplicate digits in menu items.
     *
     * @param digits if there is any digit duplicated, the exception
     * {@link DuplicateDigitException} is thrown.
     */
    protected void checkDigits(String digits) {
        for (MenuItem m : getChilds()) {
            if (m.getDigits().equals(digits)) {
                throw new DuplicatedDigitException();
            }
        }
    }

    /**
     * Notifies <code>failure listeners</code> about a <code>failure event</code>.
     *
     * @param evt the event.
     */
    protected void fireFailureListener_failurePerform(FailEvent evt) {
        for (FailureListener listener : failListenerList) {
            listener.failurePerform(evt);
        }
    }

    /**
     * Notifies <code>inter digits timeout listeners</code> about an
     * <code>inter digits timeout<code>.
     * event.
     *
     * @param evt the event.
     */
    protected void fireInterDigitsTimeoutListener_timeoutPerform(InterDigitsTimeoutEvent evt) {
        for (InterDigitsTimeoutListener listener : interDigitsTimeoutListenerList) {
            listener.timeoutPerform(evt);
        }
    }

    /**
     * Notifies <code>inter digits timeout listeners</code> about an
     * <code>inter digits timeout event</code>.
     *
     * @param evt the event.
     */
    protected void fireMaxFailureEvent_maxFailurePerform(MaxFailureEvent evt) {
        for (MaxFailureListener listener : maxFailureListenerList) {
            listener.maxFailurePerform(evt);
        }
    }

    /**
     * Notifies <code>max timeout listeners</code> about a
     * <code>max timeout event</code>.
     *
     * @param evt the event.
     */
    protected void fireMaxTimeoutEvent_maxTimeoutPerform(MaxTimeoutEvent evt) {
        for (MaxTimeoutListener listener : maxTimeoutListenerList) {
            listener.maxTimeoutPerform(evt);
        }
    }

    /**
     * Notifies <code>position change listeners</code> about a
     * <code>position change event</code>.
     *
     * @param evt the event.
     */
    protected void firePositionChangeEvent_positionChange(PositionChangeEvent evt) {
        for (PositionChangeListener listener : positionChangeListenerList) {
            listener.positionChange(evt);
        }
    }

    /**
     * Notifies <code>timeout listeners</code> about a
     * <code>timeout event</code>.
     *
     * @param evt the event.
     */
    protected void fireTimeoutListener_timeoutPerform(TimeoutEvent evt) {
        for (TimeoutListener listener : timeoutListenerList) {
            listener.timeoutPerform(evt);
        }
    }

    /**
     * Returns the current failures count.
     *
     * @return use by {@link MenuNavigator} to perform {@link MaxFailuresEvent}.
     */
    protected int getFailuresCount() {
        return failuresCount;
    }

    private void setFailuresCount(int failuresCount) {
        this.failuresCount = failuresCount;
    }

    /**
     * Sets the <code>greetings played</code>.
     *
     * @param greetingsPlayed use by {@link MenuNavigator} to avoid greetings
     * repetition unless is the desire behavior.
     */
    protected void setGreetingsPlayed(boolean greetingsPlayed) {
        this.greetingsPlayed = greetingsPlayed;
    }
}
