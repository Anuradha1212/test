package com.tdrive.ui.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tdrive.utilties.Utility;

import cucumber.api.java.it.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class InstanceDetailPage {
	private static Logger logger = LoggerFactory.getLogger(InstanceDetailPage.class);
	private static String instanceId;
	public static String[] listValues = {};
	static Map<String, String> mapPropertieSlider = new HashMap<String, String>();

	/**
	 * This method is implemented for clicking on instance
	 * 
	 * 
	 * @throws Exception
	 */
	public static void clickOnInstance() throws Exception {
		logger.info("click on instance");
		Utility.clickToElement(By.xpath(".//*[@id='instanceDataTable']/div/table/tbody/tr/td[1]/span"));
		Thread.sleep(2000);
		String currentUrl = Utility.driver.getCurrentUrl();
		String[] words = currentUrl.split("/");
		instanceId = words[words.length - 2].trim();
		Utility.updatePropFile("instanceId", instanceId);
		logger.info("instanceId: " + instanceId);
		/*
		 * Utility.getUserCredPropInstance();
		 * Utility.credProp.getProperty("instanceId");
		 */
		logger.info("Done with clicking on instance");
	}

	/**
	 * This method is implemented for getting slider property from json
	 * 
	 * 
	 * @throws Exception
	 */
	public static void getSliderAttributeFromJson() throws Exception {
		logger.info("Get slider property from json");
		String response = Utility.getInstanceResponse(instanceId);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response);
		JsonNode propertyActionGroupDTOs = root.path("propertyActionGroupDTOs");
		Set<String> setString = new HashSet<String>();
		for (JsonNode temp : propertyActionGroupDTOs) {
			JsonNode instancePropertyDTO = temp.path("instancePropertyDTOs");
			for (JsonNode property : instancePropertyDTO) {
				String category = "";
				if (property.path("type").asText().equals("NUMBER")) {
					category = property.path("category").asText();
					setString.add(category);
					mapPropertieSlider.put(category,
							property.path("minValue").asText() + "," + property.path("maxValue").asText());
				}
			}
		}
		logger.info("Done with getting Sliders from json:" + setString);

	}

	/**
	 * This method is implemented for verifying minimum and max value for slider
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyMinMaxValueForSlider() throws Exception {
		logger.info("Verify slider minimum and maximum");
		Set<String> properties = mapPropertieSlider.keySet();
		for (String temp : properties) {
			String min, max = "";
			String comparableValue = "";
			min = Utility.getFieldText(
					By.xpath(".//*[@id='" + temp + "']/following::*[@ng-reflect-class-base='min-value'][1]"));
			max = Utility.getFieldText(
					By.xpath(".//*[@id='" + temp + "']/following::*[@ng-reflect-class-base='max-value'][1]"));
			comparableValue = min + "," + max;
			assertEquals(comparableValue, mapPropertieSlider.get(temp));

		}
		logger.info("Done with verifying slider minimum and maximum");
	}

	/*
	 * public static boolean validation(String id1, String label, String Value)
	 * { Utility.isElementPresent(By.xpath(".//*[@id='" + id1 +
	 * "']//*[@placeholder='" + label + "']")); return true;
	 * 
	 * }
	 */

	/**
	 * This method is implemented for verifying slider
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws Exception
	 */
	public static void sliderRange(By locator) throws InterruptedException {
		logger.info("Verify slider slide action.");
		WebElement element = Utility.driver.findElement(locator);
		Actions action = new Actions(Utility.driver);
		action.dragAndDropBy(element, 20, 30).build();
		action.perform();
		logger.info("Done with slider slide action.");
	}

	/**
	 * This method is implemented for verifying cancel button on Update instance
	 * page
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyCancelBtnOnInstanceDtailPage() throws Exception {
		logger.info("Verifying cancel button functionality");
		assertTrue("Fail, cancel button is absent",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("instance_detail_cancel_button"))));
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("instance_detail_cancel_button")));
		Utility.waitForElementBecomeVisible(By.xpath(Utility.prop.getProperty("customer_list_title")), 3);
		assertTrue("Fail,Customer list title is not matched",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_list_title"))));

		logger.info("Done with verifying Cancel button functionality");
	}

	/**
	 * This method is implemented for verifying cross button on Update instance
	 * page
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyCrossBtnOnInstanceDtailPage() throws Exception {
		logger.info("Verifying cross button functionality");
		assertTrue("Fail, cross button is absent",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("instance_detail_cross_button"))));
		Utility.clickToElement(By.xpath(Utility.prop.getProperty("instance_detail_cross_button")));
		Utility.waitForElementBecomeVisible(By.xpath(Utility.prop.getProperty("customer_detail_back_button")), 3);
		assertTrue("Fail,cross button should direct to customer detail page",
				Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_detail_back_button"))));

		logger.info("Done with verifying cross button functionality");
	}

	/**
	 * This method is implemented for verifying elements of watchlist section
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyWatchListElementOnScreen() throws Exception {

		assertTrue("Fail,Watch title is missing or in-valid",
				Utility.isElementPresent(By.xpath("//app-watchlist//md-card-title[contains(.,'Watch List')]")));
		assertTrue("Fail, Updated property title is not valid", Utility.isElementPresent(By
				.xpath("//app-watchlist//md-card-title[contains(.,'Watch List')]/following::h3[contains(., 'abc')]")));
		assertTrue("Fail, Start time is not valid", Utility.isElementPresent(
				By.xpath("//app-watchlist//h3[1][contains(.,'abc')]//following::span[contains(.,'Start :')]")));
		assertTrue("Fail, End time is not valid.",
				Utility.isElementPresent(By.xpath("//app-watchlist//h3//following::span[contains(.,'End ')]")));
		assertTrue("Fail, Property current status is inavlid", Utility
				.isElementPresent(By.xpath("//app-watchlist/md-card/md-card-content/app-launch-action//div[4]")));
		logger.info("Done with verifying elements for watchlist section");

	}

	public static HashMap<String, String> watchListStatusIconColour() throws Exception {
		HashMap<String, String> launchMap = new HashMap<String, String>();
		launchMap.put("SUCCEEDED", "green");
		launchMap.put("FAILED", "red");
		launchMap.put("CANCELED", "grey");
		launchMap.put("RECEIVED", "blue");
		launchMap.put("IN_PROGRESS", "blue");
		launchMap.put("PENDING", "grey");
		launchMap.put("WAITING", "blue");
		launchMap.put("UNKNOWN", "red");
		return launchMap;
	}

	/**
	 * This method is implemented for verifying status icon status and color
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyWatchListStatusIcon() throws Exception {
		logger.info("Verify launch Status");
		Thread.sleep(5000);
		String launchStatus = Utility.getFieldAttribute(By.xpath("//md-icon[contains(text(),'sms')]"),
				"ng-reflect-message");
		logger.info("get launch status:"
				+ Utility.getFieldAttribute(By.xpath("//md-icon[contains(text(),'sms')]"), "ng-reflect-message"));
		Set entrySet = watchListStatusIconColour().entrySet();
		Iterator iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			if (launchStatus.equalsIgnoreCase((String) me.getKey())) {
				String[] launchColour = Utility.getFieldAttribute(
						By.xpath(".//app-watchlist/md-card//app-launch-action//md-icon[@ng-reflect-class-base]"),
						"ng-reflect-class-base").split("-");
				assertEquals("Get colour", me.getValue(), launchColour[1]);
			}
			logger.info("Done with verifying launch status");
		}
	}

	/**
	 * This method is implemented for verifying message color same as status
	 * icon colour
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws Exception
	 */
	public static void verifyWatchListLaunchMsgColour() throws Exception {
		logger.info("Verify launch Status message colour");
		Thread.sleep(5000);
		String[] statusIconColour = Utility.getFieldAttribute(
				By.xpath(".//app-watchlist/md-card//app-launch-action//md-icon[@ng-reflect-class-base]"),
				"ng-reflect-class-base").split("-");
		String[] textMessageColour = Utility
				.getFieldAttribute(By.xpath(".//app-watchlist//app-launch-action//div[4]"), "ng-reflect-ng-class-base")
				.split("-");
		assertEquals("Get colour", textMessageColour[1], statusIconColour[1]);
		logger.info("Done with verifying launch Status message colour.");
	}

	/**
	 * This method is implemented for verifying flyover message
	 * 
	 * @throws Exception
	 */
	public static void verifyFlyOverOnStatusIcon() throws Exception {
		logger.info("Verify launch Status message colour");
		Actions statusFlyOver = new Actions(Utility.driver);
		WebElement statusIcon = Utility.driver
				.findElement(By.xpath(".//app-watchlist/md-card//app-launch-action//md-icon[@ng-reflect-class-base]"));
		Thread.sleep(2000);
		statusFlyOver.clickAndHold(statusIcon).perform();
		String getFlyOverMessage = Utility
				.getFieldText(By.xpath("//*[@class='mat-tooltip ng-trigger ng-trigger-state']"));
		logger.info("Done with verifying launch Status message colour.");
	}

	/**
	 * This method is implemented for verifying configuration section
	 * 
	 * @throws Exception
	 */
	public static void verifyConfigurationSection() throws Exception {
		logger.info("Verify configuration section");
		Utility.getUserCredPropInstance();
		Utility.credProp.getProperty("instanceId");
		String readResponse = Utility.getInstanceResponse(Utility.credProp.getProperty("instanceId"));
		String applicationName, environmentName, instanceName, instanceState, applicationVersion, created,
				lastUpdated = "";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(readResponse);
		applicationName = root.path("applicationName").asText();
		environmentName = root.path("environmentName").asText();
		instanceName = root.path("instanceName").asText();
		instanceState = root.path("instanceState").asText();
		created = root.path("created").asText();
		lastUpdated = root.path("lastUpdated").asText();
		applicationVersion = root.path("applicationVersion").asText();
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='applicationName']")), applicationName);
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='applicationVersion']")), applicationVersion);
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='environmentName']/span[1]")), "environmentName");
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='environmentName']/span[2]")), environmentName);
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='instanceName']/span[1]")), "instanceName");
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='instanceName']/span[2]")), instanceName);
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='created']/span[1]")), "created");
		// assertEquals(Utility.getFieldText(By.xpath(".//*[@id='created']/span[2]")),created
		// );
		verifyTimeFormat(By.xpath(".//*[@id='created']/span[2]"));
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='instanceState']/span[1]")), "instanceState");
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='instanceState']/span[2]")), instanceState);
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='applicationVersion']/span[1]")), "applicationVersion");
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='applicationVersion']/span[2]")), applicationVersion);
		assertEquals(Utility.getFieldText(By.xpath(".//*[@id='lastUpdated']/span[1]")), "lastUpdated");
		// assertEquals(Utility.getFieldText(By.xpath(".//*[@id='lastUpdated']/span[2]")),lastUpdated
		// );
		JsonNode propertyActionGroupDTOs = root.path("propertyActionGroupDTOs");
		for (JsonNode temp : propertyActionGroupDTOs) {
			JsonNode instancePropertyDTO = temp.path("instancePropertyDTOs");
			for (JsonNode tt : instancePropertyDTO) {
				String id = "";
				String label = "";

				if (tt.path("category").asText().equals("config")) {
					id = tt.path("id").asText();
					label = tt.path("label").asText();
					String actualLabel = Utility
							.getFieldText(By.xpath(".//*[@id='" + id + "']//*[contains(.,'" + label + "')]"));

					assertEquals("Fail,label mismatch", actualLabel, label);

				}
			}

		}

	}

	/**
	 * This method is implemented for verifying feature section
	 * 
	 * @throws Exception
	 */
	public static void verifyFeatureSection() throws Exception {
		logger.info("Verify feature section");
		Utility.getUserCredPropInstance();
		Utility.credProp.getProperty("instanceId");
		String readResponse = Utility.getInstanceResponse(Utility.credProp.getProperty("instanceId"));
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(readResponse);
		JsonNode propertyActionGroupDTOs = root.path("propertyActionGroupDTOs");
		for (JsonNode temp : propertyActionGroupDTOs) {
			JsonNode instancePropertyDTO = temp.path("instancePropertyDTOs");
			for (JsonNode property : instancePropertyDTO) {
				String id, label = "";
				if (property.path("category").asText().equals("feature")) {
					id = property.path("id").asText();
					label = property.path("label").asText();
					String expectedLabel = Utility
							.getFieldText(By.xpath(".//*[@id='" + id + "']//*[contains(.,'" + label + "')]"));
					assertEquals("Fail", expectedLabel, label);
					assertFalse("Fail, feature on/off button should be disable",Utility.isElementEnable(By.xpath("//div[@id='columnar']//md-slide-toggle")));

					}
			}
		}
		logger.info("Done with verifying feature section");
	}

	

	public static void verifyStartAndEndDate() throws InterruptedException, ParseException {
		logger.info("Verify Start and end date");
		assertTrue(Utility.isElementPresent(
				By.xpath("//app-watchlist//h3[1][contains(.,'abc')]//following::span[contains(.,'Start :')]")));
		verifyTimeFormatUnderWatchList(
				By.xpath("//app-watchlist//h3[1][contains(.,'abc')]//following::span[contains(.,'Start :')]"));
		Boolean endDate = Utility
				.isElementPresent(By.xpath("//app-watchlist//h3//following::span[contains(.,'End ')]"));
		if (endDate) {
			verifyTimeFormatUnderWatchList(By.xpath("//app-watchlist//h3//following::span[contains(.,'End ')]"));
			logger.info("Done with verifying end time");
		} else {
			logger.info("Job is still in progress,end time is not available");
		}
		logger.info("Done with verifying Start and end date");
	}

	/**
	 * This method is implemented for verifying date format under watchlist
	 * 
	 * @throws InterruptedException
	 * @throws ParseException
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyTimeFormatUnderWatchList(By locator) throws InterruptedException, ParseException {
		SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a");
		String getUITime = Utility.getFieldText(locator);
		int indexOfColon = getUITime.indexOf(":");
		String getUiTime = getUITime.substring(indexOfColon + 1).trim();
		java.util.Date date = (java.util.Date) format.parse(getUiTime);
		assertTrue("Fail, date format mismatch", getUiTime.equals(format.format(date)));

	}

	/**
	 * This method is implemented for verifying time format
	 * 
	 * @throws InterruptedException
	 * @throws ParseException
	 * 
	 * 
	 * @throws Exception
	 */
	public static void verifyTimeFormat(By locator) throws InterruptedException, ParseException {
		SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a");
		String getUITime = Utility.getFieldText(locator);
		String getUiTime = getUITime;
		java.util.Date time = (java.util.Date) format.parse(getUiTime);
		assertTrue("Fail, date format mismatch", getUiTime.equals(format.format(time)));

	}
	
	public static void verifyFrom() throws InterruptedException, ParseException {
		assertTrue("Fail", Utility.isElementPresent(By.xpath("//app-watchlist//input[@placeholder='From']")));

	}
	
	public static void verifyTo() throws InterruptedException, ParseException {
		assertTrue("Fail", Utility.isElementPresent(By.xpath("//app-watchlist//input[@placeholder='To']")));

	}
}
