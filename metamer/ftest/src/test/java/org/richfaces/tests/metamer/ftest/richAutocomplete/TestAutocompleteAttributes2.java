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
import org.jboss.test.selenium.support.ui.AttributeContains;
import org.jboss.test.selenium.support.ui.ElementDisplayed;
import org.jboss.test.selenium.support.ui.ElementNotDisplayed;
import org.jboss.test.selenium.support.ui.ElementNotPresent;
import org.jboss.test.selenium.support.ui.ElementPresent;
import org.jboss.test.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test for rich:autocomplete on page faces/components/richAutocomplete/autocomplete.xhtml
 * using selenium2
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public class TestAutocompleteAttributes2 extends AbstractWebDriverTest<SimpleAutocompletePage> {

    AttributeContains attributeContains = AttributeContains.getInstance();
    ElementDisplayed displayed = ElementDisplayed.getInstance();
    ElementNotDisplayed notDisplayed = ElementNotDisplayed.getInstance();
    ElementNotPresent notPresent = ElementNotPresent.getInstance();
    Actions actions;

    @Override
    public URL getTestUrl() {
        return URLUtils.buildUrl(contextPath, "faces/components/richAutocomplete/autocomplete.xhtml");
    }

    @BeforeMethod
    public void loadPage() {
        injectWebElementsToPage(page);
        actions = new Actions(driver);
    }

    /**
     * Since Autocomplete bean is Session Scoped, have to clean after every method by hand
     */
    @AfterMethod
    public void cleanAfterMethod() {
        deleteCookies();
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
        page.autocompleteInput.click();
        new WebDriverWait(driver, WAIT_TIME).until(notDisplayed.element(page.autocompleteItems));

        page.autocompleteInput.clear();
        page.autocompleteInput.submit();

        page.autocompleteInput.sendKeys("ala");
        new WebDriverWait(driver, WAIT_TIME).until(displayed.element(page.autocompleteItems));

        page.autocompleteInput.clear();
        page.autocompleteInput.submit();

        page.autocompleteInput.sendKeys("al");
        new WebDriverWait(driver, WAIT_TIME).until(notDisplayed.element(page.autocompleteItems));
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
        page.autocompleteInput.sendKeys(Keys.TAB);
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

        new WebDriverWait(driver, 500).until(ElementPresent.getInstance().element(page.autocompleteInput));
        actions.click(page.autocompleteInput).build().perform();

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

        actions.doubleClick(page.autocompleteInput).build().perform();

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

        actions.click(page.autocompleteInput).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onfocus);
        assertEquals(event, testedValue, "Attribute onfocus doesn't work");
    }

    @Test
    public void testOnKeyDown() {
        String testedValue = "KeyDown event";
        autocompleteAttributes.set(AutocompleteAttributes.onkeydown, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");

        actions.keyDown(page.autocompleteInput, Keys.ALT).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onkeydown);
        assertEquals(event, testedValue, "Attribute onkeydown doesn't work");
    }

    @Test
    public void testOnKeyUp() {
        String testedValue = "KeyUp event";
        autocompleteAttributes.set(AutocompleteAttributes.onkeyup, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");

        actions.keyUp(page.autocompleteInput, Keys.ALT).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onkeyup);
        assertEquals(event, testedValue, "Attribute onkeyup doesn't work");
    }

    @Test
    public void testOnMouseDown() {
        String testedValue = "MouseDown event";
        autocompleteAttributes.set(AutocompleteAttributes.onmousedown, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");

        actions.clickAndHold(page.autocompleteInput).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmousedown);
        assertEquals(event, testedValue, "Attribute onmousedown doesn't work");
    }

    @Test
    public void testOnMouseMove() {
        String testedValue = "MouseMove event";
        autocompleteAttributes.set(AutocompleteAttributes.onmousemove, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");

        actions.moveToElement(page.autocompleteInput).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmousemove);
        assertEquals(event, testedValue, "Attribute onmousemove doesn't work");
    }

    @Test
    public void testOnMouseOut() {
        String testedValue = "MouseOut event";
        autocompleteAttributes.set(AutocompleteAttributes.onmouseout, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");

        Dimension dim = page.autocompleteInput.getSize();
        actions.moveToElement(page.autocompleteInput)
            .moveByOffset(-dim.getWidth()/2 - 3, dim.getHeight()/2 + 3).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmouseout);
        assertEquals(event, testedValue, "Attribute onmouseout doesn't work");
    }

    @Test
    public void testOnMouseOver() {
        String testedValue = "MouseOver event";
        autocompleteAttributes.set(AutocompleteAttributes.onmouseover, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");

        actions.moveToElement(page.autocompleteInput, 3, 3 ).build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onmouseover);
        assertEquals(event, testedValue, "Attribute onmouseover doesn't work");
    }

    @Test
    public void testOnMouseUp() {
        String testedValue = "MouseUp event";
        autocompleteAttributes.reset(AutocompleteAttributes.onmouseup);
        executeJS("window.metamerEvents = \"\";");

        actions.moveToElement(page.autocompleteInput, -3, 3).clickAndHold().release().build().perform();

        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        assertEquals(event, testedValue, "Attribute onmouseup doesn't work");
    }

    @Test
    public void testOnSelectItem() {
        String testedValue = "SelectItem event";
        autocompleteAttributes.set(AutocompleteAttributes.onselectitem, "metamerEvents += \"" + testedValue + " \"");
        executeJS("window.metamerEvents = \"\";");
        page.autocompleteInput.clear();
        page.autocompleteInput.sendKeys("ala");
        page.autocompleteInput.click();
        new WebDriverWait(driver, WAIT_TIME).until(displayed.element(page.autocompleteItems));
        page.autocompleteItems.findElements(By.cssSelector("div.rf-au-itm")).get(0).click();
        String event = expectedReturnJS("return window.metamerEvents", testedValue);
        autocompleteAttributes.reset(AutocompleteAttributes.onselectitem);
        assertEquals(event, testedValue, "Attribute onselectitem doesn't work");
    }

    @Test
    public void testRendered() {
        autocompleteAttributes.set(AutocompleteAttributes.rendered, Boolean.FALSE);
        new WebDriverWait(driver, WAIT_TIME).until(notPresent.element(page.autocomplete));
    }

    @Test
    public void testSelectedItemClass() {
        String selectedItemClassVal = "selected-item-test-class";
        autocompleteAttributes.set(AutocompleteAttributes.selectedItemClass, selectedItemClassVal);
        autocompleteAttributes.set(AutocompleteAttributes.selectFirst, Boolean.TRUE);
        page.autocompleteInput.sendKeys("ala");
        page.autocompleteInput.click();
        new WebDriverWait(driver, WAIT_TIME).until(notDisplayed.element(page.autocompleteItems));
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
        new WebDriverWait(driver, 5).until(attributeContains.element(page.autocompleteInput)
            .attributeName("tabindex").attributeValue(tabindexVal));
    }

    @Override
    protected SimpleAutocompletePage createPage() {
        return new SimpleAutocompletePage();
    }

}