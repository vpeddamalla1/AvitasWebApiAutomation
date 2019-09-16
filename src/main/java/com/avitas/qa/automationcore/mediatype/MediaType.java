package com.avitas.qa.automationcore.mediatype;

import java.util.List;

import com.jayway.restassured.response.Response;

/**
 * @author Venkat
 */
public interface MediaType {
	
	public String getMediaTypeUrl() throws Exception;
	
	public List<String> getMediaIdsBySearch(String searchTerm) throws Exception;
	
	public List<String> getAllMyFavorites() throws Exception;
	
	public List<String> removeFavorite(String mediaId) throws Exception; 
	
	public List<String> addFavorite(String mediaId) throws Exception; 
	
	public Response getAllRatedMovies(String apiKey, String sessionId, String url) throws Exception; 

}
