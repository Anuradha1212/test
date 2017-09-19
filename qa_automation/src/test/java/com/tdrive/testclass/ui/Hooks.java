package com.tdrive.testclass.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdrive.utilties.Utility;

import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * 
 * Hooks class contains methods for calling @Before and @After
 *
 */
public class Hooks {
	private static Logger logger = LoggerFactory.getLogger(Hooks.class);

	@Before
	public static void init() throws Exception {
		logger.info("Initialising Hook");
		Utility.setup();
		logger.info("Done with Initialising Hook");
	}

	@After
	public static void cleanUp() {
		logger.info("Calling Clean up");
		Utility.driver.quit();
		Utility.driver = null;
		logger.info("Webdriver closed");
	}
}