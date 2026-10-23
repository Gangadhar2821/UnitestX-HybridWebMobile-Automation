package com.unitestx.webpages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.WebAutomationUtils;

public class HomePage_Web {
	private WebDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;

	public HomePage_Web(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		this.log = new LoggerUtil();
	}



}
