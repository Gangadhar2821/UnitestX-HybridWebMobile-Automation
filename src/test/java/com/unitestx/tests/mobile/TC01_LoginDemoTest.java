package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import com.unitestx.listener.TestListener;
import org.testng.annotations.Test;

import com.unitestx.mobilebase.BaseTest_Mobile;

@Listeners(TestListener.class)
public class TC01_LoginDemoTest extends BaseTest_Mobile {

	@Test
	public void demoLogin() {
		pages.getHomePage_Mobile().tapLoginScreen();
		pages.getLoginPage_Mobile().login();
		pages.getLoginPage_Mobile().verifyinvalidLogin();

	}
}
