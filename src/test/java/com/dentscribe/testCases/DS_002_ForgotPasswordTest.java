package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;

public class DS_002_ForgotPasswordTest extends AndroidBase {

	@Test (priority = 0)
	public void verifyIsForgotPasswordPageExists() throws InterruptedException {
		
		// ______________verify home page_____________________
		assertTrue(loginPage.validateLoginPageNote());
		ExtentManager.logInfoDetails("Application launched successfully");

		//check whether user landed on Forgot password page or not
		assertTrue(forgotPasswordPage.verifyForgotPasswordLandingPage());
	}
	
	@Test (priority = 1)
	public void verifyForgotPasswordContinueButtonWithoutEmailId() throws InterruptedException
	{
		//check continue button with blank Username 
		assertTrue(forgotPasswordPage.verifyForgotPasswordWithoutEmail());
	}

	@Test (priority = 2)
	public void verifyForgotPasswordContinueButtonWithWrongEmailId() throws InterruptedException
	{				
		//check continue button with wrong Username 
		assertTrue(forgotPasswordPage.verifyForgotPasswordWithWrongEmail(readData("testData", "wrongEmailId")));
	}
	
	@Test (priority = 3)
	public void verifyForgotPasswordContinueButtonWithNonExistingEmail() throws InterruptedException
	{		
		//check continue button with non existing Username
		forgotPasswordPage.verifyForgotPasswordWithNonExistingEmail(readData("testData", "nonExistingEmailId"));
	}
	
	@Test (priority = 4)
	public void verifyForgotPasswordContinueButtonWithExistingEmail() throws InterruptedException
	{		
		//check continue button with existing Username
		Thread.sleep(5000);
		forgotPasswordPage.verifyForgotPasswordWithExistingEmail(readData("userDetails", "username"));
		
		//Verify back button functionality
		forgotPasswordPage.clickVerifyBackIcon();
	}
	
	@Test (priority = 5)
	public void verifyForgotPasswordBackIcon() throws InterruptedException
	{		
		//Verify back button functionality
		forgotPasswordPage.clickVerifyBackIcon();
	}
}
