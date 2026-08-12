package com.unitestx.mobilepages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class RegistrationPage_Mobile {

	private AndroidDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;

	public RegistrationPage_Mobile(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		log = new LoggerUtil();

	}

	// Registartion page locators

	private String institutionIdFirstFieldbox = "(//android.widget.EditText[@text=\"-\"])[1]";
	private String challengCodeFirstFieldbox = "(//android.widget.EditText[@text=\"-\"])[4]";
	private String nextBtn = "//android.view.ViewGroup[@content-desc=\"Next\"]";
	private String moreInforBtn = "//android.widget.TextView[@text=\"More Info\"]";
	private String appDetailsBtn = "//android.view.ViewGroup[@content-desc=\"App Details\"]";
	private String appInstanceBtn = "//android.view.ViewGroup[@content-desc=\"App Instance\"]";
	private String qaInstanceRadio = "//android.widget.RadioButton[@content-desc=\"QA\"]/android.view.ViewGroup";
	private String uatInstanceRadio = "//android.widget.RadioButton[@content-desc=\"UAT\"]/android.view.ViewGroup";
	private String moreInfoCloseBtn = "//android.widget.TextView[@content-desc=\"close\"]";
	private String SuccessTxtXpath = "//android.widget.TextView[@text=\"Registered Successfully\"]";
	private String OKBtn = "//android.view.ViewGroup[@content-desc=\"OK\"]";
	private String oKBtn = "//android.widget.TextView[@text=\"Ok\"]";
	private String enterUserIdTxt = "//android.widget.EditText[@content-desc=\"USER ID\"]";
	private String registerBtn = "//android.view.ViewGroup[@content-desc=\"Register\"]";
	private String registeredWithAnotherDeviceTxt = "//android.widget.TextView[@text=\"Another device is already registered for this user. Do you want to continue?\"]";
	private String alreadyExistAndActivatedTxt = "//android.widget.TextView[@text=\"User Already Existed And Activated\"]";
	private String yesBtn = "//android.widget.TextView[@text=\"Yes\"]";
	private String enterActivationCodeFirstfieldBox = "(//android.widget.EditText[@text=\"-\"])[1]";
	private String activateBtn = "//android.view.ViewGroup[@content-desc=\"Activate\"]";
	private String verifyBtn = "//android.widget.TextView[@text=\"VERIFY\"]";
	private String deviceRegSuccessTxtXpath = "//android.widget.TextView[@text=\"Device registered successfully\"]";

	public void enterInstitutionID() throws IOException {
		try {
			String institutionID = MobileAutomationUtils.getValuefromPropFile("InstitutionID");
			MobileAutomationUtils.waitForVisibility(institutionIdFirstFieldbox);
			MobileAutomationUtils.clickElement(institutionIdFirstFieldbox);
			enterCode(institutionID);
			log.info("Entered Institution ID");
		} catch (InterruptedException e) {
			log.error("Failed to enter the Institution ID", e);
			throw new RuntimeException(e);
		}
	}

	public void enterChallengeID() throws IOException {
		try {
			String challengeCode = MobileAutomationUtils.getValuefromPropFile("ChallengeCode");
			MobileAutomationUtils.waitForVisibility(challengCodeFirstFieldbox);
			MobileAutomationUtils.clickElement(challengCodeFirstFieldbox);
			enterCode(challengeCode);
			log.info("Entered Challenge ID");
		} catch (InterruptedException e) {
			log.error("Failed to enter the Challenge ID", e);
			throw new RuntimeException();
		}
	}

	public void enterRegistartionOtp() throws IOException, InterruptedException {
		try {
			String otp = MobileAutomationUtils.getValuefromPropFile("RegistrationOtp");
			MobileAutomationUtils.waitForVisibility(enterActivationCodeFirstfieldBox);
			MobileAutomationUtils.clickElement(enterActivationCodeFirstfieldBox);
			enterCode(otp);
			log.info("Entered OTP");
			MobileAutomationUtils.clickElement(verifyBtn);
			MobileAutomationUtils.handleLoader();
		} catch (InterruptedException e) {
			log.error("Failed to enter the OTP", e);
			throw new RuntimeException();
		}
	}

	public void enterCode(String Code) throws InterruptedException {
		Thread.sleep(1000);
		for (char digit : Code.toCharArray()) {
			AndroidKey key = AndroidKey.valueOf("DIGIT_" + digit);
			driver.pressKey(new KeyEvent(key));
			Thread.sleep(100);
		}
	}

	public void selectEnvAndRegisterDevice() {
		MobileAutomationUtils.clickElement(moreInforBtn);
		MobileAutomationUtils.clickElement(appInstanceBtn);
		MobileAutomationUtils.clickElement(qaInstanceRadio);
		log.info("Selected Environment: " + MobileAutomationUtils.getValuefromPropFile("Instance"));
		MobileAutomationUtils.clickElement(nextBtn);
		MobileAutomationUtils.handleLoader();
		WebElement element = MobileAutomationUtils.waitForVisibility(SuccessTxtXpath);
		String successMessage = null;
		if (element != null) {
			successMessage = MobileAutomationUtils.getTextByXPath(SuccessTxtXpath);
			log.info(successMessage);
		}
		Assert.assertEquals(successMessage, "Registered Successfully", "Device registration failed.");
		MobileAutomationUtils.clickElement(OKBtn);
	}

	public void registerUser() {
		try {
			MobileAutomationUtils.sendKeysToElement(enterUserIdTxt,
					MobileAutomationUtils.getValuefromPropFile("UserName"));
			WebElement regBtn = null;
			try {
				regBtn = MobileAutomationUtils.findElement(registerBtn);
			} catch (Exception e) {
				log.info("No Register Btn found");
			}
			if (regBtn != null) {
				MobileAutomationUtils.clickElement(registerBtn);
				MobileAutomationUtils.handleLoader();

				String message = getRegistrationMessage();

				if (message != null && message.contains("Already")) {
					Assert.assertEquals(message, "User Already Existed And Activated",
							"Failed to validate re-registration with the same user");
					log.info(message);
				} else if (message != null && message.contains("Another device")) {
					Assert.assertEquals(message,
							"Another device is already registered for this user. Do you want to continue?",
							"Failed to validate user registered with another device");
					log.info(message);
				} else {
					log.info("Expecting OTP for registration");
					enterRegistartionOtp();
					String successMessage = MobileAutomationUtils.getTextByXPath(deviceRegSuccessTxtXpath);
					Assert.assertEquals(successMessage, "Device registered successfully",
							"Failed to validate device registration");
					log.info(successMessage);
				}
				MobileAutomationUtils.clickElement(oKBtn);
			} else {
				log.info("No registartion is required");
			}

		} catch (Exception e) {
			log.error("Failed to register user.", e);
			throw new RuntimeException("Failed to register user.", e);
		}
	}

	private String getRegistrationMessage() {
		try {
			return MobileAutomationUtils.findElement(registeredWithAnotherDeviceTxt).getText().trim();
		} catch (Exception e1) {
			try {
				return MobileAutomationUtils.findElement(alreadyExistAndActivatedTxt).getText().trim();
			} catch (Exception e2) {
				return null;
			}
		}
	}
}
