package com.unitestx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.unitestx.listener.TestListener;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class MobileAutomationUtils {
	private static AndroidDriver driver;
	private static LoggerUtil log;
	private static WebDriverWait wait;
	public static String toastMessage;
	public static String methodName;

	public MobileAutomationUtils(AndroidDriver driver) {
		this.driver = driver;
		log = new LoggerUtil();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	}

	// Method to get Test Data from Excel based on Column Name & Row Name for Data

	public static String getTestData(String testCaseID, String columnName) throws IOException {
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\UnitestX_Testdata.xlsx");
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheet("SuperSheet");
		Row headerRow = sheet.getRow(0);
		int colIndex = -1;
		for (Cell cell : headerRow) {
			if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName.trim())) {
				colIndex = cell.getColumnIndex();
				break;
			}
		}
		if (colIndex == -1) {
			workbook.close();
			throw new IllegalArgumentException("Column '" + columnName + "' not found in Excel.");
		}
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell idCell = row.getCell(0);
				if (idCell != null && idCell.getStringCellValue().equalsIgnoreCase(testCaseID)) {
					String value = row.getCell(colIndex).getStringCellValue();
					workbook.close();
					return value;
				}
			}
		}
		workbook.close();
		throw new IllegalArgumentException("Test case ID '" + testCaseID + "' not found in Excel.");
	}

	public static void handleToaster() {
		String toastXpath = "//android.widget.TextView[@content-desc='check-circle-outline']/following-sibling::android.widget.TextView[1]";

		try {
			// Wait until toast container is visible
			WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(toastXpath)));

			// Capture the entire text from the toast container
			String toastMsg = toastElement.getText().trim();
			log.info("Toast Message: " + toastMsg);

			// Save message for later validation
			toastMessage = toastMsg;

			// Capture the toast message
			takescreenshot_And_AppendTo_Path();

			// handle toast
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(toastXpath)));

		} catch (TimeoutException te) {
			log.error("Toast message not visible within timeout", te);
		} catch (Exception e) {
			log.error("Unexpected error while handling toast", e);
		}
	}

	public static void clickElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		int maxAttempts = 5;

		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				return; // success
			} catch (TimeoutException e) {
				if (attempt < maxAttempts) {
					scrollDown(); // prepare for next attempt
				}
			} catch (Exception e) {
				throw new RuntimeException("Failed to click on mobile element: ");
			}
		}

		throw new RuntimeException("Failed to click on mobile element after " + maxAttempts + " attempts: ");
	}

	public static void sendKeysToElement(WebElement element, String keys) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		int maxScrolls = 5;
		int scrollCount = 0;
		while (scrollCount < maxScrolls) {
			try {
				try {
					element = wait.until(ExpectedConditions.visibilityOf(element));
				} catch (Exception e) {

				}
				if (element != null) {
					element.click();
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

	public static void switchToWebView(AndroidDriver driver) {
		try {
			String webViewContext = "WEBVIEW_com.appiumpro.the_app";

			for (String context : driver.getContextHandles()) {
				if (context.contains("WEBVIEW")) {
					driver.context(context);
					log.info("Driver switched to context: " + webViewContext);
					return;
				}
			}
		} catch (Exception e) {
			log.error("Failed to switch to Webview context", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void switchToNative(AndroidDriver driver) {
		String nativeContext = "NATIVE_APP";

		try {
			driver.context("NATIVE_APP");
			log.info("Driver switched to context: " + nativeContext);

		} catch (Exception e) {
			log.error("Failed to switch to Native context", e);
			throw new RuntimeException(e.getMessage());
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
	 * Searches for an element and returns null if not found
	 * 
	 * @param xpath XPath of the element.
	 * @return WebElement
	 * @throws InterruptedException
	 */
	public static WebElement findElement(String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
			return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (TimeoutException e) {
			throw new RuntimeException("Element not found within timeout. XPath: " + xpath, e);
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
			checkForErrorMessage();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
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
			FileReader file = new FileReader(System.getProperty("user.dir") + "\\configs\\appruntimedata.properties");
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
			return "";
		}
	}

	public static void saveToProperty(String key, String value) {
		try {
			Properties props = new Properties();
			String filePath = System.getProperty("user.dir") + "\\configs\\appruntimedata.properties";
			File file = new File(filePath);

			// Load existing properties if file already exists
			if (file.exists()) {
				FileInputStream input = new FileInputStream(file);
				props.load(input);
				input.close();
			}

			// Add or update the key-value
			props.setProperty(key, value);

			// Save back to file
			FileOutputStream output = new FileOutputStream(file);
			props.store(output, "Updated at runtime");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void attachScreenshot(AndroidDriver driver, String screenshotName) {

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

	public static String captureScreenshot(AndroidDriver driver, String testName) {
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

	public static WebElement waitForVisibility(WebElement element) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting for element to become visible. XPath: ");
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Element not found. XPath: ");
		} catch (Exception e) {
			throw new RuntimeException("Failed while waiting for element visibility. XPath: ");
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

	private static final String ERROR_MSG_XPATH = "//android.widget.ScrollView//android.widget.TextView";

	private static final List<String> KNOWN_ERRORS = List.of("User not found", "Password must be",
			"Do Not Update Last Password", "Bad Gateway");

	public static void checkForErrorMessage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
			List<WebElement> elements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(ERROR_MSG_XPATH)));

			if (elements.isEmpty()) {
				return; // No error popup displayed
			} else {
				String errorMessage = elements.get(0).getText().trim();
				if (KNOWN_ERRORS.stream().anyMatch(errorMessage::contains)) {
					log.info("Found an Application error");
					log.info("ERROR MESSAGE : " + errorMessage);
					takescreenshot_And_AppendTo_Path();
					throw new RuntimeException("Test execution failed due to an error");
				}
			}

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			log.error("Failed while validating application error popup", e);
			throw new RuntimeException("Failed while validating application error popup", e);
		}
	}

	public static void takescreenshot_And_AppendTo_Path() {
		try {
			TestListener.screenshotPath = MobileAutomationUtils.captureScreenshot(driver, methodName);
		} catch (Exception e) {
			log.error("Failed to capture Screenshot", e);

		}
	}

}
