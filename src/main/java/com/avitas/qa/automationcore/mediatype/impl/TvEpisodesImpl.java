package com.avitas.qa.automationcore.mediatype.impl;

import java.util.List;

import com.avitas.qa.automationcore.Constants;
import com.avitas.qa.automationcore.mediatype.MediaType;
import com.avitas.qa.utilities.IMDBUtils;
import com.jayway.restassured.response.Response;

/**
 * @author Venkat
 */

public class TvEpisodesImpl extends Constants implements MediaType {

	@Override
	public String getMediaTypeUrl()  throws Exception {
		return IMDBUtils.getBaseUrl() + MEDIA_TYPE_TV_EPISODES_URL;
	}

	@Override
	public List<String> getMediaIdsBySearch(String searchTerm) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllMyFavorites() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> removeFavorite(String mediaId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> addFavorite(String mediaId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAllRatedMovies(String apiKey, String sessionId, String url) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
