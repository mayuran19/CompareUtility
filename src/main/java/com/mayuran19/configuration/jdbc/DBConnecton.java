package com.mayuran19.configuration.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecton {
	public static Connection getConnection() throws Exception {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.1.7:1521:g3app", "ACHADMIN",
					"ACHADMIN");
			return con;
		} catch (ClassNotFoundException e) {
			throw new Exception(e);
		} catch (SQLException e) {
			throw new Exception(e);
		}
	}
}
