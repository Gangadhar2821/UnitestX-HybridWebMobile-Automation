package com.unitestx.mobilepages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.unitestx.utils.LoggerUtil;

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
		new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// WebView Page Loacators

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"urlInput\")")
	private WebElement urlInputField;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Go\")")
	private WebElement goBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Clear\")")
	private WebElement clearBtn;

	public void navigateToSite() {
		try {

		} catch (Exception e) {
			log.error("Failed to navigate to site url", e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
