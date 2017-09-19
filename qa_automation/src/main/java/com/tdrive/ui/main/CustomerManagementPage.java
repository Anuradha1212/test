package com.tdrive.ui.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdrive.utilties.Utility;

import junit.framework.AssertionFailedError;

/**
 * 
 * CustomerPage contain all the action performing on Customer Page
 * 
 */
public class CustomerManagementPage {
	private static List<String> list = new ArrayList<String>();
	private static String searchCustomer;

	private static Logger logger = LoggerFactory.getLogger(CustomerManagementPage.class);

	/**
	 * This method is implemented for verifying customer management page on
	 * screen
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyCustomerPageOnScreen() throws InterruptedException {
		logger.info("Verifying customer management page On Screen");
		assertEquals(Utility.getFieldText(By.id(Utility.prop.getProperty("customer_mgmt_headline"))),
				"Customer management");
		Thread.sleep(2000);
		logger.info("Done with verifying customer management page On Screen");
	}

	/**
	 * This method is implemented for verifying customer management page on
	 * screen
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyCustomerListDetails() throws InterruptedException {
		logger.info("Verifying customer page details");
		assertEquals(Utility.getFieldText(By.id(Utility.prop.getProperty("customer_mgmt_headline"))),
				"Customer management");
		assertTrue("Fail,Customer list title is not matched",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_list_title"))));
		assertTrue("Fail,Customer search button is absent",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_list_search_btn"))));
		assertTrue("Fail,Customer search bar is absent",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_list_search_bar"))));
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_list_name_title"))),
				"Customer name");
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_list_location_title"))),
				"Location");
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_list_contact_title"))),
				"Contact person");
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_list_phone_title"))), "Phone");
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_list_since_title"))), "Since");
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_list_last_updated"))),
				"Last Updated");
		assertTrue("Fail,Customer pagination bar is absent",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_list_pagingbar"))));

		logger.info("Done with verifying customer management page detais");
	}

	/**
	 * This method is implemented for customer search on customer list page
	 * 
	 * @throws InterruptedException
	 */
	public static void searchCustomer(String customerName) throws InterruptedException {
		logger.info("Enter value to search customer");
		Utility.sendKeysUsingJavascriptExecutor(By.xpath(Utility.prop.getProperty("customer_list_search_bar")),
				customerName);
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("customer_list_search_btn")));
		Thread.sleep(2000);
		logger.info("Done with search customer");
	}

	/**
	 * This method is implemented for clicking on searched customer
	 * 
	 * @throws InterruptedException
	 */
	public static void clickOnSearchCustomer(String customerName) throws InterruptedException {
		logger.info("Enter value to search customer");
		Utility.clickToElement(
				By.xpath(".//*[@id='customerDataTable']//span[contains(text(),'" + customerName + "')]"));
		logger.info("Done with search customer");
	}
	
	/**
	 * This method is implemented for validating the search functionality
	 * 
	 * @throws InterruptedException
	 */
	public static void VerifyValidSearch() throws InterruptedException {
		logger.info("Enter value to search customer");
		String customerName=Utility.getFieldText(By.xpath(".//*[@id='customerDataTable']/div/table/tbody/tr[2]/td[2]/span"));
		searchCustomer(customerName);
		assertTrue("Fail,not a valid search",Utility.isElementPresent(By.xpath(".//*[@id='customerDataTable']//span[contains(text(),'" + customerName + "')]")));
		logger.info("Done with search customer");
	}


	
	/**
	 * This method is implemented for verifying customer detail title
	 * 
	 * @throws InterruptedException
	 */
	public static void VerifyCustomerDetailTitle(String customerName) throws InterruptedException {
		logger.info("Verify customer detail page on screen");
		assertEquals(Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_detail_custName"))),
				"Whole Foods");
		logger.info("Done with Verifying customer detail page on screen");
	}

	/**
	 * This method is implemented for verifying customer detail page
	 * 
	 * @throws InterruptedException
	 */
	public static void VerifyCustomerDetailPage(String customerName) throws InterruptedException {
		logger.info("Verify customer detail page on screen");

		assertTrue("Fail,Back button is absent",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_detail_back_button"))));
		assertTrue(Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_detail_custName"))));
		assertTrue(Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_detail_location"))));
		/*
		 * assertTrue("Fail,location icon on customer detail page is absent",
		 * Utility.isElementPresent(By.xpath(Utility.prop.getProperty(
		 * "customer_detail_location_icon"))));
		 */
		assertEquals("Fail,service heading is mismatch",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_detail_service_heading"))), "Service");
		/*assertEquals("Fail,Heading is missing",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_detail_configuration_heading"))),
				"Configuration");*/
		assertEquals("Fail,Heading is missing",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_detail_version_heading"))), "Version");
		assertEquals("Fail,Heading is missing",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_detail_status_heading"))), "Status");
		assertEquals("Fail,Heading is missing",
				Utility.getFieldText(By.xpath(Utility.prop.getProperty("customer_detail_datacenter_heading"))),
				"Data center");
		assertTrue("Fail,pagination bar is absent",
				Utility.isElementPresent(By.id(Utility.prop.getProperty("customer_detail_pagination_bar"))));

		logger.info("Done with Verifying customer detail page on screen");
	}

	/**
	 * This method is implemented for verifying screen
	 * 
	 * @throws InterruptedException
	 */
	public static void verifypagination() throws InterruptedException {
		logger.info("Verify Pagination functionality");

		String[] noOfCustomerText = Utility.getFieldText(By.xpath(".//md-select/following-sibling::div/child::span"))
				.split(" ");
		int noOfCustomer = Integer.parseInt(noOfCustomerText[1]);
		if (noOfCustomer > 4 && noOfCustomer <= 8) {
			clickToArrowDropDown();
			Utility.clickToElement(By.xpath(".//md-option[contains(., '8')]"));
		} else if (noOfCustomer > 8 && noOfCustomer <= 12) {
			clickToArrowDropDown();
			Utility.clickToElement(By.xpath(".//md-option[contains(., '12')]"));

		} else if (noOfCustomer > 12) {
			clickToArrowDropDown();
			Utility.clickToElement(By.xpath(".//md-option[contains(., '12')]"));

			for (int i = 0; i < (noOfCustomer / 12); i++) {
				Thread.sleep(3000);
				clickToNavigateNext();
			}
			Thread.sleep(1000);

		}
		logger.info("Done with verifying pagination");
	}

	/**
	 * This method is implemented for verifying previous button of pagination
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyPaginatonPreviousClick() throws InterruptedException {
		logger.info("verify pagination previous click");
		if (Utility.isElementEnable(By.xpath(".//button[contains(., 'navigate_before')]"))) {
			Utility.clickToElement(By.xpath(".//button[contains(., 'navigate_before')]"));
			logger.info("Done with clicking on previous page button");
		} else
			logger.info("User already on previous page button");
	}

	/**
	 * This method is implemented for verifying first button of pagination
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyPaginatonFirstClick() throws InterruptedException {
		logger.info("verify pagination first click");
		if (Utility.isElementEnable(By.xpath(".//button[contains(., 'skip_previous')]"))) {
			Utility.clickToElement(By.xpath(".//button[contains(., 'skip_previous')]"));
			logger.info("Done with clicking on first page button");
		} else
			logger.info("User is already on first page button");
	}

	/**
	 * This method is implemented for verifying last button of pagination
	 * 
	 * @throws InterruptedException
	 */
	public static void verifyPaginatonLastClick() throws InterruptedException {
		logger.info("verify pagination last click");
		if (Utility.isElementEnable(By.xpath(".//button[contains(., 'skip_next')]"))) {
			Utility.clickToElement(By.xpath(".//button[contains(., 'skip_next')]"));
			logger.info("Done with clicking on last page button");
		} else
			logger.info("User already on last page button");
	}

	/**
	 * This method is implemented for verifying pagination drop down arrow
	 * 
	 * @throws InterruptedException
	 */
	public static void clickToArrowDropDown() throws InterruptedException {
		Utility.clickToElement(By.xpath(".//span[@class='mat-select-arrow']"));
	}

	/**
	 * This method is implemented for verifying pagination next button
	 * 
	 * @throws InterruptedException
	 */
	public static void clickToNavigateNext() throws InterruptedException {
		if (Utility.isElementEnable(By.xpath(".//button[contains(., 'navigate_next')]"))) {
			Utility.clickToElement(By.xpath(".//button[contains(., 'navigate_next')]"));
			logger.info("Done with clicking on next page button");
		}
	}

	/**
	 * This method is implemented for getting the default sort list
	 * 
	 * @throws InterruptedException
	 */
	public static List<String> originalList(String columnHeading) throws InterruptedException {
		int columnNo = 1;
		String tablename = null;
		if (columnHeading.equalsIgnoreCase("name")) {
			columnNo = 2;
			tablename = "customerDataTable";
		} else if (columnHeading.equalsIgnoreCase("location")) {
			columnNo = 3;
			tablename = "customerDataTable";
		}

		if (columnHeading.equalsIgnoreCase("service")) {
			columnNo = 1;
			tablename = "instanceDataTable";
		} else if (columnHeading.equalsIgnoreCase("location")) {
			columnNo = 3;
		}

		ArrayList<String> originList = new ArrayList<String>();
		verifyPaginatonFirstClick();

		for (int i = 0; i < noOfPage(); i++) {
			Thread.sleep(3000);
			// add detail in the list
			for (int j = 1; j < 5; j++) {
				boolean ele = Utility
						.isElementPresent(By.xpath("//*[@id='" + tablename + "']//tr[" + j + "]/td[" + columnNo + "]"));
				if (ele) {

					String fieldAttribute = Utility
							.getFieldText(By.xpath("//*[@id='" + tablename + "']//tr[" + j + "]/td[" + columnNo + "]"));
					originList.add(fieldAttribute);

				}
			}

			if (Utility.isElementEnable(By.xpath(".//button[contains(., 'navigate_next')]"))) {
				clickToNavigateNext();
			}
		}
		return originList;

	}

	/**
	 * This method is implemented for comparing the list
	 * 
	 * @throws InterruptedException
	 */
	public static void compareList(String columnHeading, String order) throws InterruptedException {
		// customerName
		list.addAll(originalList(columnHeading));
		logger.info("Before sorting: " + list);

		List<String> expectedAscList = new ArrayList<String>();
		expectedAscList.addAll(list);
		Collections.sort(expectedAscList);
		logger.info("expectedAscList: " + expectedAscList);

		List<String> expectedDesList = new ArrayList<String>();
		expectedDesList.addAll(list);
		Collections.sort(expectedDesList, Collections.reverseOrder());
		logger.info("expectedDesList: " + expectedDesList);

		if (order.equalsIgnoreCase("ascending")) {
			String acsLocator = "mat-asc";
			if (setOrderButtonStatus(columnHeading, acsLocator)) {
				// Utility.clickToElement(By.xpath("//*[@ng-reflect-name='" +
				// columnHeading
				// + "']/*/following-sibling::*[contains(@class, 'mat-asc')]"));
				List<String> orderedList = new ArrayList<String>();
				orderedList.addAll(originalList(columnHeading));
				logger.info("originalAcendingList: " + orderedList);
				if (expectedAscList.equals(orderedList)) {
					logger.info("done with verifying ascending order");
				} else
					logger.info("Fail");
			}
		}

		else if (order.equalsIgnoreCase("descending")) {
			String descLocator = "mat-desc";
			if (setOrderButtonStatus(columnHeading, descLocator)) {
				List<String> orderedList = new ArrayList<String>();
				orderedList.addAll(originalList(columnHeading));
				logger.info("originalDescendingList: " + orderedList);
				assertTrue("Fail, descending order mismatch", expectedDesList.equals(orderedList));
			}
		}
		list.clear();
	}

	/**
	 * This method is implemented for getting the no of page
	 * 
	 * @throws InterruptedException
	 */
	public static int noOfPage() throws InterruptedException {

		String[] noOfCustomer = Utility.getFieldText(By.xpath(".//md-select/following-sibling::div/child::span"))
				.split(" ");
		int noOfPage = 0;
		if ((Integer.parseInt(noOfCustomer[1])) <= 4) {
			logger.info("Next page is not available.");
			Utility.driver.close();
		} else if ((Integer.parseInt(noOfCustomer[1])) / 4 == 0) {
			noOfPage = (Integer.parseInt(noOfCustomer[1])) / 4;
		} else
			noOfPage = (Integer.parseInt(noOfCustomer[1])) / 4 + 1;
		return noOfPage;
	}

	/**
	 * This method is implemented for setting the sorting button on customer
	 * listing page
	 * 
	 * @throws InterruptedException
	 */

	public static boolean setOrderButtonStatus(String columnHeading, String order) throws InterruptedException {
		boolean flag = false;
		while (true) {
			if (Utility.isElementPresent(By.xpath("//*[@ng-reflect-name='" + columnHeading
					+ "']/*/following-sibling::*[contains(@class, '" + order + "')]"))) {
				flag = true;
				break;
			} else {
				Utility.clickToElement(By.xpath("//*[@ng-reflect-name='" + columnHeading + "']/span"));
				Thread.sleep(1000);
			}
		}
		return flag;
	}

	/**
	 * This method is implemented for clicking on back button
	 * 
	 * @throws InterruptedException
	 */
	public static void clickBackButton() throws InterruptedException {
		assertTrue("Verify back button is clickable",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_detail_back_button"))));
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("customer_detail_back_button")));
	}

	/**
	 * This method is implemented for clicking action/ellipses button
	 * 
	 * @throws InterruptedException
	 */
	public static void clickOnInstanceActionBtn() throws InterruptedException {
		Utility.clickToElement(By.xpath("//*[@id='instanceDataTable']//tr[1]//button"));
	}

	/**
	 * This method is implemented for getting action status(enable/disable)
	 * 
	 * @throws InterruptedException
	 */
	public static boolean getStatus(String locator) throws Exception {
		logger.info("Getting button status");
		boolean flag;
		boolean status = Utility.isElementEnable(By.xpath(Utility.prop.getProperty(locator)));
		if (status) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * This method is implemented for verifying action status(enable/disable)
	 * 
	 * @throws Exception
	 */
	public static void verifyActionStatus(String action, String status) throws Exception {
		logger.info("Verifying " + action + "status");
		clickOnInstanceActionBtn();
		Thread.sleep(500);
		boolean state = false;
		if (status.equalsIgnoreCase("enable"))
			state = true;
		else if (status.equalsIgnoreCase("disable"))
			state = false;
		if (action.equalsIgnoreCase("Delete instance")) {
			assertEquals("Button status not matched", getStatus("customer_detail_delete_instance"), state);
			logger.info("Done with verifying " + action + " status");
		}
		Thread.sleep(1000);
	}
	
	
	
	}