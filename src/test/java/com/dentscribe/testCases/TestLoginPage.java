package com.dentscribe.testCases;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;


public class TestLoginPage extends AndroidBase {

	@Test (priority = 1)
	public void verifyIsLoginPageExistsAndItsFields() throws IOException, InterruptedException 
	{
		loginPage.verifyIsApplicationLaunched();
		loginPage.validateLoginPage();
		// ______________verify home page_____________________
		loginPage.verifyHomePageElement();
	}

	@Test (priority = 2, dependsOnMethods = { "verifyIsLoginPageExistsAndItsFields" })
	public void verifyLoginWithoutMandatoryFields()
	{
		// _______________verify mandatory fields_____________
		loginPage.verifyLoginMandatoryField();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyLoginWithoutMandatoryFields" })
	public void verifyLoginWithNonExistingUser() throws IOException, InterruptedException
	{
		// __________verify invalid email format error should display______________
		loginPage.validateWrongEmailId("kapoor.arun@gmail.c");
				
		// ______________verify login with wrong credentials______________
		loginPage.loginApplication("kapoor@gmail.com", "kapoor@1234", "no record");
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyLoginWithNonExistingUser" })
	public void verifyLoginWithInvalidPassword() throws IOException, InterruptedException
	{	
		// ______________verify login with valid credentials______________
		Thread.sleep(10000);
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), "kapoor@123", "invalid");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyLoginWithInvalidPassword" })
	public void verifyLoginWithThreeInvalidAttempts() throws IOException, InterruptedException
	{	
		// ______________verify login with invalid credentials______________
		Thread.sleep(5000);
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), "kapoor@123", "invalid");
		loginPage.enterUsernamePassword(readData(CommonVariables.inputFileUserDetails, "username"), "kapoor@123");
		actions.scrollableClick("Continue");
		IsElementPresent(driver, CommonLocators.errorMessageForThreeFailAttempts, "Error message");
		ExtentManager.logInfoDetails("User not able to login because expected error message found - <b>" + getText(CommonLocators.errorMessageForThreeFailAttempts) + "<b>");
		
		Thread.sleep(5000);
		actions.scrollableClick("Continue");
		IsElementPresent(driver, CommonLocators.errorMessageLockedUser, "Error message");
		ExtentManager.logInfoDetails("User not able to login because expected error message found - <b>" + getText(CommonLocators.errorMessageLockedUser) + "<b>");	
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyLoginWithThreeInvalidAttempts" })
	public void verifyLoginWithValidCredentials() throws IOException, InterruptedException
	{
		// login with valid credentials
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), readData(CommonVariables.inputFileUserDetails, "password"), "valid");
	}
}
