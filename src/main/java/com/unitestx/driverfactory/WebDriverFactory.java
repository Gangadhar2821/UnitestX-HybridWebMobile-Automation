package com.unitestx.driverfactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.unitestx.utils.LoggerUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	private static WebDriverFactory instance;
	private static WebDriver driver;
	private static final LoggerUtil log = new LoggerUtil();

	private WebDriverFactory() {
		// private constructor prevents external instantiation
	}

	public static synchronized WebDriverFactory getInstance() {
		if (instance == null) {
			instance = new WebDriverFactory();
		}
		return instance;
	}

	public WebDriver getDriver() {
		if (driver == null) {

			WebDriverManager.chromedriver().cachePath("./drivers").setup();
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-notifications");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-translate");
			options.addArguments("--no-default-browser-check");

			options.addArguments("--disable-features=" + "AutofillServerCommunication," + "PasswordManagerEnabled,"
					+ "PasswordManagerSigninPromo," + "PasswordLeakDetection");

			Map<String, Object> prefs = new HashMap<>();

			prefs.put("profile.password_manager_leak_detection", false);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("autofill.password_manager_enabled", false);

			options.setExperimentalOption("prefs", prefs);

			log.info("Initializing ChromeDriver...");

			driver = new ChromeDriver(options);

			driver.manage().window().maximize();

			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

			log.info("ChromeDriver started successfully!");
		}

		return driver;
	}

	public void quitDriver() {
		if (driver != null) {
			try {
				driver.quit();
				log.info("Terminated the ChromeDriver!");
			} catch (Exception e) {
				log.error("Error while quitting WebDriver: " + e.getMessage(), e);
			} finally {
				driver = null;
			}
		}
	}

	public WebDriver getCurrentDriver() {
		return driver;
	}
}
