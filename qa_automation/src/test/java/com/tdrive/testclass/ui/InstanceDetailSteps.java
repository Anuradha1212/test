package com.tdrive.testclass.ui;

import org.openqa.selenium.By;

import com.tdrive.ui.main.CustomerManagementPage;
import com.tdrive.ui.main.InstanceDetailPage;
import com.tdrive.utilties.Utility;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InstanceDetailSteps {

	@Then("^I click on instance from instance list$")
	public void I_click_on_instance_from_instance_list() throws Throwable {
		InstanceDetailPage.clickOnInstance();
	}

	@Then("^I verify update instance landing page$")
	public void i_verify_update_instance_landing_page() throws Throwable {
		Utility.isElementPresent(By.xpath("//*[@id='updateInstanceCard']//span[contains(.,'Teradata Database')]"));
	}

	@Then("^I verify slider min max value$")
	public void I_verify_slider_min_max_value() throws Throwable {
		InstanceDetailPage.getSliderAttributeFromJson();
		InstanceDetailPage.verifyMinMaxValueForSlider();
	}
	
	@Then("^I should get instance detail page$")
	public void i_should_get_instance_detail_page() throws Throwable {
		InstanceDetailPage.verifyConfigurationSection();
		InstanceDetailPage.verifyFeatureSection();
		InstanceDetailPage.verifyWatchListElementOnScreen();
	}

	@Then("^I verify watchlist status icon colour$")
	public void i_verify_watchlist_status_icon_colour() throws Throwable {
		InstanceDetailPage.verifyWatchListStatusIcon();
	}

	@Then("^I verify the message text colour same as status icon$")
	public void i_verify_the_message_text_colour_same_as_status_icon() throws Throwable {
		InstanceDetailPage.verifyWatchListLaunchMsgColour();
	}

	@Then("^I verify flyover over status icon$")
	public void i_verify_flyover_over_status_icon() throws Throwable {
		InstanceDetailPage.verifyFlyOverOnStatusIcon();
	}
	
	@When("^I verify cancel button functionality$")
	public void i_verify_cancel_button_functionality() throws Throwable {
		InstanceDetailPage.verifyCancelBtnOnInstanceDtailPage();
	}
	
	@When("^I verify cross button functionality$")
	public void i_verify_cross_button_functionality() throws Throwable {
		InstanceDetailPage.verifyCrossBtnOnInstanceDtailPage();
	}
	
	@Then("^I verify start and end date format$")
	public void i_verify_start_and_end_date_format() throws Throwable {
		InstanceDetailPage.verifyStartAndEndDate();
	}
	
	@Then("^I verify From and To attribute of watchlist$")
	public void i_verify_From_and_To_attribute_of_watchlist() throws Throwable {
		InstanceDetailPage.verifyFrom();
		InstanceDetailPage.verifyTo();
		
	}
	

	

}
