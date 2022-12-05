package com.cs.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.cs.model.Event;
import com.cs.util.ConnectDatabase;

public class LogDao {

	ConnectDatabase connectDatabase = new ConnectDatabase();

	public boolean insertEvents(List<Event> events) throws SQLException {
		Connection con = connectDatabase.getConnection();
		Statement stmt = con.createStatement();
		for (Event event : events) {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO events(id,duration,type,host,alert) VALUES (" + event.getId() + ","
					+ event.getDuration() + "," + event.getType() + "," + event.getHost() + "," + event.isAlert()
					+ ")");
			con.commit();
		}
		con.commit();
		return true;
	}

}
