package com.unitestx.mobilepages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class DatePickerDemoPage_mobile {
	private AndroidDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;

	public DatePickerDemoPage_mobile(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// Date picker page locators
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"January\")")
	private WebElement monthDD;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"1\")")
	private WebElement dateDD;

	public void pickDatefromSelectedMonth() {
		try {

			String requiredMonth = "November";
			String requiredDate = "14";

			// Pick month
			MobileAutomationUtils.clickElement(monthDD);
			List<WebElement> allMonths = driver.findElements(By.xpath(
					"//android.widget.FrameLayout[@resource-id='com.appiumpro.the_app:id/contentPanel']//android.widget.CheckedTextView"));
			for (WebElement mon : allMonths) {
				String actMonth = mon.getText().trim();
				if (actMonth.equalsIgnoreCase(requiredMonth)) {
					MobileAutomationUtils.clickElement(mon);
					break;
				}
			}

			// Pick date
			MobileAutomationUtils.clickElement(dateDD);
			List<WebElement> allDates = driver.findElements(By.xpath(
					"//android.widget.FrameLayout[@resource-id='com.appiumpro.the_app:id/contentPanel']//android.widget.CheckedTextView"));
			for (WebElement date : allDates) {
				String actdate = date.getText().trim();
				if (actdate.equalsIgnoreCase(requiredDate)) {
					MobileAutomationUtils.clickElement(date);
					break;
				}
			}
			MobileAutomationUtils.takescreenshot_And_AppendTo_Path();
		} catch (Exception e) {
			log.error("Failed to pick date ", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
