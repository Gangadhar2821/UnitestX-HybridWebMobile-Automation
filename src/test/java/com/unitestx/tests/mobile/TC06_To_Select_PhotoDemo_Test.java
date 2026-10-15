package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.TestListener;

@Listeners(TestListener.class)
public class TC06_To_Select_PhotoDemo_Test extends BaseTest_Mobile {

	@Test
	public void photoviewDemo() {
		log.info("Started executing TC06_To_Select_PhotoDemo_Test Test");
		pages.getHomePage_Mobile().navigateToPhotoDemoScreen();
		pages.getPhotoDemo_Page_mobile().pickPhoto();
		pages.getPhotoDemo_Page_mobile().verifyPhotoSelected();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();
		log.info("Completed executing TC06_To_Select_PhotoDemo_Test Test");

	}
}
