package com.pacemaker.ecom.model;

public class ServerDetails {
	private String name;
	private String logLocation;
	private String deployLocation;	 
	private String configLocation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogLocation() {
		return logLocation;
	}
	public void setLogLocation(String logLocation) {
		this.logLocation = logLocation;
	}
	public String getDeployLocation() {
		return deployLocation;
	}
	public void setDeployLocation(String deployLocation) {
		this.deployLocation = deployLocation;
	}
	public String getConfigLocation() {
		return configLocation;
	}
	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}	 
	 
	 
}
