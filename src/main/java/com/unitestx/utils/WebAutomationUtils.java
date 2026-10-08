package com.unitestx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.unitestx.driverfactory.WebDriverFactory;
import com.unitestx.listener.TestListener;

public class WebAutomationUtils {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static LoggerUtil log;
	public static String toastMessage;
	public static String methodName;

	// Constructor
	public WebAutomationUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		log = new LoggerUtil();
	}

	public static String getTomorrowDate() {
		// Define the formatter with the desired pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH);

		// Get today's date
		LocalDate today = LocalDate.now();

		// Add one day
		LocalDate tomorrow = today.plusDays(1);

		// Format tomorrow's date
		return tomorrow.format(formatter);
	}

	public static void clear_database_testdata() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			log.info("JDBC Driver loaded Successfully");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("SQL Server JDBC Driver not found!", e);
		}
		DBUtil.runQuery("//SQL QUERY//");
		DBUtil.runQuery("//SQL QUERY//");
		DBUtil.runQuery("//SQL QUERY//");
		DBUtil.runQuery("//SQL QUERY//");
		DBUtil.runQuery("//SQL QUERY//");

		// log
		log.info("Database queries to reset the testdata were executed successfully");
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

	public static void verifyLandingPage() {
		String currentUrl = driver.getCurrentUrl().trim();
		String[] expectedFragments = { "", "" };

		boolean matchFound = Arrays.stream(expectedFragments).anyMatch(currentUrl::contains);

		Assert.assertTrue(matchFound, "Landing Page not found!" + currentUrl);

	}

	public static String extractTextFromxPath(String xpath) {
		Pattern pattern = Pattern.compile("text\\(\\)\\s*=\\s*['\"]([^'\"]+)['\"]");
		Matcher matcher = pattern.matcher(xpath);

		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	public static void click(String xPath) {
		for (int attempt = 1; attempt <= 3; attempt++) {
			try {
				// Wait for element to be visible
				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));

				// Scroll into view
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

				// Wait until clickable and click
				wait.until(ExpectedConditions.elementToBeClickable(element)).click();

				log.info("Clicked on element: " + extractTextFromxPath(xPath) + " button");

				return;

			} catch (TimeoutException te) {
				log.warn("Timeout on click attempt {}/3 for: {}" + attempt);
			} catch (ElementClickInterceptedException ice) {
				log.warn("Click intercepted on attempt {}/3 for: {}" + attempt);
			} catch (Exception e) {
				log.warn("Unexpected error on click attempt {}/3 for: {}" + attempt);
			}
		}

		// JS fallback if all attempts fail
		try {
			WebElement element = driver.findElement(By.xpath(xPath));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

			log.info("Clicked on element using js click: " + extractTextFromxPath(xPath) + " button");

		} catch (Exception e) {
			log.error("Click failed for XPath: {}" + xPath, e);
			throw new RuntimeException("Click failed for XPath: " + xPath, e);
		}
	}

	public static void jsClick(String xPath) {
		// JS fallback if all attempts fail
		try {
			WebElement element = driver.findElement(By.xpath(xPath));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

			log.info("Clicked on element: " + extractTextFromxPath(xPath) + " button");

		} catch (Exception e) {
			log.error("Click failed for XPath: {}" + xPath, e);
			throw new RuntimeException("Click failed for XPath: " + xPath, e);
		}
	}

	/**
	 * @author gangadhar.b
	 * @param elementxPath in type String
	 * @param data         option to be selected in type String
	 */
	public static void handleDynamicDropdown(String elementxPath, String data) {
		try {
			WebAutomationUtils.click(elementxPath);
			List<WebElement> allOptions = null;
			if (allOptions == null) {
				allOptions = driver.findElements(By.xpath("//div[@role='option']/span"));
				if (allOptions.isEmpty()) {
					allOptions = driver.findElements(By.xpath("//span[contains(text(),'" + data + "')]"));
				}
				if (allOptions.isEmpty()) {
					allOptions = driver.findElements(By.xpath("//div[@data-dropdown-panel='true']//button/span"));
				}
				if (allOptions.isEmpty()) {
					List<WebElement> empty = driver
							.findElements(By.xpath("//div[@data-dropdown-panel='true']//p[text()='No results found']"));
					if (empty.size() >= 1) {
						return;
					}
				}
				if (!allOptions.isEmpty()) {
					for (WebElement ele : allOptions) {
						String text = ele.getText().trim();
						if (text.equals(data) || text.contains(data)) {
							ele.click();
							break;
						} else {
							ele.click();
							break;
						}
					}
				} else {
					return;
				}
			}
		} catch (Exception e) {
			log.error("Failed to select the dropdown option", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	// Type text into input field with robust handling
	public static void sendKeys(String xPath, String text) {
		WebElement element = null;
		try {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
			} catch (Exception e) {
				scrollToElement(xPath);
				element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
			}

			try {
				// Attempt to clear normally
				element.clear();
			} catch (InvalidElementStateException ex) {
				// Fallback: select all and delete
				element.sendKeys(Keys.CONTROL + "a");
				element.sendKeys(Keys.DELETE);
			}

			element.sendKeys(text);

		} catch (Exception e) {
			log.error("SendKeys failed on: " + xPath + " | " + e.getMessage(), e);
		}
	}

	// Get text from element
	public static String getText(String xPath) {
		try {
			scrollToElement(xPath);
			WebElement element = driver.findElement(By.xpath(xPath));
			return wait.until(ExpectedConditions.visibilityOf(element)).getText();
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
			scrollToElement(xPath);
			WebElement element = driver.findElement(By.xpath(xPath));
			return element.getAttribute(attribute);
		} catch (Exception e) {
			log.error("GetAttribute failed on: " + xPath + " | " + e.getMessage(), e);
			return null;
		}
	}

	public static String getValuefromPropFile(String key) {
		String value = null;
		try {
			Properties properties = new Properties();
			FileReader file = new FileReader(System.getProperty("user.dir") + "\\configs\\glowconfig.properties");
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
			FileReader file = new FileReader(System.getProperty("user.dir") + "\\configs\\glowruntimedata.properties");
			properties.load(file);
			value = properties.getProperty(key);
		} catch (Exception e) {
			log.error("Failed to fetch the Runtime Value", e);
		}
		return value;
	}

	public static void handleLoader() {
		try {
			String loaderXpath = "//img[@data-testid='loaderImage']";
			boolean loadingStatus = isLoaderPresent();
			while (loadingStatus) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loaderXpath)));
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

	public static void handleToaster() {
		String toastXpath = "//div[contains(@data-testid,'toast')]";

		try {
			// Wait until toast container is visible
			WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(toastXpath)));

			// Capture the entire text from the toast container
			String toastMsg = toastElement.getText().trim();
			log.info("Toast Message: " + toastMsg);

			// Save message for later validation
			toastMessage = toastMsg;
			if (toastMsg.toLowerCase().contains("login") || toastMsg.toLowerCase().contains("success")) {
				toastHighlighterOnPass();
				takescreenshot_And_AppendTo_Path();
			}
			// handle toast
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(toastXpath)));

		} catch (TimeoutException te) {
			log.error("Toast message not visible within timeout", te);
		} catch (Exception e) {
			log.error("Unexpected error while handling toast", e);
		}
	}

	public static void takescreenshot_And_AppendTo_Path() {
		try {
			TestListener.screenshotPath = WebAutomationUtils
					.captureScreenshot(WebDriverFactory.getInstance().getCurrentDriver(), methodName);
		} catch (Exception e) {
			log.error("Failed to capture Screenshot", e);

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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		String postLoginToastmsg = "//div[contains(@data-testid,'toast')]";
		WebElement toastmsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(postLoginToastmsg)));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid green'", toastmsg);
	}

	public static void toastHighlighterOnFail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		String postLoginToastmsg = "//div[contains(@data-testid,'toast')]";
		WebElement toastmsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(postLoginToastmsg)));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid red'", toastmsg);
	}

	public static void saveToProperty(String key, String value) {
		try {
			Properties props = new Properties();
			String filePath = System.getProperty("user.dir") + "\\configs\\glowruntimedata.properties";
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

	/**
	 * @author gangadhar.b
	 * @param calenderIcon xpath in String
	 * @param date         in the format of DD/MMM/YYYY eg: 09/Oct/2026
	 */
	public static void handleCalender(String calenderIcon, String date) {
		try {
			String yearBtn = "//button[contains(text(),'20')]";
			String monthBtn = "//button[contains(text(),'20')]/parent::div/preceding-sibling::div/button";
			String yearOpts = "(//button[contains(text(),'20')])[2]/parent::div//button";
			String monthOpts = "//button[contains(text(),'20')]/parent::div/preceding-sibling::div/button/parent::div//div[contains(@class,'dropdown')]/button";
			WebAutomationUtils.click(calenderIcon);
			String[] split = date.trim().split("/");
			String daydata = split[0];
			String monthdata = split[1];
			String yeardata = split[2];

			// picking year
			WebAutomationUtils.click(yearBtn);
			List<WebElement> years = driver.findElements(By.xpath(yearOpts));
			for (WebElement ele : years) {
				String actualyear = ele.getText().trim();
				if (yeardata.equals(actualyear)) {
					ele.click();
					break;
				}
			}

			// picking month
			WebAutomationUtils.click(monthBtn);
			List<WebElement> months = driver.findElements(By.xpath(monthOpts));
			for (WebElement ele : months) {
				String actualMonth = ele.getText().trim();
				if (actualMonth.contains(monthdata)) {
					ele.click();
					break;
				}
			}

			// selecting date
			WebAutomationUtils.click("//button[text()='" + daydata + "']");

		} catch (Exception e) {
			log.error("Failed to handle calender input", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void pressEsckey() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE);
	}
}
