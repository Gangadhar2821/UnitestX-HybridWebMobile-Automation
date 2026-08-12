package com.unitestx.driverfactory;

import java.net.URI;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class AndroidDriverFactory {

	private static AndroidDriverFactory instance;
	private static AndroidDriver driver;
	private static final LoggerUtil log = new LoggerUtil();

	private AndroidDriverFactory() {
		// private constructor to prevent external instantiation(Singleton)
	}

	public static synchronized AndroidDriverFactory getInstance() {
		if (instance == null) {
			instance = new AndroidDriverFactory();
		}
		return instance;
	}

	public AndroidDriver getDriver(UiAutomator2Options options) {
		if (driver == null) {
			try {
				log.info("Creating Android Driver..");
				driver = new AndroidDriver(new URI(MobileAutomationUtils.getValuefromPropFile("serverUrl")).toURL(),
						options);
				log.info("Started the AndroidDriver!");
			} catch (Exception e) {
				log.error("Failed to Start the AndroidDriver!", e);
				throw new RuntimeException(e);
			}
		}
		return driver;
	}

	public AndroidDriver getCurrentDriver() {
		return driver;
	}

	public void quitDriver() {
		if (driver != null) {
			try {
				driver.terminateApp(MobileAutomationUtils.getValuefromPropFile("appPackageName"));
				log.info("Terminated the Application: " + MobileAutomationUtils.getValuefromPropFile("appPackageName"));
				driver.quit();
				log.info("Terminated the Android driver!");
			} catch (Exception e) {
				log.error("Error during driver quit: " + e.getMessage(), e);
			} finally {
				driver = null;
			}
		}
	}


}
