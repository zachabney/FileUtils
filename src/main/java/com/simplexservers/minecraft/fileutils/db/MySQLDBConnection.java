package com.simplexservers.minecraft.fileutils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Future;

/**
 * A connection to a MySQL database.
 */
public class MySQLDBConnection implements DBConnection {

	/**
	 * The connection settings to the MySQL server.
	 */
	private String host, database, username, password; // char[] for password is not any more secure since DriverManager::getConnection takes a String anyway.
	private int port;
	/**
	 * The connection to the database.
	 */
	private Connection conn;

	/**
	 * Constructs a new connection to a MySQL database.
	 *
	 * @param host The IP/host of the database server.
	 * @param port The port of the database server.
	 * @param database The name of the database to work with.
	 * @param username The username used to connection to the server.
	 * @param password The password used to connect to the server.
	 */
	public MySQLDBConnection(String host, int port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
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
			if (conn == null || conn.isClosed() || !conn.isValid(0)) {
				Class.forName("com.mysql.jdbc.Driver"); // Load MySQL into the class loader.
				conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
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
