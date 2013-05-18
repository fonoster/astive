/*
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.menu;

import org.astivetoolkit.menu.event.FailEvent;
import org.astivetoolkit.menu.event.InterDigitsTimeoutListener;
import org.astivetoolkit.menu.event.FailListener;
import org.astivetoolkit.menu.event.PositionChangeListener;
import org.astivetoolkit.menu.event.MaxTimeoutEvent;
import org.astivetoolkit.menu.event.MaxFailureListener;
import org.astivetoolkit.menu.event.MaxFailureEvent;
import org.astivetoolkit.menu.event.TimeoutEvent;
import org.astivetoolkit.menu.event.InterDigitsTimeoutEvent;
import org.astivetoolkit.menu.event.PositionChangeEvent;
import org.astivetoolkit.menu.event.MaxTimeoutListener;
import org.astivetoolkit.menu.event.TimeoutListener;
import java.util.ArrayList;
import org.astivetoolkit.menu.action.Action;

/**
 *
 * @since 1.0.0
 * @see MenuItem
 */
public class Menu extends MenuItem {
  private ArrayList<MenuItem> childs = new ArrayList<MenuItem>();
  private ArrayList<FailListener> failListenerList = new ArrayList<FailListener>();
  private ArrayList<InterDigitsTimeoutListener> interDigitsTimeoutListenerList = new ArrayList<InterDigitsTimeoutListener>();
  private ArrayList<MaxFailureListener> maxFailureListenerList = new ArrayList<MaxFailureListener>();
  private ArrayList<MaxTimeoutListener> maxTimeoutListenerList = new ArrayList<MaxTimeoutListener>();
  private ArrayList<PositionChangeListener> positionChangeListenerList = new ArrayList<PositionChangeListener>();
  private ArrayList<TimeoutListener> timeoutListenerList = new ArrayList<TimeoutListener>();
  private String exitFile;
  private String greetingsFile;
  private String invalidDigitFile;
  private boolean canInterruptGreetings = true;
  private boolean greetingsPlayed = false;
  private boolean playGreetingsAllways = false;
  private boolean sortChildsByDigits;
  private int failuresCount;
  private int interDigitsTimeout = 500;
  private int lastDigitsTimeout = 1500;
  private int maxDigits = 1;
  private int maxFailures = 3;
  private int maxTimeouts = 3;

  //private int timeout;
  private int timeoutCount;

  /**
   * <p>Creates a new instance of Menu</p>
   */
  public Menu() {
	  super();
  }

  /**
   * <p>Creates a new instance of Menu</p>
   */
  public Menu(final String digits, final String file) {
    super(digits, file);
  }

  /**
   * <p>Creates a new instance of Menu</p>
   */
  public Menu(final String digits, final String file, final Action action) {
    super(digits, file, action);
  }

  /**
   * Creates a new Menu object.
   *
   * @param digit DOCUMENT ME!
   * @param file DOCUMENT ME!
   * @param action DOCUMENT ME!
   * @param priority DOCUMENT ME!
   */
  public Menu(String digits, String file, Action action, int priority) {
    super(digits, file, action, priority);
  }

