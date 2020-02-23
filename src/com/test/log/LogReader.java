package com.test.log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;

import com.test.model.ServiceData;

public class LogReader {
	static HashMap<String,ServiceData> allEntries = new HashMap<String,ServiceData>();
	static HashMap<String,Long> maxExecutionEntries = new HashMap<String,Long>();
	
	public static void main(String[] args) throws IOException {
			initLoader("test.log");
			
			System.out.println("Enter service Name");
			Scanner scanner = new Scanner(System.in);
			String serviceName = scanner.next();
			System.out.println("Total Number of request to service "+ serviceName + " =" + getExecutionNumber(serviceName));
			
			scanner.close();
			System.out.println("Max time taken for the service :" + maxExecutionEntries.get(serviceName));
			
		
	}
	
	public static int getExecutionNumber(String serviceName) {
		int count=0;
		for(Entry<String,ServiceData> entry: allEntries.entrySet()) {
			if(entry.getValue().serviceName.equals(serviceName)) {
				count++;
			}
		}
		if(count == 0)
			throw new InputMismatchException("Provide valid serviceName");
		return count;
	}

	public static String getServiceName(String input) {
		int len = input.length();
		return input.substring(1,len-1).split(":")[0];
	}

	public static String getServiceId(String input) {
		int len = input.length();
		return input.substring(1,len-1).split(":")[1];
	}
	
	public static HashMap<String,ServiceData> initLoader(String fileName) throws IOException{
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\test\\log\\"+fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fs));
		

		while(reader.readLine() != null) {
			try {

				String readLine = reader.readLine();
				String[] items = readLine.split(" ");
				//2015-10-26T16:09:47,958
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
				Date date = df.parse(items[0]);
				long epoch = date.getTime();

				if(items[3].equals("entry")) {
					ServiceData sd = new ServiceData(getServiceName(items[5]), Long.parseLong(getServiceId(items[5])));
					sd.setEntryTime(epoch);
					allEntries.put(getServiceId(items[5]), sd);
				}

				else if(items[3].equals("exit")) {
					ServiceData sd = allEntries.get(getServiceId(items[5]));
					sd.setExitTime(epoch);
					allEntries.put(getServiceId(items[5]), sd);
					if(maxExecutionEntries.containsKey(sd.serviceName)) {
						if(maxExecutionEntries.get(sd.serviceName) < (sd.exitTime -sd.entryTime)) {
							maxExecutionEntries.put(sd.serviceName, sd.exitTime -sd.entryTime);
						}
					}  
					else
						maxExecutionEntries.put(sd.serviceName, sd.exitTime - sd.entryTime);	
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		reader.close();
		return allEntries;
	}

}