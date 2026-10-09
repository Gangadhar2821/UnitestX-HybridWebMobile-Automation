package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.unitestx.listener.*;

import com.unitestx.base.BaseTest_Mobile;

@Listeners(TestListener.class)
public class TC03_ToSet_ClipboardText_Test extends BaseTest_Mobile {

	@Test
	public void setClipboardTxt() {
		pages.getHomePage_Mobile().navigateToClipBoardDemoScreen();
		pages.getClipBoard_DemoPage_mobile().enterAndSetTextToClipboard();
		pages.getClipBoard_DemoPage_mobile().verifyTextSetToClipboard();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();

	}
}
