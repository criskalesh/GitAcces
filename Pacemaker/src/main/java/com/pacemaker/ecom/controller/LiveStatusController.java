package com.pacemaker.ecom.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pacemaker.ecom.model.Member;
import com.pacemaker.ecom.service.LiveStatusService;

@Controller
@RequestMapping("/liveStatus")
public class LiveStatusController {
	private static final Logger logger = Logger.getLogger(LiveStatusController.class);
	
	@Autowired
	LiveStatusService liveStatusService;
	
	@RequestMapping("/liveStatusList.json")
    public @ResponseBody List<Member> getLiveSummaryList() {
		List<Member> liveStatuslist = liveStatusService.listAllServices();
		logger.info("Exiting method getLiveSummaryList");
		return liveStatuslist;
    }
	
	@RequestMapping(value = "/member.json/{serviceName}/{env}", method = RequestMethod.GET)
    public @ResponseBody Member getSingleLiveSummaryList(@PathVariable(value="serviceName")  String name,
    		@PathVariable("env") String env) {
		logger.info("key : " + name + ">>env: " + env);
		Member member = liveStatusService.fetchHealthStatus(name, env);
		liveStatusService.fetchOperations(member);
		logger.info("Exiting method getSingleLiveSummaryList");
		return member;
    }
	
	@RequestMapping("/layout")
    public String getLivePartialPage() {
        return "liveStatus/layout";
    }
	
	@RequestMapping("/singleLayout")
    public String getLiveStatusPage() {
        return "liveStatus/singleLayout";
    }
}
