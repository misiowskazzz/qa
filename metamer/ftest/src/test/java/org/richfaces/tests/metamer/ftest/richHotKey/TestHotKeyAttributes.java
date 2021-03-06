/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2013, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.richHotKey;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestHotKeyAttributes extends AbstractHotKeyTest {

    @Inject
    @Use(empty = false)
    private KeysEnum key;

    private enum KeysEnum {

        CONTROL_Z(Keys.chord(Keys.CONTROL, "z"), "ctrl+z"),
        T("t", "t"),
        CTRL_X(HOTKEY_CTRL_X, "ctrl+x");
        private final String keysToSend;
        private final String keysString;

        private KeysEnum(String keysToSend, String keysString) {
            this.keysToSend = keysToSend;
            this.keysString = keysString;
        }
    }

    @Test
    public void enabledInInput() {
        // true
        pressHotkeyOnElement(HOTKEY_CTRL_X, firstInput.getInput());
        checkEvents(1, 0);
        clearHotKeyEvents();
        // false
        ATTRIBUTES_FIRST.set(HotKeyAttributes.enabledInInput, false);
        pressHotkeyOnElement(HOTKEY_CTRL_X, firstInput.getInput());
        checkEvents(0, 0);
    }

    @Test
    @Use(field = "key", enumeration = true)
    public void testKey() {
        ATTRIBUTES_FIRST.set(HotKeyAttributes.key, key.keysString);
        for (KeysEnum fireKeyEvent : KeysEnum.values()) {
            pressHotkeyOnElement(fireKeyEvent.keysToSend, firstInput.getInput());
            if (fireKeyEvent.equals(key)) {
                checkEvents(1, 0);
            } else {
                checkEvents(0, 0);
            }
            clearHotKeyEvents();
        }
    }

    private void testKeyForPreventDefault(String keys, String keyString, int expectedNum) {
        ATTRIBUTES_FIRST.set(HotKeyAttributes.key, keyString);
        // hits the hotkey twice, but default browser events will prevent from hitting the hotkey second time.
        new Actions(driver).sendKeys(keys).sendKeys(keys).perform();
        checkEvent("onkeydown", expectedNum);
        clearHotKeyEvents();
    }

    @Test
    public void testOnkeydownOnkeyup() {
        //these events are already binded, they add message to a4j:log on the page
        pressHotkeyOnElement(HOTKEY_CTRL_X, firstInput.getInput());
        checkEvents(1, 0);
        pressHotkeyOnElement(HOTKEY_ALT_X, firstInput.getInput());
        checkEvents(1, 1);
    }

    @Test
    public void testPreventDefaultFalse() {
        ATTRIBUTES_FIRST.set(HotKeyAttributes.preventDefault, Boolean.FALSE);
        try {
            testKeyForPreventDefault(Keys.chord(Keys.CONTROL, "f"), "ctrl+f", 1);
        } finally {
            firstInput.getInput().sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void testPreventDefaultTrue() {
        ATTRIBUTES_FIRST.set(HotKeyAttributes.preventDefault, Boolean.TRUE);
        testKeyForPreventDefault(Keys.chord(Keys.CONTROL, "f"), "ctrl+f", 2);
        testKeyForPreventDefault(Keys.chord(Keys.CONTROL, "h"), "ctrl+h", 2);
        testKeyForPreventDefault(Keys.chord(Keys.CONTROL, "u"), "ctrl+u", 2);
    }

    @Test
    public void testRendered() {
        ATTRIBUTES_FIRST.set(HotKeyAttributes.rendered, false);
        assertNotPresent(hotkey1, "Hotkey should not be present on page.");
        pressHotkeyOnElement(HOTKEY_CTRL_X, firstInput.getInput());
        pressHotkeyOnElement(HOTKEY_ALT_X, firstInput.getInput());
        checkEvents(0, 1);
    }

    @Test
    public void testSelector() {
        ATTRIBUTES_FIRST.set(HotKeyAttributes.selector, "input.first-input");
        pressHotkeyOnElement(HOTKEY_CTRL_X, firstInput.getInput());
        checkEvents(1, 0);
        pressHotkeyOnElement(HOTKEY_CTRL_X, secondInput.getInput());
        checkEvents(1, 0);//no change
        pressHotkeyOnElement(HOTKEY_CTRL_X, firstInput.getInput());
        checkEvents(2, 0);
    }
}
