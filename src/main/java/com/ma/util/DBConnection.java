package com.ma.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	static final Properties props = new Properties();
	static Connection connection = null;

	static {
		try {
			InputStream in = DBConnection.class.getResourceAsStream("/db.properties");
			props.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				// load the Driver Class
				Class.forName(props.getProperty("DB_DRIVER_CLASS"));

				// create the connection now
				connection = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"), props.getProperty("DB_PASSWORD"));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
