package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.TestListener;

@Listeners(TestListener.class)
public class TC05_ToSelectListOptionDemo_Test extends BaseTest_Mobile {

	@Test
	public void slectOption() {
		pages.getHomePage_Mobile().navigateToListDemoScreen();
		pages.getListDemoPage_mobile().selectListOption();
		pages.getListDemoPage_mobile().verifyOptionSelection();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();
	}
}
