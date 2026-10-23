package com.unitestx.tests.web;

import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Web;
import com.unitestx.utils.WebAutomationUtils;

public class TC01_Webapp_Login_Test extends BaseTest_Web {

	@Test
	public void login() {
		WebAutomationUtils.navigateToURL(WebAutomationUtils.getValuefromPropFile("TEST_URL"));
		pages.getLoginPage_WebApp().login();
	}
}
