package com.unitestx.pagemanagers;

import com.unitestx.mobilepages.ClipBoard_DemoPage_mobile;
import com.unitestx.mobilepages.DatePickerDemoPage_mobile;
import com.unitestx.mobilepages.EchoBoxPage_mobile;
import com.unitestx.mobilepages.GeoLocationDemoPage_mobile;
import com.unitestx.mobilepages.HomePage_mobile;
import com.unitestx.mobilepages.ListDemoPage_mobile;
import com.unitestx.mobilepages.LoginPage_Mobile;
import com.unitestx.mobilepages.PhotoDemo_Page_mobile;
import com.unitestx.mobilepages.WebView_DemoPage_mobile;

import io.appium.java_client.android.AndroidDriver;

public class MobilePageManager {
	private AndroidDriver driver;

	public MobilePageManager(AndroidDriver driver) {
		this.driver = driver;
	}

	// page object variables
	private LoginPage_Mobile loginPage_Mobile;
	private HomePage_mobile homePage_Mobile;
	private EchoBoxPage_mobile echoBoxPage_mobile;
	private ClipBoard_DemoPage_mobile clipBoard_DemoPage_mobile;
	private WebView_DemoPage_mobile webView_DemoPage_mobile;
	private ListDemoPage_mobile listDemoPage_mobile;
	private PhotoDemo_Page_mobile photoDemo_Page_mobile;
	private GeoLocationDemoPage_mobile geoLocationDemoPage_mobile;
	private DatePickerDemoPage_mobile datePickerDemoPage_mobile;

	public HomePage_mobile getHomePage_Mobile() {
		if (homePage_Mobile == null) {
			homePage_Mobile = new HomePage_mobile(driver);
		}
		return homePage_Mobile;
	}

	public LoginPage_Mobile getLoginPage_Mobile() {
		if (loginPage_Mobile == null) {
			loginPage_Mobile = new LoginPage_Mobile(driver);
		}
		return loginPage_Mobile;
	}

	public EchoBoxPage_mobile getEchoBoxPage_mobile() {
		if (echoBoxPage_mobile == null) {
			echoBoxPage_mobile = new EchoBoxPage_mobile(driver);
		}
		return echoBoxPage_mobile;
	}

	public ClipBoard_DemoPage_mobile getClipBoard_DemoPage_mobile() {
		if (clipBoard_DemoPage_mobile == null) {
			clipBoard_DemoPage_mobile = new ClipBoard_DemoPage_mobile(driver);
		}
		return clipBoard_DemoPage_mobile;
	}

	public WebView_DemoPage_mobile getWebView_DemoPage_mobile() {
		if (webView_DemoPage_mobile == null) {
			webView_DemoPage_mobile = new WebView_DemoPage_mobile(driver);
		}
		return webView_DemoPage_mobile;
	}

	public ListDemoPage_mobile getListDemoPage_mobile() {
		if (listDemoPage_mobile == null) {
			listDemoPage_mobile = new ListDemoPage_mobile(driver);
		}
		return listDemoPage_mobile;
	}

	public PhotoDemo_Page_mobile getPhotoDemo_Page_mobile() {
		if (photoDemo_Page_mobile == null) {
			photoDemo_Page_mobile = new PhotoDemo_Page_mobile(driver);
		}
		return photoDemo_Page_mobile;
	}

	public GeoLocationDemoPage_mobile getGeoLocationDemoPage_mobile() {
		if (geoLocationDemoPage_mobile == null) {
			geoLocationDemoPage_mobile = new GeoLocationDemoPage_mobile(driver);
		}
		return geoLocationDemoPage_mobile;
	}

	public DatePickerDemoPage_mobile getDatePickerDemoPage_mobile() {
		if (datePickerDemoPage_mobile == null) {
			datePickerDemoPage_mobile = new DatePickerDemoPage_mobile(driver);
		}
		return datePickerDemoPage_mobile;
	}

}
