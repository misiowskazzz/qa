/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
 */
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import static org.richfaces.tests.metamer.ftest.webdriver.AttributeList.autocompleteAttributes;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.ajocado.utils.URLUtils;
import org.jboss.test.selenium.support.ui.ElementDisplayed;
import org.jboss.test.selenium.support.ui.ElementNotDisplayed;
import org.jboss.test.selenium.support.ui.ElementNotPresent;
import org.jboss.test.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test for rich:autocomplete on page faces/components/richAutocomplete/autocomplete.xhtml
 * using selenium2
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public class TestAutocompleteAttributes2 extends AbstractWebDriverTest {

    private SimpleAutocompletePage page;

    @Override
    public URL getTestUrl() {
        return URLUtils.buildUrl(contextPath, "faces/components/richAutocomplete/autocomplete.xhtml");
    }

    @BeforeMethod
    public void loadPage() {
        page = new SimpleAutocompletePage();
        injectWebElementsToPage(page);
    }

    @Test
    public void testInputClass() {
        String inputClassVal = "test-input-class";
        autocompleteAttributes.set(AutocompleteAttributes.inputClass, inputClassVal);
        assertTrue(page.autocompleteInput.getAttribute("class").contains(inputClassVal));
    }

    @Test
    public void testMinChars() {
        int minCharsVal = 3;
        autocompleteAttributes.set(AutocompleteAttributes.minChars, minCharsVal);
        page.autocompleteInput.sendKeys("al");
        new WebDriverWait(driver, WAIT_TIME).until(
            ElementNotDisplayed.getInstance().element(page.autocompleteItems));

        page.autocompleteInput.clear();
        page.autocompleteInput.submit();

        page.autocompleteInput.sendKeys("ala");
        new WebDriverWait(driver, WAIT_TIME).until(
            ElementDisplayed.getInstance().element(page.autocompleteItems));

        page.autocompleteInput.clear();
        page.autocompleteInput.submit();

        page.autocompleteInput.sendKeys("al");
        new WebDriverWait(driver, WAIT_TIME).until(
            ElementNotDisplayed.getInstance().element(page.autocompleteItems));
    }

    @Test
    public void testOnBeforeDomUpdate() {
        String testedValue = "before DOM update";
        autocompleteAttributes.set(AutocompleteAttributes.onbeforedomupdate, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("blah");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onbeforedomupdate);
        assertEquals(event, testedValue, "Attribute beforedomupdate doesn't work");
    }

    @Test
    public void testOnBegin() {
        String testedValue = "begin event";
        autocompleteAttributes.set(AutocompleteAttributes.onbegin, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("ala");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onbegin);
        assertEquals(event, testedValue, "Attribute onbegin doesn't work");
    }

    @Test
    public void testOnBlur() {
        String testedValue = "Blur event";
        autocompleteAttributes.set(AutocompleteAttributes.onblur, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("\t");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onblur);
        assertEquals(event, testedValue, "Attribute onblur doesn't work");
    }

    @Test
    public void testOnChange() {
        String testedValue = "Change event";
        autocompleteAttributes.set(AutocompleteAttributes.selectFirst, Boolean.TRUE);
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("Alabama");
        page.autocompleteInput.sendKeys("\n\r");
        page.autocompleteInput.submit();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onchange);
        assertEquals(event, testedValue, "Attribute onchange doesn't work");
    }

    @Test
    public void testOnClick() {
        String testedValue = "Click event";
        autocompleteAttributes.set(AutocompleteAttributes.onclick, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.click();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onclick);
        assertEquals(event, testedValue, "Attribute onclick doesn't work");
    }

    @Test
    public void testOnComplete() {
        String testedValue = "Complete event";
        autocompleteAttributes.set(AutocompleteAttributes.oncomplete, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("ala");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.oncomplete);
        assertEquals(event, testedValue, "Attribute oncomplete doesn't work");
    }

    @Test
    public void testOnDblClick() {
        String testedValue = "DblClick event";
        autocompleteAttributes.set(AutocompleteAttributes.ondblclick, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.ondblclick);
        assertEquals(event, testedValue, "Attribute ondblclick doesn't work");
    }

    @Test
    public void testOnError() {
        String testedValue = "Error event";
        autocompleteAttributes.set(AutocompleteAttributes.onerror, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onerror);
        assertEquals(event, testedValue, "Attribute onerror doesn't work");
    }

    @Test
    public void testOnFocus() {
        String testedValue = "Focus event";
        autocompleteAttributes.set(AutocompleteAttributes.onfocus, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("\t");
        page.autocompleteInput.click();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onfocus);
        assertEquals(event, testedValue, "Attribute onfocus doesn't work");
    }

    @Test
    public void testOnKeyDown() {
        String testedValue = "KeyDown event";
        autocompleteAttributes.set(AutocompleteAttributes.onkeydown, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("a");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onkeydown);
        assertEquals(event, testedValue, "Attribute onkeydown doesn't work");
    }

    @Test
    public void testOnListClick() {
        String testedValue = "ListClick event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistclick, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistclick);
        assertEquals(event, testedValue, "Attribute onlistclick doesn't work");
    }

    @Test
    public void testOnListDblClick() {
        String testedValue = "ListDblClick event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistdblclick, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistdblclick);
        assertEquals(event, testedValue, "Attribute onlistdblclick doesn't work");
    }

    @Test
    public void testOnListKeyDown() {
        String testedValue = "ListKeyDown event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistkeydown, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistkeydown);
        assertEquals(event, testedValue, "Attribute onlistkeydown doesn't work");
    }

    @Test
    public void testOnListKeyPress() {
        String testedValue = "ListKeyPress event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistkeypress, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistkeypress);
        assertEquals(event, testedValue, "Attribute onlistkeypress doesn't work");
    }

    @Test
    public void testOnListKeyUp() {
        String testedValue = "ListKeyUp event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistkeyup, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistkeyup);
        assertEquals(event, testedValue, "Attribute onlistkeyup doesn't work");
    }

    @Test
    public void testOnListMouseDown() {
        String testedValue = "ListMouseDown event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistmousedown, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistmousedown);
        assertEquals(event, testedValue, "Attribute onlistmousedown doesn't work");
    }

    @Test
    public void testOnListMouseMove() {
        String testedValue = "ListMouseMove event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistmousemove, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistmousemove);
        assertEquals(event, testedValue, "Attribute onlistmousemove doesn't work");
    }

    @Test
    public void testOnListMouseOut() {
        String testedValue = "ListMouseOut event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistmouseout, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistmouseout);
        assertEquals(event, testedValue, "Attribute onlistmouseout doesn't work");
    }

    @Test
    public void testOnListMouseOver() {
        String testedValue = "ListMouseOver event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistmouseover, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistmouseover);
        assertEquals(event, testedValue, "Attribute onlistmouseover doesn't work");
    }

    @Test
    public void testOnListMouseUp() {
        String testedValue = "ListMouseUp event";
        autocompleteAttributes.set(AutocompleteAttributes.onlistmouseup, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onlistmouseup);
        assertEquals(event, testedValue, "Attribute onlistmouseup doesn't work");
    }

    @Test
    public void testOnMouseDown() {
        String testedValue = "MouseDown event";
        autocompleteAttributes.set(AutocompleteAttributes.onmousedown, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmousedown);
        assertEquals(event, testedValue, "Attribute onmousedown doesn't work");
    }

    @Test
    public void testOnMouseMove() {
        String testedValue = "MouseMove event";
        autocompleteAttributes.set(AutocompleteAttributes.onmousemove, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmousemove);
        assertEquals(event, testedValue, "Attribute onmousemove doesn't work");
    }

    @Test
    public void testOnMouseOut() {
        String testedValue = "MouseOut event";
        autocompleteAttributes.set(AutocompleteAttributes.onmouseout, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmouseout);
        assertEquals(event, testedValue, "Attribute onmouseout doesn't work");
    }

    @Test
    public void testOnMouseOver() {
        String testedValue = "MouseOver event";
        autocompleteAttributes.set(AutocompleteAttributes.onmouseover, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmouseover);
        assertEquals(event, testedValue, "Attribute onmouseover doesn't work");
    }

    @Test
    public void testOnMouseUp() {
        String testedValue = "MouseUp event";
        autocompleteAttributes.reset(AutocompleteAttributes.onmouseup);
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        assertEquals(event, testedValue, "Attribute onmouseup doesn't work");
    }

    @Test
    public void testOnSelectItem() {
        String testedValue = "SelectItem event";
        autocompleteAttributes.set(AutocompleteAttributes.onselectitem, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("alabama");
        page.autocompleteItems.findElements(By.cssSelector("rf-au-opt")).get(0).click();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onselectitem);
        assertEquals(event, testedValue, "Attribute onselectitem doesn't work");
    }

    @Test
    public void testRendered() {
        autocompleteAttributes.set(AutocompleteAttributes.rendered, Boolean.FALSE);
        new WebDriverWait(driver, WAIT_TIME).until(
            ElementNotPresent.getInstance().element(page.autocomplete));
    }

    @Test
    public void testSelectedItemClass() {
        String selectedItemClassVal = "selected-item-test-class";
        autocompleteAttributes.set(AutocompleteAttributes.selectedItemClass, selectedItemClassVal);
        autocompleteAttributes.set(AutocompleteAttributes.selectFirst, Boolean.TRUE);
        page.autocompleteInput.sendKeys("ala");
        // page.autocompleteInput.sendKeys("\31");
        assertTrue(page.autocompleteSelectedItem.getAttribute("class").contains(selectedItemClassVal));
    }

    @Test
    public void testStyle() {
        String styleVal = "color: red;";
        autocompleteAttributes.set(AutocompleteAttributes.style, styleVal);
        assertTrue(page.autocomplete.getAttribute("style").contains(styleVal));
    }

    @Test
    public void testStyleClass() {
        String styleClassVal = "my-test-style-class";
        autocompleteAttributes.set(AutocompleteAttributes.styleClass, styleClassVal);
        assertTrue(page.autocomplete.getAttribute("class").contains(styleClassVal));
    }

    @Test
    public void testTabindex() {
        String tabindexVal = "3";
        autocompleteAttributes.set(AutocompleteAttributes.tabindex, tabindexVal);
        assertTrue(page.autocompleteInput.getAttribute("tabindex").contains(tabindexVal));
    }

}
