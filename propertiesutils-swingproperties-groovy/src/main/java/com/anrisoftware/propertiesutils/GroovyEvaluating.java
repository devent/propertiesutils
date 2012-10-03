/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils-swingproperties-groovy.
 *
 * propertiesutils-swingproperties-groovy is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils-swingproperties-groovy is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils-swingproperties-groovy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils;

import static java.util.Arrays.asList;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.script.ScriptException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;

/**
 * Evaluating the script with the Java Scripting (JSR 223).
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class GroovyEvaluating implements Evaluating {

	private static final String IMPORTS_KEY = "imports";

	private static final String STAR_IMPORTS_KEY = "star_imports";

	private static final String STATIC_IMPORTS_KEY = "static_imports";

	private static final String STATIC_STAR_IMPORTS_KEY = "static_star_imports";

	private final String[] staticStarImports;

	private final String[] staticImports;

	private final String[] starImports;

	private final String[] imports;

	private final CompilerConfiguration compilerConfiguration;

	/**
	 * Sets the engine name that should be used.
	 * 
	 * @param engineName
	 *            the name of the engine.
	 */
	@Inject
	GroovyEvaluating(
			@Named("groovy-evaluating-properties") Properties properties) {
		ContextProperties p = new ContextProperties(this, properties);
		this.staticStarImports = (String[]) p.getListProperty(
				STATIC_STAR_IMPORTS_KEY).toArray();
		this.staticImports = (String[]) p.getListProperty(STATIC_IMPORTS_KEY)
				.toArray();
		this.starImports = (String[]) p.getListProperty(STAR_IMPORTS_KEY)
				.toArray();
		this.imports = (String[]) p.getListProperty(IMPORTS_KEY).toArray();
		this.compilerConfiguration = createCompilerConfiguration();
	}

	private CompilerConfiguration createCompilerConfiguration() {
		CompilerConfiguration compiler = new CompilerConfiguration();
		compiler.addCompilationCustomizers(createImportCustomizer());
		compiler.addCompilationCustomizers(createSecureCustomizer());
		return compiler;
	}

	private ImportCustomizer createImportCustomizer() {
		ImportCustomizer importCustomizer = new ImportCustomizer();
		importCustomizer.addImports(imports);
		importCustomizer.addStarImports(starImports);
		importCustomizer.addStaticStars(staticStarImports);
		return importCustomizer;
	}

	private SecureASTCustomizer createSecureCustomizer() {
		SecureASTCustomizer secure = new SecureASTCustomizer();
		secure.setClosuresAllowed(false);
		secure.setMethodDefinitionAllowed(false);
		secure.setImportsWhitelist(asList(imports));
		secure.setStarImportsWhitelist(asList(starImports));
		secure.setStaticImportsWhitelist(asList(staticImports));
		secure.setStaticStarImportsWhitelist(asList(staticStarImports));
		return secure;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(String script, Map<String, Object> variables)
			throws ScriptException {
		GroovyShell shell = createShell();
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			shell.setVariable(entry.getKey(), entry.getValue());
		}
		try {
			T result = (T) shell.evaluate(script);
			return result;
		} catch (CompilationFailedException e) {
			throw new ScriptException(e);
		}
	}

	private GroovyShell createShell() {
		ClassLoader classLoader = getClass().getClassLoader();
		return new GroovyShell(classLoader, new Binding(),
				compilerConfiguration);
	}

}
