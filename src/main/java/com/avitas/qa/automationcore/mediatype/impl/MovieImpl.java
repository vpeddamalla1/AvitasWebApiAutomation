package com.avitas.qa.automationcore.mediatype.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.avitas.qa.automationcore.Constants;
import com.avitas.qa.automationcore.mediatype.MediaType;
import com.avitas.qa.utilities.IMDBUtils;
import com.avitas.qa.utilities.JsonParsers;
import com.avitas.qa.utilities.ResponseUtils;
import com.avitas.qa.utilities.RestUtils;
import com.avitas.qa.utilities.TestBase;
import com.jayway.restassured.response.Response;

/**
 * @author Venkat
 */

public class MovieImpl extends TestBase implements MediaType {
	
	public static Logger log = Logger.getLogger(MovieImpl.class);
	
	public JsonParsers jsonParsers = new JsonParsers();
	
	@Override
	public String getMediaTypeUrl() throws Exception {
		return IMDBUtils.getBaseUrl() + Constants.MEDIA_TYPE_MOVIE_URL;
	}

	@Override
	public List<String> getMediaIdsBySearch(String searchTerm) throws Exception {
		// Search for title name
		String searchUrl = IMDBUtils.getSearchUrl(Constants.MEDIA_TYPE_MOVIE);
		Map<String, String> requestHeaders = new HashMap<>();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(Constants.API_KEY, IMDBUtils.getImdbDetails().getApiKey());
		queryParams.put(Constants.QUERY_PARAM_QUERY, searchTerm);

		Response aResponse = RestUtils.doGet(searchUrl, requestHeaders, queryParams);
		List<String> alist = null;
		if (ResponseUtils.validateSuccessResponseCode(aResponse)) {
			log.info("Response is: " + aResponse.getBody().asString());
			alist = jsonParsers.getListFromJsonStringOnly(aResponse.getBody().asString(), "$.results[*].id");
		}
		log.info("Results returned from search are: "+ alist);
		return alist;
	}

	@Override
	public List<String> getAllMyFavorites() throws Exception {
		
		String getAllFavoritesUrl = IMDBUtils.getAllFavoritesUrl(Constants.MEDIA_TYPE_MOVIE);
		Map<String, String> requestHeaders = new HashMap<>();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(Constants.API_KEY, IMDBUtils.getImdbDetails().getApiKey());
		queryParams.put(Constants.QUERY_PARAM_SESSION_ID, anAccountBean.getSessionId());

		Response aResponse = RestUtils.doGet(getAllFavoritesUrl, requestHeaders, queryParams);
		List<String> theFavoritesList = null;
		if (ResponseUtils.validateSuccessResponseCode(aResponse)) {
			log.info("Favorites Returned: " + aResponse.getBody().asString());
			theFavoritesList = jsonParsers.getListFromJsonStringOnly(aResponse.getBody().asString(), "$.results[*].id");
		}
		
		return theFavoritesList;
	}

	@Override
	public List<String> removeFavorite(String mediaId) throws Exception {
		updateFavorites(mediaId, false);
		return getAllMyFavorites();
	}
	
	private void updateFavorites(String mediaId, boolean newFlag) throws Exception {
		String updateFavoriteUrl = IMDBUtils.updateFavoriteUrl();
		Map<String, String> requestHeaders = new HashMap<>();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(Constants.API_KEY, IMDBUtils.getImdbDetails().getApiKey());
		queryParams.put(Constants.QUERY_PARAM_SESSION_ID, anAccountBean.getSessionId());

		JSONObject queryParamsForSession = new JSONObject();
		queryParamsForSession.put(Constants.REQ_BODY_MEDIA_TYPE, Constants.MEDIA_TYPE_MOVIE);
		queryParamsForSession.put(Constants.REQ_BODY_MEDIA_ID, mediaId);
		
		//Mark favorite if the flag is true. Remove favorite by passing false
		queryParamsForSession.put(Constants.REQ_BODY_FAVORITE, newFlag);
		
		String sessionRequestBodyJson = queryParamsForSession.toJSONString();
		Response anUpdatedResponse = RestUtils.doPost(updateFavoriteUrl, sessionRequestBodyJson, requestHeaders, queryParams, Constants.CONTENT_TYPE_JSON);
		if (ResponseUtils.validateSuccessResponseCode(anUpdatedResponse)) {
			log.info("Mark/Unmark favorites :" + anUpdatedResponse.getBody().asString());
		}
	}

	@Override
	public List<String> addFavorite(String mediaId) throws Exception {
		updateFavorites(mediaId, true);
		return getAllMyFavorites();
	}

	@Override
	public Response getAllRatedMovies(String apiKey, String sessionId, String url) throws Exception {
		String getAllRatedMoviesUrl = url;
		Map<String, String> requestHeaders = new HashMap<>();
		Map<String, String> queryParams = new HashMap<>();
		if(apiKey != null) {
			queryParams.put(Constants.API_KEY, apiKey);
		}
		if(sessionId != null) {
			queryParams.put(Constants.QUERY_PARAM_SESSION_ID, sessionId);
		}
		
		Response aResponse = RestUtils.doGet(getAllRatedMoviesUrl, requestHeaders, queryParams);
		return aResponse;
	}
	
	

}
