package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.TestListener;

@Listeners(TestListener.class)
public class TC08_DatePickerDemo_Test extends BaseTest_Mobile {

	@Test
	public void pickDateDemo() {
		log.info("Started executing TC08_DatePickerDemo_Test Test");
		pages.getHomePage_Mobile().navigateToPickerDemoScreen();
		pages.getDatePickerDemoPage_mobile().pickDatefromSelectedMonth();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();
		log.info("Completed executing TC08_DatePickerDemo_Test Test");

	}
}
