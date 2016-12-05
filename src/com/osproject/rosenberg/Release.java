package com.osproject.rosenberg;

public class Release {

	private int arrivalTime;
	private int jobNumber;
	private int devices;
	
	public Release(int arrivalTime, int jobNumber, int devices) {
		this.arrivalTime = arrivalTime;
		this.jobNumber = jobNumber;
		this.devices = devices;
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

	public int getDevices() {
		return devices;
	}

	public void setDevices(int devices) {
		this.devices = devices;
	}
}
