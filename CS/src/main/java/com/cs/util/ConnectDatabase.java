package com.cs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDatabase {

	public Connection getConnection() {
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:C:/Users/Ajit.Dadas/Desktop/CS/testdb", "SA", "");
			stmt = con.createStatement();
			try {
				int result = stmt.executeUpdate(
						"CREATE TABLE events (id INT, duration INT, type VARCHAR(200), host VARCHAR(200), alert VARCHAR(200))");
			} catch (Exception e) {
				System.out.println("Table already exists");
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return con;
	}
}
