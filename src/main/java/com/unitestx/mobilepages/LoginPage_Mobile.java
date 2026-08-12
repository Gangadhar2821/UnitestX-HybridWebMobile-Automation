package com.unitestx.mobilepages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;
import com.unitestx.utils.WebAutomationUtils;

import io.appium.java_client.android.AndroidDriver;

public class LoginPage_Mobile {
	private AndroidDriver driver;
	private WebDriverWait wait;
	private LoggerUtil log;
	private RegistrationPage_Mobile registartionPage;

	public LoginPage_Mobile(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		log = new LoggerUtil();
		registartionPage = new RegistrationPage_Mobile(driver);
	}

	// Login Page Locators
	private String userIdTxt = "//android.widget.EditText[contains(@content-desc,'USER ID')]";
	private String enterUserIDtxt = "//android.widget.EditText[@content-desc=\"Enter User ID\"]";
	private String passwordTxt = "//android.widget.EditText[@content-desc=\"Password\"]";
	private String forgotPasswordTxt = "//android.widget.TextView[@text=\"Forgot Password?\"]";
	private String loginBtn = "//android.view.ViewGroup[@content-desc=\"LOGIN\"]";
	private String moreInforBtn = "//android.view.ViewGroup[@content-desc=\"More Info\"]";
	private String passwordViewIcon = "//android.view.View[@content-desc=\"eye\"]";
	private String successfulLoginToast = "//android.widget.TextView[@text=\"Please Setup new MPIN\"]";
	private String invalidUserIdError = "//android.widget.TextView[@text=\"Employee ID does not exist\"]";
	private String invalidPasswordError = "//android.widget.TextView[@text=\"Invalid Password\"]";
	private String accountLockedmsg = "//android.widget.TextView[@text=\"Account is locked\"]";
	private String mpinFirstFieldBox = "(//android.widget.EditText[@text=\"-\"])[1]";
	private String skipBtn = "//android.widget.TextView[@text=\"Skip\"]";
	private String nextBtn = "//android.view.ViewGroup[@content-desc=\"Next\"]";
	private String sendVerificationCodeBtn = "//android.widget.TextView[@text=\"Send Verification Code\"]";
	private String otpFirstFieldBox = "(//android.widget.EditText[@text=\"-\"])[1]";
	private String newPasswordTxt = "//android.widget.EditText[@content-desc=\"New Password\"]";
	private String confirmNewPasswordTxt = "//android.widget.EditText[@content-desc=\"Confirm Password\"]";
	private String submitBtn = "//android.widget.TextView[@text=\"Submit\"]";
	private String passwordChangeSuccessmsg = "//android.widget.TextView[@text=\"Password Updated Successfully\"]";
	private String okBtn = "//android.widget.TextView[@text=\"Ok\"]";
	private String verifyBtn = "//android.widget.TextView[@text=\"VERIFY\"]";

	public void registerUser() {
		try {
			registartionPage.registerUser();
		} catch (Exception e) {
			throw new RuntimeException("Failed to register user");
		}
	}

	public void setMpin() {
		try {
			String mpin = MobileAutomationUtils.getValuefromPropFile("MPIN");
			MobileAutomationUtils.waitForVisibility(mpinFirstFieldBox);
			MobileAutomationUtils.clickElement(mpinFirstFieldBox);
			registartionPage.enterCode(mpin);
			MobileAutomationUtils.clickElement(nextBtn);
		} catch (Exception e) {
			throw new RuntimeException("Failed to setMpin");
		}
	}

	public void enterOTP() {
		try {
			String otp = MobileAutomationUtils.getValuefromPropFile("OTP_Mobile");
			MobileAutomationUtils.waitForVisibility(otpFirstFieldBox);
			MobileAutomationUtils.clickElement(otpFirstFieldBox);
			registartionPage.enterCode(otp);
		} catch (Exception e) {
			throw new RuntimeException("Failed to setMpin");
		}
	}

	public void loginToApplication() {
		try {
			registerUser();
			MobileAutomationUtils.sendKeysToElement(userIdTxt, MobileAutomationUtils.getValuefromPropFile("UserName"));
			MobileAutomationUtils.sendKeysToElement(passwordTxt,
					MobileAutomationUtils.getValuefromPropFile("Password"));
			MobileAutomationUtils.clickElement(loginBtn);
			MobileAutomationUtils.handleLoader();
			WebAutomationUtils.verifyLandingPage();
			// MobileAutomationUtils.clickElement(skipBtn);
			// MobileAutomationUtils.handleLoader();

		} catch (Exception e) {
			throw new RuntimeException("Failed to Login");
		}
	}

