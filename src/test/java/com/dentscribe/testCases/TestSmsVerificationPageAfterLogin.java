package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;


public class TestSmsVerificationPageAfterLogin extends AndroidBase 
{	
	@Test (priority = 1)
	public void verifyIsSmsVerificationPageExistsAfterLogin() throws IOException, InterruptedException
	{
		loginPage.verifyIsApplicationLaunched();
		// login with valid credentials
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsSmsVerificationPageExistsAfterLogin" })
	public void verifySmsVerificationWithBlankOtp() throws InterruptedException
	{
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		// _____________validate OTP with empty_____________
		smsVerificationPage. validateSmsVerificationPage();
		// _____________validate OTP with empty_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP("");
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifySmsVerificationWithBlankOtp" })
	public void verifySmsVerificationWithWrongOtp() throws InterruptedException
	{
		// ____________validate OTP with wrong value_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP(readData("testData", "otp"));
		Thread.sleep(5000);
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifySmsVerificationWithWrongOtp" })
	public void verifySmsVerificationResendCodeLink() throws InterruptedException
	{
		// ___________validate ReSend Code link_____________
		smsVerificationPage.verifyResendCodeLink();
	}
	
	@Test (priority = 5)
	public void verifyWhetherSmsVerificationPageBackIconRedirectToLoginPage() throws InterruptedException
	{
		// ________________validate back button_____________
		smsVerificationPage.clickcBackIconSmsVerificationPage();
		Thread.sleep(5000);
		loginPage.validateLoginPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyWhetherSmsVerificationPageBackIconRedirectToLoginPage" })
	public void verifySmsVerificationWithValidOtp() throws IOException, InterruptedException
	{
		// login with valid credentials
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		tourPages.validateTourPageCalendarScheduleView();
	}
}
