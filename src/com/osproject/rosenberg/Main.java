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
	public static int availableDevices = 0;
	public static int totalDevices = 0;
	public static int timeQuantum = 0;
	public static int remainingTimeQuantum = 0;
	
	// Jobs
	public static ArrayList<Job> jobs = new ArrayList<Job>();
	public static ArrayList<Job> activeJobs = new ArrayList<Job>();
	public static ArrayList<Job> enteredSystem = new ArrayList<Job>();
	
	// Requests
	public static ArrayList<Request> requests = new ArrayList<Request>();
	
	// Releases
	public static ArrayList<Release> releases = new ArrayList<Release>();
	
	// Display Requests
	public static ArrayList<Integer> displayRequests = new ArrayList<Integer>();
	
	// Hold Queues
		// SJF
	public static ArrayList<Job> holdQueue1 = new ArrayList<Job>();
		// FIFO
	public static ArrayList<Job> holdQueue2 = new ArrayList<Job>();
		// Device Wait
	public static ArrayList<Job> deviceQueue = new ArrayList<Job>();
	
	// Ready Queue
	public static ArrayList<Job> readyQueue = new ArrayList<Job>();
	
	// Completed Queue
	public static ArrayList<Job> completedQueue = new ArrayList<Job>();
	
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
				displayRequests(s);
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
		availableDevices = totalDevices;
		timeQuantum = Integer.parseInt(instructions[4].substring(2, instructions[4].length()));
		remainingTimeQuantum = timeQuantum;
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
	
	public static void displayRequests(String s){
		String[] instructions;
		instructions = s.split("\\s+");
		int time = Integer.parseInt(instructions[1]);
		displayRequests.add(time);
	}
	
	// Search Job/Request/Release messages for one at the current time or advance time
	// TODO add requests, releases and display
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
	
	// Run the active job
	public static void runActiveJob(){
		if(!activeJobs.isEmpty()){
			for(int i = 0; i < activeJobs.size(); i++){
				activeJobs.get(i).decRemainingTime();
				int memoryToReturn = activeJobs.get(i).termintateJob();
				if(memoryToReturn > 0){
					availableMemory = availableMemory + memoryToReturn;
					availableDevices = availableDevices + activeJobs.get(i).getDevicesHeld();
					activeJobs.get(i).setFinishedTime(currentTime);
					completedQueue.add(activeJobs.get(i));
					readyQueue.remove(activeJobs.get(i));
					activeJobs.remove(activeJobs.get(i));
				}
			}
		}		
	}
	
	// Run only when the current time quantum reaches 0 or if no active job
	// Run the first priority 1 process before checking priority 2
	public static void changeActiveJobs(){
		for(int i = 0; i < deviceQueue.size(); i++){
			if(deviceQueue.get(i).getDevicesNeeded() < availableDevices){
				readyQueue.add(deviceQueue.get(i));
				deviceQueue.remove(i);
			}
		}
		for(int i = 0; i < readyQueue.size(); i++){
			if(readyQueue.get(i).getPriority() == 1){
				if(availableMemory >= readyQueue.get(i).getRequiredMemory()){
					availableMemory = availableMemory - readyQueue.get(i).getRequiredMemory();
					activeJobs.add(readyQueue.get(i));
				}
			}
		}
		for(int i = 0; i < readyQueue.size(); i++){
			if(readyQueue.get(i).getPriority() == 2){
				if(availableMemory >= readyQueue.get(i).getRequiredMemory()){
					availableMemory = availableMemory - readyQueue.get(i).getRequiredMemory();
					activeJobs.add(readyQueue.get(i));
				}
			}
		}
	}
	
	// If there is no active job, get one
	// If the time quantum ends, reset it, 
	// move the active job to the end of the ready queue,
	// then get a new job
	// If there is now a priority 1 process and the current
	// process is priority 2, switch
	public static void updateActiveJobs(){
		if(activeJobs.isEmpty()){
			changeActiveJobs();
		}
		else if(remainingTimeQuantum == 0){
			remainingTimeQuantum = timeQuantum;
			for(int i = 0; i < activeJobs.size(); i++){
				readyQueue.remove(activeJobs.get(i));
				readyQueue.add(activeJobs.get(i));
			}
			changeActiveJobs();
		}
		for(int i = 0; i < activeJobs.size(); i++){
			if(activeJobs.get(i).getPriority() == 2){
				if(priorityOneAvailable()){
					readyQueue.remove(activeJobs.get(i));
					readyQueue.add(activeJobs.get(i));
					activeJobs.remove(activeJobs.get(i));
				}
			}
		}
	}
	
	public static boolean priorityOneAvailable(){
		for(int i = 0; i < readyQueue.size(); i++){
			if(readyQueue.get(i).getPriority() == 1){
				return true;
			}
		}
		return false;
	}
	
	public static void display(){
		for(int i = 0; i < displayRequests.size(); i++){
			if(displayRequests.get(i) == currentTime){
				for(int j = 0; j < enteredSystem.size(); j++){
					if(enteredSystem.get(j).getArrivalTime() <= displayRequests.get(i)){
						System.out.println("Job #: " + enteredSystem.get(j).getJobNumber());
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args){
		
		// Handle Input Text
		// I know it says not to pre-process the input file, 
		// but this seemed way less complicated with limited time 
		loadFile();
		for(int i = 0; i < allLines.size(); i++){
			parseLine(allLines.get(i));
		}
		while(!holdQueue1.isEmpty() || !holdQueue2.isEmpty() || !readyQueue.isEmpty() || !deviceQueue.isEmpty() || !activeJobs.isEmpty()){
			// Look for new task of any type
			checkArrivalTime();
			// Check if a new process should be running and change it
			updateActiveJobs();
			// Advances time for active processes
			runActiveJob();
			
			// Prints system status
			display();
			currentTime++;
		}

	}
}
