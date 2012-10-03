/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils-swingproperties.
 *
 * propertiesutils-swingproperties is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils-swingproperties is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils-swingproperties. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils

import java.awt.BorderLayout

import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestFrameUtil
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Test the swing context properties with Groovy script.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class SwingPropertiesTest extends TestFrameUtil {

	static URL RESOURCE_URL = resourceURL("swingtest.properties", SwingPropertiesTest)

	static title = "Swing Properties"

	static Injector injector

	static SwingPropertiesFactory factory

	@BeforeClass
	static void createFactory() {
		injector = Guice.createInjector new SwingPropertiesModule()
		factory = injector.getInstance SwingPropertiesFactory
	}

	@Test
	void "style label"() {
		def p = new ContextPropertiesFactory(this).fromResource(RESOURCE_URL)
		p = factory.create(this, p, new ScriptEvaluating("groovy"))

		def panel = new JPanel()
		def label = new JLabel()
		panel.add label, BorderLayout.NORTH
		label = p.getStyledComponent(label, "label")
		beginPanelFrame title, panel, {
			fixture.label().requireText "Test"
		}
	}

	@Test
	void "style panel with label and text field"() {
		def p = new ContextPropertiesFactory(this).fromResource(RESOURCE_URL)
		p = factory.create(this, p, new ScriptEvaluating("groovy"))

		def panel = new JPanel()
		def label = new JLabel()
		def textField = new JTextField()
		panel = p.getStyledComponent(panel, "panel", ["textLabel": label, "textField": textField])
		beginPanelFrame title, panel, {
			fixture.label().requireText "Test"
			fixture.textBox().requireText ""
		}
	}
}
