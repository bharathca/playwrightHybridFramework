package com.OpenCart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.OpenCart.base.BaseTest;
import com.OpenCart.constants.ApplicationConstantsHelper;
import com.OpenCart.pages.LoginPage;

public class LoginPageTests extends BaseTest {

	@Test(priority = 1)
	public void login() {
		loginPage = (LoginPage) homePage.navigateTo("Login");
		Assert.assertEquals(loginPage.getLoginPageTitle(), ApplicationConstantsHelper.valueOf("LOGIN_PAGE_TITLE").getLabel(), "The page title is wrong");
	}

	@Test(priority=2)
	public void forgotPasswordLinkVisibility() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkVisible(), "Forgot Password link is not visible");
	}
	
	@Test(priority=3)
	public void appLoginTest() {
		Assert.assertTrue(loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password")),"There is an issue with the login");
	}
}
