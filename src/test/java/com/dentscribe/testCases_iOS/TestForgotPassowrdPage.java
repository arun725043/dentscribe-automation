package com.dentscribe.testCases_iOS;

import org.testng.annotations.Test;
import com.dentscribe.base.iOSBase;


public class TestForgotPassowrdPage extends iOSBase {

	@Test (priority = 1)
	public void DS_046_verityIsForgotPasswordPageExists() throws InterruptedException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// To perform click on forgot password link 
		loginPage.performClickOnForgotPasswordLink();
		
		// To verify that user navigated on 'Forgot Password' page
		forgotPasswordPage.validateForgotPasswordPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "DS_046_verityIsForgotPasswordPageExists" })
	public void DS_047_verifyForgotPasswordMandatoryFields()
	{
		// To verify 'User Name is required' after clicking on continue button without enter email
		forgotPasswordPage.verifyUserNameIsRequired();
	}
	
	@Test (priority = 3, dependsOnMethods = { "DS_046_verityIsForgotPasswordPageExists" })
	public void DS_048_verifyForgotPasswordForWrongEmailId()
	{
		// To verify 'enter valid email' message
		forgotPasswordPage.verifyWrongUserName("kapoor.arun");
	}
	
	@Test (priority = 4, dependsOnMethods = { "DS_046_verityIsForgotPasswordPageExists" })
	public void DS_049_verifyForgotPasswordForNotExistsEmailId()
	{
		// To verify 'No record found' error message
		forgotPasswordPage.verifyInvalidUserName("kapoor@gmail.com");
	}
	
	@Test (priority = 5, dependsOnMethods = { "DS_046_verityIsForgotPasswordPageExists" })
	public void DS_050_verifyForgotPasswordForValidEmailId() throws InterruptedException
	{
		// To verify the for send reset password email
		forgotPasswordPage.sendResetPasswordEmail(readData("UserDetails", "username"));
	}
	
	@Test (priority = 6, dependsOnMethods = { "DS_046_verityIsForgotPasswordPageExists" })
	public void DS_051_verifyForgotPasswordBackIcon() throws InterruptedException
	{
		// To verify the back icon
		forgotPasswordPage.clickBackIconForgotPassword();
		loginPage.validateLoginPage();
	}

}
