package com.unitestx.mobilebase;

import com.unitestx.mobilepages.HomePage_mobile;
import com.unitestx.mobilepages.LoginPage_Mobile;

import io.appium.java_client.android.AndroidDriver;

public class MobilePageManager {
	private AndroidDriver driver;

	public MobilePageManager(AndroidDriver driver) {
		this.driver = driver;
	}

	// page object variables
	private LoginPage_Mobile loginPage_Mobile;
	private HomePage_mobile homePage_Mobile;

	public HomePage_mobile getHomePage_Mobile() {
		if (homePage_Mobile == null) {
			homePage_Mobile = new HomePage_mobile(driver);
		}
		return homePage_Mobile;
	}

	public LoginPage_Mobile getLoginPage_Mobile() {
		if (loginPage_Mobile == null) {
			loginPage_Mobile = new LoginPage_Mobile(driver);
		}
		return loginPage_Mobile;
	}

}
