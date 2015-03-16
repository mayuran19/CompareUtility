package com.mayuran19.configuration.jdbc;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class CSVExportHelper {
	private static final String DELIMITER = ",";

	public String[] getColumnsNames(String tableName) throws Exception {
		List<String> list = new ArrayList<String>();
		Connection connection = DBConnecton.getConnection();
		PreparedStatement ps = connection
				.prepareStatement("select column_name from user_tab_columns where table_name = upper(?)");
		ps.setString(1, tableName);
		ResultSet rs = ps.executeQuery();
		int index = 1;
		while (rs.next()) {
			list.add(rs.getString(index));
		}
		rs.close();
		ps.close();
		connection.close();
		String[] array = new String[list.size()];
		return list.toArray(array);
	}

	public List<String[]> readData(String tableName) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		Connection connection = DBConnecton.getConnection();
		String[] columnNames = this.getColumnsNames(tableName);
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append(this.join(columnNames, DELIMITER));
		sb.append(" from ").append(tableName);
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		System.out.println(sb.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String[] row = new String[columnNames.length];
			for (int i = 0; i < columnNames.length; i++) {
				row[i] = rs.getString(columnNames[i]);
			}
			list.add(row);
		}
		rs.close();
		ps.close();
		connection.close();
		return list;
	}

	public String join(String[] array, String delimiter) {
		StringBuffer sb = new StringBuffer();
		for (String s : array) {
			if (sb.toString().isEmpty()) {
				sb.append(s);
			} else {
				sb.append(delimiter).append(s);
			}
		}

		return sb.toString();
	}

	public void exportToCsv(String tableName) throws Exception {
		List<String[]> rows = this.readData(tableName);
		String fileName = tableName + ".csv";
		CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));
		csvWriter.writeNext(this.getColumnsNames(tableName));
		csvWriter.writeAll(rows);
		csvWriter.close();
	}

	public static void main(String[] args) throws Exception {
		CSVExportHelper csvExportHelper = new CSVExportHelper();
		System.out.println(Arrays.deepToString(csvExportHelper
				.getColumnsNames("subjects")));
		List<String[]> rows = csvExportHelper.readData("subjects");
		for (String[] row : rows) {
			System.out.println(Arrays.deepToString(row));
		}
		csvExportHelper.exportToCsv("subjects");
	}
}
