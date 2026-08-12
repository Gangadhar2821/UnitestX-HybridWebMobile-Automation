package com.unitestx.mobilebase;

import com.unitestx.mobilepages.LoginPage_Mobile;
import com.unitestx.mobilepages.RegistrationPage_Mobile;

import io.appium.java_client.android.AndroidDriver;

public class MobilePageManager {
	private AndroidDriver driver;

	public MobilePageManager(AndroidDriver driver) {
		this.driver = driver;
	}

	// page object variables
	private RegistrationPage_Mobile registartionPage_Mobile;
	private LoginPage_Mobile loginPage_Mobile;

	public RegistrationPage_Mobile getRegistartionPage_Mobile() {
		if (registartionPage_Mobile == null) {
			registartionPage_Mobile = new RegistrationPage_Mobile(driver);
		}
		return registartionPage_Mobile;
	}

	public LoginPage_Mobile getLoginPage_Mobile() {
		if (loginPage_Mobile == null) {
			loginPage_Mobile = new LoginPage_Mobile(driver);
		}
		return loginPage_Mobile;
	}

}
