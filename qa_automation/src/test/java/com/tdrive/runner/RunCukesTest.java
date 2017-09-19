package com.tdrive.runner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.Select;
import com.tdrive.listener.ExtentCucumberFormatter;
import com.tdrive.utilties.ReadXLS;
import com.tdrive.utilties.Utility;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features/ui/" }, glue = { "com.tdrive.testclass.ui" }, monochrome = true, plugin = {
		"com.tdrive.listener.ExtentCucumberFormatter", "pretty",
		"json:target/cucumber/index.json" }, tags = { "@sanityTest1" })

public class RunCukesTest {

	@BeforeClass
	public static void setup() throws Exception {
		ExtentCucumberFormatter.initiateExtentCucumberFormatter();
		ExtentCucumberFormatter.loadConfig(new File(
				"src/test/resources/extent-config.xml"));

		ExtentCucumberFormatter.addSystemInfo("Browser Name",
				System.getenv("BROWSER"));
		ExtentCucumberFormatter.addSystemInfo("Browser version", "v.56.0");
		ExtentCucumberFormatter.addSystemInfo("Selenium version", "v2.53.0");

		Map systemInfo = new HashMap();
		systemInfo.put("Cucumber version", "v1.2.3");
		systemInfo.put("Extent Cucumber Reporter version", "v1.1.0");
		ExtentCucumberFormatter.addSystemInfo(systemInfo);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		//Utility.updatePropFile("admin", "Opex@123");
	}
}