package com.unitestx.mobilepages;

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage_mobile extends BaseTest_Mobile {

	private AndroidDriver driver;
	private static LoggerUtil log;
	private WebDriverWait wait;

	public HomePage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// HomePage locators
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Echo Box\")")
	private WebElement echoBoxBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Login Screen\")")
	private WebElement loginBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Clipboard Demo\")")
	private WebElement clipBoardDemoBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Webview Demo\")")
	private WebElement webViewDemoBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"List Demo\")")
	private WebElement listDemoBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Photo Demo\")")
	private WebElement photoDemoBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Geolocation Demo\")")
	private WebElement geolocationDemoBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Picker Demo\")")
	private WebElement pickerDemoBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Verify Phone Number\")")
	private WebElement verifyPhoneNumberBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Echo Screen\")")
	private WebElement echoScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Login\").instance(0)")
	private WebElement loginScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"noClipboardText\")")
	private WebElement clipBoardScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"urlInput\")")
	private WebElement webViewDemoScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Check out these clouds\")")
	private WebElement listDemoScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Photo Library. Tap a photo!\")")
	private WebElement photoDemoScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Latitude: 12.9063633\")")
	private WebElement geoLocationDemoScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Pick a date to learn more about it\")")
	private WebElement pickerDemoScreenTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"waiting\")")
	private WebElement verifyPhoneNumberScreenTitle;

	@AndroidFindBy(accessibility = "Navigate Up")
	private WebElement navigateUpBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"TheApp\")")
	private WebElement theHomeAppTxt;

	// Helper methods

	public void navigateToEchoBoxcreen() {
		try {
			MobileAutomationUtils.clickElement(echoBoxBtn);
			verifyLandingScreen(echoScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to Echo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToClipBoardDemoScreen() {
		try {
			MobileAutomationUtils.clickElement(clipBoardDemoBtn);
			verifyLandingScreen(clipBoardScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to clip Board Demo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToWebViewDemoScreen() {
		try {
			MobileAutomationUtils.clickElement(webViewDemoBtn);
			verifyLandingScreen(webViewDemoScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to web view Demo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToListDemoScreen() {
		try {
			MobileAutomationUtils.clickElement(listDemoBtn);
			verifyLandingScreen(listDemoScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to List Demo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToPhotoDemoScreen() {
		try {
			MobileAutomationUtils.clickElement(photoDemoBtn);
			verifyLandingScreen(photoDemoScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to Photo Demo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToGeoLocationDemoScreen() {
		try {
			MobileAutomationUtils.clickElement(geolocationDemoBtn);
			verifyLandingScreen(geoLocationDemoScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to Geolocation Demo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToPickerDemoScreen() {
		try {
			MobileAutomationUtils.clickElement(pickerDemoBtn);
			verifyLandingScreen(pickerDemoScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to Picker Demo screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToVerifyPhoneNumberScreen() {
		try {
			MobileAutomationUtils.clickElement(verifyPhoneNumberBtn);
			verifyLandingScreen(verifyPhoneNumberScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to Verify Phone Number screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateToLoginScreen() {
		try {
			MobileAutomationUtils.clickElement(loginBtn);
			verifyLandingScreen(loginScreenTitle);
		} catch (Exception e) {
			log.error("Failed to navigate to Login screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void navigateBackToHomeScreen() {
		try {
			MobileAutomationUtils.takescreenshot_And_AppendTo_Path();
			MobileAutomationUtils.clickElement(navigateUpBtn);
			verifyLandingScreen(theHomeAppTxt);
		} catch (Exception e) {
			log.error("Failed to navigate to Login screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void verifyLandingScreen(WebElement element) {
		try {
			String[] expectedFragments = { "Echo Screen", "Login", "(No clipboard text)", "https://appiumpro.com",
					"This is webview '1'", "Check out these clouds", "Photo Library. Tap a photo!",
					"Latitude: 12.9063633", "Pick a date to learn more about it", "waiting", "TheApp" };
			String screenTitle = MobileAutomationUtils.getTextByXPath(element);

			boolean matchFound = Arrays.stream(expectedFragments).anyMatch(screenTitle::contains);

			Assert.assertTrue(matchFound, "Landing Screen not found!" + screenTitle);

		} catch (Exception e) {
			log.error("Failed to Verify landing screen", e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
