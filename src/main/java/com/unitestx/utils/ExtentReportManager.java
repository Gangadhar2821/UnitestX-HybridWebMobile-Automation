package com.unitestx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;

	public static ExtentReports createInstance(String suiteName) {

		if (extent == null) {

			String timestamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
			String safeSuiteName = suiteName.replaceAll("\\s+", "_");
			ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/"
					+ safeSuiteName + "/" + "ExtentReport_" + safeSuiteName + "_" + timestamp + ".html");
			reporter.config().setReportName("Automation Test Report");
			reporter.config().setDocumentTitle("Automation Test Results");

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Environment", "UAT");
			extent.setSystemInfo("Tester", "Gangadhar");

		}
		return extent;

	}

	public static ExtentReports getInstance() {
		return extent;
	}

}
