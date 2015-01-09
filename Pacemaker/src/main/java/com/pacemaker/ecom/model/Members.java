package com.pacemaker.ecom.model;

import java.util.List;

public class Members {
	private List<Member> members;

    public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
    public String toString() {
        return "Version [versions=" + members + "]";
    }	
}
