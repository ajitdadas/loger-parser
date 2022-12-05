package com.cs.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cs.dao.LogDao;
import com.cs.model.Event;
import com.cs.model.Log;
import com.cs.util.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogService {

	private ObjectMapper objectMapper = new ObjectMapper();
	private Event event;
	private LogDao logDao = new LogDao();

	public Log parseLog(String json) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(json, Log.class);
	}

	public List<Log> parseFile(String filePath) throws JsonMappingException, JsonProcessingException, IOException {
		List<Log> logs = new ArrayList<Log>();
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String line;
		while ((line = br.readLine()) != null) {
			Log log = parseLog(line);
			logs.add(log);
		}
		return logs;
	}

	public List<Event> findLoggerTakesMoreTime(List<Log> logs) {
		List<Event> events = new ArrayList<>();
		Map<String, List<Log>> logByGroups = logs.stream().collect(Collectors.groupingBy(Log::getId));
		logByGroups.forEach((id, list) -> {
			event = new Event();
			list.forEach(log -> {
				if (log.getState().equals(State.FINISHED)) {
					event.setEndTime(log.getTimestamp());
				} else {
					event.setStartTime(log.getTimestamp());
				}
			});
			event.setDuruation(event.getEndTime() - event.getStartTime());
			event.setId(list.get(0).getId());
			event.setType(list.get(0).getType());
			event.setHost(list.get(0).getHost());
			event.setAlert(false);
			if (event.getDuruation() > 4) {
				event.setAlert(true);
			}
			events.add(event);
		});
		return events;
	}

	public boolean insertEvent(List<Event> events) throws SQLException {
		return logDao.insertEvents(events);
	}

}
