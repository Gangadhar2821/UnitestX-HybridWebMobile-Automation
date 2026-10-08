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
		// private constructor prevents external instantiation(Singleton)
	}

	public static synchronized AndroidDriverFactory getInstance() {
		if (instance == null) {
			instance = new AndroidDriverFactory();
		}
		return instance;
	}

	public AndroidDriver getDriver(String testClassName) {
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(MobileAutomationUtils.getValuefromPropFile("DEVICE_NAME"));
		options.setAutomationName("UiAutomator2");

		String apkName = MobileAutomationUtils.getValuefromPropFile("APK_NAME");
		String apkPath = System.getProperty("user.dir") + MobileAutomationUtils.getValuefromPropFile("APK_PATH")
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
		if (testClassName.contains("Registration")) {
			options.setNoReset(false);
		} else {
			options.setNoReset(true);
		}
		if (driver == null) {
			try {
				driver = new AndroidDriver(new URI(MobileAutomationUtils.getValuefromPropFile("SERVER_URL")).toURL(),
						options);
				log.info("Started the AndroidDriver!");
				log.info("Started Test Execution");
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
				driver.terminateApp(MobileAutomationUtils.getValuefromPropFile("APP_PACKAGE_NAME"));
				log.info("Terminated the Application: "
						+ MobileAutomationUtils.getValuefromPropFile("APP_PACKAGE_NAME"));
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
