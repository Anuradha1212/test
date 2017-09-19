package com.tdrive.ui.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.xalan.templates.ElemAttributeSet;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.schemas.office.x2006.encryption.impl.CTKeyEncryptorImpl.UriImpl;
import com.tdrive.utilties.Utility;

/**
 * 
 * LoginPage class contains all actions which performing on Login page
 *
 */

public class LoginPage {
	private static Logger logger = LoggerFactory.getLogger(LoginPage.class);
	public static String username, password;

	/**
	 * This method is implemented for logging to TDrive dashboard
	 * 
	 * @throws Exception
	 */
	public static void loginToTdriveApplication() throws Exception {
		logger.info("Trying to login To Tdrive application");
		username = System.getenv("USER_NAME");
		password = System.getenv("USER_PASSWORD");
		Utility.sendKeysWithText(By.xpath(Utility.prop.getProperty("login_username_text_box")), username);
		Utility.sendKeysWithText(By.xpath(Utility.prop.getProperty("login_password_text_box")), password);
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("login_btn")));
		Utility.waitForElementBecomeVisible(By.xpath(Utility.prop.getProperty("home_page_menu_icon")), 50);
		logger.info("Done with clicking on Login button");
	}

	/**
	 * This method is implemented for validating Login page opened
	 * 
	 * @throws InterruptedException
	 */
	public static void validateLoginPageOpened() throws InterruptedException {
		logger.info("Validating Login page opened");
		assertEquals("TDrive title is not matched", "TDrive2-DEV",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("tdrive_login_page_title"))));

	}

	/**
	 * This method is implemented for logging to TDrive dashboard
	 * 
	 * @throws Exception
	 */
	public static void loginWithInvalidCredentail(String userName, String passwordSting) throws Exception {
		logger.info("Trying to login To Tdrive application");
			Utility.sendKeysWithText(By.xpath(Utility.prop.getProperty("login_username_text_box")), userName);
			Utility.sendKeysWithText(By.xpath(Utility.prop.getProperty("login_password_text_box")), passwordSting);
			Utility.clickToElement(By.xpath(Utility.prop.getProperty("login_btn")));
			Thread.sleep(1000);
		logger.info("Done with clicking on Login button");
	}

	/**
	 * This method is implemented for verifying LOGIN page elements
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyLoginPage() throws InterruptedException {
		logger.info("Verifying Login page elements");
		assertEquals("TDrive title is not matched", "T-Drive Login",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("tdrive_login_page_title"))));
		assertTrue("Username text box is not present",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("login_username_text_box"))));
		assertTrue("Password text box is not present",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("login_password_text_box"))));
		assertTrue("Login button is not present",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("login_btn"))));
	//	Utility.waitForElementBecomeVisible(By.xpath(Utility.prop.getProperty("home_page_title")), 20);
		 Thread.sleep(5000);
	}

	/**
	 * This method is implemented for verifying LOGIN page elements
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyHomePage() throws InterruptedException {
		logger.info("Verifying home page title");

		assertTrue("Failed, Unable to Login TDrive.",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("home_page_title"))));
		assertTrue("Failed, Unable to Login TDrive.",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("home_page_menu_icon"))));
	}

	/**
	 * This method is implemented for verifying Invalid Credentials message
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyInvalidCredentialMessage(String message) throws InterruptedException {
		logger.info("Verifying Invalid Credential message");
		if (message.equalsIgnoreCase("Can't be blank")) {
			Utility.waitForElementBecomeVisible(By.xpath(Utility.prop.getProperty("login_error_msg")), 10);
			assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("login_error_msg"))),
					"Can't be blank");
		} else if (message.equalsIgnoreCase("Wrong username or password")) {Thread.sleep(2000);
			Utility.waitForElementBecomeVisible(By.xpath(Utility.prop.getProperty("login_invalid_credential")), 20);
			assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("login_invalid_credential"))),
					"WRONG USERNAME OR PASSWORD.");
		}

	}

}
