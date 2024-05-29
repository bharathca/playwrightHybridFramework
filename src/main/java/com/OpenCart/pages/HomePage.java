package com.OpenCart.pages;

import com.microsoft.playwright.Page;

public class HomePage {

	private Page page;

	// Locators
	private String search = "input[name='search1']";
	private String searchIcon = "#search button";

	private String searchResultHeader = "div#content h1";

	private String myAccountNavigation = ".nav a[title='My Account']";
	private String navigateToPage = "a:text('replaceText')";

	// constructor
	public HomePage(Page page) {
		this.page = page;
	}

	// Actions
	public String getHomePageTitle() {
		String pageTitle = page.title();
		System.out.println("Page title is :" + pageTitle);
		return pageTitle;
	}

	public String getHomePageURL() {
		String pageURL = page.url();
		System.out.println("Page URL is :" + pageURL);
		return pageURL;
	}

	public String searchForProducts(String searchFor) {
		page.fill(search, searchFor);
		page.click(searchIcon);
		page.locator(searchResultHeader).waitFor();
		String searchResultsHeader = page.textContent(searchResultHeader);
		System.out.println("Search Results Header is :" + searchResultsHeader);
		return searchResultsHeader;
	}

	public Object navigateTo(String pageToClick) {
		page.click(myAccountNavigation);
		page.click(navigateToPage.replace("replaceText", pageToClick));
		if(pageToClick.equals("Register")) {
			return new RegistrationPage(page);
		} else {
			return new LoginPage(page);
		}
	}
};