package com.unitestx.mobilepages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.unitestx.mobilebase.BaseTest_Mobile;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage_mobile extends BaseTest_Mobile {

	private AndroidDriver driver;
	private LoggerUtil log;

	public HomePage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// HomePage xpath
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Echo Box\")")
	private WebElement echoBoxBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Login Screen\")")
	private WebElement loginScreenBtn;

	@AndroidFindBy(accessibility = "Navigate Up")
	private WebElement navigateUpBtn;

	public void tapEchoBox() {
		try {
			MobileAutomationUtils.clickElement(echoBoxBtn);
			MobileAutomationUtils.waitForVisibility(navigateUpBtn);
		} catch (Exception e) {
			log.error("Failed to tap on EchoBox Tab", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void tapLoginScreen() {
		try {
			MobileAutomationUtils.clickElement(loginScreenBtn);
			MobileAutomationUtils.waitForVisibility(navigateUpBtn);
		} catch (Exception e) {
			log.error("Failed to tap on EchoBox Tab", e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
