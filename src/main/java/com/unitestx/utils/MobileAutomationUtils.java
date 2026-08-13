package com.unitestx.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.unitestx.listener.TestListener;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class MobileAutomationUtils {
	private static AndroidDriver driver;
	private static LoggerUtil log;
	private static WebDriverWait wait;

	public MobileAutomationUtils(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	}

	public static void clickElement(WebElement element) {

		try {
			WebElement readyElement = waitForVisibility(element);
			readyElement.click();

		} catch (Exception e) {
			log.error("Failed to click on: " + element, e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void sendKeysToElement(WebElement element, String keys) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		int maxScrolls = 5;
		int scrollCount = 0;

		while (scrollCount < maxScrolls) {
			try {
				WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));

				if (visibleElement != null) {
					visibleElement.clear();
					visibleElement.sendKeys(keys);
					return;
				}

			} catch (Exception e) {
				// Element not visible yet, continue scrolling
			}

			scrollDown();
			scrollCount++;
		}

		throw new RuntimeException("Failed to enter text into mobile element after " + maxScrolls + " scrolls.");
	}

	public static void handleLoader() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		try {
			boolean loadingStatus = isLoaderPresent();
			while (loadingStatus) {
				log.info("Loading..");
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"Loading...\"]")));
				loadingStatus = isLoaderPresent();
			}
		} catch (Exception e) {

		}
	}

	public static boolean isLoaderPresent() {
		try {
			List<WebElement> loaders = driver.findElements(By.xpath("//android.widget.TextView[@text=\"Loading...\"]"));
			return !loaders.isEmpty();
		} catch (Exception e) {

			return false;
		}
	}

	public static WebElement scrollToElementByText(String text) {
		WebElement element = null;
		try {
			element = driver
					.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
							+ ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"));
			Thread.sleep(1000);
		} catch (Exception e) {
			System.err.println("Failed to scroll to element: " + e.getMessage());
		}
		return element; // returns the element after scrolling
	}

	public static void scrollDown() {
		Dimension size = driver.manage().window().getSize();
		int startX = size.width / 2;
		int startY = (int) (size.height * 0.7);
		int endY = (int) (size.height * 0.3);

		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);

		swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), startX, endY));
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Collections.singletonList(swipe));

		// Optional: small wait after scroll
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	public static String getValuefromPropFile(String key) {
		String value = null;
		try {
			Properties properties = new Properties();
			FileReader file = new FileReader(System.getProperty("user.dir") + "\\configs\\app.properties");
			properties.load(file);
			value = properties.getProperty(key);
		} catch (Exception e) {
			log.error("Failed to read property from file : " + key + " ", e);
		}
		return value;
	}

	public static String getRuntimeValue(String key) {
		String value = null;
		try {
			Properties properties = new Properties();
			FileReader file = new FileReader(System.getProperty("user.dir") + "\\glowruntimedata.properties");
			properties.load(file);
			value = properties.getProperty(key);
		} catch (Exception e) {
			log.error("Failed to fetch the Runtime Value", e);
		}
		return value;
	}

	public static String getTextByXPath(WebElement element) {
		try {

			String text = element.getText();

			if (text != null && !text.trim().isEmpty()) {
				return text.trim();
			}

			text = element.getAttribute("content-desc");

			return text != null ? text.trim() : "";

		} catch (Exception e) {
			log.error("Failed to get text", e);
			throw new RuntimeException("Unable to get text from element. XPath: " + element, e);
		}
	}

	public void attachScreenshot(WebDriver driver, String screenshotName) {

		ExtentTest test = TestListener.getTest();

		if (test != null) {
			try {

				String path = captureScreenshot(driver, screenshotName);
				log.info("Screenshot Path: " + path);

				System.out.println("Attaching screenshot: " + path);
				test.addScreenCaptureFromPath(path);

			} catch (Exception e) {
				log.error("Failed to attach screenshot", e);
			}
		}
	}

	public static String captureScreenshot(WebDriver driver, String testName) {
		File folder = new File(System.getProperty("user.dir") + "/screenshots/");
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_"
				+ System.currentTimeMillis() + ".png";

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(srcFile, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenshotPath;
	}

	public static void toastHighlighter() {
		WebElement toastmsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid red'", toastmsg);
	}

	public static WebElement waitForVisibility(WebElement element) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting for element to become visible. XPath: " + element, e);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Element not found. XPath: " + element, e);
		} catch (Exception e) {
			throw new RuntimeException("Failed while waiting for element visibility. XPath: " + element, e);
		}
	}

	public static WebElement waitForClickable(WebElement element) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting for element to become clickable. XPath: " + element, e);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Element not found. XPath: " + element, e);
		} catch (Exception e) {
			throw new RuntimeException("Failed while waiting for element clickable. XPath: " + element, e);
		}
	}
}
