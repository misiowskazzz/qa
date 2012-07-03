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
package org.richfaces.tests.archetypes.kitchensink.ftest.mobile;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jboss.test.selenium.support.pagefactory.StaleReferenceAwareFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.richfaces.tests.archetypes.kitchensink.ftest.common.AbstractKitchensinkTest;
import org.richfaces.tests.archetypes.kitchensink.ftest.common.page.MemberDetails;
import org.richfaces.tests.archetypes.kitchensink.ftest.common.page.MembersTable;
import org.richfaces.tests.archetypes.kitchensink.ftest.common.page.RegisterForm;
import org.richfaces.tests.archetypes.kitchensink.ftest.mobile.page.MenuPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @version $Revision$
 */
public class TestForm extends AbstractKitchensinkTest {

    private RegisterForm registerForm = new RegisterForm();
    private MembersTable membersTable = new MembersTable();
    private MemberDetails memberDetails = new MemberDetails();
    private MenuPage menuPage = new MenuPage();

    @BeforeMethod(groups = "arquillian")
    public void initialiseWebElements() {
        FieldDecorator fd = new StaleReferenceAwareFieldDecorator(new DefaultElementLocatorFactory(webDriver), 2);
        PageFactory.initElements(fd, registerForm);
        PageFactory.initElements(fd, membersTable);
        PageFactory.initElements(fd, memberDetails);
        PageFactory.initElements(fd, menuPage);
    }

    @Test
    public void testAddCorrectMember() {
        menuPage.gotoAddMemberPage();
        registerForm.switchOffAutocompleteOnInputs(webDriver);
        
        // set twice as workaround for first time filling in input
        String nameSet = registerForm.setCorrectName();
        registerForm.setCorrectName();
        registerForm.setCorrectEmail();
        registerForm.setCorrectPhone();

        registerForm.clickOnRegisterButton();
        menuPage.waitFor(MenuPage.PAGE_TRANSITION_WAIT);

        String table = membersTable.getTable().getText();
        assertTrue(table.contains(nameSet), "The new member was not added correctly!");
    }

    @Test
    public void testCSVEmailPattern() {
        menuPage.gotoAddMemberPage();
        registerForm.switchOffAutocompleteOnInputs(webDriver);
        
        final int numberOfErrorMessagesBefore = registerForm.getErrorMessages().size();

        registerForm.setIncorrectEmailPatternViolation();
        registerForm.getPhoneInput().click();

        registerForm.waitForErrorMessages(3, webDriver, numberOfErrorMessagesBefore);
        assertEquals(registerForm.getErrorMessages().size(), 1, ERROR_MSG_CSV);

        registerForm.isErrorMessageRendered(CSV_EMAIL);
    }

    @Test(groups = "4.Future")
    public void testCSVNamePattern() {
        menuPage.gotoAddMemberPage();
        registerForm.switchOffAutocompleteOnInputs(webDriver);
        
        final int numberOfErrorMessagesBefore = registerForm.getErrorMessages().size();

        registerForm.setIncorrectNamePatternViolation();
        registerForm.getEmailInput().click();

        registerForm.waitForErrorMessages(WAIT_FOR_ERR_MSG_RENDER, webDriver, numberOfErrorMessagesBefore);
        assertEquals(registerForm.getErrorMessages().size(), 1, ERROR_MSG_CSV);
    }

    @Test
    public void testCSVPhonePattern() {
        menuPage.gotoAddMemberPage();
        registerForm.switchOffAutocompleteOnInputs(webDriver);
        
        final int numberOfErrorMessagesBefore = registerForm.getErrorMessages().size();

        registerForm.setIncorrectPhonePatternViolation();
        registerForm.getEmailInput().click();

        registerForm.waitForErrorMessages(WAIT_FOR_ERR_MSG_RENDER, webDriver, numberOfErrorMessagesBefore);
        assertEquals(registerForm.getErrorMessages().size(), 1, ERROR_MSG_CSV);

        registerForm.isErrorMessageRendered(CSV_PHONE);
    }

    @Test
    public void testSSVInputsEmpty() {
        menuPage.gotoAddMemberPage();
        registerForm.switchOffAutocompleteOnInputs(webDriver);
        
        final int numberOfErrorMessagesBefore = registerForm.getErrorMessages().size();
        registerForm.clickOnRegisterButton();

        registerForm.waitForErrorMessages(WAIT_FOR_ERR_MSG_RENDER, webDriver, numberOfErrorMessagesBefore);
        assertEquals(registerForm.getErrorMessages().size(), 3, ERROR_MSG_CSV);
    }
    
    @Test
    public void testSSVWrongInputs() {
        menuPage.gotoAddMemberPage();
        registerForm.switchOffAutocompleteOnInputs(webDriver);
        
        final int numberOfErrorMessagesBefore = registerForm.getErrorMessages().size();
        
        registerForm.setIncorrectNameTooShort();
        registerForm.setIncorrectEmailPatternViolation();
        registerForm.setIncorrectPhonePatternViolation();
        
        registerForm.clickOnRegisterButton();
        
        registerForm.waitForErrorMessages(WAIT_FOR_ERR_MSG_RENDER, webDriver, numberOfErrorMessagesBefore);
        assertEquals(registerForm.getErrorMessages().size(), 3, ERROR_MSG_CSV);

        registerForm.isErrorMessageRendered(SSV_NAME_SIZE, SSV_PHONE_SIZE, CSV_EMAIL);
    }
    
//    @Test
//    public void testViewMemberDetails() {
//        
//    }

}
