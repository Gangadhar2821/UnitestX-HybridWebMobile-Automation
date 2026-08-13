package com.unitestx.mobilebase;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.unitestx.driverfactory.AndroidDriverFactory;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;

public class BaseTest_Mobile {

	protected AndroidDriver driver;
	protected MobilePageManager pages;
	protected LoggerUtil log;
	protected MobileAutomationUtils mobileAutomationutils;

	@BeforeSuite
	public void startServer() {
		AppiumServerManager.startServer();
	}

	@BeforeClass(alwaysRun = true)
	public void configuration() throws URISyntaxException, IOException {

		try {
			// Use Singleton Design
			driver = AndroidDriverFactory.getInstance().getDriver();
			if (driver != null) {
				pages = new MobilePageManager(driver);
				mobileAutomationutils = new MobileAutomationUtils(driver);
				log = new LoggerUtil();
			}
		} catch (Exception e) {
			log.error("Failed to configure and Set up Android driver", e);
			throw new RuntimeException(e.getMessage());
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
}
