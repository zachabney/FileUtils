package com.simplexservers.minecraft.fileutils.db;

import java.sql.SQLException;

/**
 * An exception that occurs with a database.
 */
public class DBException extends SQLException {

	/**
	 * Constructs a DBException with the given message.
	 *
	 * @param message The exception message.
	 */
	public DBException(String message) {
		super(message);
	}

}
