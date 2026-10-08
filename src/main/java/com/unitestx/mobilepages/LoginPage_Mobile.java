package com.unitestx.mobilepages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage_Mobile {
	private AndroidDriver driver;
	private LoggerUtil log;
	private WebDriverWait wait;

	public LoginPage_Mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// Login page Locators

	@AndroidFindBy(accessibility = "username")
	private WebElement usernamefield;

	@AndroidFindBy(accessibility = "password")
	private WebElement passwordfield;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Login\").instance(1)")
	private WebElement loginBtn;

	@AndroidFindBy(accessibility = "Navigate Up")
	private WebElement navigateUpBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/message\")")
	private WebElement invalidLoginErrorMsg;

	@AndroidFindBy(id = "android:id/button1")
	private WebElement okBtn;

	public void login() {
		try {
			MobileAutomationUtils.sendKeysToElement(usernamefield, "Gangadhar");
			MobileAutomationUtils.sendKeysToElement(passwordfield, "password");
			MobileAutomationUtils.clickElement(loginBtn);
		} catch (Exception e) {
			log.error("Failed to login", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void verifyinvalidLogin() {
		try {
			MobileAutomationUtils.waitForVisibility(invalidLoginErrorMsg);
			String errorTxt = MobileAutomationUtils.getTextByXPath(invalidLoginErrorMsg);
			if (errorTxt.contains("Invalid")) {
				log.info(errorTxt);
				MobileAutomationUtils.takescreenshot_And_AppendTo_Path();
				MobileAutomationUtils.clickElement(okBtn);
			} else {
				log.info("No error message was found");
			}
		} catch (Exception e) {
			log.error("Failed to verify invalid login error message", e);
			throw new RuntimeException(e.getMessage());

		}
	}
}
