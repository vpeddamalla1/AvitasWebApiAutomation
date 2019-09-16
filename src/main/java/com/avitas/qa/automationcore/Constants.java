package com.avitas.qa.automationcore;

/**
 * @author Venkat
 * Project level constants are declared here.
 */
public class Constants {
	
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String MEDIA_TYPE_MOVIE = "movie";
	public static final String MEDIA_TYPE_TV = "tv";
	public static final String MEDIA_TYPE_MOVIE_URL = "/movies";
	public static final String MEDIA_TYPE_TV_URL = "/tv";
	public static final String MEDIA_TYPE_TV_EPISODES_URL = "/tv/episodes";
	
	public static final String API_KEY="api_key";
	public static final String IMDB_USERNAME = "username";
	public static final String IMDB_PASSWORD = "password";
	public static final String REQUEST_TOKEN = "request_token";
	public static final String SESSION_ID="sessionId";
	public static final String QUERY_PARAM_QUERY = "query";
	public static final String QUERY_PARAM_SESSION_ID = "session_id";
	public static final String REQ_BODY_MEDIA_TYPE = "media_type";
	public static final String REQ_BODY_MEDIA_ID = "media_id";
	public static final String REQ_BODY_FAVORITE = "favorite";

	//Account
	public static final String REQUEST_TOKEN_URL ="/authentication/token/new";
	public static final String VALIDATE_TOKEN_URL = "/authentication/token/validate_with_login";
	public static final String CREATE_SESSION_URL="/authentication/session/new";
	public static final String ACCOUNT_URL = "/account";
	public static final String INVALID_ACCOUNT_ID = "11111XXXXX1111111";
	
	//Tests
	public static final String FAVORITES_TESTS = "$.['ACCOUNT']['FAVORITES_TEST']";
	public static final String IMDB_FILE_NAME = "imdbData.json";
	
	//Search
	public static final String TV_SEARCH_URL = "/search/tv";
	public static final String MOVIE_SEARCH_URL = "/search/movie";
	
	//All favorites url
	public static final String TV_ALL_FAVORITES_URL = "/favorite/tv";
	public static final String MOVIE_ALL_FAVORITES_URL = "/favorite/movies";
	public static final String UPDATE_FAVORITES_URL = "/favorite";
	
	//Rated movies
	public static final String RATED_MOVIES_URL="/rated/movies";
	
	//Headers
	public static final String RES_CONTENT_TYPE = "application/json;charset=utf-8";
	public static final String HEADER_X_RATE_LIMIT_REMAINING = "X-RateLimit-Remaining";
	
}
