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
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class EchoBoxPage_mobile {

	private AndroidDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;

	public EchoBoxPage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	private String randomString = null;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"messageInput\")")
	private WebElement saySomethingTextfield;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"messageSaveBtn\")")
	private WebElement saveBtn;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"savedMessage\")")
	private WebElement savedMsg;

	public void enterTextAndSave() {
		try {
			randomString = TestDatagenerator.generateString();
			MobileAutomationUtils.sendKeysToElement(saySomethingTextfield, randomString);
			savetext();
		} catch (Exception e) {
			log.error("Failed to enter the text", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void savetext() {
		try {
			MobileAutomationUtils.clickElement(saveBtn);
		} catch (Exception e) {
			log.error("Failed to save the text", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void verifySavedTxt() {
		try {
			String savedMsgg = MobileAutomationUtils.getTextByXPath(savedMsg);
			if (savedMsgg != null) {
				Assert.assertEquals(savedMsgg, randomString, "Failed to verify saved Message");
				log.info("Message Text: " + savedMsgg);
			} else {
				log.warn("Please sapecify the expected message to verify the saved Text");
			}

		} catch (Exception e) {
			log.error("Failed to save the text", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
