package com.OpenCart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

	private Page page;

	// Locators
	private String emailID = "input[name='email']";
	private String password = "input[name='password']";
	private String loginButton = "//input[@value='Login']";
	private String forgotPasswordLink = "a:text('Forgotten Password')";
	private String logOutLink = "a:text('Logout')";
	private String myAccountNavigation = ".nav a[title='My Account']";
	// Constructor
	public LoginPage(Page page) {
		this.page = page;
	}

	// Action/Methods
	public String getLoginPageTitle() {
		String pageTitle = page.title();
		System.out.println("Page title is :" + pageTitle);
		return pageTitle;
	}

	public boolean isForgotPasswordLinkVisible() {
		return page.isVisible(forgotPasswordLink);
	}

	public boolean doLogin(String applicationUsername, String applicationPassword) {
		page.fill(emailID, applicationUsername);
		page.fill(password, applicationPassword);
		page.click(loginButton);
		page.click(myAccountNavigation);
		if (page.isVisible(logOutLink)) {
			System.out.println("Successfully logged in");
			return true;
		} else {
			System.out.println("There is an issue with the login");
			return false;
		}
	}
}
