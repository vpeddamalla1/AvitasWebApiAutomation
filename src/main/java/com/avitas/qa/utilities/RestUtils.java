package com.avitas.qa.utilities;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * @author Venkat
 */

public class RestUtils  {
	public static Logger log = Logger.getLogger(RestUtils.class);
	
	
	public static Response doGet(String url, Map<String,String> requestHeaders, Map<String,String> queryParams) {
		RequestSpecBuilder aRequestSpecBuilder = new RequestSpecBuilder();
		
		//Add Headers
		aRequestSpecBuilder = addHeaders(requestHeaders, aRequestSpecBuilder);
		
		//Add Query Params
		aRequestSpecBuilder = addQueryParams(queryParams, aRequestSpecBuilder);
		
		RequestSpecification reqSpec = aRequestSpecBuilder.build();
		
		Response aResponse = RestAssured.given()
								.spec(reqSpec)
								.get(url)
								.then()
								.extract()
								.response();
		return aResponse;
	}
	
	public static Response doPost(String url, String requestBody, Map<String,String> requestHeaders, Map<String,String> queryParams, String contentType) {
		RequestSpecBuilder aRequestSpecBuilder = new RequestSpecBuilder();
		
		//Add Headers
		aRequestSpecBuilder = addHeaders(requestHeaders, aRequestSpecBuilder);
		
		//Set content type
		aRequestSpecBuilder = setContentType(contentType, aRequestSpecBuilder);
		
		//Add Query Params
		aRequestSpecBuilder = addQueryParams(queryParams, aRequestSpecBuilder);
		
		RequestSpecification reqSpec = aRequestSpecBuilder.build();
		
		Response aResponse = RestAssured.given()
								.spec(reqSpec)
								.body(requestBody)
								.post(url)
								.then()
								.extract().response();
		return aResponse;
	}
	
	public static RequestSpecBuilder addHeaders(Map<String, String> requestHeaders, RequestSpecBuilder aRequestSpecBuilder) {
		
		// Set the headers
		for (Map.Entry<String, String> anEntry : requestHeaders.entrySet()) {
			aRequestSpecBuilder.addHeader(anEntry.getKey(), anEntry.getValue());
			log.debug(anEntry.getKey() + " Header added to request");
		}
		return aRequestSpecBuilder;
	}
	
	public static RequestSpecBuilder setContentType(String contentType, RequestSpecBuilder aRequestSpecBuilder) {
		
		// Set the contentType
		aRequestSpecBuilder.setContentType(contentType);
		return aRequestSpecBuilder;
	}
	
	public static RequestSpecBuilder addQueryParams(Map<String, String> queryParams, RequestSpecBuilder aRequestSpecBuilder) {
		
		// Set required query parameters
		for (Map.Entry<String, String> anEntry : queryParams.entrySet()) {
			aRequestSpecBuilder.addQueryParam(anEntry.getKey(), anEntry.getValue());
			log.debug(anEntry.getKey() + " Query Param added to request");
		}
		return aRequestSpecBuilder;
	}

}
