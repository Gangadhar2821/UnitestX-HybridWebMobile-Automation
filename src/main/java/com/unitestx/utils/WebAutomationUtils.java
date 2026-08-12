package com.unitestx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.unitestx.listener.TestListener;

public class WebAutomationUtils {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static LoggerUtil log;

	// Constructor
	public WebAutomationUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		log = new LoggerUtil();
	}

	// Method to get Test Data from Excel based on Column Name & Row Name for Data

	public static String getTestData(String testCaseID, String columnName) throws IOException {
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\Nimble_Glow_TestData.xlsx");
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

	public static void verifyLandingPage() {
		String currentUrl = driver.getCurrentUrl().trim();
		String[] expectedFragments = { "bank-configuration", "branch-configuration", "holiday-calendar",
				"password-configuration", "mobile-app-configuration", "mobile-admin-panel", "user-maintenance",
				"role-maintenance", "user-role-mapping", "product-mapping", "bucket-configuration",
				"write-off-recovery", "overdue-assignment", "user-delegation", "agency-master", "agency-mapping",
				"roles-management", "audit-logs", "dashboard", "login", "nimbleglowqa" };

		boolean matchFound = Arrays.stream(expectedFragments).anyMatch(currentUrl::contains);

		Assert.assertTrue(matchFound, "Landing Page not found!" + currentUrl);

	}

	// Click element with retry and JS fallback
	public static void click(String xPath) {
		int retries = 3;
		int attempt = 0;

		while (attempt < retries) {
			try {
				WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
				element.click();
				log.info("Clicked element with XPath: {}" + xPath);
				return; // success, exit method
			} catch (TimeoutException te) {
				log.warn("Attempt {}: Element not clickable within timeout: {}" + xPath);
			} catch (Exception e) {
				log.warn("Attempt {}: Standard click failed for XPath: {}" + xPath);
			}
			attempt++;
		}

		// Final fallback: JavaScript click
		try {
			WebElement element = driver.findElement(By.xpath(xPath));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			log.info("Clicked element via JavaScript fallback: {}" + xPath);
		} catch (Exception jsEx) {
			log.error("JS click also failed for XPath: {}" + xPath, jsEx);
			throw new RuntimeException("Click failed after retries and JS fallback for XPath: " + xPath, jsEx);
		}
	}

	// Type text into input field
	public static void sendKeys(String xPath, String text) {
		WebElement element = null;
		try {
			try {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
			} catch (Exception e) {
				scrollToElement(xPath);
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
			}

			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			log.error("SendKeys failed on: " + xPath + " | " + e.getMessage(), e);
		}
	}

	// Get text from element
	public static String getText(String xPath) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath))).getText();
		} catch (Exception e) {
			log.error("GetText failed on: " + xPath + " | " + e.getMessage(), e);
			return null;
		}
	}

	// Check if element is displayed
	public static boolean isDisplayed(String xPath) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath))).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Wait for element to be visible
	public static WebElement waitForVisibility(String xPath) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		} catch (Exception e) {
			log.error("WaitForVisibility failed on: " + xPath + " | " + e.getMessage(), e);
			return null;
		}
	}

	// Wait for element to be visible
	public static Boolean waitForInVisibility(String xPath) {
		try {
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
		} catch (Exception e) {
			log.error("WaitForVisibility failed on: " + xPath + " | " + e.getMessage(), e);
			return null;
		}
	}

	// Wait for element to be clickable
	public static WebElement waitForClickable(String xPath) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
		} catch (Exception e) {
			log.error("WaitForClickable failed on: " + xPath + " | " + e.getMessage(), e);
			return null;
		}
	}

	// Get attribute value
	public static String getAttribute(String xPath, String attribute) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath))).getAttribute(attribute);
		} catch (Exception e) {
			log.error("GetAttribute failed on: " + xPath + " | " + e.getMessage(), e);
			return null;
		}
	}

	public static void handleDropdown(String eleXpath, String data) {
		try {

			WebAutomationUtils.handleLoader();
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(eleXpath)));
			} catch (Exception e) {
				scrollToElement(eleXpath);
				WebAutomationUtils.click(eleXpath);
			}
			WebAutomationUtils.click(eleXpath);

			boolean found = false;
			for (int i = 0; i < 5; i++) {
				try {
					List<WebElement> options = driver
							.findElements(By.xpath("//span[@class='mdc-list-item__primary-text']"));
					if (options.isEmpty()) {
						options = driver.findElements(By.xpath("//span[@class='mdc-list-item__primary-text']"));
					}
					if (options.isEmpty()) {
						options = driver.findElements(By.xpath(eleXpath + "/option"));
					}
					for (WebElement option : options) {
						String optText = null;
						try {
							optText = option.findElement(By.xpath("./span")).getText().trim();
						} catch (Exception e) {
							log.info("No Text found for dropdown Option");
						}
						if (optText == null || optText.isEmpty()) {
							optText = option.getText().trim();
						}
						if (optText.contains(data)) {
							option.click();
							found = true;
							break;
						} else if (!optText.isEmpty() & data.equalsIgnoreCase("any")) {
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

	public static String getValuefromPropFile(String key) {
		String value = null;
		try {
			Properties properties = new Properties();
			FileReader file = new FileReader(System.getProperty("user.dir") + "\\propertyfiles\\glowconfig.properties");
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
			FileReader file = new FileReader(
					System.getProperty("user.dir") + "\\propertyfiles\\glowruntimedata.properties");
			properties.load(file);
			value = properties.getProperty(key);
		} catch (Exception e) {
			log.error("Failed to fetch the Runtime Value", e);
		}
		return value;
	}

	public static void handleLoader() {
		try {
			boolean loadingStatus = isLoaderPresent();
			while (loadingStatus) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));
				loadingStatus = isLoaderPresent();
			}
		} catch (Exception e) {

		}
	}

	public static boolean isLoaderPresent() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
		try {
			Thread.sleep(200);
		} catch (Exception e) {
		}
		List<WebElement> loader = driver.findElements(By.xpath("//img[@alt='Loading...']"));
		if (!loader.isEmpty()) {
			return true;
		}

		return false;
	}

	public static WebElement FindElementByStringXpath(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = null;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

		} catch (Exception e) {
		}
		return element;
	}

	public static void scrollAndClick(String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

			// Wait until element is clickable
			wait.until(ExpectedConditions.elementToBeClickable(element));
			try {
				element.click();
				Thread.sleep(1000);
			} catch (Exception clickEx) {
				// Fallback to JS click if normal click fails
				js.executeScript("arguments[0].click();", element);
				Thread.sleep(1000);
			}
		} catch (Exception e) {

		}
	}

	public static void scrollToElement(String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

		} catch (Exception e) {
			log.error("Scroll failed for xpath: " + xpath + " | Error: ", e);
		}
	}

	public static String generateName(char ch) {
		String[] FIRST_NAMES = { "Aarav", "Vivaan", "Aditya", "Sai", "Ishaan", "Krishna", "Ananya", "Diya", "Aisha",
				"Saanvi", "Pranav", "Rohit", "Karthik", "Vikram", "Neha", "Pooja", "Sneha", "Riya", "Harsha", "Tejas",
				"James", "Oliver", "Ethan", "Liam", "Noah", "Emma", "Olivia", "Ava", "Sophia", "Mia", "Lucas",
				"Benjamin", "Charlotte", "Amelia", "Isabella", "Henry", "Jack", "Emily", "Grace", "Chloe" };

		String[] LAST_NAMES = { "Sharma", "Verma", "Gupta", "Mehta", "Reddy", "Iyer", "Menon", "Patel", "Khan", "Singh",
				"Nair", "Bhat", "Kulkarni", "Deshpande", "Jain", "Kapoor", "Mishra", "Chatterjee", "Mukherjee",
				"Saxena", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez",
				"Martinez", "Wilson", "Anderson", "Taylor", "Thomas", "Moore", "Jackson", "White", "Harris", "Thompson",
				"Martin" };

		String[] MIDDLE_NAMES = { "Kumar", "Prasad", "Raj", "Singh", "Anand", "Rao", "Chandra", "Mohan", "Lal", "Dev",
				"James", "Lee", "Marie", "Grace", "Rose", "Ann", "John", "Paul", "Ray", "Jane", };

		String[] ADDRESSES = { "Indiranagar", "Koramangala", "Whitefield", "Hebbal", "Jayanagar", "HSR", "BTM",
				"Marathahalli", "Yelahanka", "Malleshwaram", "Richmond", "Ulsoor", "Vasanthnagar", "Rajajinagar",
				"Banashankari", "Domlur", "Sarjapur", "ElectronicCity", "Bellandur", "Kengeri" };

		String[] REMARKS = { "Approved", "OK", "Accepted", "Incomplete", "Pass" };
		String[] STATUS = { "Active", "Inactive" };
		String[] VEHICLETYPE = { "3 Wheeler", "4 Wheeler", "2 Wheeler" };

		String[] IFSC = { "SBIN0000813", "SBIN0000814", "KARB0000815", "KARB0000526" };

		SecureRandom random = new SecureRandom();
		String firstname = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String middlename = MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
		String lastname = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		String address = ADDRESSES[random.nextInt(ADDRESSES.length)];
		String remarks = REMARKS[random.nextInt(REMARKS.length)];
		String status = STATUS[random.nextInt(STATUS.length)];
		String vechicle = VEHICLETYPE[random.nextInt(VEHICLETYPE.length)];
		String ifscCodes = IFSC[random.nextInt(IFSC.length)];

		switch (ch) {
		case 'F':
			return firstname;
		case 'M':
			return middlename;
		case 'L':
			return lastname;
		case 'A':
			return address;
		case 'R':
			return remarks;
		case 'S':
			return status;
		case 'V':
			return vechicle;
		case 'I':
			return ifscCodes;
		default:
			return "Invalid choice!";
		}

	}

	public static String generateRandomNumber(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Number of digits must be greater than 0");
		}
		StringBuilder sb = new StringBuilder();
		java.util.Random random = new java.util.Random();

		// First digit should not be zero (to ensure n digits)
		sb.append(random.nextInt(9) + 1);

		// Remaining digits can be 0-9
		for (int i = 1; i < n; i++) {
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}

	public static void handleAlert() {
		String alert = "//div[@role='alert']";
		WebElement alertt = FindElementByStringXpath(alert);
		if (alertt != null) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(alert)));
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

	public static void toastHighlighterOnPass() {
		String postLoginToastmsg = "//div[@role='alert']";
		WebElement toastmsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(postLoginToastmsg)));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid green'", toastmsg);
	}

	public static void toastHighlighterOnFail() {
		String postLoginToastmsg = "//div[@role='alert']";
		WebElement toastmsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(postLoginToastmsg)));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid red'", toastmsg);
	}
}
