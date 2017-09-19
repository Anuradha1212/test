package com.tdrive.testclass.ui;

import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import com.tdrive.ui.main.CustomerManagementPage;
import com.tdrive.utilties.Utility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomerManagementStep {

	@Then("^I verify customer management list page details$")
	public void i_verify_customer_management_list_page_details() throws Throwable {
		CustomerManagementPage.verifyCustomerListDetails();
	}

	@And("^I enter customer detail to search \"([^\"]*)\"$")
	public void i_enter_customer_detail_to_search(String customerName) throws Throwable {
		CustomerManagementPage.searchCustomer(customerName);
	}
	
	@Then("^I verify search option filter valid value$")
	public void i_verify_search_option_filter_valid_value() throws Throwable {
		CustomerManagementPage.VerifyValidSearch();
	}

	@When("^I click on \"([^\"]*)\" customer details$")
	public void i_click_on_customer_details(String customerName) throws Throwable {
		CustomerManagementPage.clickOnSearchCustomer(customerName);
	}

	@Then("^I verify \"([^\"]*)\" customer detail title$")
	public void i_verify_customer_detail_title(String customerName) throws Throwable {
		CustomerManagementPage.VerifyCustomerDetailTitle(customerName);
	}

	@Then("^I verify \"([^\"]*)\" customer detail page$")
	public void i_verify_customer_detail_page(String customerName) throws Throwable {
		CustomerManagementPage.VerifyCustomerDetailPage(customerName);
	}

	@Then("^I verify \"([^\"]*)\" pagination bar is displayed$")
	public void i_verify_pagination_bar_is_displayed(String pageName) throws Throwable {
		if (pageName.equalsIgnoreCase("customer list")) {
			assertTrue("Fail,Customer pagination bar is absent",
					Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_list_pagingbar"))));
		} else if (pageName.equalsIgnoreCase("customer detail")) {
			assertTrue("Fail,Customer pagination bar is absent",
					Utility.isElementPresent(By.xpath(Utility.prop.getProperty("customer_detail_pagingbar"))));
		}

	}

	@Then("^I verify pagination functionality$")
	public void i_verify_pagination_functionality() throws Throwable {
		CustomerManagementPage.verifyPaginatonLastClick();
		Thread.sleep(2000);
		CustomerManagementPage.verifyPaginatonPreviousClick();
		CustomerManagementPage.verifyPaginatonFirstClick();
		CustomerManagementPage.verifypagination();
	}

	@Then("^I verify \"([^\"]*)\" \"([^\"]*)\" order$")
	public void i_verify_sort_button_for_customer_name(String columnHeading, String order) throws Throwable {
		CustomerManagementPage.compareList(columnHeading, order);
	}

	@Then("^I verify back button functionality$")
	public void i_verify_back_button_functionality() throws Throwable {
		CustomerManagementPage.clickBackButton();
		CustomerManagementPage.verifyCustomerListDetails();
	}

	@Then("^I verify action status \"([^\"]*)\" as \"([^\"]*)\"$")
	public void i_verify_action_status_as(String action, String status) throws Throwable {
		CustomerManagementPage.verifyActionStatus("Update instance", "enable");
	}
	
}
