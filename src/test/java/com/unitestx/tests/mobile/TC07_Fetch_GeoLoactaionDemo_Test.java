package com.unitestx.tests.mobile;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.unitestx.base.BaseTest_Mobile;
import com.unitestx.listener.TestListener;

@Listeners(TestListener.class)
public class TC07_Fetch_GeoLoactaionDemo_Test extends BaseTest_Mobile {

	@Test
	public void fetchGeoLocationDemo() {
		log.info("Started executing TC07_Fetch_GeoLoactaionDemo_Test Test");
		pages.getHomePage_Mobile().navigateToGeoLocationDemoScreen();
		pages.getGeoLocationDemoPage_mobile().verifylatitudeValue();
		pages.getHomePage_Mobile().navigateBackToHomeScreen();
		log.info("Completed executing TC07_Fetch_GeoLoactaionDemo_Test Test");

	}
}
