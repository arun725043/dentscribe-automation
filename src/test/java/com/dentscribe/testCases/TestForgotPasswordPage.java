package com.dentscribe.testCases;

import org.testng.annotations.Test;

import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class TestForgotPasswordPage extends AndroidBase {

	@Test (priority = 1)
	public void verityIsForgotPasswordPageExists() throws InterruptedException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		loginPage.validateLoginPage();
		
		// To perform click on forgot password link and verify forgot password page
		click(driver, loginPage.linkForgotPassword, "Forgot password link");
		forgotPasswordPage.validateForgotPasswordPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verityIsForgotPasswordPageExists" })
	public void verifyForgotPasswordMandatoryFields()
	{
		// To verify 'User Name is required' after clicking on continue button without enter email
		forgotPasswordPage.verifyForgotPasswordWithoutEmail();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verityIsForgotPasswordPageExists" })
	public void verifyForgotPasswordForWrongEmailId()
	{
		// To verify 'enter valid email' message
		forgotPasswordPage.verifyForgotPasswordWithWrongEmail(readData(CommonVariables.inputFileTestData, "wrongEmailId"));
	}
	
	@Test (priority = 4, dependsOnMethods = { "verityIsForgotPasswordPageExists" })
	public void verifyForgotPasswordForNotExistsEmailId()
	{
		// To verify 'No record found' error message
		forgotPasswordPage.verifyForgotPasswordWithNonExistingEmail(readData(CommonVariables.inputFileTestData, "nonExistingEmailId"));
	}
	
	@Test (priority = 5, dependsOnMethods = { "verityIsForgotPasswordPageExists" })
	public void verifyForgotPasswordForValidEmailId() throws InterruptedException
	{
		//check continue button with existing Username
		Thread.sleep(5000);
		forgotPasswordPage.verifyForgotPasswordWithExistingEmail(readData(CommonVariables.inputFileUserDetails, "username"));
	}
	
	@Test (priority = 6, dependsOnMethods = { "verityIsForgotPasswordPageExists" })
	public void verifyForgotPasswordBackIcon() throws InterruptedException
	{
		// To verify the back icon
		forgotPasswordPage.clickBackIconForgotPasswordPage();
		loginPage.validateLoginPage();
	}
}
