package com.OpenCart.base;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.OpenCart.factory.PlaywrightFactory;
import com.OpenCart.pages.HomePage;
import com.OpenCart.pages.LoginPage;
import com.microsoft.playwright.Page;

public class BaseTest {
	
	PlaywrightFactory pf;
	Page page;
	protected Properties prop;
	
	protected HomePage homePage;
	protected LoginPage loginPage;

	@BeforeTest
	public void setupTest() {
		pf = new PlaywrightFactory();
		prop = pf.initializePropertyFileReader();
		page = pf.setupBrowser(prop);
		homePage = new HomePage(page);
	}
	
	@AfterTest
	public void tearDownTest() {
		pf.tearDownBrowser(page);
	}
	
	@AfterSuite(alwaysRun = true)
	public static void cleanUpReportFiles() {
		File directory = new File("./Reports");
		if (directory.exists() && directory.isDirectory()) {
			File[] subDirectories = directory.listFiles(File::isDirectory);
			if (subDirectories != null && subDirectories.length > 3) {
				Arrays.sort(subDirectories, (dir1, dir2) -> {
					try {
						// Parsing the folder names ato dates and compare them.
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:aa");
						Date date1 = sdf.parse(dir1.getClass().getName().replace("ExtentReport ", "dd-MM-yyyy HH:mm:ss:aa"));
						Date date2 = sdf.parse(dir2.getClass().getName().replace("ExtentReport ", "dd-MM-yyyy HH:mm:ss:aa"));
						return date1.compareTo(date2);
					} catch (ParseException e) {
						e.printStackTrace();
						return 0;
					}
				});

				// Delete the oldest folders until only three recent folders are left
				for (int i = 0; i < subDirectories.length - 3; i++) {
					deleteDirectory(subDirectories[i]);
				}
			}
		}
	}

	public static void deleteDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				} else {
					if (file.delete()) {
						System.out.println("Deleted: " + file.getAbsolutePath());
					} else {
						System.out.println("Failed to delete: " + file.getAbsoluteFile());
					}
				}
			}
		}
		if (directory.delete()) {
			System.out.println("Deleted directory: " + directory.getAbsolutePath());
		} else {
			System.out.println("Failed to delete the directory: " + directory.getAbsoluteFile());
		}

	}
}
