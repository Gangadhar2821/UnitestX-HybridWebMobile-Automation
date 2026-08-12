package com.unitestx.webpages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.WebAutomationUtils;

public class LoginPage_WebApp {
	private WebDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;

	public LoginPage_WebApp(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		this.log = new LoggerUtil();
	}


}
