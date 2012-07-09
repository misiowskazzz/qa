/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2012, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import static java.text.MessageFormat.format;
import static org.jboss.arquillian.ajocado.Graphene.guardHttp;
import static org.jboss.arquillian.ajocado.Graphene.waitGui;
import static org.jboss.arquillian.ajocado.utils.URLUtils.buildUrl;
import static org.jboss.test.selenium.locator.utils.LocatorEscaping.jq;
import static org.richfaces.tests.metamer.ftest.attributes.AttributeList.autocompleteAttributes;
import static org.testng.Assert.assertEquals;

import java.net.URL;

import org.jboss.arquillian.ajocado.dom.Event;
import org.jboss.arquillian.ajocado.locator.JQueryLocator;
import org.jboss.arquillian.ajocado.locator.element.ElementLocator;
import org.jboss.arquillian.ajocado.waiting.conditions.ElementPresent;
import org.jboss.test.selenium.waiting.EventFiredCondition;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class TestAutocompleteAttributes extends AbstractAutocompleteTest {

    private static final JQueryLocator PHASE_FORMAT = jq("div#phasesPanel li:eq({0})");
    private static final String PHASE_LISTENER_LOG_FORMAT = "*1 value changed: {0} -> {1}";

    private JQueryLocator autocompleteInput = pjq("input.rf-au-inp[id$=autocompleteInput]");
    private JQueryLocator autocompleteList = pjq("div[id$=autocompleteList]");

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richAutocomplete/autocomplete.xhtml");
    }

    @Test
    public void testOnblur() {
        // testFireEvent(Event.BLUR, autocompleteComponent);
        String eventValue = "blur event";
        autocompleteAttributes.set(AutocompleteAttributes.onblur, "metamerEvents += \"" + eventValue + "\"");
        selenium.click(autocompleteInput);
        autocomplete.typeKeys("\t");
        waitGui.failWith("Attribute onblur does not work correctly").until(new EventFiredCondition(Event.BLUR));
    }

    @Test
    public void testOnchange() {
        String eventValue = "change event";
        autocompleteAttributes.set(AutocompleteAttributes.onchange, "metamerEvents += \"" + eventValue + "\"");
        autocomplete.typeKeys("alabama");
        selenium.fireEvent(autocompleteInput, Event.BLUR);
        waitGui.failWith("Attribute onblur does not work correctly").until(new EventFiredCondition(Event.CHANGE));
    }

    @Test
    public void testOnClick() {
        testFireEvent(Event.CLICK, autocompleteInput);
    }

    @Test
    public void testOnComplete() {
        String eventName = "complete";
        ElementLocator<?> eventInput = pjq("input[id$=oncompleteInput]");
        String value = "metamerEvents += \"" + eventName + " \"";

        guardHttp(selenium).type(eventInput, value);

        // before perform action on list, need display suggestions
        autocomplete.clearInputValue();
        autocomplete.typeKeys2("ala");

        waitGui.failWith("Attribute on" + eventName + " does not work correctly").until(
                new EventFiredCondition(new Event(eventName)));
    }

    @Test
    public void testOnDblclick() {
        testFireEvent(Event.DBLCLICK, autocompleteInput);
    }

    @Test
    public void testOnFocus() {
        testFireEvent(Event.FOCUS, autocompleteInput);
    }

    @Test
    public void testOnKeyDown() {
        testFireEvent(Event.KEYDOWN, autocompleteInput);
    }

    @Test
    public void testOnKeyUp() {
        testFireEvent(Event.KEYUP, autocompleteInput);
    }

    @Test
    public void testOnKeyPress() {
        testFireEvent(Event.KEYPRESS, autocompleteInput);
    }

    @Test
    public void testOnListClick() {
        verifyFireEventOnList(Event.CLICK, autocompleteList, "listclick");
    }

    @Test
    public void testOnListDblClick() {
        verifyFireEventOnList(Event.DBLCLICK, autocompleteList, "listdblclick");
    }

    @Test
    public void testOnListKeyDown() {
        verifyFireEventOnList(Event.KEYDOWN, autocompleteList, "listkeydown");
    }

    @Test
    public void testOnListKeyPress() {
        verifyFireEventOnList(Event.KEYPRESS, autocompleteList, "listkeypress");
    }

    @Test
    public void testOnListKeyUp() {
        verifyFireEventOnList(Event.KEYUP, autocompleteList, "listkeyup");
    }

    @Test
    public void testOnListMouseDown() {
        verifyFireEventOnList(Event.MOUSEDOWN, autocompleteList, "listmousedown");
    }

    @Test
    public void testOnListMouseUp() {
        verifyFireEventOnList(Event.MOUSEUP, autocompleteList, "listmouseup");
    }

    @Test
    public void testOnListMouseMove() {
        verifyFireEventOnList(Event.MOUSEMOVE, autocompleteList, "listmousemove");
    }

    @Test
    public void testOnListMouseOut() {
        verifyFireEventOnList(Event.MOUSEOUT, autocompleteList, "listmouseout");
    }

    @Test
    public void testOnListMouseOver() {
        verifyFireEventOnList(Event.MOUSEOVER, autocompleteList, "listmouseover");
    }

    @Test
    public void testOnMouseDown() {
        testFireEvent(Event.MOUSEDOWN, autocompleteInput, "mousedown");
    }

    @Test
    public void testOnMouseMove() {
        testFireEvent(Event.MOUSEMOVE, autocompleteInput, "mousemove");
    }

    @Test
    public void testOnMouseOut() {
        testFireEvent(Event.MOUSEOUT, autocompleteInput, "mouseout");
    }

    @Test
    public void testOnMouseOver() {
        testFireEvent(Event.MOUSEOVER, autocompleteInput, "mouseover");
    }

    @Test
    public void testOnMouseUp() {
        testFireEvent(Event.MOUSEUP, autocompleteInput, "mouseup");
    }

    @Test
    public void testOnSelectItem() {
        autocompleteAttributes.set(AutocompleteAttributes.onselectitem, "metamerEvents += \" selectItem \"");
        autocomplete.clearInputValue();
        autocomplete.typeKeys2("ala");
        autocomplete.selectByMouse("Alaska");
    }

    @Test
    @Templates(exclude = { "richPopupPanel" })
    public void testValueChangeListener() {
        getAutocomplete().clearInputValue();
        getAutocomplete().typeKeys2("something");
        getAutocomplete().confirmByKeys();

        waitFor(2000);

        getAutocomplete().clearInputValue();
        getAutocomplete().typeKeys2("something else");
        getAutocomplete().confirmByKeys();
        // valueChangeListener output as 4th record
        waitFor(2000);
        assertEquals(selenium.getText(PHASE_FORMAT.format(3)), format(PHASE_LISTENER_LOG_FORMAT, "something", "something else"));
    }

    @Test(groups = { "4.Future" })
    @Templates(value = { "richPopupPanel" })
    public void testValueChangeListenerInPopupPanel() {
        testValueChangeListener();
    }

    private void verifyFireEventOnList(Event event, ElementLocator<?> element, String attributeName) {

        autocomplete.clearInputValue();
        ElementLocator<?> eventInput = pjq("input[id$=on" + attributeName + "Input]");
        String value = "metamerEvents += \"" + event.getEventName() + " \"";

        guardHttp(selenium).type(eventInput, value);

        // before perform action on list, need display suggestions
        autocomplete.typeKeys("ala");
        waitGui.until(ElementPresent.getInstance().locator(element));
        selenium.fireEvent(element, event);

        waitGui.failWith("Attribute on" + attributeName + " does not work correctly").until(
                new EventFiredCondition(event));

    }

}
