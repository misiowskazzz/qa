/**
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
 */
package org.richfaces.tests.metamer.ftest.richPlaceholder;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.page.fragments.impl.input.inplaceInput.InplaceInput;
import org.richfaces.tests.page.fragments.impl.input.inplaceInput.RichFacesInplaceInput;
import org.testng.annotations.Test;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestPlaceHolderWithInplaceInput extends AbstractPlaceholderJSFTest {

    @FindBy(css = INPUT1_ID + " [id$=Input]")
    WebElement input1;
    @FindBy(css = INPUT2_ID + " [id$=Input]")
    WebElement input2;
    @FindBy(css = INPUT1_ID)
    RichFacesInplaceInput ii1;
    @FindBy(css = INPUT2_ID)
    RichFacesInplaceInput ii2;

    public TestPlaceHolderWithInplaceInput() {
        super("inplaceInput");
    }

    @Override
    WebElement getInput1() {
        return input1;
    }

    @Override
    WebElement getInput2() {
        return input2;
    }

    @Override
    protected String getInput1EditValue() {
        return ii1.getEditValue();
    }

    @Override
    protected String getInput1Value() {
        return ii1.getLabelValue();
    }

    @Override
    protected String getInput2Value() {
        return ii2.getLabelValue().trim();
    }

    @Override
    protected String getInput1StyleClass() {
        return ii1.getLabelInputElement().getAttribute("class");
    }

    @Override
    protected String getInput2StyleClass() {
        return ii2.getLabelInputElement().getAttribute("class");
    }

    @Override
    protected void sendKeysToInput1(String keys) {
        ii1.editBy(InplaceInput.OpenBy.CLICK).type(keys).confirm();
    }

    @Override
    protected void clearInput1() {
        ii1.editBy(InplaceInput.OpenBy.CLICK).type("").confirm();
    }

    @Override
    protected void clickOnInput1() {
        ii1.editBy(InplaceInput.OpenBy.CLICK);
    }

    @Override
    protected void clickOnInput2() {
        ii2.editBy(InplaceInput.OpenBy.CLICK);
    }

    @Test
    @RegressionTest({ "https://issues.jboss.org/browse/RF-12625", "https://issues.jboss.org/browse/RF-12623" })
    @Override
    public void testAjaxSubmit() {
        super.testAjaxSubmit();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testClickOnInputWithPlaceholder() {
        super.testClickOnInputWithPlaceholder();
    }

    @Test(groups = "4.Future")
    @IssueTracking("https://issues.jboss.org/browse/RF-12651")
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testDeleteTextFromInputWithPlaceholder() {
        super.testDeleteTextFromInputWithPlaceholder();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testHTTPSubmit() {
        super.testHTTPSubmit();
    }

    @Test(groups = "4.Future")
    @IssueTracking("https://issues.jboss.org/browse/RF-12650")
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testRendered() {
        super.testRendered();
    }

    @Test(groups = "4.Future")
    @IssueTracking("https://issues.jboss.org/browse/RF-12651")
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testStyleClass() {
        super.testStyleClass();
    }

    @Test(groups = "4.Future")
    @IssueTracking("https://issues.jboss.org/browse/RF-12651")
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testTypeToInputWithPlaceholder() {
        super.testTypeToInputWithPlaceholder();
    }
}
