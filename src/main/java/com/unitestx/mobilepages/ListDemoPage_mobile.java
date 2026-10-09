package com.unitestx.mobilepages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ListDemoPage_mobile {
	private AndroidDriver driver;
	private LoggerUtil log;
	private WebDriverWait wait;

	public ListDemoPage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// List Demo page locators
	@AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.TextView")
	private List<WebElement> visibleOpts;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/button1\")")
	private WebElement okBtn;

	public List<String> captureAllOptions() {
		Set<String> allOptions = new LinkedHashSet<>();
		try {

			while (true) {

				List<WebElement> visibleOptions = driver
						.findElements(By.xpath("//android.widget.ScrollView//android.widget.TextView"));

				String lastVisibleText = visibleOptions.get(visibleOptions.size() - 1).getText();

				visibleOptions.stream().map(WebElement::getText).map(String::trim).forEach(allOptions::add);

				if ("Stratus".equals(lastVisibleText)) {
					break;
				}

				MobileAutomationUtils.scrollDown();
			}

			return new ArrayList<>(allOptions);

		} catch (Exception e) {
			log.error("Failed to capture all the options", e);
			throw new RuntimeException(e.getMessage());
		}

	}

	public void selectListOption() {

		try {
			List<String> options = captureAllOptions();
			for (String opt : options) {
				if (opt.equalsIgnoreCase("RedHat")) {
					MobileAutomationUtils.clickElement(MobileAutomationUtils.findElement(
							"//android.widget.TextView[@resource-id=\"listItemTitle\" and @text='" + opt + "']"));
				}
			}

		} catch (Exception e) {
			log.error("Failed to perform option selection", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void verifyOptionSelection() {
		try {
			String messageXpath = "//android.widget.TextView[@resource-id=\"android:id/message\"]";
			WebElement successMsg = MobileAutomationUtils.findElement(messageXpath);
			String actualTxt = MobileAutomationUtils.getTextByXPath(successMsg);
			Assert.assertTrue(actualTxt.toLowerCase().contains("congratulations"));
			MobileAutomationUtils.clickElement(okBtn);
		} catch (Exception e) {
			log.error("Failed to verify option selection", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
