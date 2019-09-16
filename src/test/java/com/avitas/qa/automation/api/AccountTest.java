package com.avitas.qa.automation.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.avitas.qa.automationcore.Constants;
import com.avitas.qa.automationcore.mediatype.MediaType;
import com.avitas.qa.utilities.IMDBUtils;
import com.avitas.qa.utilities.TestBase;

/**
 * @author Venkat
 * This is a test where end to end scenario is covered.
 * 		1. Search for a String in TV and Movie media type
 * 		2. Retrieve all the favorites for current account for both media types.
 * 		3. If the search result is present in my favorite list - Remove it.
 * 		4. Now add that search result as favorite.
 * 		5. Verify the favorite is coming in my list
 * It is a paramaterized test case
 */

public class AccountTest extends TestBase {
	
	public static Logger log = Logger.getLogger(AccountTest.class);
	
	@DataProvider(name = "dataProviderForFavorites", parallel = false)
	public Iterator<Object[]> dataProviderForFavorites(ITestContext iTestContext) {
		Collection<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String,String>> favoriteTestList = jsonParsers.getListOfMapFromJsonString(Constants.IMDB_FILE_NAME, Constants.FAVORITES_TESTS );
				
		for(Map<String,String> aMap : favoriteTestList) {
			data.add(new Object[] { aMap });
		}
		return data.iterator();
	}
	
	
	@Test(description = "Test to get favorites, update favorites, delete favorites", dataProvider = "dataProviderForFavorites")
	public void getMyFavoritesTest(Map<String,String> inputMap) throws Exception {
		
		String inputMediaType = inputMap.get("mediaType");
		String searchTerm = inputMap.get("searchTerm");
		MediaType aMediaType = IMDBUtils.getMediaType(inputMediaType);
		List<String> listOfMediaIds = aMediaType.getMediaIdsBySearch(searchTerm);
		
		//Take one media id out of all results returned from search
		if(listOfMediaIds.size() > 0) {
			
			//First get all my favorites for the media type(tv or movie)
			List<String> theFavoriteIds = aMediaType.getAllMyFavorites();
			
			//Clear the searched media item from my existing favorites list
			if(theFavoriteIds.contains(listOfMediaIds.get(0))) {
				List<String> theLatestFavoriteIdsAfterRemoval = aMediaType.removeFavorite(listOfMediaIds.get(0));
				log.info("After marking favorite, Selected media id is: "+ listOfMediaIds.get(0) + " ,All my favorites are "+theLatestFavoriteIdsAfterRemoval);
				Assert.assertFalse(theLatestFavoriteIdsAfterRemoval.contains(listOfMediaIds.get(0)), "Please make sure media is not present in current favorites");
			}
			
			//Mark favorite for the media item retrieved from search
			List<String> theLatestFavoriteIds  = aMediaType.addFavorite(listOfMediaIds.get(0));
			
			//Now get all my favorites and verify if the newly added favorite is present.
			log.info("After marking favorite, Selected media id is: "+ listOfMediaIds.get(0) + " ,All my favorites are "+theLatestFavoriteIds);
			Assert.assertTrue(theLatestFavoriteIds.contains(listOfMediaIds.get(0)), "Unable to favorite media id");
			
		} else {
			Assert.fail("Search results are empty");
		}
		
	}
	
}

