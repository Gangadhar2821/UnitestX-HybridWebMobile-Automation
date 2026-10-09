package com.unitestx.mobilepages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WebView_DemoPage_mobile {
	private AndroidDriver driver;
	private LoggerUtil log;
	private WebDriverWait wait;

	public WebView_DemoPage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// WebView Page Loacators

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"urlInput\")")
	private WebElement urlInputField;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Go\")")
	private WebElement goBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Clear\")")
	private WebElement clearBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Error loading page\")")
	private WebElement webPageTxt;

	public void navigateToSite() {
		try {
			MobileAutomationUtils.sendKeysToElement(urlInputField,
					MobileAutomationUtils.getValuefromPropFile("MOBILE_URLDATA"));
			MobileAutomationUtils.clickElement(goBtn);
		} catch (Exception e) {
			log.error("Failed to navigate to site url", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void verifyWebViewText() {
		try {
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(webPageTxt)).isDisplayed());
		} catch (Exception e) {
			log.error("Failed to navigate to site url", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
