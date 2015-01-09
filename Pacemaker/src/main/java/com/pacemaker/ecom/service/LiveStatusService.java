package com.pacemaker.ecom.service;

import java.util.List;

import com.pacemaker.ecom.model.Member;
import com.pacemaker.ecom.model.MessageDetails;


public interface LiveStatusService {
    public List<Member> listAllServices();
    public Member fetchHealthStatus(String name, String env);
    public Member fetchOperations(Member member);
    public MessageDetails testSOAPService(MessageDetails messageDetails);
}
