/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils.
 *
 * propertiesutils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils

import groovy.util.logging.Slf4j

import java.awt.Color
import java.awt.Font

import javax.swing.BorderFactory

import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.common.io.Resources

/**
 * Test the swing context properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
@Slf4j
class SwingPropertiesTest extends TestUtils {

	static URL RESOURCE_URL = Resources.getResource("swing_test.properties")

	@Test
	void "border property"() {
		def properties = new SwingProperties(this,
						new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL))
		def border = properties.getBorderProperty "border"
		log.info "Loaded border {}.", border
		assert border == BorderFactory.createEtchedBorder()
	}

	@Test
	void "color property"() {
		def properties = new SwingProperties(this,
						new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL))
		def color = properties.getColorProperty "color"
		log.info "Loaded color {}.", color
		assert color == new Color(10, 5, 20)
	}

	@Test
	void "font property"() {
		def properties = new SwingProperties(this,
						new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL))
		def font = properties.getFontProperty "font"
		log.info "Loaded font {}.", font
		assert font == new Font(Font.SERIF, Font.BOLD, 20)
	}

	@Test
	void "table layout property"() {
		def properties = new SwingProperties(this,
						new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL))
		def layout = properties.getLayoutProperty "table_layout"
		log.info "Loaded font {}.", layout
	}
}
