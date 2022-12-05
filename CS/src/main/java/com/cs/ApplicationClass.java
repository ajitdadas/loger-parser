package com.cs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.cs.model.Event;
import com.cs.model.Log;
import com.cs.service.LogService;

public class ApplicationClass {

	private static LogService logService = new LogService();

	public static void main(String... strings) throws IOException, SQLException {
		System.out.println("Please Enter log file path");
		try (Scanner scanner = new Scanner(System.in)) {
			String logFilePath = scanner.nextLine();
			List<Log> logs = logService.parseFile(logFilePath);

			List<Event> events = logService.findLoggerTakesMoreTime(logs);
			if (events.size() > 0) {
				logService.insertEvent(events);
			}
		}
	}

}
