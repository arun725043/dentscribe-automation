package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_001_008_030_LoginSmsVerificationTest extends AndroidBase {
	
	@Test (priority = 0)
	public void verifyHomePage() throws IOException, InterruptedException 
	{
		// ______________verify home page and its fields_____________________
		ExtentManager.logInfoDetails("Application launched successfully");
		loginPage.verifyHomePageElement();
	}
	
	@Test (priority = 1)
	public void verifyLoginPageWithoutMandatoryFields()
	{
		// _______________verify mandatory fields_____________
		actions.scrollToText("Continue");
		loginPage.verifyLoginMandatoryField();
	}
	
	@Test (priority = 2)
	public void verifyLoginWithNonRegisteredUser() throws IOException, InterruptedException
	{
		// __________verify invalid email format error should display______________
		loginPage.validateWrongEmailId("kapoor.arun@gmail.c");
		
		// ______________verify login with wrong credentials______________
		assertTrue(loginPage.loginApplication("kapoor@gmail.com", "kapoor@1234", "no record"));
	}
	
	@Test (priority = 3)
	public void verifyLoginWithInvalidUsernameOrPassword() throws InterruptedException, IOException
	{
		// ______________verify login with valid credentials______________
		Thread.sleep(10000);
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), "kapoor@123", "invalid"));
		
	}
	
	@Test (priority = 4)
	public void verifyLoginWithThreeInvalidAttempts() throws InterruptedException, IOException
	{
		// ______________verify login with valid credentials______________
		Thread.sleep(5000);
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), "kapoor@123", "invalid"));
		loginPage.enterUsernamePassword(readData("userDetails", "username"), "kapoor@123");
		actions.scrollableClick("Continue");
		IsElementPresent(driver, CommonLocators.errorMessageForThreeFailAttempts, "Error message");
		ExtentManager.logInfoDetails("User not able to login because expected error message found - <b>" + getText(CommonLocators.errorMessageForThreeFailAttempts) + "<b>");
		
		Thread.sleep(5000);
		actions.scrollableClick("Continue");
		IsElementPresent(driver, CommonLocators.errorMessageLockedUser, "Error message");
		ExtentManager.logInfoDetails("User not able to login because expected error message found - <b>" + getText(CommonLocators.errorMessageLockedUser) + "<b>");	
	}
	
	@Test (priority = 5)
	public void verifyLoginWithValidUsernamePassword() throws InterruptedException, IOException
	{
		// ______________verify login with valid credentials______________
		Thread.sleep(5000);
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
	}
	
	@Test (priority = 6)
	public void verifyBlankInvalidOtp() throws InterruptedException
	{
		// _____________validate OTP with empty_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP("");

		// ____________validate OTP with wrong value_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP(readData("testData", "otp"));
		Thread.sleep(5000);
	}
	
	@Test (priority = 7)
	public void verifyResendLinkCodeAndBackIcon() throws InterruptedException
	{
		// ___________validate ReSend Code link_____________
		smsVerificationPage.verifyResendCodeLink();

		// ________________validate back button_____________
		assertTrue(smsVerificationPage.verifyBackIcon("login"));
		Thread.sleep(5000);
	}
	
	@Test (priority = 8)
	public void verifySmsVerificationWithValidOTP() throws InterruptedException
	{		
		actions.scrollableClick("Continue");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.validateValidOTP(getOtp, "Tour Screen");
	}
}
