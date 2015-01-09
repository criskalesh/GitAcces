package com.pacemaker.ecom.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestServiceClient {  

	@Autowired
	RestTemplate restTemplate;
	
    public String checkServiceHealth(String url) {
        String healthStatus = "0";
        try{
        	 restTemplate.getForObject(url, String.class);
        	 healthStatus = "100";
        }catch(Exception e){
        	 healthStatus = "0";
        }
        return healthStatus;
    }
    
    public String getRestResponse(String url, String request, String reqType, String acceptType) {
    	String response;
	    try{	
	    	HttpHeaders headers = new HttpHeaders();	    	
	    	if(null != acceptType && acceptType.trim().length() >0){
	    		String[] str_array = acceptType.split("/");
		    	String head = str_array[0]; 
		    	String tail = str_array[1];	    	
		    	headers.setContentType(new MediaType(head, tail));
	    	}
	    	
	    	if(reqType.equalsIgnoreCase("POST")){
	    		HttpEntity<String> entity = new HttpEntity<String>(request,headers);
	    		response =  restTemplate.postForObject(url,entity,String.class);
	    	}else if(reqType.equalsIgnoreCase("PUT")){
	    		HttpEntity<String> entity = new HttpEntity<String>(request,headers);
	    		ResponseEntity<String>  
	    			responseEntity = restTemplate.exchange(url,
	    				HttpMethod.PUT ,entity, String.class);
	    		response = responseEntity.getBody();
	    	} else{
	    		response =  restTemplate.getForObject(url, String.class);
	    	}    	
	    }catch(Exception e){
	    	response = ExceptionUtils.getStackTrace(e);
		}    	
        return response;
    }
    
    
    
}
