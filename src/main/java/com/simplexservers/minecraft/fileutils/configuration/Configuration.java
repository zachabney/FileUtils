package com.simplexservers.minecraft.fileutils.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A model that represents a configuration or configuration section.
 *
 * @author Zach Abney
 */
public class Configuration {

	/**
	 * The character to separate sections in key paths.
	 */
	private static final char DELIMITER = '.';
	/**
	 * The properties and values of the configuration.
	 */
	protected Map<String, Object> properties;

	/**
	 * Constructs a new Configuration with the given property mapping.
	 *
	 * @param properties The properties and values of the configuration.
	 */
	protected Configuration(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Gets the lowest level section of the key in the form of a Configuration.
	 * <p>
	 * Note: Configuration sections within the key should be delimited with the '.' character.
	 * </p>
	 *
	 * @param key The key to find the lowest level section of.
	 * @return The Configuration that represents the lowest level section of the given key.
	 */
	@SuppressWarnings("unchecked")
	private Configuration getLowestSection(String key) {
		int index = key.indexOf(DELIMITER);
		if (index == -1) { // We're in the lowest section level
			return this;
		}

		String root = key.substring(0, index);
		Object section = properties.get(root);
		if (section == null) {
			section = new LinkedHashMap<>();
			properties.put(root, section);
		}

		return new Configuration((Map<String, Object>) section).getLowestSection(key.substring(index + 1));
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param key The key to set.
	 * @param value The value of the key.
	 */
	public void set(String key, Object value) {
		getLowestSection(key).properties.put(getFinalKey(key), value);
	}

	/**
	 * Gets the raw Object value mapped to given key.
	 * <p>
	 * Note: Configuration sections can be delimited with the '.' character.
	 * </p>
	 *
	 * @param key The key of the object to fetch.
	 * @return The value associated with the key or null if the nothing is set for the key.
	 */
	public Object get(String key) {
		Configuration section = getLowestSection(key);
		return section.properties.get(getFinalKey(key));
	}

	/**
	 * Gets the String value mapped to the given key.
	 * <p>
	 * Note: Configuration sections can be delimited with the '.' character.
	 * </p>
	 *
	 * @param key The key of the String to fetch.
	 * @return The value associated with the key in String form or an empty String if the key is invalid.
	 */
	public String getString(String key) {
		return getString(key, "");
	}

	/**
	 * Gets the String value mapped to the given key.
	 * <p>
	 * Note: Configuration sections can be delimited with the '.' character.
	 * </p>
	 *
	 * @param key The key of the String to fetch.
	 * @param def The default value of the String to return if the key is not set.
	 * @return The value associated with the key in String form or the default String provided if the key is invalid.
	 */
	public String getString(String key, String def) {
		Object value = get(key);
		return value instanceof String ? (String) value : def;
	}

	/**
	 * Gets the int value mapped to the given key.
	 * <p>
	 * Note: Configuration sections can be delimited with the '.' character.
	 * </p>
	 *
	 * @param key The key of the String to fetch.
	 * @return The value associated with the key in int form or 0 if the key is invalid.
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * Gets the int value mapped to the given key.
	 * <p>
	 * Note: Configuration sections can be delimited with the '.' character.
	 * </p>
	 *
	 * @param key The key of the int to fetch.
	 * @param def The default value of the int to return if the key is not set.
	 * @return The value associated with the key in int form or the default int provided if the key is invalid.
	 */
	public int getInt(String key, int def) {
		Object value = get(key);
		return value instanceof Number ? ((Number) value).intValue() : 0;
	}

	/**
	 * Gets the final key of the parameter stripped of the configuration sections.
	 *
	 * @param key The full key to strip.
	 * @return The final key of the parameter without configuration sections.
	 */
	private static String getFinalKey(String key) {
		int index = key.lastIndexOf(DELIMITER);
		return index == -1 ? key : key.substring(index + 1);
	}

}