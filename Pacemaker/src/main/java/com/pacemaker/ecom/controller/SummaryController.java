package com.pacemaker.ecom.controller;


import java.util.ArrayList;
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
import com.pacemaker.ecom.util.PacemakerUtil;

@Controller
@RequestMapping("/summary")
public class SummaryController {
	private static final Logger logger = Logger.getLogger(SummaryController.class);
	
	@Autowired
	PacemakerUtil util;
	
	@Autowired
	LiveStatusService liveStatusService;
	
	@RequestMapping("/summarylist.json")
    public @ResponseBody List<Member> getSummaryList() {
		logger.info("Inside method getSummaryList");
        List<Member> list = util.getSummaryList();
        logger.info("Exiting method getSummaryList");
		return list;
    }
	
	
	@RequestMapping("/summaryProdlist.json")
    public @ResponseBody List<Member> getProdSummaryList() {
		logger.info("Inside method getProdSummaryList");
        List<Member> list = util.getSummaryProdList();
        logger.info("Exiting method getProdSummaryList");
		return list;
    }
	
	@RequestMapping(value = "/summarylivelist.json/{env}", method = RequestMethod.GET)
	public @ResponseBody List<Member> getLiveSummaryList(@PathVariable("env") String env) {
		logger.info("Inside method getLiveSummaryList with env" + env );
		List<Member> list;
		if(null != env && env.equals("PROD_ENV")){
			list = util.getSummaryProdList();
		}else{
			list = util.getSummaryList();
		}        
        List <Member> liveList = new ArrayList<Member>();
        for (Member data : list) {
        	Member member = liveStatusService.fetchHealthStatus(data.getName(), env);
			liveList.add(member);
		}
        logger.info("Exiting method getLiveSummaryList");
		return liveList;
    }
	
	@RequestMapping("/layout")
    public String getSummaryPartialPage() {
        return "summary/layout";
    }
	
	@RequestMapping("/progress")
    public String getSummaryPartialPage1() {
        return "summary/progress";
    }
	@RequestMapping("/serviceSummary")
    public String getSummaryFullPage() {
        return "summary/serviceSummary";
    }
	@RequestMapping("/serviceTroubleShoot")
    public String getSummaryTroublePage() {
        return "summary/serviceTroubleShoot";
    }
	@RequestMapping("/utility")
    public String getSummaryUtilityePage() {
        return "summary/utility";
    }
	@RequestMapping("/dbinfo")
    public String getSummaryDBPage() {
        return "summary/dbinfo";
    }
	@RequestMapping("/editSpec")
    public String getUpdateSpecPage() {
        return "summary/editSpec";
    }
	@RequestMapping("/addSpec")
    public String getAddSpecPage() {
        return "summary/addSpec";
    }
}
