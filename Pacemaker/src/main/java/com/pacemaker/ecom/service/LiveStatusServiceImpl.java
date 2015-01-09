package com.pacemaker.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pacemaker.ecom.model.Member;
import com.pacemaker.ecom.model.MessageDetails;
import com.pacemaker.ecom.util.PacemakerUtil;
import com.pacemaker.ecom.util.RestServiceClient;
import com.pacemaker.ecom.util.SoapUtil;

@Service("liveStatusService")
public class LiveStatusServiceImpl implements LiveStatusService {
    
    private static final Logger logger = Logger.getLogger(LiveStatusServiceImpl.class);
    
    @Autowired
	RestServiceClient restServiceClient;
    
    @Autowired
	PacemakerUtil util;

	public List<Member> listAllServices() {
		logger.info("Inside listAllServices");
		List<Member> liveServiceList = new ArrayList<Member>();
        for(Entry<String, Member> entry : util.getSummaryMap().entrySet()) {
        	Member member = entry.getValue();
        	logger.info(member.getName());
        	logger.info(member.getEndpoint());
        	logger.info(member.getHealthStatus()); 
        	member.setHealthStatus("No Data");
        	liveServiceList.add(member);
		}
    	logger.info("Exit listAllServices");
		return liveServiceList;
	}
	
	public Member fetchHealthStatus(String name, String env) {
		logger.info("Inside fetchHealthStatus");
		Member member;
		if(null != env && env.equals("PROD_ENV")){
			member = new Member(util.getProdSummaryMap().get(name + "ProductionEnv")); 
		}else{
			member = new Member(util.getSummaryMap().get(name)); 
		}      
        member.setHealthStatus(restServiceClient.checkServiceHealth(member.getEndpoint()));
        logger.info("Exit fetchHealthStatus");
		return member;
	}

	public Member fetchOperations(Member member) {
		logger.info("Inside fetchOperations");
		SoapUtil util = new SoapUtil();
		if(member.getType().equalsIgnoreCase("SOAP")){		
			member = util.fetchOperations(member);
		}
		logger.info("Inside fetchOperations");
		return member;
	}

	public MessageDetails testSOAPService(MessageDetails messageDetails){
		logger.info("Inside fetchHealthStatus");
		SoapUtil util = new SoapUtil();
		messageDetails = util.testSOAPService(messageDetails);
        logger.info("Exit fetchHealthStatus");
		return messageDetails;
	}
		
	public MessageDetails populateMember(MessageDetails messageDetails){
		logger.info("Inside fetchHealthStatus");
		SoapUtil util = new SoapUtil();
		messageDetails = util.testSOAPService(messageDetails);
        logger.info("Exit fetchHealthStatus");
		return messageDetails;
	}
}
