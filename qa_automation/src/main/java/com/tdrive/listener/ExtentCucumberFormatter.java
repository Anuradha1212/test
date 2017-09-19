package com.tdrive.listener;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import com.tdrive.utilties.Utility;

import cucumber.runtime.CucumberException;
import cucumber.runtime.io.URLOutputStream;

/**
 * Cucumber custom format listener which generates ExtentsReport html file
 */
public class ExtentCucumberFormatter implements Reporter, Formatter {

	private static ExtentReports extent;
	private ExtentTest featureTest;
	private ExtentTest scenarioTest;
	private LinkedList<Step> testSteps = new LinkedList<Step>();
	private static File htmlReportDir;
	private static Map systemInfo;
	private boolean scenarioOutlineTest;
	private static Logger logger = LoggerFactory.getLogger(Utility.class);
	
	private static final Map<String, String> MIME_TYPES_EXTENSIONS = new HashMap() {
		{
			this.put("image/bmp", "bmp");
			this.put("image/gif", "gif");
			this.put("image/jpeg", "jpg");
			this.put("image/png", "png");
			this.put("image/svg+xml", "svg");
			this.put("video/ogg", "ogg");
		}
	};

	public ExtentCucumberFormatter(File filePath) {
		if (!filePath.getPath().equals("")) {
			String reportPath = filePath.getPath();
			this.htmlReportDir = new File(reportPath);
			this.extent = new ExtentReports(reportPath);
		} else {
			String reportDir = "output/Run_" + System.currentTimeMillis();
			this.htmlReportDir = new File(reportDir);
			logger.info("Report Directory:" + reportDir);
			this.extent = new ExtentReports(reportDir + "/report.html");
			logger.info("Extend Report:" + extent);
		}
	}

	public ExtentCucumberFormatter() {
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, DisplayOrder displayOrder,
			NetworkMode networkMode, Locale locale) {
		htmlReportDir = filePath;
		extent = new ExtentReports(filePath.getAbsolutePath(), replaceExisting,
				displayOrder, networkMode, locale);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, DisplayOrder displayOrder,
			NetworkMode networkMode) {
		initiateExtentCucumberFormatter(filePath, replaceExisting,
				displayOrder, networkMode, null);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, DisplayOrder displayOrder, Locale locale) {
		initiateExtentCucumberFormatter(filePath, replaceExisting,
				displayOrder, null, locale);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, DisplayOrder displayOrder) {
		initiateExtentCucumberFormatter(filePath, replaceExisting,
				displayOrder, null, null);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, NetworkMode networkMode, Locale locale) {
		initiateExtentCucumberFormatter(filePath, replaceExisting, null,
				networkMode, locale);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, NetworkMode networkMode) {
		initiateExtentCucumberFormatter(filePath, replaceExisting, null,
				networkMode, null);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			NetworkMode networkMode) {
		initiateExtentCucumberFormatter(filePath, null, null, networkMode, null);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting, Locale locale) {
		initiateExtentCucumberFormatter(filePath, replaceExisting, null, null,
				locale);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Boolean replaceExisting) {
		initiateExtentCucumberFormatter(filePath, replaceExisting, null, null,
				null);
	}

	public static void initiateExtentCucumberFormatter(File filePath,
			Locale locale) {
		initiateExtentCucumberFormatter(filePath, null, null, null, locale);
	}

	public static void initiateExtentCucumberFormatter(File filePath) {
		initiateExtentCucumberFormatter(filePath, null, null, null, null);
	}

	public static void initiateExtentCucumberFormatter() {
		String reportFilePath = "target/cucumber" + File.separator
				+ "index.html";

		initiateExtentCucumberFormatter(new File(reportFilePath), true, null,
				null, null);
	}

	public static void setTestRunnerOutput(String s) {
		extent.setTestRunnerOutput(s);
	}

	public static void loadConfig(File configFile) {
		extent.loadConfig(configFile);
	}

	public static void addSystemInfo(String param, String value) {
		if (systemInfo == null) {
			systemInfo = new HashMap();
		}
		systemInfo.put(param, value);
	}

	public static void addSystemInfo(Map<String, String> info) {
		if (systemInfo == null) {
			systemInfo = new HashMap();
		}
		systemInfo.putAll(info);
	}

