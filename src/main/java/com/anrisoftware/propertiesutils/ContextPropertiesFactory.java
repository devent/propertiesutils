package com.anrisoftware.propertiesutils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import com.google.common.io.Resources;

public class ContextPropertiesFactory {

	private final Class<?> context;

	public ContextPropertiesFactory(Object context) {
		this(context.getClass());
	}

	public ContextPropertiesFactory(Class<?> context) {
		this.context = context;
	}

	public ContextProperties fromResource(String resourceName)
			throws IOException {
		return fromResource(resourceName, Charset.defaultCharset());
	}

	public ContextProperties fromResource(String resourceName, Charset charset)
			throws IOException {
		URL url = Resources.getResource(context, resourceName);
		Properties properties = new Properties();
		Reader reader = new InputStreamReader(url.openStream(), charset);
		properties.load(reader);
		return new ContextProperties(context, properties);
	}
}
