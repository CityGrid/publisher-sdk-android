/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.logging;

import java.util.logging.LogManager;

public class LogConfiguration {
	public LogConfiguration() {
		String fileName = System.getProperty("java.util.logging.config.file");
		if (fileName == null) {
			fileName = "logging.properties";
		}

		try {
			LogManager.getLogManager().readConfiguration(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
		} catch (Exception e) {
			System.out.println(String.format("Could not initialize java.util.logging from configuration file %s", fileName));
			e.printStackTrace();
		}
		
		System.out.println(String.format("Initialized java.util.logging from configuration file %s", fileName));
	}
}