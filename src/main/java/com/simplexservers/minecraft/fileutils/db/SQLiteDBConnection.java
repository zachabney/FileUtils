package com.simplexservers.minecraft.fileutils.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A connection to an SQLite database.
 */
public class SQLiteDBConnection implements DBConnection {

	/**
	 * The local database file.
	 */
	private File dbFile;
	/**
	 * The connection to the database.
	 */
	private Connection conn;

	/**
	 * Constructs a new connection to an SQLite database.
	 *
	 * @param dbFile The location of the SQLite database file.
	 */
	public SQLiteDBConnection(File dbFile) {
		this.dbFile = dbFile;
	}

	/**
	 * Gets the previously made connection to the database, or a new connection
	 * if one does not exist.
	 *
	 * @return The connection to the database.
	 * @throws DBException If the connection could not be made to the database.
	 */
	@Override
	public Connection getConnection() throws DBException {
		try {
			// Create the parent directories
			if (!dbFile.exists()) {
				File parentDir = dbFile.getParentFile();
				if (parentDir != null) {
					parentDir.mkdirs();
				}
			}

			if (conn == null || conn.isClosed()) {
				Class.forName("org.sqlite.JDBC" ); // Load sqlite into the class loader.
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
			}

			return conn;
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Cleanly closes the connection to the database.
	 */
	@Override
	public void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
		}
	}

}
