package com.tdrive.testclass.ui;

import org.openqa.selenium.By;
import com.tdrive.ui.main.CommonPage;
import com.tdrive.ui.main.LoginPage;
import com.tdrive.utilties.Utility;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStep {
	
	public static String username, password;
	@Given("I login to tdrive application")
	public void i_login_to_tdrive_application() throws Exception {
		LoginPage.loginToTdriveApplication();	
		}
	
	@Then("I validate login page opened")
	public void i_validate_login_page() throws InterruptedException {
		LoginPage.validateLoginPageOpened();
	}
	
	@Then("^I validate login page details$")
	public void i_validate_login_page_details() throws Throwable {
		LoginPage.verifyLoginPage();
	}
	
	@Then("^I am on Dashboard page$")
	public void i_am_on_dashboard_page() throws Throwable {
		LoginPage.verifyHomePage();
	}
	
	@When("^I click on menu icon$")
	public void i_click_on_menu_icon() throws Throwable {
		CommonPage.clickToTdriveMenu();
	}

	@Then("^I verify navigation pane with logged in user details$")
	public void i_verify_navigation_pane() throws Throwable {
		CommonPage.verifyMenuIconNavigationPane();
	}
	
	@Then("I signout")
	public void I_signout() throws Throwable {
	CommonPage.signOutFromTDrive();
	}
	
	@When("^I enter \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_enter_and(String userName, String password) throws Throwable {
		LoginPage.loginWithInvalidCredentail(userName, password);
	}

	@Then("^I should get \"([^\"]*)\" message$")
	public void I_should_get_message(String message) throws Throwable {
		LoginPage.verifyInvalidCredentialMessage(message);
	}
	@Given("I am logging as Inavlid username")
	public void I_login_with_invalid_username() throws Exception {
		//LoginPage.loginToTdriveDashboard("amdin", "Opex@123");
	}

	@Given("I am logging as Invalid password")
	public void I_login_with_invalid_password() throws Exception {
		//LoginPage.loginToTdriveDashboard("admin", "adminn");
	}

	@Then("I should get Invalid credential error message")
	public void I_should_get_error_msg() throws Throwable {
		//LoginPage.verifyInvalidCredentialMessage();
	}

	@Given("I am logging with blank space in username and password")
	public void I_login_with_blankspace_in_username_password() throws Exception {
		//LoginPage.loginToTdriveDashboard(" ", " ");
	}
	
	

}
