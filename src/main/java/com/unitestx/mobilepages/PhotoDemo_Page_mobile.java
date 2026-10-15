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

public class PhotoDemo_Page_mobile {
	private AndroidDriver driver;
	private LoggerUtil log;
	private WebDriverWait wait;

	public PhotoDemo_Page_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// Photo Demo page locators
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(0)")
	private WebElement image1;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(1)")
	private WebElement image2;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(2)")
	private WebElement image3;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(3)")
	private WebElement image4;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(4)")
	private WebElement image5;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(5)")
	private WebElement image6;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/alertTitle\")")
	private WebElement photoSelectedAlert;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/button1\")")
	private WebElement okBtn;

	public void pickPhoto() {
		try {
			MobileAutomationUtils.clickElement(image2);
		} catch (Exception e) {
			log.error("Failed to pick a photo", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void verifyPhotoSelected() {
		try {
			MobileAutomationUtils.waitForVisibility(photoSelectedAlert);
			Assert.assertTrue(photoSelectedAlert.isDisplayed());
			MobileAutomationUtils.takescreenshot_And_AppendTo_Path();
			MobileAutomationUtils.clickElement(okBtn);
		} catch (Exception e) {
			log.error("Failed to pick a photo", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