	public void loginToApplication_invalidUserName() {
		try {
			registerUser();
			MobileAutomationUtils.sendKeysToElement(userIdTxt,
					MobileAutomationUtils.getValuefromPropFile("invalidUserName"));
			MobileAutomationUtils.sendKeysToElement(passwordTxt,
					MobileAutomationUtils.getValuefromPropFile("Password"));
			MobileAutomationUtils.clickElement(loginBtn);
			MobileAutomationUtils.handleLoader();
		} catch (Exception e) {
			throw new RuntimeException("Failed to Login");
		}
	}

	public void loginToApplication_invalidPassword() {
		try {
			registerUser();
			MobileAutomationUtils.sendKeysToElement(userIdTxt, MobileAutomationUtils.getValuefromPropFile("UserName"));
			MobileAutomationUtils.sendKeysToElement(passwordTxt,
					MobileAutomationUtils.getValuefromPropFile("invalidPassword"));
			MobileAutomationUtils.clickElement(loginBtn);
			MobileAutomationUtils.handleLoader();
		} catch (Exception e) {
			throw new RuntimeException("Failed to Login");
		}
	}

	public void loginToApplication_lockedUser() {
		try {
			registerUser();
			MobileAutomationUtils.sendKeysToElement(userIdTxt,
					MobileAutomationUtils.getValuefromPropFile("LockedUsername"));
			MobileAutomationUtils.sendKeysToElement(passwordTxt,
					MobileAutomationUtils.getValuefromPropFile("Password"));
			MobileAutomationUtils.clickElement(loginBtn);
			MobileAutomationUtils.handleLoader();
		} catch (Exception e) {
			throw new RuntimeException("Failed to Login");
		}
	}

	public void triggerOTPtoResetPassword() {
		try {
			MobileAutomationUtils.clickElement(forgotPasswordTxt);
			MobileAutomationUtils.handleLoader();
			enterOTP();
			MobileAutomationUtils.clickElement(verifyBtn);
			MobileAutomationUtils.handleLoader();
		} catch (Exception e) {
			throw new RuntimeException("Failed to reset password");
		}
	}

	public void setNewPassword() {
		try {
			String newPassword = MobileAutomationUtils.getValuefromPropFile("Newpassword");

			// Enter new password and confirm
			MobileAutomationUtils.sendKeysToElement(newPasswordTxt, newPassword);
			MobileAutomationUtils.sendKeysToElement(confirmNewPasswordTxt, newPassword);
			MobileAutomationUtils.clickElement(submitBtn);
			MobileAutomationUtils.handleLoader();

			// Wait for success message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordChangeSuccessmsg)));
			String successMsg = MobileAutomationUtils.getTextByXPath(passwordChangeSuccessmsg);

			Assert.assertEquals(successMsg, "Password Updated Successfully",
					"Expected success message but found: " + successMsg);
			MobileAutomationUtils.clickElement(okBtn);
			log.info("Password reset completed successfully.");
		} catch (Exception e) {
			log.error("Failed to reset password", e);
			throw new RuntimeException("Failed to reset password", e);
		}
	}

	public void verifyNegativeLogin() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement messageElement = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(successfulLoginToast + "|"
							+ invalidUserIdError + "|" + invalidPasswordError + "|" + accountLockedmsg)));

			String messageText = messageElement.getText().trim();
			log.info("Login validation message: " + messageText);

			if (messageText.equalsIgnoreCase("Please Setup new MPIN")) {
				Assert.assertTrue(messageElement.isDisplayed(), "Login success toast not displayed");
			} else if (messageText.equalsIgnoreCase("Employee ID does not exist")) {
				Assert.assertTrue(messageElement.isDisplayed(), "Invalid User ID error not displayed");
			} else if (messageText.equalsIgnoreCase("Invalid Password")) {
				Assert.assertTrue(messageElement.isDisplayed(), "Invalid Password error not displayed");
			} else if (messageText.equalsIgnoreCase("Account is locked")) {
				Assert.assertTrue(messageElement.isDisplayed(), "Account Locked message not displayed");
			} else {
				Assert.fail("Unexpected login message: " + messageText);
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to validate Login", e);
		}
	}

	public void verifyLogin() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement messageElement = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(successfulLoginToast + "|"
							+ invalidUserIdError + "|" + invalidPasswordError + "|" + accountLockedmsg)));

			String messageText = messageElement.getText().trim();
			log.info("Login validation message: " + messageText);

			if (messageText.equalsIgnoreCase("Please Setup new MPIN")) {
				Assert.assertTrue(messageElement.isDisplayed(), "Login success toast not displayed");
			} else if (messageText.equalsIgnoreCase("Employee ID does not exist")) {
				Assert.fail(messageText);
			} else if (messageText.equalsIgnoreCase("Invalid Password")) {
				Assert.fail(messageText);
			} else if (messageText.equalsIgnoreCase("Account is locked")) {
				Assert.fail(messageText);
			} else {
				Assert.fail("Unexpected login message: " + messageText);
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to validate Login", e);
		}
	}

}
