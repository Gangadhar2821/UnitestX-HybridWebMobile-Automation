package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.unitestx.listener.*;

import com.unitestx.base.BaseTest_Mobile;

@Listeners(TestListener.class)
public class TC04_WebViewDemo_Test extends BaseTest_Mobile {

	@Test
	public void navigateToWebPage() {
		pages.getHomePage_Mobile().navigateToWebViewDemoScreen();
		pages.getWebView_DemoPage_mobile().navigateToSite();
		pages.getWebView_DemoPage_mobile().verifyWebViewText();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();

	}

}
