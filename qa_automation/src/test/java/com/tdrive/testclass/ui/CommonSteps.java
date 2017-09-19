package com.tdrive.testclass.ui;
import org.openqa.selenium.By;

import com.tdrive.ui.main.CommonPage;
import com.tdrive.ui.main.CustomerManagementPage;
import com.tdrive.utilties.Utility;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CommonSteps {
	@Given("I open T-Drive url on browser")
	public void i_open_tdrive_url() {
		//Utility.driver.get(System.getenv("TEST_URL"));
	}

	@And("I navigate to \"([^\"]*)\" page")
	public static void i_navigate_to(String navigateTo) throws InterruptedException {
		CommonPage.clickToTdriveMenu();
		if (navigateTo.equalsIgnoreCase("dashboard"))
			CommonPage.clickOnDashboardBtn();
		else if (navigateTo.equalsIgnoreCase("Customer management"))
			CommonPage.clickOnCustomerManagementBtn();
	}

	@Then("I Verify \"([^\"]*)\" Page on screen")
	public void I_should_be_on_home_page(String pageName) throws Throwable {
		if (pageName.equalsIgnoreCase("dashboard"))
			// DashboardPage.verifyDashboardPageOnScreen();
			System.out.println("InProgess");
		else if (pageName.equalsIgnoreCase("Customer management"))
			CustomerManagementPage.verifyCustomerPageOnScreen();
	}

}
