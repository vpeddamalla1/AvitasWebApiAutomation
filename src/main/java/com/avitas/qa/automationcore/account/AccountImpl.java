package com.avitas.qa.automationcore.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.avitas.qa.automationcore.Constants;
import com.avitas.qa.pojo.AccountBean;
import com.avitas.qa.pojo.IMDBDetails;
import com.avitas.qa.utilities.IMDBUtils;
import com.avitas.qa.utilities.JsonParsers;
import com.avitas.qa.utilities.ResponseUtils;
import com.avitas.qa.utilities.RestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;

/**
 * @author Venkat
 */
public class AccountImpl extends Constants implements Account {

	public static Logger log = Logger.getLogger(AccountImpl.class);
	private String baseUrl;
	private IMDBDetails imdbDetails; 
	private JsonParsers jsonParsers = new JsonParsers();
	public AccountImpl() throws Exception {
		imdbDetails = IMDBUtils.getImdbDetails();
		this.baseUrl = imdbDetails.getBaseUrl() + "/" + imdbDetails.getVersion();
	}
	
	@Override
	public AccountBean fetAccountDetails() throws Exception {
		
		AccountBean anAccountBean = new AccountBean();
		
		try {
			String requestTokenUrl = this.baseUrl + Constants.REQUEST_TOKEN_URL;
			Map<String, String> requestHeaders = new HashMap<>();
			Map<String, String> queryParams = new HashMap<>();
			queryParams.put(Constants.API_KEY, imdbDetails.getApiKey());

			// Step 1: Request new token
			Response aResponse = RestUtils.doGet(requestTokenUrl, requestHeaders, queryParams);
			String requestToken = null;
			if (ResponseUtils.validateSuccessResponseCode(aResponse)) {
				requestToken = jsonParsers.readStringValueFromJson(aResponse.getBody().asString(), "$.request_token");
			}

			// Step 2: Authorize token with login credentials
			String validateTokenUrl = this.baseUrl + Constants.VALIDATE_TOKEN_URL;
			Map<String, String> requestBody = new HashMap<>();
			requestBody.put(Constants.IMDB_USERNAME, this.imdbDetails.getUserName());
			requestBody.put(Constants.IMDB_PASSWORD, this.imdbDetails.getPassWord());
			requestBody.put(Constants.REQUEST_TOKEN, requestToken);
			String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);
			Response aValidateTokenResponse = RestUtils.doPost(validateTokenUrl, requestBodyJson, requestHeaders, queryParams, Constants.CONTENT_TYPE_JSON);
			if (ResponseUtils.validateSuccessResponseCode(aValidateTokenResponse)) {
				log.info("Request token validated with Username/Password");
			}

			// Step 3: Prepare session_id
			String createSessionUrl = this.baseUrl + Constants.CREATE_SESSION_URL;
			Map<String, String> queryParamsForSession = new HashMap<>();
			queryParamsForSession.put(Constants.API_KEY, imdbDetails.getApiKey());
			queryParamsForSession.put(Constants.REQUEST_TOKEN, requestToken);
			String sessionRequestBodyJson = new ObjectMapper().writeValueAsString(queryParamsForSession);
			Response aCreateSessionResponse = RestUtils.doPost(createSessionUrl, sessionRequestBodyJson, requestHeaders, queryParamsForSession, Constants.CONTENT_TYPE_JSON);
			if (ResponseUtils.validateSuccessResponseCode(aCreateSessionResponse)) {
				log.info("created valid session id");
				String sessionId = jsonParsers.readStringValueFromJson(aCreateSessionResponse.getBody().asString(), "$.session_id");
				anAccountBean.setSessionId(sessionId);
			}

			// Finally get Account Details
			String accountUrl = this.baseUrl + Constants.ACCOUNT_URL;
			Map<String, String> queryParamsWithKeyAndSession = new HashMap<>();
			queryParamsWithKeyAndSession.put(Constants.API_KEY, imdbDetails.getApiKey());
			queryParamsWithKeyAndSession.put(Constants.QUERY_PARAM_SESSION_ID, anAccountBean.getSessionId());
			Response anAccountDetailsResponse = RestUtils.doGet(accountUrl, requestHeaders,queryParamsWithKeyAndSession);
			if (ResponseUtils.validateSuccessResponseCode(anAccountDetailsResponse)) {
				log.info("Retrieved Account details successfully");
				String accountId = jsonParsers.readStringValueFromJson(anAccountDetailsResponse.getBody().asString(), "$.id");
				anAccountBean.setAccountId(accountId);
			}
		} catch (Exception anException) {
			log.error("Problem occurred while fetching account details");
		}
		return anAccountBean;
	}
	
	
	

}
