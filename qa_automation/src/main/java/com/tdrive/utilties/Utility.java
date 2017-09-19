package com.tdrive.utilties;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Utility class contains all actions which using repeatedly.
 *
 */
public class Utility {
	public static WebDriver driver;
	public static Select selectvalue;
	public static ReadXLS testAPIExcel = null;
	public static String strContentType = "application/json";
	public static String defaultSheetName = "APIList";
	public static Properties prop, credProp = null;
	public static FileInputStream objFile, credFile = null;
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	private static String XlsFilePath = "src/test/resources/TestData.xlsx";
	public static String[] data = null;
	public static int RowNum, lastCellNum;
	public static String[] listValues = {};
	private static Logger logger = LoggerFactory.getLogger(Utility.class);
	private static WebDriverWait wait;

	/**
	 * This method is implemented for setting up deriver instance and other
	 * pre-requisites
	 * 
	 * @throws Exception
	 */
	public static void setup() throws Exception {
		logger.info("Initializing driver instance");
		if (driver == null) {

			objFile = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/object.properties");
			prop = new Properties();
			prop.load(objFile);

			String browser = System.getenv("BROWSER");
			if (browser == null) {
				browser = "firefox";
			}
			logger.info("BROWSER selected is:" + browser);
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER_PATH"));
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("--start-maximized");
				options.addArguments("--disable-web-security");
				options.addArguments("--allow-running-insecure-content");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (browser.equalsIgnoreCase("device")) {
				String deviceName = System.getenv("VERSION");
				deviceName = deviceName.replace("_", " ");
				Map<String, String> mobileEmulation = new HashMap<String, String>();
				mobileEmulation.put("deviceName", deviceName);

				Map<String, Object> chromeOptions = new HashMap<String, Object>();
				chromeOptions.put("mobileEmulation", mobileEmulation);
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);

			} else if (browser.equalsIgnoreCase("ie")) {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setJavascriptEnabled(true);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new RemoteWebDriver(new URL(System.getenv("TEST_URL")), cap);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (browser.equalsIgnoreCase("opera")) {
				DesiredCapabilities cap = DesiredCapabilities.operaBlink();
				cap.setBrowserName("opera");
				OperaOptions options = new OperaOptions();
				options.setBinary("/usr/bin/opera");
				options.addArguments("--ignore-certificate-errors");
				cap.setCapability(OperaOptions.CAPABILITY, options);
				OperaDriverService service = new OperaDriverService.Builder()
						.usingDriverExecutable(new File("/usr/local/bin/operadriver")).usingAnyFreePort().build();
				service.start();
				driver = new RemoteWebDriver(service.getUrl(), cap);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (browser.equalsIgnoreCase("android")) {
				driver = new RemoteWebDriver(DesiredCapabilities.android());
			} else {
				FirefoxProfile profile = new FirefoxProfile();
				driver = new FirefoxDriver(profile);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			}

			if (System.getenv("TEST_URL") != null) {
				logger.info("TEST URL:" + System.getenv("TEST_URL"));
				driver.get(System.getenv("TEST_URL"));
			} else {
				logger.error("TEST URL not found");
				System.exit(1);
			}

		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is implemented for creating user credentials properties file
	 * instance
	 * 
	 * @throws IOException
	 */
	public static void getUserCredPropInstance() throws IOException {
		credFile = new FileInputStream(System.getenv("USER_CREDENTIALS_FILE_PATH"));
		credProp = new Properties();
		credProp.load(credFile);
		credFile.close();
	}

	/**
	 * This method is implemented for clearing text box
	 * 
	 * @param locator
	 */
	private static void clearTextField(By locator) {
		logger.debug("Clearing text box...");
		WebElement element = driver.findElement(locator);
		element.clear();
		logger.debug("Done with clearing text box...");
	}

	/**
	 * This method is implemented for sending text to Text Box element
	 * 
	 * @param locator
	 * @param string
	 */
	public static void sendKeysWithText(By locator, String string) {
		logger.debug("Sending text keys...");
		WebElement element = driver.findElement(locator);
		clearTextField(locator);
		element.sendKeys(string);
		logger.debug("Done with Sending text keys...");
	}

	/**
	 * This method is implemented for sending KEYS to Text Box
	 * 
	 * @param locator
	 * @param key
	 *            - ENTER, TAB, RETURN
	 */
	public static void sendKeysWithKey(By locator, Keys key) {
		logger.debug("Sending KEY keys...");
		WebElement element = driver.findElement(locator);
		element.sendKeys(key);
		logger.debug("Done with Sending KEY keys...");
	}

	/**
	 * This method is implemented for sending keys without clearing text box
	 * 
	 * @param locator
	 * @param string
	 */
	public static void sendKeysWithoutClear(By locator, String string) {
		logger.debug("Sending keys without clearing text box...");
		WebElement element = driver.findElement(locator);
		element.sendKeys(string);
		logger.debug("Done with sending keys without clearing text box...");
	}

	/**
	 * This method is implemented for sending keys using JavaScriptExecutor
	 * 
	 * @param locator
	 * @param string
	 */
	public static void sendKeysUsingJavascriptExecutor(By locator, String string) {
		logger.debug("Sending keys to text box using javascriptexecutor..");
		String enterString = string + " ";
		WebElement element = driver.findElement(locator);
		clearTextField(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, enterString);
		sendKeysWithKey(locator, Keys.BACK_SPACE);
		logger.debug("Done with Sending keys to text box using javascriptexecutor...");
	}

	/**
	 * This method is implemented for sending keys/text to specific text box
	 * when there is multiple element with same locator
	 * 
	 * @param locator
	 * @param string
	 * @param elementNumber
	 */
	public static void sendKeysForSpecificTextField(By locator, String string, int elementNumber) {
		logger.debug("Sending text for specific TextField...");
		List<WebElement> element = driver.findElements(locator);
		WebElement textElem;
		if (element.size() == 1) {
			textElem = element.get(0);
		} else {
			textElem = element.get(elementNumber - 1);
		}

		textElem.clear();
		textElem.sendKeys(string);
		logger.debug("Done with sending text for specific TextField...");
	}

	/**
	 * This method is implemented for pressing ENTER key
	 * 
	 * @throws AWTException
	 */
	public static void pressEnterKey() throws AWTException {
		logger.debug("Pressing ENTER keys");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		logger.debug("Done with Pressing ENTER keys");
	}

	/**
	 * This method is implemented for pressing TAB key
	 * 
	 * @throws AWTException
	 */
	public static void pressTabKey() throws AWTException {
		logger.debug("Pressing TAB keys");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		logger.debug("Done with Pressing TAB keys");
	}

	/**
	 * This method is implemented for clicking on given element
	 * 
	 * @param string
	 * @throws InterruptedException
	 */
	public static void clickToElement(By string) throws InterruptedException {
		logger.debug("Clicking To Element");
		for (int i = 0; i < 2; i++) {
			try {
				pageScrollIntoElement(string);
				driver.findElement(string).click();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		logger.debug("Done with clicking To Element");
	}

	/**
	 * This method is implemented for getting field text length
	 * 
	 * @param locator
	 * @return
	 */
	public static Integer getfieldTextLength(By locator) {
		logger.debug("Getting text length of element");
		WebElement element = driver.findElement(locator);
		int textLen = element.getText().length();
		logger.debug("Getting text length of element");
		return textLen;
	}

	public static String getFieldText(By locator) throws InterruptedException {
		logger.debug("Getting text of element");
		String fieldText = null;
		for (int i = 0; i < 5; i++) {
			try {
				WebElement element = driver.findElement(locator);
				fieldText = element.getText();
				break;
			} catch (StaleElementReferenceException e) {
				Thread.sleep(1000);
			}
		}

		logger.debug("Done with Getting text of element");
		return fieldText;
	}

	/**
	 * This method is implemented for verifying given element is present on web
	 * page or not
	 * 
	 * @param by
	 * @return
	 */
	public static boolean isElementPresent(By by) {
		logger.debug("Verifying Element Present on webpage");
		boolean present;
		try {
			driver.findElement(by);
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
		}
		logger.debug("Done with Verifying Element Present on webpage");
		return present;
	}

	/**
	 * This method is implemented for taking screenshot
	 * 
	 * @param stepName
	 * @throws IOException
	 */
	public static String takeScreenhot(String stepName) throws IOException {
		logger.debug("Trying to take Screenshot");
		TakesScreenshot ts = (TakesScreenshot) driver;
		String source = ts.getScreenshotAs(OutputType.BASE64);
		// FileUtils.copyFile(source, new File("./target/Screenshots/" +
		// stepName
		// + ".png"));
		logger.debug("Done with taking Screenshot");
		return source;
	}

	/**
	 * This method is implemented for waiting for given element become visible
	 * on web page
	 * 
	 * @param locator
	 * @param time
	 */
	public static void waitForElementBecomeVisible(By locator, int time) {
		logger.debug("Waiting for element become visible");
		wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		logger.debug("Done with Waiting for element become visible");
	}

	public static void waitForElementBecomeClickable(By locator, int time) {
		logger.debug("Waiting for element become clickable");
		wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		logger.debug("Done with waiting for element become clickable");
	}

	/**
	 * This method is implemented for setting Excel file
	 * 
	 * @param sheetName
	 * @throws Exception
	 */
	public static void setExcelFile(String sheetName) throws Exception {
		logger.debug("Setting Excel file");
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(XlsFilePath);

			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);

		} catch (Exception e) {
			throw (e);
		}
		logger.debug("Done with setting Excel file");
	}

	/**
	 * This method is implemented for getting cell data
	 * 
	 * @param sheetName
	 * @param RowNum
	 * @param ColNum
	 * @return
	 * @throws Exception
	 */
	public static String getCellData(String sheetName, int RowNum, int ColNum) throws Exception {
		logger.debug("Getting cell data");
		setExcelFile(sheetName);
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			logger.debug("Done with getting cell data");
			return CellData;
		} catch (Exception e) {
			logger.error("Cell not found");
			return "";
		}
	}

	/**
	 * This method is implemented for setting cell data
	 * 
	 * @param sheetName
	 * @param RowNum
	 * @param ColNum
	 * @param Result
	 * @throws Exception
	 */
	public static void setCellData(String sheetName, String searchText, int cellNum, String changeText)
			throws Exception {
		logger.debug("Setting cell data");
		setExcelFile(sheetName);
		RowNum = findCellIndexOfString(sheetName, searchText);

		try {
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(cellNum);

			if (Cell == null) {
				Cell = Row.createCell(cellNum);
				Cell.setCellValue(changeText);
			} else {
				Cell.setCellValue(changeText);
			}

			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(XlsFilePath);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			logger.debug("Done with setting cell data");
		} catch (Exception e) {
			throw (e);
		}
	}

	/**
	 * This method is implemented for finding cell index of string
	 * 
	 * @param sheetName
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static int findCellIndexOfString(String sheetName, String text) throws Exception {
		setExcelFile(sheetName);
		logger.debug("Finding Cell Index of String");
		int i;
		int rowCount = ExcelWSheet.getLastRowNum() - ExcelWSheet.getFirstRowNum();

		for (i = 1; i < rowCount; i++) {
			XSSFRow row = ExcelWSheet.getRow(i);
			if (text.equalsIgnoreCase("yes") | text.equalsIgnoreCase("no"))
				Cell = row.getCell(row.getLastCellNum() - 1);
			else
				Cell = row.getCell(row.getFirstCellNum());
			String CellData = Cell.getStringCellValue();
			if (CellData.equalsIgnoreCase(text)) {
				break;
			}
		}
		return i;
	}

	/**
	 * This method is implemented for getting Row data of last cell
	 * 
	 * @param sheetName
	 * @param text
	 * @throws Exception
	 */
	public static void getRowDataOfLastCell(String sheetName, String text) throws Exception {
		logger.debug("Getting Row Data of Last Cell");
		if (text.equalsIgnoreCase("yes"))
			RowNum = findCellIndexOfString(sheetName, "no") - 1;
		else if (text.equalsIgnoreCase("no"))
			RowNum = findCellIndexOfString(sheetName, text);
		else
			RowNum = findCellIndexOfString(sheetName, text);
		lastCellNum = ExcelWSheet.getRow(RowNum).getLastCellNum();
		data = new String[lastCellNum];
		for (int i = 0; i < lastCellNum; i++) {
			data[i] = getCellData(sheetName, RowNum, i);
		}
		logger.debug("Done with getting Row Data of Last Cell");
	}

	/**
	 * This method is implemented for accepting pop up
	 */
	public static void acceptPopUp() {
		logger.debug("Accepting Popup");
		Alert popUp = Utility.driver.switchTo().alert();
		popUp.accept();
		logger.debug("Done with Accepting Popup");
	}

	/**
	 * This method is implemented for reading JSON file
	 */
	public static <E> void readJsonFile() {
		logger.debug("Reading JSON file");
		JSONParser parser = new JSONParser();
		String[] listKeys = {};
		int i = 0;

		try {
			Object object = parser.parse(new FileReader(""));

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;
			listValues = new String[listKeys.length];
			// Reading the keys
			for (String s : listKeys) {
				listValues[i] = jsonObject.get(s).toString();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("Done with Reading JSON file");
	}

	/**
	 * This method is implemented for scroll to the element
	 */
	public static void pageScrollIntoElement(By locator) {
		logger.info("Scrolling Down the page...");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
		logger.info("Done with Scrolling Down the page");
	}

	/**
	 * This method is implemented for getting field Attribute length
	 * 
	 * @param locator
	 * @return
	 */
	public static Integer getfieldAttributeLength(By locator) {
		logger.debug("Getting text length of element");
		WebElement element = driver.findElement(locator);
		int textLen = element.getAttribute("value").length();
		logger.debug("Getting text length of element");
		return textLen;
	}

	/**
	 * This method is implemented for getting field Attribute
	 * 
	 * @param locator
	 * @return
	 */
	public static String getFieldAttribute(By locator, String value) {
		logger.debug("Getting text of element");
		WebElement element = driver.findElement(locator);
		logger.debug("Done with fetting text of element");
		return element.getAttribute(value);
	}

	/**
	 * This method is implemented for getting field Attribute
	 * 
	 * @param locator
	 * @return
	 */
	public static String getfieldAttribute(By locator) {
		logger.debug("Getting text of element");
		WebElement element = driver.findElement(locator);
		logger.debug("Done with fetting text of element");
		return element.getAttribute("value");
	}

	/**
	 * This method is implemented for refresh page
	 * 
	 * @param locator
	 * @param time
	 */
	public static void refreshBrowserPage(By locator, int time) {
		driver.navigate().refresh();
		waitForElementBecomeVisible(locator, time);
	}

	/**
	 * This method is implemented for update properties file values
	 * 
	 * @param key
	 * @param value
	 */
	public static void updatePropFile(String key, String value) {
		logger.info("Updating properties file...");

		try {
			if (credFile != null) {
				credFile.close();
			}

			FileInputStream in = new FileInputStream(System.getenv("USER_CREDENTIALS_FILE_PATH"));
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(System.getenv("USER_CREDENTIALS_FILE_PATH"));
			props.setProperty(key, value);
			props.store(out, null);
			out.close();
		} catch (IOException io) {
			io.printStackTrace();
		}

		logger.info("Done with updating properties file");
	}

	/**
	 * This method is implemented for verifying given element is enable on web
	 * page or not
	 * 
	 * @param by
	 * @return
	 */
	public static boolean isElementEnable(By locator) {
		logger.debug("Verifying Element enable on webpage");
		boolean enable;
		boolean elementEnable = driver.findElement(locator).isEnabled();
		if (elementEnable) {
			enable = true;
		} else {
			enable = false;
		}
		logger.debug("Done with Verifying Element enable/disable on webpage");
		return enable;
	}

	/**
	 * This method is implemented for getting time stamp
	 * 
	 * 
	 * @return
	 */
	public static String getTimeStamp() {
		String currentTime;
		Calendar rightNow = Calendar.getInstance();
		int month = rightNow.get(Calendar.MONTH) + 1;
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		int hour = rightNow.get(Calendar.HOUR);
		int min = rightNow.get(Calendar.MINUTE);
		currentTime = "" + month + day + hour + min;
		return currentTime;
	}

	public static void slide(By locator) throws Exception {
		Utility.waitForElementBecomeVisible(locator, 5);
		WebElement elementToSlide = Utility.driver.findElement(locator);
		Actions action = new Actions(driver);
		action.moveToElement(elementToSlide);
		action.dragAndDropBy(elementToSlide, 80, 10).perform();
		action.build();
		Thread.sleep(1000);

	}

	/**
	 * This method is implemented for getting instance response
	 * 
	 * 
	 * @throws Exception
	 */
	public static String getInstanceResponse(String instanceId) throws ClientProtocolException, IOException {
		StringBuffer result = new StringBuffer();
		HttpClient client = new DefaultHttpClient();
		String url = "http://localhost:32006/gateway-service/rest/v1/api/gateway/instances/" + instanceId;
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		try {
			if (responseCode == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					result.append(line);
					// System.out.println(result.toString());
				}
			}
			return result.toString();
		} catch (Exception ex) {
			result.append("Get Response Failed");
			return result.toString();
		}

	}

}
