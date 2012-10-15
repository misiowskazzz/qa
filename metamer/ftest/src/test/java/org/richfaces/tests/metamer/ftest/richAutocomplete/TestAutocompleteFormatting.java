/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import java.net.URL;
import org.openqa.selenium.support.FindBy;
import static org.jboss.arquillian.ajocado.utils.URLUtils.buildUrl;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.component.object.api.autocomplete.ClearType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.BeforeMethod;
import org.richfaces.tests.page.fragments.impl.autocomplete.AutocompleteComponentImpl;
import org.richfaces.tests.page.fragments.impl.autocomplete.TextSuggestionParser;
import static org.richfaces.tests.metamer.ftest.webdriver.AttributeList.autocompleteAttributes;
import org.testng.annotations.Test;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class TestAutocompleteFormatting extends AbstractAutocompleteTest<SimplePage>{

    @FindBy(id="form:autocomplete")
    private AutocompleteComponentImpl<String> autocomplete;

    @Inject
    @Use(booleans = { true, false })
    Boolean autofill;

    @Inject
    @Use(booleans = { true, false })
    Boolean selectFirst;

    @Inject
    @Use(strings= {"div", "list", "table"})
    String layout;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richAutocomplete/fetchValueAttr.xhtml");
    }

    @BeforeMethod
    public void setParser() {
        autocomplete.setSuggestionParser(new TextSuggestionParser());
    }

    @BeforeMethod
    public void prepareProperties() {
        autocompleteAttributes.set(AutocompleteAttributes.autofill, autofill);
        autocompleteAttributes.set(AutocompleteAttributes.selectFirst, selectFirst);
        autocompleteAttributes.set(AutocompleteAttributes.layout, layout);
        if (autofill == null) {
            autofill = false;
        }
        if (selectFirst == null) {
            selectFirst = false;
        }
        autocomplete.clear(ClearType.BACK_SPACE);
    }


    @Override
    protected SimplePage createPage() {
        return new SimplePage();
    }

    /**
     * This should test combination of @var and @fetchValue attributes of autocomplete
     */
    @Test
    public void testFormatting() {
        assertFalse(autocomplete.areSuggestionsAvailable());
        autocomplete.clear(ClearType.BACK_SPACE);
        autocomplete.type("ala");
        assertTrue(autocomplete.areSuggestionsAvailable());
        autocomplete.autocomplete();
        Graphene.waitGui().until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return !autocomplete.areSuggestionsAvailable();
            }
        });
        String expected = getExpectedStateForPrefix("ala", selectFirst).toLowerCase();
        String found = autocomplete.getInputValue().toLowerCase();
        assertTrue(found.startsWith(expected), "The input value should start with '"+expected+"', but '" + found + "' found.");
    }

    @Test
    public void testLayout() {
        autocomplete.type("Co");
        assertTrue(Graphene.element(getSuggestion("Colorado")).isPresent().apply(driver));
        assertTrue(Graphene.element(getSuggestion("[Denver]")).isPresent().apply(driver));
        assertTrue(Graphene.element(getSuggestion("Connecticut")).isPresent().apply(driver));
        assertTrue(Graphene.element(getSuggestion("[Hartford]")).isPresent().apply(driver));
    }

    private By getSuggestion(String value) {
        switch(getLayout()) {
            case DIV:
                return By.xpath("//div[@id='form:autocompleteItems']/div[contains(text(), '" + value + "')]");
            case LIST:
                return By.xpath("//ul[@id='form:autocompleteItems']/li[contains(text(), '" + value + "')]");
            case TABLE:
                return By.xpath("//table[@id='form:autocompleteItems']/tbody/tr/td[contains(text(), '" + value + "')]");
            default:
                throw new UnsupportedOperationException();
        }
    }

    private Layout getLayout() {
        return Layout.valueOf(layout.toUpperCase());
    }

    private static enum Layout {
        DIV, LIST, TABLE;
    }

}
