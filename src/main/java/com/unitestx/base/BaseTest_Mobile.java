package com.unitestx.base;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.unitestx.driverfactory.AndroidDriverFactory;
import com.unitestx.pagemanagers.MobilePageManager;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;

public class BaseTest_Mobile {

	protected AndroidDriver driver;
	protected MobilePageManager pages;
	protected LoggerUtil log;
	protected MobileAutomationUtils mobileAutomationutils;
	protected static String testClassName;

	@BeforeSuite
	public void startServer() {
		AppiumServerManager.startServer();
	}

	@BeforeClass(alwaysRun = true)
	public void configuration() throws URISyntaxException, IOException {

		try {
			log = new LoggerUtil();
			// Use Singleton Design pattern
			driver = AndroidDriverFactory.getInstance().getDriver(this.getClass().getSimpleName());
		} catch (Exception e) {
			log.error("Failed to configure and setup AndroidDriver", e);
			throw new RuntimeException(e.getMessage());
		}
		if (driver != null) {
			pages = new MobilePageManager(driver);
			mobileAutomationutils = new MobileAutomationUtils(driver);
			// Get current test class name
			testClassName = this.getClass().getSimpleName();

		}
	}

	@AfterClass(alwaysRun = true)
	public void Teardown() {
		AndroidDriverFactory.getInstance().quitDriver();
	}

	@AfterSuite
	public void killAppiumserver() {
		AppiumServerManager.killListeningProcess(4723);
	}

	public static String getCurrentTestcaseID() {
		return testClassName.split("_")[0];
	}
}
