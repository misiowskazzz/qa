/**
 *
 */
package org.richfaces.tests.metamer.ftest.richInputNumberSlider;

import static org.jboss.arquillian.ajocado.Graphene.retrieveText;
import static org.jboss.arquillian.ajocado.Graphene.waitGui;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jboss.arquillian.ajocado.geometry.Point;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author jjamrich
 *
 */
public class TestInputNumberSliderRE extends AbstractSliderTest {

    @Test(groups = { "screenshot" })
    @Use(field = "number", value = "correctNumbers")
    @Override
    public void testTypeIntoInputCorrect() {
        super.testTypeIntoInputCorrect();
    }

    @Test(groups = { "screenshot" })
    @Override
    public void testClickRight() {
        super.testClickRight();
    }

    @Test(groups = { "screenshot" })
    @Templates(exclude = {"richPopupPanel", "richAccordion"})
    @Override
    public void testClick() {
        String reqTime = selenium.getText(time);

        selenium.mouseDownAt(track, new Point(0, 0));

        waitGui.failWith("Page was not updated").waitForChange(reqTime, retrieveText.locator(time));
        assertEquals(selenium.getText(output), "-10", "Output was not updated.");
        int margin = selenium.getElementPositionLeft(handle) - selenium.getElementPositionLeft(track);
        assertTrue(margin <= 3, "Left margin of handle should be 0 (was " + margin + ").");

        reqTime = selenium.getText(time);
        selenium.mouseDownAt(track, new Point(30, 0));
        waitGui.failWith("Page was not updated").waitForChange(reqTime, retrieveText.locator(time));

        assertEquals(selenium.getText(output), "-7", "Output was not updated.");
        margin = selenium.getElementPositionLeft(handle) - selenium.getElementPositionLeft(track);
        assertTrue(margin >= 27 && margin <= 33, "Left margin of handle should be between 27 and 33 (was " + margin + ").");

        reqTime = selenium.getText(time);
        selenium.mouseDownAt(track, new Point(195, 0));
        waitGui.failWith("Page was not updated").waitForChange(reqTime, retrieveText.locator(time));
        assertEquals(selenium.getText(output), "10", "Output was not updated.");
        margin = selenium.getElementPositionLeft(handle) - selenium.getElementPositionLeft(track);
        assertTrue(margin >= 192 && margin <= 198, "Left margin of handle should be between 192 and 198 (was " + margin + ").");
    }

}
