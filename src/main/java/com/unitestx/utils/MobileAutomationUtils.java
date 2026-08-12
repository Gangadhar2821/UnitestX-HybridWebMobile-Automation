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

	public static void clickElement(String xpath) {

		try {
			String normalizedXpath = xpath.toUpperCase();

			// Define supported button texts
			List<String> buttonLabels = Arrays.asList("NEXT", "SUBMIT", "DEDUPE CHECK", "PROCEED");
			for (String label : buttonLabels) {
				if (normalizedXpath.contains(label)) {
					scrollToElementByText(label);
					break; // Exit once matched
				}
			}
			WebElement element = waitForClickable(xpath);
			element.click();
		} catch (Exception e) {
			throw new RuntimeException("Failed to click on mobile element: " + xpath);
		}
	}

	public static void sendKeysToElement(String xpath, String keys) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		int maxScrolls = 5;
		int scrollCount = 0;
		while (scrollCount < maxScrolls) {
			try {
				WebElement element = null;
				try {
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				} catch (Exception e) {

				}
				if (element != null) {
					element.clear();
					element.sendKeys(keys);
					return;
				}
			} catch (NoSuchElementException e) {
			}
			scrollDown();
			scrollCount++;
		}

	}

	public static void waitAndClick(WebElement ele, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(ele)).click();

		} catch (TimeoutException e) {
			scrollDown();
			wait.until(ExpectedConditions.elementToBeClickable(ele)).click();

		}
	}

	/**
	 * @author gangadhar.b
	 * @param eleXpath is the Drop down element
	 * @param data     is the option to be selected from drop down and "any" opts
	 *                 for 1st value
	 * @throws Exception
	 * 
	 */
	public static void selectOption(String eleXpath, String data) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
			WebElement dropdown = null;
			for (int i = 0; i < 10; i++) {
				try {
					dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(eleXpath)));
					MobileAutomationUtils.waitAndClick(dropdown, 4);
					break;
				} catch (Exception e) {
					scrollDown();
					try {
						MobileAutomationUtils.waitAndClick(dropdown, 4);
						break;
					} catch (Exception e2) {
					}
				}
			}

			boolean found = false;
			for (int i = 0; i < 5; i++) {
				try {

					List<WebElement> options = driver
							.findElements(By.xpath("//android.view.ViewGroup//android.widget.TextView"));
					if (options.isEmpty()) {
						options = driver
								.findElements(By.xpath("//android.view.ViewGroup[@content-desc!='Please Select']"));

					}
					if (options.isEmpty()) {
						options = driver.findElements(By.xpath("//android.widget.ListView/android.view.View"));

					}
					for (WebElement option : options) {
						String optText = option.getAttribute("content-desc").trim();
						if (optText == null || optText.trim().isEmpty() || optText.equalsIgnoreCase("null")) {
							optText = option.getAttribute("text").trim();
						}
						if (optText.equalsIgnoreCase(data)) {
							option.click();
							found = true;
							break;
						} else if (!optText.isEmpty() & data.equalsIgnoreCase("any")
								& !optText.contains("Please Select") && !optText.contains("Select")) {
							option.click();
							found = true;
							break;
						}
					}
					if (found)
						break;
				} catch (StaleElementReferenceException se) {

				} catch (Exception e) {
					throw new RuntimeException("Failed to handle the Dropdown!");
				}
				Thread.sleep(200);
			}

			if (!found) {
				log.info("No Options found!");
				throw new RuntimeException();
			}

		} catch (Exception e) {
			throw new RuntimeException();

		}

	}

	/**
	 * Searches for an element and returns the same
	 *
	 * @param xpath XPath of the element.
	 * @return WebElement
	 */
	public static WebElement findElement(String xpath) {
		try {
			return driver.findElement(By.xpath(xpath));
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Element not found. XPath: " + xpath, e);
		}
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

	public static void waitAndClick(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		try {
			int maxScrolls = 5;
			int scrollCount = 0;
			while (scrollCount < maxScrolls) {
				try {
					WebElement ele = null;
					try {
						ele = wait.until(ExpectedConditions.visibilityOf(element));
					} catch (Exception e) {
					}
					if (ele != null) {
						ele.click();
						return;
					}
				} catch (NoSuchElementException e) {
				}
				scrollDown();
				scrollCount++;
			}

		} catch (Exception e) {

		}
	}

	public static void uploadImgOrDoc(WebElement uploadDocsBtn) {
		String cameraIcon = "//android.widget.TextView[@content-desc='camera']";
		String shutterIcon = "//android.widget.ImageView[@content-desc='Shutter']";
		String bottomDoneMark = "//android.widget.ImageButton[@content-desc='Done']";
		// String topDoneMark = "//android.widget.Button[@content-desc='Crop']";

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			waitAndClick(uploadDocsBtn);

			try {
				// Wait for camera icon
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cameraIcon))).click();

				// Wait for shutter icon
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(shutterIcon))).click();

				// Wait for bottom Done button
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(bottomDoneMark))).click();

				// Wait for top Done button (Crop)
				// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(topDoneMark))).click();
			} catch (Exception e) {

			}

			// Handle loader
			handleLoader();

		} catch (Exception e) {
			log.error("Failed to upload Docs/Pics: ", e);
		}
	}

	public static void uploadImage(String xPath) {
		String uploadPhotoIcon = "//android.widget.TextView[contains(@text,'Upload')]";
		String firstImg = "//android.view.View[contains(@content-desc,'Photo taken')]";
		String firstImg1 = "//android.widget.ImageView[@resource-id=\"com.google.android.documentsui:id/icon_thumb\"]";
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // longer timeout

		try {
			WebElement photoIcon = null;
			waitForVisibility(xPath);
			MobileAutomationUtils.clickElement(xPath);
			MobileAutomationUtils.handleLoader();
			photoIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uploadPhotoIcon)));
			if (photoIcon != null) {
				photoIcon.click();
			}
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(firstImg1)));
				clickElement(firstImg1);
			} catch (Exception e) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(firstImg)));
				clickElement(firstImg);
			}
			MobileAutomationUtils.handleLoader();

		} catch (Exception e) {
			log.info("Failed to upload Image");
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

	public static String getTextByXPath(String xpath) {
		try {
			WebElement element;

			try {
				element = driver.findElement(By.xpath(xpath));
			} catch (TimeoutException e) {
				log.info("Element not visible, attempting scroll");
				scrollDown();
				element = driver.findElement(By.xpath(xpath));
			}

			String text = element.getText();

			if (text != null && !text.trim().isEmpty()) {
				return text.trim();
			}

			text = element.getAttribute("content-desc");

			return text != null ? text.trim() : "";

		} catch (Exception e) {
			log.error("Failed to get text", e);
			throw new RuntimeException("Unable to get text from element. XPath: " + xpath, e);
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

	public static WebElement waitForVisibility(String xpath) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting for element to become visible. XPath: " + xpath, e);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Element not found. XPath: " + xpath, e);
		} catch (Exception e) {
			throw new RuntimeException("Failed while waiting for element visibility. XPath: " + xpath, e);
		}
	}

	public static WebElement waitForClickable(String xpath) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting for element to become clickable. XPath: " + xpath, e);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Element not found. XPath: " + xpath, e);
		} catch (Exception e) {
			throw new RuntimeException("Failed while waiting for element clickable. XPath: " + xpath, e);
		}
	}
}
