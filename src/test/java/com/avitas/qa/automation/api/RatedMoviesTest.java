package com.avitas.qa.automation.api;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.avitas.qa.automationcore.Constants;
import com.avitas.qa.automationcore.mediatype.MediaType;
import com.avitas.qa.automationcore.mediatype.impl.MovieImpl;
import com.avitas.qa.utilities.IMDBUtils;
import com.avitas.qa.utilities.TestBase;
import com.jayway.restassured.response.Response;

/**
 * @author Venkat
 * Covering multiple scenarios on one end point specifically
 */

public class RatedMoviesTest extends TestBase {
	
	public static Logger log = Logger.getLogger(RatedMoviesTest.class);
	
	
	@Test(description = "Test to get all rated movies. Positive scenario")
	public void getMyRatedMoviesTest() throws Exception {
		
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.RATED_MOVIES_URL;
		String api_key = IMDBUtils.getImdbDetails().getApiKey();
		String sessionId = anAccountBean.getSessionId();
		MediaType aMediaType = new MovieImpl();
		Response aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		Assert.assertTrue(aResponse.getStatusCode()==200, "Response code should be 200");
		Assert.assertTrue(aResponse.getStatusLine().contains("OK"), "Mismatch occurred while validating status line");
		Assert.assertTrue(aResponse.getContentType().equals(Constants.RES_CONTENT_TYPE));
		
		log.info("X-RateLimit-Remaining value is: " + aResponse.getHeader(Constants.HEADER_X_RATE_LIMIT_REMAINING));
		//Validate X-RateLimit value. It should be less than 40
		Assert.assertTrue(Integer.parseInt(aResponse.getHeader(Constants.HEADER_X_RATE_LIMIT_REMAINING)) < 40, "X-RateLimit-Remaining value should be less than 40");
	}
	
	@Test(description = "Test to get all rated movies without passing api_key. Negative scenario")
	public void getMyRatedMoviesWithoutApiKeyTest() throws Exception {
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.RATED_MOVIES_URL;
		
		//Expecting 401 - UnAutorized
		String api_key = null;
		String sessionId = anAccountBean.getSessionId();
		MediaType aMediaType = new MovieImpl();
		Response aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		Assert.assertEquals(aResponse.getStatusCode(), 401, "Response code should be 401");
	}
	
	@Test(description = "Test to get all rated movies without passing sessionId. Negative scenario")
	public void getMyRatedMoviesWithoutSessionTest() throws Exception {
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.RATED_MOVIES_URL;
		
		//Expecting 401 - UnAutorized
		String api_key = IMDBUtils.getImdbDetails().getApiKey();;
		String sessionId = null;
		MediaType aMediaType = new MovieImpl();
		Response aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		Assert.assertEquals(aResponse.getStatusCode(), 401, "Response code should be 401");
	}
	
	@Test(description = "Test to get all rated movies without passing both apikey and sessionId. Negative scenario")
	public void getMyRatedMoviesWithoutApiKeyAndSessionTest() throws Exception {
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.RATED_MOVIES_URL;
		
		//Expecting 401 - UnAutorized
		String api_key = null;;
		String sessionId = null;
		MediaType aMediaType = new MovieImpl();
		Response aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		Assert.assertEquals(aResponse.getStatusCode(), 401, "Response code should be 401");
	}
	
	@Test(description = "Test to get all rated movies passing invalid url. Negative scenario")
	public void getMyRatedMoviesWithInvalidUrlTest() throws Exception {
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.TV_SEARCH_URL;
		
		String api_key = IMDBUtils.getImdbDetails().getApiKey();
		String sessionId = anAccountBean.getSessionId();
		MediaType aMediaType = new MovieImpl();
		Response aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		Assert.assertEquals(aResponse.getStatusCode(), 404, "Response code should be 404 - Page not found");
	}
	
	@Test(description = "Test to get all rated movies passing invalid accountId. Negative scenario")
	public void getMyRatedMoviesWithInvalidAccountIdTest() throws Exception {
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ Constants.INVALID_ACCOUNT_ID + Constants.TV_SEARCH_URL;
		
		String api_key = IMDBUtils.getImdbDetails().getApiKey();
		String sessionId = anAccountBean.getSessionId();
		MediaType aMediaType = new MovieImpl();
		Response aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		Assert.assertEquals(aResponse.getStatusCode(), 404, "Response code should be 404 - Page not found");
	}
	
	@Test(description = "Test to get all rated movies exceeding X-RateLimit. Negative scenario")
	public void getMyRatedMoviesXRateLimitTest() throws Exception {
		
		String url = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.RATED_MOVIES_URL;
		String api_key = IMDBUtils.getImdbDetails().getApiKey();
		String sessionId = anAccountBean.getSessionId();
		MediaType aMediaType = new MovieImpl();
		Response aResponse;
		
		//TO DO: Use threads to simulate high load so that we are exceeding X-RateLimit
//		Runnable runnableTask = () -> {
//            try {
//            	aMediaType.getAllRatedMovies(api_key, sessionId, url);
//            }  catch (Exception e) {
//				log.error("Error occurred while thread execution");
//			}
//        };
//         
//        //Executor service instance
//        ExecutorService executor = Executors.newFixedThreadPool(40);
//		for(int i=0; i<500; i++) {
//			executor.execute(runnableTask);
//		}
		aResponse = aMediaType.getAllRatedMovies(api_key, sessionId, url);
		
		log.info("Exceed X-RateLimit Test: X-RateLimit-Remaining value is: " + aResponse.getHeader(Constants.HEADER_X_RATE_LIMIT_REMAINING));
		
		//Validate X-RateLimit value. It should be less than 0
//		Assert.assertTrue(Integer.parseInt(aResponse.getHeader(Constants.HEADER_X_RATE_LIMIT_REMAINING)) == 0, "X-RateLimit-Remaining value should be less than 40");
	}
	
}