	public void before(Match match, Result result) {

	}

	public void result(Result result) {
		if (!scenarioOutlineTest) {
			Random randm = new Random();
			String fileName = null;
			String imgName = null;
			Step step = testSteps.poll();
			String source = null;
			if ("passed".equals(result.getStatus())) {
				scenarioTest.log(LogStatus.PASS,
						step.getKeyword() + step.getName(), "PASSED");
			} else if ("failed".equals(result.getStatus())) {
				if (!step.getName().contains("API")) {
					try {
						fileName = "Screenshot" + randm.nextInt();
						source = Utility.takeScreenhot(fileName);
						imgName = System.getProperty("user.dir")
								+ "/target/Screenshots/" + fileName.toString()
								+ ".png";
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					logger.info("Screenshot attached to report from Location: "
//									+ imgName);
					
					scenarioTest.log(LogStatus.FAIL,
							step.getKeyword() + step.getName(),
							scenarioTest.addBase64ScreenShot(" data:image/png;base64,"+source));
				}
				scenarioTest.log(LogStatus.FAIL,
						step.getKeyword() + step.getName(), result.getError());
				
				
			} else if ("skipped".equals(result.getStatus())) {
				scenarioTest.log(LogStatus.SKIP,
						step.getKeyword() + step.getName(), "SKIPPED");
			} else if ("undefined".equals(result.getStatus())) {
				scenarioTest.log(LogStatus.UNKNOWN,
						step.getKeyword() + step.getName(), "UNDEFINED");
			}
		}
	}

	public void after(Match match, Result result) {

	}

	public void match(Match match) {

	}

	public void embedding(String s, byte[] bytes) {
		if (!scenarioOutlineTest) {
			String extension = (String) MIME_TYPES_EXTENSIONS.get(s);
			String fileName = "screenshot-" + System.currentTimeMillis() + "."
					+ extension;
			this.writeBytesAndClose(bytes,
					this.reportFileOutputStream(fileName));
			scenarioTest.log(LogStatus.INFO,
					scenarioTest.addScreenCapture(fileName));
		}
	}

	public void write(String s) {
		if (!scenarioOutlineTest)
			scenarioTest.log(LogStatus.INFO, s);
	}

	public void syntaxError(String s, String s1, List<String> list, String s2,
			Integer integer) {
	}

	public void uri(String s) {
	}

	public void feature(Feature feature) {
		featureTest = extent.startTest("Feature: " + feature.getName());

		for (Tag tag : feature.getTags()) {
			featureTest.assignCategory(tag.getName());
		}
	}

	public void scenarioOutline(ScenarioOutline scenarioOutline) {
		scenarioOutlineTest = true;
	}

	public void examples(Examples examples) {
	}

	public void startOfScenarioLifeCycle(Scenario scenario) {
		scenarioTest = extent.startTest("Scenario: " + scenario.getName());

		for (Tag tag : scenario.getTags()) {
			scenarioTest.assignCategory(tag.getName());
		}
		scenarioOutlineTest = false;
	}

	public void background(Background background) {
	}

	public void scenario(Scenario scenario) {
	}

	public void step(Step step) {
		if (!scenarioOutlineTest)
			testSteps.add(step);
	}

	public void endOfScenarioLifeCycle(Scenario scenario) {
		if (!scenarioOutlineTest) {
			extent.endTest(scenarioTest);
			featureTest.appendChild(scenarioTest);
		}
	}

	public void done() {
	}

	public void close() {
		extent.addSystemInfo(systemInfo);
		extent.close();
	}

	public void eof() {
		extent.endTest(featureTest);
		extent.flush();
	}

	private OutputStream reportFileOutputStream(String fileName) {
		try {
			return new URLOutputStream(new URL(this.htmlReportDir.toURI()
					.toURL(), fileName));
		} catch (IOException var3) {
			throw new CucumberException(var3);
		}
	}

	private void writeBytesAndClose(byte[] buf, OutputStream out) {
		try {
			out.write(buf);
		} catch (IOException var4) {
			throw new CucumberException(
					"Unable to write to report file item: ", var4);
		}
	}
}