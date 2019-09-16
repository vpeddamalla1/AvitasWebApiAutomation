package com.avitas.qa.utilities;

import org.apache.log4j.Logger;

import com.jayway.restassured.response.Response;

/**
 * @author Venkat
 */

public class ResponseUtils  {
	public static Logger log = Logger.getLogger(ResponseUtils.class);
	
	public static boolean validateSuccessResponseCode(Response response) {
		
		//Validate using success http code
		if(response.getStatusCode() == 200) {
			return true;
		}
		
		return false;
	}

}
