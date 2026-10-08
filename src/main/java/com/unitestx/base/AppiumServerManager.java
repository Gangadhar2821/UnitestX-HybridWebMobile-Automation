package com.unitestx.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.Duration;

import com.unitestx.utils.LoggerUtil;
import com.unitestx.utils.MobileAutomationUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerManager {
	private static AppiumDriverLocalService service = null;
	private static LoggerUtil log;

	public static void startServer() {
		log = new LoggerUtil();
		String userHome = System.getProperty("user.home");

		String nodePath = MobileAutomationUtils.getValuefromPropFile("NODE_PATH");
		String ipAddress = MobileAutomationUtils.getValuefromPropFile("IP_ADDRESS");
		String appiumJSpath = MobileAutomationUtils.getValuefromPropFile("APPIUM_JSPATH");
		AppiumServiceBuilder builder = new AppiumServiceBuilder().withIPAddress(ipAddress).usingPort(4723)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE).withArgument(GeneralServerFlag.LOG_LEVEL, "error")
				.usingDriverExecutable(new File(nodePath)).withAppiumJS(new File(userHome + appiumJSpath))
				.withTimeout(Duration.ofSeconds(60));

		service = builder.build();
		service.start();
		if (service.isRunning()) {
			log.info("Started the Appium server!");
		} else {
			log.info("Failed to start the Appium server!");
		}
	}

	public static void killListeningProcess(int port) {
		try {
			// Run netstat via cmd.exe so pipes work
			Process process = Runtime.getRuntime()
					.exec(new String[] { "cmd.exe", "/c", "netstat -ano | findstr " + port });

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			boolean killed = false;

			while ((line = reader.readLine()) != null) {
				// Example line: TCP 127.0.0.1:4723 0.0.0.0:0 LISTENING 25584
				if (line.contains("LISTENING")) {
					String[] parts = line.trim().split("\\s+");
					String pid = parts[parts.length - 1]; // last column is PID

					// Kill the process
					Runtime.getRuntime().exec("taskkill /PID " + pid + " /F");
					log.info("Killed the Appium server");
					killed = true;
				}
			}
			reader.close();

			if (!killed) {
				log.info("No LISTENING process found on port " + port);
			}

		} catch (Exception e) {
			log.info("Error killing Appium Server on port " + port + ": " + e.getMessage());
		}
	}
}
