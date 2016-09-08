package com.simplexservers.minecraft.fileutils.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A configuration that supports YAML files.
 *
 * @author Zach Abney
 */
public class YAMLConfiguration extends Configuration {

	/**
	 * Constructs a new YAMLConfiguration which loads the configuration from
	 * the given .yml file.
	 *
	 * @param configFile The YML configuration file to load.
	 * @throws IOException If there was an error loading or parsing the configuration file.
	 */
	@SuppressWarnings("unchecked")
	public YAMLConfiguration(File configFile) throws IOException {
		super(new LinkedHashMap<>());
		try (InputStream in = new FileInputStream(configFile)) {
			Map<String, Object> properties = new Yaml().loadAs(in, LinkedHashMap.class);
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			super.properties.putAll(properties);
		}
	}

}
