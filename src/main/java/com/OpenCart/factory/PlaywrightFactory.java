package com.OpenCart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {
	Playwright playwright;
	Browser browser;
	BrowserContext browserContext;
	Page page;
	Properties prop;

	private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<Playwright>();
	private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<Browser>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<BrowserContext>();
	private static ThreadLocal<Page> tlPage = new ThreadLocal<Page>();

	public static Playwright getTlPlaywright() {
		return tlPlaywright.get();
	}

	public static Browser getTlBrowser() {
		return tlBrowser.get();
	}

	public static BrowserContext getTlBrowserContext() {
		return tlBrowserContext.get();
	}

	public static Page getTlPage() {
		return tlPage.get();
	}

	public Page setupBrowser(Properties prop) {
		String browserName = prop.getProperty("browserName").trim();
		Boolean headless = Boolean.valueOf(prop.getProperty("headless").trim());
		System.out.println("The browser selected for the execution is :" + browserName);
		// playwright = Playwright.create(); ( used before threadLocal implementation )
		tlPlaywright.set(Playwright.create());

		switch (browserName.toLowerCase()) {
		case "chromium":
			// browser = playwright.chromium().launch(new
			// BrowserType.LaunchOptions().setHeadless(headless));
			tlBrowser.set(getTlPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
			break;
		case "safari":
//			browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
			tlBrowser.set(getTlPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
			break;
		case "firefox":
//			browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
			tlBrowser.set(getTlPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
			break;
		case "edge":
			// browser = playwright.chromium().launch(new
			// LaunchOptions().setChannel("edge").setHeadless(headless));
			tlBrowser.set(
					getTlPlaywright().chromium().launch(new LaunchOptions().setChannel("edge").setHeadless(headless)));
			break;
		case "chrome":
			// browser = playwright.chromium().launch(new
			// LaunchOptions().setChannel("chrome").setHeadless(headless));
			tlBrowser.set(getTlPlaywright().chromium()
					.launch(new LaunchOptions().setChannel("chrome").setHeadless(headless)));
			break;
		default:
			System.out.println("The given browsername is not in the list");
			break;
		}

		// browserContext = browser.newContext();
		tlBrowserContext.set(getTlBrowser().newContext());

		// page = browserContext.newPage();
		tlPage.set(getTlBrowser().newPage());

		// page.navigate(prop.getProperty("applicationURL"));
		getTlPage().navigate(prop.getProperty("applicationURL"));

		return getTlPage();

	}

	public void tearDownBrowser(Page page) {
		page.context().browser().close();
	}

	/**
	 * This method is used to initialize the properties from the config file
	 * 
	 * @return Properties
	 */
	public Properties initializePropertyFileReader() {
		try {
			FileInputStream fileInput = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/*
	 * Take screenshot
	 */
	public static String takeScreenshot() {
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		getTlPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		try {
			byte[] imageBytes = Files.readAllBytes(Paths.get(path));
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}