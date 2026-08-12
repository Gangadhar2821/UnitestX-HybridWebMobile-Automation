package com.unitestx.webbase;

import org.openqa.selenium.WebDriver;

import com.unitestx.webpages.HomePage_Web;
import com.unitestx.webpages.LoginPage_WebApp;

public class WebPageManager {
	private WebDriver driver;

	public WebPageManager(WebDriver driver) {
		this.driver = driver;
	}

	// page object variables
	protected LoginPage_WebApp loginPage_web;
	private HomePage_Web homePage_Web;

	public HomePage_Web getHomePage_Web() {
		if (homePage_Web == null) {
			homePage_Web = new HomePage_Web(driver);
		}
		return homePage_Web;
	}

	public LoginPage_WebApp getLoginPage_WebApp() {
		if (loginPage_web == null) {
			loginPage_web = new LoginPage_WebApp(driver);
		}
		return loginPage_web;
	}

}
