package com.pacemaker.ecom.util;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 *******************************************************************************
 *  Description 		  : This is a Util class to load data from properties  
 * 							file available in class path using resource bundle.
 * 
 * External Objects called 	  : None
 * Known Bugs       	      :  None
 * Modification Log
 * Date         	Author 		Description
 * ------------------------------------------------------------
 */

public class PropertyFileLoader{
	private static ResourceBundle resourceBundle;
	private static Logger logger = Logger.getLogger(PropertyFileLoader.class.getName());
	private static Enumeration<String> enumeration;

    /**
     * Description			: This method return value from properties file for the 
     *                        key accepted.
     * Parameters 			: @param String
     * Parameters 			: @return String
     */
    public String getProperty(String key) {
        String value = null;
            value = (String) this.resourceBundle.getString(key);
        return value;
    }
    
    
    /**
     * Description			: This method load the property files using resource bundle.
     * Parameters 			: @param String
     * Parameters 			: @return String
     */
    public void loadBundle() {
    	logger.info("Started loading bundle properties.");
    	this.resourceBundle =  ResourceBundle.getBundle("pacemaker");
    	logger.info("Completed loading bundle properties.");
    }
    
    /**
     * Description			: This method load the property files using resource bundle.
     * Parameters 			: @param String
     * Parameters 			: @return String
     */
    public Enumeration<String> getKeys() {
    	logger.info("Started loading bundle properties.");
    	enumeration =  resourceBundle.getKeys();
    	logger.info("Completed loading bundle properties.");
    	return enumeration;
    }

}
