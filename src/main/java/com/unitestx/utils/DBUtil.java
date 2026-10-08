package com.unitestx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	public static void runQuery(String sql) {
		String URL = WebAutomationUtils.getValuefromPropFile("DATABASE_URL");
		String USER = WebAutomationUtils.getValuefromPropFile("DATABASE_USR");
		String PASSWORD = WebAutomationUtils.getValuefromPropFile("DATABASE_PASSWORD");
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			int rows = stmt.executeUpdate(sql);
			System.out.println(rows + ": no of table Row was affected");
		} catch (SQLException e) {
			throw new RuntimeException("DB query failed: " + e.getMessage(), e);
		}
	}
}
