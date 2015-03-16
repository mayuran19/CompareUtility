package com.mayuran19.configuration.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CSVToHSQLHelper {
	public void export() throws Exception {
		String driver = "org.hsqldb.jdbcDriver";
		Driver d = (Driver) Class.forName(driver).newInstance();

		final StringBuilder linkTable = new StringBuilder();
		final StringBuilder createTable = new StringBuilder();
		createTable
				.append("CREATE TEXT TABLE subjects(SUBJECT_ID varchar(100),SUBJECT_NAME varchar(100),DESCRIPTION varchar(100),CREATED_AT varchar(100))");
		linkTable.append("SET TABLE subjects SOURCE ");
		linkTable
				.append("\"/home/mayuran/Dropbox/workspace/java/compare/subjects.csv;ignore_first=true;all_quoted=true\"");
		System.out.println("Driver was successfully loaded.");
		String protocol = "jdbc:hsqldb:file";
		String database = "/home/mayuran/Dropbox/workspace/java/compare/hsqldb";
		String url = protocol + ":" + database;
		Connection con = DriverManager.getConnection(url);
		Statement stm = con.createStatement();
		stm.execute(createTable.toString());
		stm.execute(linkTable.toString());
		ResultSet resultSet = stm.executeQuery("select * from subjects");
		if (resultSet != null) {
			while (resultSet.next()) {
				System.out.println("FULL NAME = "
						+ resultSet.getString("usr_FULL_NAME"));
			}
		}
	}

	public static void main(String[] args) throws Exception {
		CSVToHSQLHelper csvToHSQLHelper = new CSVToHSQLHelper();
		csvToHSQLHelper.export();
	}
}
