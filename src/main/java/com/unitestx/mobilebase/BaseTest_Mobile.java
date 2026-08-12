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
import io.appium.java_client.android.options.UiAutomator2Options;

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

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(MobileAutomationUtils.getValuefromPropFile("DeviceName"));
		options.setAutomationName("UiAutomator2");

		String apkName = MobileAutomationUtils.getValuefromPropFile("ApkName");
		String apkPath = System.getProperty("user.dir") + MobileAutomationUtils.getValuefromPropFile("apkPath")
				+ apkName;
		options.setApp(apkPath);
		options.setCapability("autoGrantPermissions", true);
		options.noReset();
		options.setCapability("newCommandTimeout", 600);
		options.setCapability("unicodeKeyboard", true);
		options.setCapability("resetKeyboard", true);
		options.setCapability("uiautomator2ServerLaunchTimeout", 60000);
		options.setCapability("uiautomator2ServerInstallTimeout", 60000);
		options.setCapability("adbExecTimeout", 60000);
		String testClassName = this.getClass().getSimpleName();
		options.setNoReset(true);

		// Use Singleton DriverFactory
		driver = AndroidDriverFactory.getInstance().getDriver(options);

		if (driver != null) {
			pages = new MobilePageManager(driver);
			mobileAutomationutils = new MobileAutomationUtils(driver);
			log = new LoggerUtil();
			log.info("Page Manager is initialized");
		} else {
			log.info("Page Manager not initialized");
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
