package com.pacemaker.ecom.model;

import java.util.List;


public class Member {
	private String id;
    private String name;
    private String type;
    private String endpoint;
    private String healthStatus;
    private String description;
    private List<ServerDetails> serverDetails;
    private List<MessageDetails> messageDetails;
    private List<String> specificationLinks;
    
    public Member(Member member) {
    	this.id = member.id;
    	this.name = member.name;
    	this.type = member.type;
    	this.endpoint = member.endpoint;
    	this.healthStatus = member.healthStatus;
    	this.description = member.description;
    	this.serverDetails = member.serverDetails;
    	this.messageDetails = member.messageDetails;
    	this.specificationLinks = member.specificationLinks;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getHealthStatus() {
        return healthStatus;
    }
    
    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setServerDetails(List<ServerDetails> serverDetails) {
		this.serverDetails = serverDetails;
	}

	public List<ServerDetails> getServerDetails() {
		return serverDetails;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MessageDetails> getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(List<MessageDetails> messageDetails) {
		this.messageDetails = messageDetails;
	}

	public List<String> getSpecificationLinks() {
		return specificationLinks;
	}

	public void setSpecificationLinks(List<String> specificationLinks) {
		this.specificationLinks = specificationLinks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
