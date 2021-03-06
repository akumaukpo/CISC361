package com.osproject.rosenberg;

public class Job {
	
	private int arrivalTime;
	private int jobNumber;
	private int requiredMemory;
	private int requiredDevices;
	private int runLength;
	private int priority;
	private int remainingTime;
	private int devicesHeld;
	private int devicesNeeded;
	private int finishedTime;
	
	public Job(int arrivalTime, int jobNumber, int requiredMemory, int requiredDevices, int runLength, int priority) {
		this.arrivalTime = arrivalTime;
		this.jobNumber = jobNumber;
		this.requiredMemory = requiredMemory;
		this.requiredDevices = requiredDevices;
		this.runLength = runLength;
		this.priority = priority;
		this.remainingTime = runLength;
		this.devicesNeeded = 0;
	}

	// Checks if the job can be terminated
	// if returns non-zero to main, remove from list and return memory
	public int termintateJob(){
		if(remainingTime > 0){
			return 0;
		}
		else{
			return requiredMemory;
		}
	}
	
	public void decRemainingTime(){
		remainingTime--;
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

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getDevicesHeld() {
		return devicesHeld;
	}

	public void setDevicesHeld(int devicesHeld) {
		this.devicesHeld = devicesHeld;
	}

	public int getDevicesNeeded() {
		return devicesNeeded;
	}

	public void setDevicesNeeded(int devicesNeeded) {
		this.devicesNeeded = devicesNeeded;
	}

	public int getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(int finishedTime) {
		this.finishedTime = finishedTime;
	}
	
}
