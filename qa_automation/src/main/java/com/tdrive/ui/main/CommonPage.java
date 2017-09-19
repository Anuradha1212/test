package com.tdrive.ui.main;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdrive.utilties.Utility;

/**
 * 
 * CommonPage class contains all action which are common throughout the
 * application
 *
 */
public class CommonPage {
	private static Logger logger = LoggerFactory.getLogger(CommonPage.class);

	/**
	 * This method is implemented for clicking on TDrive Tool bar
	 * 
	 * @throws InterruptedException
	 */
	public static void clickToTdriveMenu() throws InterruptedException {
		logger.info("Clicking to dashboard menu");
		assertTrue("Failed,unable to locate menu icon on titlebar.",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("home_page_menu_icon"))));
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("home_page_menu_icon")));
		// Utility.waitForElementBecomeVisible(By.id(Utility.prop.getProperty("dashboard_button")),
		// 10);
		Utility.waitForElementBecomeVisible(By.id(Utility.prop.getProperty("customer_mgmt_btn")), 40);
		logger.info("Done with clicking to dashboard menu");
	}

	/**
	 * This method is implemented for clicking on DASHBOARD button
	 * 
	 * @throws InterruptedException
	 */
	public static void clickOnDashboardBtn() throws InterruptedException {
		logger.info("Clicking on Dashboard option");
		Utility.clickToElement(By.id(Utility.prop.getProperty("dashboard_button")));
		// Utility.waitForElementBecomeVisible(By.id(Utility.prop.getProperty("dashboard_headline")),
		// 10);
		logger.info("Done with clicking on Dashboard option");
	}

	/**
	 * This method is implemented for clicking on Customer management button
	 * 
	 * @throws InterruptedException
	 */
	public static void clickOnCustomerManagementBtn() throws InterruptedException {
		logger.info("Clicking on customber management option");
		Utility.clickToElement(By.id(Utility.prop.getProperty("customer_mgmt_btn")));
		Utility.waitForElementBecomeVisible(By.id(Utility.prop.getProperty("customer_mgmt_headline")), 10);
		Thread.sleep(2000);
		logger.info("Done with clicking on customber management option");
	}

	/**
	 * This method is implemented for clicking on Dashboard menu icon
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyMenuIconNavigationPane() throws InterruptedException {
		logger.info("Verify navigation pane");
		//assertTrue(Utility.isElementPresent(By.xpath(Utility.prop.getProperty("user_name"))));
		assertTrue("Fail, drop down is absent",Utility.isElementPresent(By.xpath(Utility.prop.getProperty("user_name_drop_down"))));
		assertTrue(Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_mgmt_btn"))));

		logger.info("Done with Verifying navigation pane");
	}

	/**
	 * This method is implemented for sign out from TDrive
	 * 
	 * @throws InterruptedException
	 */
	public static void signOutFromTDrive() throws InterruptedException {
		clickToTdriveMenu();
		Thread.sleep(1000);
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("user_name_drop_down")));
		Thread.sleep(1000);
		Utility.clickToElement(By.id(Utility.prop.getProperty("signout")));
		logger.info("Done with clicking on signout");
	}

}
