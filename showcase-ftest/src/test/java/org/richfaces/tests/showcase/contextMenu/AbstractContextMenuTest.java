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
package org.richfaces.tests.showcase.contextMenu;

import static org.jboss.arquillian.ajocado.locator.LocatorFactory.jq;

import static org.jboss.arquillian.ajocado.Ajocado.elementVisible;
import static org.jboss.arquillian.ajocado.Ajocado.waitGui;

import static org.testng.Assert.assertEquals;

import org.jboss.arquillian.ajocado.geometry.Point;
import org.jboss.arquillian.ajocado.locator.JQueryLocator;
import org.richfaces.tests.showcase.AbstractAjocadoTest;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @version $Revision$
 */
public class AbstractContextMenuTest extends AbstractAjocadoTest {

    protected JQueryLocator contextMenu = jq(".rf-ctx-lst");

    protected void checkContextMenuRenderedAtCorrectPosition( JQueryLocator target, Point offset ) {
        selenium.contextMenuAt(target, offset);

        waitGui.failWith(new RuntimeException("The context menu should be visible")).timeout(2000)
            .until(elementVisible.locator(contextMenu));

        Point actualContextMenuPosition = selenium.getElementPosition(contextMenu);
        Point targetPosition = selenium.getElementPosition(target);
        Point expectedContextMenuPosition = targetPosition.add(offset);

        assertEquals(actualContextMenuPosition.getX(), expectedContextMenuPosition.getX());
        assertEquals(actualContextMenuPosition.getY(), expectedContextMenuPosition.getY());
    }

}