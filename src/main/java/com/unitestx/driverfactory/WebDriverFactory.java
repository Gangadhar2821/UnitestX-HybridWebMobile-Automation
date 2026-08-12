package com.unitestx.driverfactory;

import java.time.Duration;

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
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-notifications");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-translate");
			options.addArguments("--no-default-browser-check");
			options.addArguments(
					"--disable-features=AutofillServerCommunication,PasswordManagerEnabled,PasswordManagerSigninPromo");

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