  /**
   * DOCUMENT ME!
   *
   * @param item DOCUMENT ME!
   */
  public void addChild(MenuItem item) {
    item.parent = this;
    childs.add(item);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void addFailListener(FailListener listener) {
    failListenerList.add(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void addInterDigitTimeoutListener(InterDigitsTimeoutListener listener) {
    interDigitsTimeoutListenerList.add(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void addMaxFailureListener(MaxFailureListener listener) {
    maxFailureListenerList.add(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void addMaxTimeoutListener(MaxTimeoutListener listener) {
    maxTimeoutListenerList.add(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void addPositionChangeListener(PositionChangeListener listener) {
    positionChangeListenerList.add(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void addTimeoutListener(TimeoutListener listener) {
    timeoutListenerList.add(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param digits DOCUMENT ME!
   */
  protected void checkDigits(String digits) {
    for (MenuItem m : getChilds()) {
      if (m.getDigits().equals(digits)) {
        throw new DuplicatedDigitsException();
      }
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param evt DOCUMENT ME!
   */
  protected void fireFailListenerFailurePerform(FailEvent evt) {
    for (FailListener listener : failListenerList) {
      listener.failurePerform(evt);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param evt DOCUMENT ME!
   */
  protected void fireInterDigitsTimeoutListener_timeoutPerform(InterDigitsTimeoutEvent evt) {
    for (InterDigitsTimeoutListener listener : interDigitsTimeoutListenerList) {
      listener.timeoutPerform(evt);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param evt DOCUMENT ME!
   */
  protected void fireMaxFailureEvent_maxFailurePerform(MaxFailureEvent evt) {
    for (MaxFailureListener listener : maxFailureListenerList) {
      listener.maxFailurePerform(evt);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param evt DOCUMENT ME!
   */
  protected void fireMaxTimeoutEventMaxTimeoutPerform(MaxTimeoutEvent evt) {
    for (MaxTimeoutListener listener : maxTimeoutListenerList) {
      listener.maxTimeoutPerform(evt);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param evt DOCUMENT ME!
   */
  protected void firePositionChangeEventPositionChange(PositionChangeEvent evt) {
    for (PositionChangeListener listener : positionChangeListenerList) {
      listener.positionChange(evt);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param evt DOCUMENT ME!
   */
  protected void fireTimeoutListenerTimeoutPerform(TimeoutEvent evt) {
    for (TimeoutListener listener : timeoutListenerList) {
      listener.timeoutPerform(evt);
    }
  }

  /**
   * @return the childs
   */
  public ArrayList<MenuItem> getChilds() {
    return childs;
  }

  /**
   * @return the exitFile
   */
  public String getExitFile() {
    return exitFile;
  }

  /**
   * @return the failuresCount
   */
  protected int getFailuresCount() {
    return failuresCount;
  }

  /**
   * @return the greetingsFile
   */
  public String getGreetingsFile() {
    return greetingsFile;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getInterDigitsTimeout() {
    return interDigitsTimeout;
  }

  /**
   * @return the invalidDigitFile
   */
  public String getInvalidDigitFile() {
    return invalidDigitFile;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getLastDigitsTimeout() {
    return lastDigitsTimeout;
  }

  /**
   * @return the maxDigits
   */
  public int getMaxDigits() {
    return maxDigits;
  }

  /**
   * @return the maxFailures
   */
  public int getMaxFailures() {
    return maxFailures;
  }

  /**
   * @return the maxTimeouts
   */
  protected int getMaxTimeouts() {
    return maxTimeouts;
  }

  /**
   * @return the timeoutCount
   */
  protected int getTimeoutCount() {
    return timeoutCount;
  }

  /**
   * DOCUMENT ME!
   */
  protected void incremenTimeoutCount() {
    setTimeoutCount(getTimeoutCount() + 1);
  }

  /**
   * DOCUMENT ME!
   */
  protected void incrementFailuresCount() {
    setFailuresCount(getFailuresCount() + 1);
  }

  /**
   * @return the canInterruptGreetings
   */
  public boolean isCanInterruptGreetings() {
    return canInterruptGreetings;
  }

  /**
   * @return the greetingsPlayed
   */
  public boolean isGreetingsPlayed() {
    return greetingsPlayed;
  }

  /**
   * @return the playGreetingsAllways
   */
  public boolean isPlayGreetingsAllways() {
    return playGreetingsAllways;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isSortChildsByDigits() {
    return sortChildsByDigits;
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void removeInterDigitsTimeoutListener(InterDigitsTimeoutListener listener) {
    interDigitsTimeoutListenerList.remove(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void removeMaxFailureListener(MaxFailureListener listener) {
    maxFailureListenerList.remove(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void removeMaxTimeoutListener(MaxTimeoutListener listener) {
    maxTimeoutListenerList.remove(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void removePositionChangeListener(PositionChangeListener listener) {
    positionChangeListenerList.remove(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void removeTimeoutListener(TimeoutListener listener) {
    timeoutListenerList.remove(listener);
  }

  /**
   * DOCUMENT ME!
   *
   * @param listener DOCUMENT ME!
   */
  public void removeTimeoutListener(FailListener listener) {
    failListenerList.remove(listener);
  }

  /**
   * DOCUMENT ME!
   */
  public void resetFailuresCount() {
    setFailuresCount(0);
  }

  /**
   * DOCUMENT ME!
   */
  public void resetTimeoutCount() {
    setTimeoutCount(0);
  }

  /**
   * @param canInterruptGreetings the canInterruptGreetings to set
   */
  public void setCanInterruptGreetings(boolean canInterruptGreetings) {
    this.canInterruptGreetings = canInterruptGreetings;
  }

  /**
   * @param childs the childs to set
   */
  public void setChilds(ArrayList<MenuItem> childs) {
    this.childs = childs;
  }

  /**
   * @param exitFile the exitFile to set
   */
  public void setExitFile(String exitFile) {
    this.exitFile = exitFile;
  }

  /**
   * @param failuresCount the failuresCount to set
   */
  public void setFailuresCount(int failuresCount) {
    this.failuresCount = failuresCount;
  }

  /**
   * @param greetingsFile the greetingsFile to set
   */
  public void setGreetingsFile(String greetingsFile) {
    this.greetingsFile = greetingsFile;
  }

  /**
   * @param greetingsPlayed the greetingsPlayed to set
   */
  protected void setGreetingsPlayed(boolean greetingsPlayed) {
    this.greetingsPlayed = greetingsPlayed;
  }

  /**
   * DOCUMENT ME!
   *
   * @param interDigitsTimeout DOCUMENT ME!
   */
  public void setInterDigitsTimeout(int interDigitsTimeout) {
    this.interDigitsTimeout = interDigitsTimeout;
  }

  /**
   * @param invalidDigitFile the invalidDigitFile to set
   */
  public void setInvalidDigitFile(String invalidDigitFile) {
    this.invalidDigitFile = invalidDigitFile;
  }

  /**
   * DOCUMENT ME!
   *
   * @param lastDigitsTimeout DOCUMENT ME!
   */
  public void setLastDigitsTimeout(int lastDigitsTimeout) {
    this.lastDigitsTimeout = lastDigitsTimeout;
  }

  /**
   * @param maxDigits the maxDigits to set
   */
  public void setMaxDigits(int maxDigits) {
    this.maxDigits = maxDigits;
  }

  /**
   * @param maxFailures the maxFailures to set
   */
  public void setMaxFailures(int maxFailures) {
    this.maxFailures = maxFailures;
  }

  /**
   * @param maxTimeouts the maxTimeouts to set
   */
  public void setMaxTimeouts(int maxTimeouts) {
    this.maxTimeouts = maxTimeouts;
  }

  /**
   * @param playGreetingsAllways the playGreetingsAllways to set
   */
  public void setPlayGreetingsAllways(boolean playGreetingsAllways) {
    this.playGreetingsAllways = playGreetingsAllways;
  }

  /**
   * DOCUMENT ME!
   *
   * @param sortChildsByDigits DOCUMENT ME!
   */
  public void setSortChildsByDigits(boolean sortChildsByDigits) {
    this.sortChildsByDigits = sortChildsByDigits;
  }

  /**
   * DOCUMENT ME!
   *
   * @param timeoutCount DOCUMENT ME!
   */
  protected void setTimeoutCount(int timeoutCount) {
    this.timeoutCount = timeoutCount;
  }
}
