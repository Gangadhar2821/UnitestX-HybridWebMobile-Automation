package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.*;

@Listeners(TestListener.class)
public class TC02_EchoBox_Input_Test extends BaseTest_Mobile {

	@Test
	public void inputkeys() {
		pages.getHomePage_Mobile().navigateToEchoBoxcreen();
		pages.getEchoBoxPage_mobile().enterTextAndSave();
		pages.getEchoBoxPage_mobile().verifySavedTxt();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();

	}
}
