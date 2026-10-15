package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.*;

@Listeners(TestListener.class)
public class TC02_EchoBox_Input_Test extends BaseTest_Mobile {

	@Test
	public void inputkeys() {
		log.info("Started executing TC02_EchoBox_Input_Test Test");
		pages.getHomePage_Mobile().navigateToEchoBoxcreen();
		pages.getEchoBoxPage_mobile().enterTextAndSave();
		pages.getEchoBoxPage_mobile().verifySavedTxt();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();
		log.info("Completed executing TC02_EchoBox_Input_Test Test");

	}
}
