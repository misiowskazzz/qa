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
package org.richfaces.tests.showcase.ftest.webdriver.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public abstract class AbstractRichMessagePage implements ShowcasePage {

    @FindBy(xpath = "//*[@class='example-cnt']//input[contains(@id, 'address')]")
    private WebElement addressInput;
    @FindBy(xpath = "//*[@class='example-cnt']//input[contains(@id, 'job')]")
    private WebElement jobInput;
    @FindBy(xpath = "//*[@class='example-cnt']//input[contains(@id, 'name')]")
    private WebElement nameInput;
    @FindBy(xpath = "//*[@class='example-cnt']//input[@type='submit']")
    private WebElement submitButton;
    @FindBy(xpath = "//*[@class='example-cnt']//input[contains(@id, 'zip')]")
    private WebElement zipInput;

    public WebElement getAddressInput() {
        return addressInput;
    }

    public WebElement getJobInput() {
        return jobInput;
    }

    public WebElement getNameInput() {
        return nameInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getZipInput() {
        return zipInput;
    }

    public abstract WebElement getAddressErrorMessageArea();
    public abstract WebElement getJobErrorMessageArea();
    public abstract WebElement getNameErrorMessageArea();
    public abstract WebElement getZipErrorMessageArea();

}
