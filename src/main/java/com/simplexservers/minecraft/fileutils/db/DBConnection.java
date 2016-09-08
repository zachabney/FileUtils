package com.simplexservers.minecraft.fileutils.db;

import java.sql.Connection;

/**
 * A connection to a SQL database.
 */
public interface DBConnection {

	/**
	 * Gets the connection to the database.
	 *
	 * @return The connection to the database.
	 * @throws DBException If a connection could not be made to the database.
	 */
	Connection getConnection() throws DBException;

	/**
	 * Closes the connection to the database.
	 */
	void close();

}
