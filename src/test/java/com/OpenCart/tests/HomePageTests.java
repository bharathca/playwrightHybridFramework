package com.OpenCart.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.OpenCart.base.BaseTest;
import com.OpenCart.constants.ApplicationConstantsHelper;

public class HomePageTests extends BaseTest{
	
	@Test
	public void homePageTitleTest() {
		Assert.assertEquals(homePage.getHomePageTitle(), ApplicationConstantsHelper.valueOf("HOME_PAGE_TITLE").getLabel(),"Home Page title is wrong");
	}
	
	@Test
	public void homePageURLTest() {
		Assert.assertEquals(homePage.getHomePageURL(), prop.getProperty("applicationURL"),"Home Page URL is wrong");
	}
	
	@Test
	public void searchTest() {
		String searchForProducts = homePage.searchForProducts("Macbook");
		Assert.assertEquals(searchForProducts, "Search - Macbook", "The search result header is incorrect");
	}
	
	
	
	
	
}
