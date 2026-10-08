package com.unitestx.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.unitestx.driverfactory.WebDriverFactory;
import com.unitestx.pagemanagers.WebPageManager;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.WebAutomationUtils;

public class BaseTest_Web {
	protected WebDriver driver;
	protected WebPageManager pages;
	protected WebAutomationUtils utilityWeb;
	protected LoggerUtil log;
	protected static String testClassName;

	@BeforeClass(alwaysRun = true)
	public void setupWeb() {
		log = new LoggerUtil();

		// Use Singleton WebDriverFactory
		driver = WebDriverFactory.getInstance().getDriver();
		// Get current test class name
		testClassName = this.getClass().getSimpleName();
		log.info("Initializing the page objects...");
		preInitializer();
	}

	public void preInitializer() {
		pages = new WebPageManager(driver);
		utilityWeb = new WebAutomationUtils(driver);
	}

	@AfterClass(alwaysRun = true)
	public void tearDownWeb() {
		WebDriverFactory.getInstance().quitDriver();
	}

	public static String getCurrentTestcaseID() {
		return testClassName.split("_")[0];
	}
}
