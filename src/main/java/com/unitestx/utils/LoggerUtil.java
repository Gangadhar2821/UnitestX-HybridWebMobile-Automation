package com.unitestx.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.unitestx.listener.TestListener;

public class LoggerUtil {
	private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

	public void info(String message) {

		logger.info(message);

		ExtentTest test = TestListener.getTest();

		if (test != null) {
			test.info(message);
		}

	}

	public void error(String message, Throwable t) {

		logger.error(message, t);

		ExtentTest test = TestListener.getTest();

		if (test != null) {
			test.fail(message);
		}
	}

	public void warn(String message) {

		logger.warn(message);

		ExtentTest test = TestListener.getTest();

		if (test != null) {
			test.warning(message);
		}
	}

	public void debug(String message) {

		logger.debug(message);

		ExtentTest test = TestListener.getTest();

		if (test != null) {
			test.info("[DEBUG] " + message);
		}
	}
}
