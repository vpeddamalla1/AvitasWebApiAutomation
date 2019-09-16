package com.avitas.qa.utilities;

import java.util.Map;

import org.apache.log4j.Logger;

import com.avitas.qa.automationcore.Constants;
import com.avitas.qa.automationcore.mediatype.MediaType;
import com.avitas.qa.automationcore.mediatype.impl.MovieImpl;
import com.avitas.qa.automationcore.mediatype.impl.TvImpl;
import com.avitas.qa.pojo.IMDBDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Venkat
 */

public class IMDBUtils extends TestBase {
	
	private static final String fileName = "imdbData.json";
	private static JsonParsers jsonParser = new JsonParsers();
	public static Logger log = Logger.getLogger(IMDBUtils.class);
	
	public static IMDBDetails getImdbDetails() throws Exception{
		IMDBDetails imdbDetails = null;
		try {
			Map<String,String> imdbJsonMap = jsonParser.getMapFromJsonString(fileName, "$.['IMDB_DATA']");
			
			ObjectMapper mapper = new ObjectMapper();

			// Convert Map to POJO
			imdbDetails = mapper.convertValue(imdbJsonMap, IMDBDetails.class);
			
		} catch (Exception anException) {
			log.error("Unable to read project details from file");
			throw anException;
		}
		
		//Set apikey dynamically if passed from VM args
		if(System.getProperty(Constants.API_KEY) != null) {
			imdbDetails.setApiKey(System.getProperty(Constants.API_KEY));
		}
		
		// Set username dynamically if passed from VM args
		if (System.getProperty(Constants.IMDB_USERNAME) != null) {
			imdbDetails.setUserName(System.getProperty(Constants.IMDB_USERNAME));
		}

		// Set password dynamically if passed from VM args
		if (System.getProperty(Constants.IMDB_PASSWORD) != null) {
			imdbDetails.setPassWord(System.getProperty(Constants.IMDB_PASSWORD));
		}
		return imdbDetails;
	}
	
	public static String getBaseUrl() throws Exception {
		IMDBDetails imdbDetails = getImdbDetails();
		return imdbDetails.getBaseUrl() + "/" + imdbDetails.getVersion();
	}
	
	public static MediaType getMediaType(String mediaType) {
		MediaType aMediaType = null;
		if(mediaType.equals(Constants.MEDIA_TYPE_TV)){
			aMediaType = new TvImpl();
		} else if (mediaType.equals(Constants.MEDIA_TYPE_MOVIE)) {
			aMediaType = new MovieImpl();
		}
		return aMediaType;
	}
	
	public static String getSearchUrl(String mediaType) throws Exception{
		String searchUrl = null;
		switch (mediaType) {
			case "tv":
				searchUrl = IMDBUtils.getBaseUrl() + Constants.TV_SEARCH_URL;
				break;
				
			case "movie":
				searchUrl = IMDBUtils.getBaseUrl() + Constants.MOVIE_SEARCH_URL;
				break;
			
		}
		return searchUrl;
	}
	
	public static String getAllFavoritesUrl(String mediaType) throws Exception{
		String allFavoritesUrl = null;
		switch (mediaType) {
			case "tv":
				allFavoritesUrl = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId() + Constants.TV_ALL_FAVORITES_URL;
				break;
				
			case "movie":
				allFavoritesUrl = IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.MOVIE_ALL_FAVORITES_URL;
				break;
			
		}
		return allFavoritesUrl;
	}
	
	public static String updateFavoriteUrl() throws Exception{
		return IMDBUtils.getBaseUrl() + Constants.ACCOUNT_URL+ "/"+ anAccountBean.getAccountId()+ Constants.UPDATE_FAVORITES_URL;
	}

}
