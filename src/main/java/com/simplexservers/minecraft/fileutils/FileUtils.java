package com.simplexservers.minecraft.fileutils;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Commonly needed utilities for File handling.
 *
 * @author Zach Abney
 */
public class FileUtils {

	/**
	 * Saves the package resource to the given destination. If the destination
	 * already exists the resource will NOT be saved.
	 *
	 * @param resourceName The name of the resource.
	 * @param destination The destination File to save the resource.
	 * @throws IOException If the resource doesn't exist or an error occurs saving to the destination.
	 */
	public static void saveResource(String resourceName, File destination) throws IOException {
		saveResource(resourceName, destination, false);
	}

	/**
	 * Saves the package resource to the given destination.
	 *
	 * @param resourceName The name of the resource.
	 * @param destination The destination File to save the resource.
	 * @param replace True if the file should be replaced if it exists, false otherwise.
	 * @throws IOException If the resource doesn't exist or an error occurs saving to the destination.
	 */
	public static void saveResource(String resourceName, File destination, boolean replace) throws IOException {
		if (destination.exists()) {
			if (replace) {
				destination.delete();
			} else {
				return;
			}
		}

		destination.createNewFile();
		try (InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(resourceName)) {
			try (OutputStream out = new FileOutputStream(destination)) {
				ByteStreams.copy(in, out);
			}
		}
	}

}
