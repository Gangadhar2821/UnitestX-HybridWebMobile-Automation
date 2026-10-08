package com.unitestx.pagemanagers;

import com.unitestx.mobilepages.ClipBoard_DemoPage_mobile;
import com.unitestx.mobilepages.EchoBoxPage_mobile;
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
	private EchoBoxPage_mobile echoBoxPage_mobile;
	private ClipBoard_DemoPage_mobile clipBoard_DemoPage_mobile;

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

	public EchoBoxPage_mobile getEchoBoxPage_mobile() {
		if (echoBoxPage_mobile == null) {
			echoBoxPage_mobile = new EchoBoxPage_mobile(driver);
		}
		return echoBoxPage_mobile;
	}

	public ClipBoard_DemoPage_mobile getClipBoard_DemoPage_mobile() {
		if (clipBoard_DemoPage_mobile == null) {
			clipBoard_DemoPage_mobile = new ClipBoard_DemoPage_mobile(driver);
		}
		return clipBoard_DemoPage_mobile;
	}

}
