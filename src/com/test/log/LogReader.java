package com.test.log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.test.model.ServiceData;

public class LogReader {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static HashMap<String, ServiceData> allEntries = new HashMap<String, ServiceData>();
	static HashMap<String, Long> maxExecutionEntries = new HashMap<String, Long>();
	static String fileName = "test.log";

	public static void main(String[] args) {
		// Parsing the log file
		initLoader();

		Map<String, Integer> serviceExecutionMap = getSvcExecutionNumber();
		for (String serviceName : serviceExecutionMap.keySet()) {
			System.out.println(
					"Amount of request to service \"" + serviceName + "\" is " + serviceExecutionMap.get(serviceName)
							+ " and max time for request execution is " + maxExecutionEntries.get(serviceName)/1000 +" seconds.");
		}
	}

	public static Map<String, Integer> getSvcExecutionNumber() {
		Map<String, Integer> serviceMap = new HashMap<String, Integer>();
		for (Entry<String, ServiceData> entry : allEntries.entrySet()) {
			if (serviceMap.containsKey(entry.getValue().serviceName)) {
				serviceMap.put(entry.getValue().serviceName, serviceMap.get(entry.getValue().serviceName) + 1);
			} else {
				serviceMap.put(entry.getValue().serviceName, 1);
			}
		}
		return serviceMap;
	}

	//Extract Service Name
	public static String getServiceName(String input) {
		int len = input.length();
		return input.substring(1, len - 1).split(":")[0];
	}

	//Extract Service ID
	public static String getServiceId(String input) {
		int len = input.length();
		return input.substring(1, len - 1).split(":")[1];
	}

	public static HashMap<String, ServiceData> initLoader() {

		FileInputStream fs;
		try {
			fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\com\\test\\resource\\" + fileName);

			BufferedReader reader = new BufferedReader(new InputStreamReader(fs));
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {

				String[] items = readLine.split(" ");
				// 2015-10-26T16:09:47,958
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
				Date date = df.parse(items[0]);
				long epoch = date.getTime();

				if (items[3].equals("entry")) {
					ServiceData sd = new ServiceData(getServiceName(items[5]), Long.parseLong(getServiceId(items[5])));
					sd.setEntryTime(epoch);
					allEntries.put(getServiceId(items[5]), sd);
				}
				else if (items[3].equals("exit")) {
					ServiceData sd = allEntries.get(getServiceId(items[5]));
					if (sd != null) {
						sd.setExitTime(epoch);
						allEntries.put(getServiceId(items[5]), sd);
						if (maxExecutionEntries.containsKey(sd.serviceName)) {
							if (maxExecutionEntries.get(sd.serviceName) < (sd.exitTime - sd.entryTime)) {
								maxExecutionEntries.put(sd.serviceName, sd.exitTime - sd.entryTime);
							}
						} else
							maxExecutionEntries.put(sd.serviceName, sd.exitTime - sd.entryTime);
					}
				}
			}
		

		reader.close();
		} catch (FileNotFoundException fe) {
			LOGGER.log(Level.SEVERE, "Specified file path is not found, Details: " +fe.getMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Execution failed, Details:  "+ e.getMessage());
		}
		return allEntries;
	}

}
