package com.unitestx.mobilepages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class GeoLocationDemoPage_mobile {

	private AndroidDriver driver;
	private LoggerUtil log;
	private WebDriverWait wait;

	public GeoLocationDemoPage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// Geo location page Loacators

	@AndroidFindBy(uiAutomator = "")
	private WebElement latitudeTxt;

	@AndroidFindBy(uiAutomator = "")
	private WebElement longitude;

	public void verifylatitudeValue() {
		try {

			String actualTxt = MobileAutomationUtils.getTextByXPath(latitudeTxt);
			String expectedTxt = "12.9063633";
			Assert.assertTrue(actualTxt.contains(expectedTxt), "Expected latitude value not found");
			MobileAutomationUtils.takescreenshot_And_AppendTo_Path();

		} catch (Exception e) {
			log.error("Failed to verify latitude value", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
