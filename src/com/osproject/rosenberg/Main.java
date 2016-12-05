package com.osproject.rosenberg;
import java.io.*;
import java.util.ArrayList;

public class Main {
	
	// For reading file
	public static ArrayList<String> allLines = new ArrayList<String>();
	
	// System Configuration
	public static int currentTime = 0;
	public static int totalMemory = 0;
	public static int availableMemory = 0;
	public static int totalDevices = 0;
	public static int timeQuantum = 0;
	
	// Jobs
	public static ArrayList<Job> jobs = new ArrayList<Job>();
	
	// Requests
	public static ArrayList<Request> requests = new ArrayList<Request>();
	
	// Releases
	public static ArrayList<Release> releases = new ArrayList<Release>();
	
	// Hold Queues
		// SJF
	public static ArrayList<Job> holdQueue1 = new ArrayList<Job>();
		// FIFO
	public static ArrayList<Job> holdQueue2 = new ArrayList<Job>();
	
	// Ready Queue
	public static ArrayList<Job> readyQueue = new ArrayList<Job>();
	
	// Reads input text and saves messages into lists to be read later
	public static void loadFile(){
		String fileName = "/Users/kennyrosenberg/Documents/workspace/OSProject/src/com/osproject/rosenberg/projectInput.txt";
		String line = null;
		String[] numbers = null;
		
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
            	allLines.add(line);         	
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
	}
	
	// routes input text to save messages to appropriate location
	// TODO: display message 
	public static void parseLine(String s){
		switch(s.charAt(0)){
			case 'C':
				systemConfig(s);
				break;
			case 'A':
				jobArrival(s);
				break;
			case 'Q':
				deviceRequest(s);
				break;
			case 'L':
				deviceRelease(s);
				break;
			case 'D':
				///
				break;
		}
	}
	
	// Configures system settings
	public static void systemConfig(String s){
		String[] instructions;
		instructions = s.split("\\s+");
		currentTime = Integer.parseInt(instructions[1]);
		totalMemory = Integer.parseInt(instructions[2].substring(2, instructions[2].length()));
		availableMemory = totalMemory;
		totalDevices = Integer.parseInt(instructions[3].substring(2, instructions[3].length()));
		timeQuantum = Integer.parseInt(instructions[4].substring(2, instructions[4].length()));
	}
	
	// Save job arrival messages
	public static void jobArrival(String s){
		String[] instructions;
		instructions = s.split("\\s+");
		int arrivalTime = Integer.parseInt(instructions[1]);
		int jobNumber = Integer.parseInt(instructions[2].substring(2, instructions[2].length()));
		int requiredMemory = Integer.parseInt(instructions[3].substring(2, instructions[3].length()));
		int requiredDevices = Integer.parseInt(instructions[4].substring(2, instructions[4].length()));
		int runLength = Integer.parseInt(instructions[5].substring(2, instructions[5].length()));
		int priority = Integer.parseInt(instructions[6].substring(2, instructions[6].length()));
		
		if(requiredMemory <= totalMemory && requiredDevices <= totalDevices){
			jobs.add(new Job(arrivalTime, jobNumber, requiredMemory, requiredDevices, runLength, priority));
		}
		
	}
	
	// Save device request messages
	public static void deviceRequest(String s){
		String[] instructions;
		instructions = s.split("\\s+");
		int arrivalTime = Integer.parseInt(instructions[1]);
		int jobNumber = Integer.parseInt(instructions[2].substring(2, instructions[2].length()));
		int devices = Integer.parseInt(instructions[3].substring(2, instructions[3].length()));
		
		requests.add(new Request(arrivalTime, jobNumber, devices));
	}
	
	// Save device release messages
	public static void deviceRelease(String s){
		String[] instructions;
		instructions = s.split("\\s+");
		int arrivalTime = Integer.parseInt(instructions[1]);
		int jobNumber = Integer.parseInt(instructions[2].substring(2, instructions[2].length()));
		int devices = Integer.parseInt(instructions[3].substring(2, instructions[3].length()));
		
		releases.add(new Release(arrivalTime, jobNumber, devices));
	}
	
	// Search Job/Request/Release messages for one at the current time or advance time
	public static void checkArrivalTime(){
		for(int i = 0; i < jobs.size(); i++){
			Job j = jobs.get(i);
			if(j.getArrivalTime() == currentTime){
				routeJob(j);
			}
		}
	}
	
	// Handles jobs that arrive at the current time
	public static void routeJob(Job j){
		if(j.getRequiredMemory() <= availableMemory){
			readyQueue.add(j);
			availableMemory =  availableMemory - j.getRequiredMemory();
		}
		else if(j.getPriority() == 1){
			holdQueue1.add(j);
		}
		else{
			holdQueue2.add(j);
		}
	}
	
	public static void main(String[] args){
		
		// Handle Input Text
		loadFile();
		for(int i = 0; i < allLines.size(); i++){
			parseLine(allLines.get(i));
		}
		
		// Look for new task of any type
		checkArrivalTime();
	}
}
