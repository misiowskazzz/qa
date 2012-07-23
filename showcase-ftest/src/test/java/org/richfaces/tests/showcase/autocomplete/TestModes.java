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
package org.richfaces.tests.showcase.autocomplete;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.List;

import org.jboss.arquillian.graphene.spi.components.autocomplete.Suggestion;
import org.jboss.arquillian.graphene.spi.components.scrolling.ScrollingType;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.components.autocomplete.AutocompleteComponentImpl;
import org.richfaces.tests.components.autocomplete.TextSuggestionParser;
import org.richfaces.tests.showcase.AbstractWebDriverTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @version $Revision$
 */
public class TestModes extends AbstractWebDriverTest {

    /* **************************************************************************************
     * Locators**************************************************************************************
     */

    @FindBy(css = "form:nth-of-type(1)")
    private AutocompleteComponentImpl<String> autocompleteMinChar;

    @FindBy(css = "form:nth-of-type(2)")
    private AutocompleteComponentImpl<String> autocompleteMultipleSelections;

    @FindBy(css = "form:nth-of-type(3)")
    private AutocompleteComponentImpl<String> autocompleteSelectFirstFalse;

    /* **************************************************************************************
     * Tests**************************************************************************************
     */

    @BeforeMethod
    public void setParserToAutocompletes() {
        TextSuggestionParser parser = new TextSuggestionParser();

        autocompleteMinChar.setSuggestionParser(parser);
        autocompleteMultipleSelections.setSuggestionParser(parser);
        autocompleteSelectFirstFalse.setSuggestionParser(parser);
    }

    // @Test
    public void testMinCharInput() {
        String expected = "California";

        List<Suggestion<String>> suggestions = autocompleteMinChar.typeAndReturn("C");
        assertNull(suggestions, "There should be no suggestions!");

        suggestions = autocompleteMinChar.typeAndReturn("a");
        assertEquals(suggestions.size(), 1, "There should be one suggestion!");
        Suggestion<String> sugg = suggestions.get(0);
        assertEquals((suggestions.get(0).getValue()), expected, "The expected suggestion should be: " + expected);

        autocompleteMinChar.autocompleteWithSuggestion(sugg);
        String actual = autocompleteMinChar.getFirstInputValue();
        assertEquals(actual, expected, "The suggestion was not selected correctly!");
    }

    @Test(groups = "4.Future")
    public void testMultipleSelectionInputSelectByMouse() {
        checkMultipleAutocomplete(ScrollingType.BY_MOUSE);
    }

    @Test
    public void testMultipleSelectionInputSelectByKeys() {
        checkMultipleAutocomplete(ScrollingType.BY_KEYS);
    }

    private void checkMultipleAutocomplete(ScrollingType scrollingType) {
        String expected = "California";

        autocompleteMultipleSelections.type("Ca");
        Suggestion<String> suggestion = autocompleteMultipleSelections.getFirstSuggestion();
        assertEquals(suggestion.getValue(), expected, "The available suggestion is wrong!");

        autocompleteMultipleSelections.autocompleteWithSuggestion(suggestion);

        autocompleteMultipleSelections.type(" ");
        autocompleteMultipleSelections.type("Was");

        expected += " Washington";
        suggestion = autocompleteMultipleSelections.getFirstSuggestion();
        autocompleteMultipleSelections.autocompleteWithSuggestion(suggestion, scrollingType);

        assertEquals(autocompleteMultipleSelections.getInputValue(), expected, "The input shoul now contain diferent values!");
    }

    @Test
    public void testSelectFirstFalseInput() {
        String expected = "Ca";

        autocompleteSelectFirstFalse.type(expected);

        Actions builder = new Actions(webDriver);
        builder.sendKeys(Keys.ENTER);
        builder.build().perform();

        String actual = autocompleteSelectFirstFalse.getInputValue();
        assertEquals(actual, expected, "The input should contains typed in value, not the suggestion!");

        autocompleteSelectFirstFalse.type("l");
        autocompleteSelectFirstFalse.autocompleteWithSuggestion(autocompleteSelectFirstFalse.getFirstSuggestion());

        expected = "California";
        actual = autocompleteSelectFirstFalse.getInputValue();
        assertEquals(actual, expected, "The input should now contain value from suggestion");
    }

}
