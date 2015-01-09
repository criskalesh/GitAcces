package com.pacemaker.ecom.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pacemaker.ecom.model.Member;
import com.pacemaker.ecom.model.Members;


public class PacemakerConstants {

	//private static PropertyFileLoader propertyFileLoader;
	//private static Enumeration<String> enumeration;
	//private static  ResourceBundle resourceBundle = ResourceBundle.getBundle("pacemakerDetails");
	public static HashMap<String, Member> summaryList = new HashMap<String, Member>();
	public static HashMap<String, Member> summaryProdList = new HashMap<String, Member>();
	private static final Logger logger = Logger.getLogger(PacemakerConstants.class);
	
	static{
		/*propertyFileLoader = new PropertyFileLoader();
		propertyFileLoader.loadBundle();	
		enumeration = propertyFileLoader.getKeys();
		while(enumeration.hasMoreElements()){
			Member member = new Member();
			String name = enumeration.nextElement();
			member.setName(name);
			member.setEndpoint(propertyFileLoader.getProperty(member.getName()));
			member.setDescription(resourceBundle.getString(name));
			System.out.println("Enum  Nmae=" + member.getName());
			System.out.println("Enum  Val=" + member.getEndpoint());
			summaryList.put(name, member);
		}*/		
		logger.info("Inside PacemakerConstants - static");
		Gson gson = new Gson();
		InputStream is = PacemakerConstants.class.getResourceAsStream("/svcs.json");
		JsonReader reader = new JsonReader(new InputStreamReader(is));
	    Members members = gson.fromJson(reader, Members.class);
        for (Member member : members.getMembers()) {
        	summaryList.put(member.getName(), member);
		}
        
        Gson gsonProd = new Gson();
		InputStream isProd = PacemakerConstants.class.getResourceAsStream("/svcs_prod.json");
		JsonReader readerProd = new JsonReader(new InputStreamReader(isProd));
	    Members memberProd = gsonProd.fromJson(readerProd, Members.class);
        for (Member member : memberProd.getMembers()) {
        	summaryProdList.put(member.getName() +"ProductionEnv", member);
		}
        logger.info("Exit PacemakerConstants - static");
	}	
}
