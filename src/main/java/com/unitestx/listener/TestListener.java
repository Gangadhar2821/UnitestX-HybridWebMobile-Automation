package com.unitestx.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.unitestx.driverfactory.AndroidDriverFactory;
import com.unitestx.driverfactory.WebDriverFactory;
import com.unitestx.utils.ExtentReportManager;
import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;
import com.unitestx.utils.WebAutomationUtils;

public class TestListener implements ITestListener {

	LoggerUtil log = new LoggerUtil();
	private static boolean stopExecution = false;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public static String screenshotPath = null;
	private String methodName = null;

	public static ExtentTest getTest() {
		return test.get();
	}

	@Override
	public void onStart(ITestContext context) {
		String suiteName = context.getSuite().getName();
		ExtentReportManager.createInstance(suiteName);
	}

	@Override
	public void onTestStart(ITestResult result) {
		if (stopExecution) {
			throw new SkipException("Skipping due to previous failure");
		}
		String className = result.getTestClass().getRealClass().getSimpleName();

		ExtentTest extentTest = ExtentReportManager.getInstance().createTest(className);
		test.set(extentTest);
		if (test.get() != null) {
			test.get().log(Status.INFO, "Started the execution of testcase: " + className);
		}
		WebAutomationUtils.methodName = result.getMethod().getMethodName();
		MobileAutomationUtils.methodName = result.getMethod().getMethodName();
		methodName = result.getMethod().getMethodName();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String className = result.getTestClass().getRealClass().getSimpleName();
		if (test.get() != null) {
			test.get().log(Status.PASS, "Completed the execution of testcase: " + className);
		}

		if (test.get() != null && screenshotPath != null) {
			test.get().addScreenCaptureFromPath(screenshotPath, methodName + " test - Success Screenshot");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		if (test.get() != null) {
			test.get().log(Status.FAIL,
					result.getTestClass().getRealClass().getSimpleName() + " testcase execution Failed");
			test.get().log(Status.FAIL, result.getThrowable());
		}

		if (test.get() != null && screenshotPath != null) {
			test.get().addScreenCaptureFromPath(screenshotPath, methodName + " test - Failure Screenshot");

		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		if (test.get() != null) {
			test.get().log(Status.SKIP,
					result.getTestClass().getRealClass().getSimpleName() + " testcase execution Skipped!");
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.getInstance().flush();

	}
}
