package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.TestListener;

@Listeners(TestListener.class)
public class TC01_LoginDemoTest extends BaseTest_Mobile {

	@Test
	public void demoLogin() {
		log.info("Started executing TC01_LoginDemoTest Test");
		pages.getHomePage_Mobile().navigateToLoginScreen();
		pages.getLoginPage_Mobile().login();
		pages.getLoginPage_Mobile().verifyinvalidLogin();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();
		log.info("Completed executing TC01_LoginDemoTest Test");

	}
}
