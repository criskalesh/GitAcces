package com.pacemaker.ecom.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pacemaker.ecom.model.MessageDetails;
import com.pacemaker.ecom.service.LiveStatusService;
import com.pacemaker.ecom.util.RestServiceClient;

@Controller
@RequestMapping("/testservice")
public class TestServiceController {
	
	@Autowired
	LiveStatusService liveStatusService;
	
	@Autowired
	RestServiceClient restServiceClient;
	
	private static final Logger logger = Logger.getLogger(TestServiceController.class);
	
	@RequestMapping(value = "/soaptest", method = RequestMethod.POST)
    public @ResponseBody MessageDetails testSOAPService(@RequestBody MessageDetails messageDetails) {
		logger.info("Inside method testSOAPService - Test" + messageDetails.getName() +
				" with end point " + messageDetails.getTestEndPoint() +
				" with data " + messageDetails.getTestDescription());
		liveStatusService.testSOAPService(messageDetails);
		logger.info("Response is " + messageDetails.getTestOutPut());
        logger.info("Exiting method testSOAPService");
        return messageDetails;
    }
	
	@RequestMapping(value = "/resttest", method = RequestMethod.POST)
    public @ResponseBody MessageDetails testRESTService(@RequestBody MessageDetails messageDetails) {
		logger.info("Inside method testRESTService - Test" + messageDetails.getName() +
				" with end point " + messageDetails.getTestEndPoint() +
				" with data " + messageDetails.getTestDescription());
		
		String response = restServiceClient.getRestResponse(messageDetails.getTestEndPoint(), messageDetails.getTestDescription(), messageDetails.getInputType(), messageDetails.getStyle());
		messageDetails.setTestOutPut(response);		
		logger.info("Response is " + messageDetails.getTestOutPut());
        logger.info("Exiting method testRESTService");
        return messageDetails;
    }
}
