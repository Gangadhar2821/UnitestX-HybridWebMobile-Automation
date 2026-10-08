package com.unitestx.mobilepages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.datagenerators.TestDatagenerator;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ClipBoard_DemoPage_mobile {
	private AndroidDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;

	public ClipBoard_DemoPage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// clipBoard screen locators

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"messageInput\")")
	private WebElement textInputfield;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Set Clipboard Text\")")
	private WebElement setClipboardTextBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Refresh Clipboard Text\")")
	private WebElement refreshClipboardTextBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"clipboardText\")")
	private WebElement clipBoardText;

	private String str;

	public void enterAndSetTextToClipboard() {
		try {
			str = TestDatagenerator.generateUniqueStrings('F');
			MobileAutomationUtils.sendKeysToElement(textInputfield, str);
			MobileAutomationUtils.clickElement(setClipboardTextBtn);
		} catch (Exception e) {
			log.error("Failed to set the text to the clipboard", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void verifyTextSetToClipboard() {
		try {
			MobileAutomationUtils.clickElement(refreshClipboardTextBtn);
			String clipBoardTxt = MobileAutomationUtils.getTextByXPath(clipBoardText);
			Assert.assertEquals(clipBoardTxt, str, "Clipboard Text is not same as the expected text");
			log.info("Clipboard text: " + clipBoardTxt);
		} catch (Exception e) {
			log.error("Failed to verify the text set to the clipboard", e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
