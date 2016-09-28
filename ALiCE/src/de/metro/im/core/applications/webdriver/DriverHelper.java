package de.metro.im.core.applications.webdriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class DriverHelper {
	//NOTIZ: https://technet.microsoft.com/de-de/library/bb491009.aspx
	
	/**
	 * Kills a single process by providing the EXACT process name 
	 * @param processName The name of the process you want to stop. 
	 * @throws Exception Throws a generics exception. 
	 */
	private static void taskKill(String processName) throws Exception  {
	  ///F forcefull termination //IM Specifies the image name of the process to be terminated
	  final String KILL = "taskkill /F /IM ";
	  Runtime.getRuntime().exec(KILL + processName); 
	  Thread.sleep(750); 
	}
		
	/**
	 * Runs Windows Management Instrumentation Command Line tool to close firefox related processes. 
	 * To reduce system load this will be run by default if there are more thant 3 firefox processes running. 
	 * 
	 */
	public static void removeFirefoxDriverLeftovers(){
		String line;
		int leftovers = 0;
		BufferedReader input = null;
		try {
		    Process proc = Runtime.getRuntime().exec("wmic.exe");
		    input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		    OutputStreamWriter oStream = new OutputStreamWriter(proc.getOutputStream());
		    oStream .write("process where name='WerFault.exe'");
		    oStream .flush();
		    oStream .close();
		    while ((line = input.readLine()) != null) {
//		        System.out.println(line);
		        if(line.contains("WerFault")){
		        	leftovers++;
		        	if(leftovers >= 3){
		        		System.out.println("Driver leftover count bigger than 3, executing kill. : " + leftovers);
		        		for (int i = 0; i < leftovers; i++) {
		        			System.out.println("There are driver " + leftovers + " driver error dialogs leftovers. Trying to remove...");
		        			taskKill("WerFault.exe");
						}
		        		leftovers = 0;
		        	}
		        } 
		    }
		    System.out.println("Driver leftover removal successfull.");
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				System.out.println("Error occured closing the process input stream buffer.");
				e.printStackTrace();
			}
		}
	}
}