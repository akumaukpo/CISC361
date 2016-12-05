package com.osproject.rosenberg;

public class Job {
	
	private int arrivalTime;
	private int jobNumber;
	private int requiredMemory;
	private int requiredDevices;
	private int runLength;
	private int priority;
	
	public Job(int arrivalTime, int jobNumber, int requiredMemory, int requiredDevices, int runLength, int priority) {
		this.arrivalTime = arrivalTime;
		this.jobNumber = jobNumber;
		this.requiredMemory = requiredMemory;
		this.requiredDevices = requiredDevices;
		this.runLength = runLength;
		this.priority = priority;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public int getRequiredMemory() {
		return requiredMemory;
	}

	public void setRequiredMemory(int requiredMemory) {
		this.requiredMemory = requiredMemory;
	}

	public int getRequiredDevices() {
		return requiredDevices;
	}

	public void setRequiredDevices(int requiredDevices) {
		this.requiredDevices = requiredDevices;
	}

	public int getRunLength() {
		return runLength;
	}

	public void setRunLength(int runLength) {
		this.runLength = runLength;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
