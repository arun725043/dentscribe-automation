package com.dentscribe.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class TestSmsVerificationPageAfterSignup extends AndroidBase 
{	
	@Test (priority = 1)
	public void verifyIsSmsVerificationPageExistsAfterSignup() throws IOException, InterruptedException
	{
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignupPage();
		
		// ____________________Fill SignUp form and verify confirmation PopUp button_________________
		String password = CommonVariables.generatePassword;
		signUpPage.fillSignupForm(genrateRandomFirstName(), "", readData("testData", "countryCode"), readData("testData", "mobile"), CommonVariables.generateEmailId, 
				"", otpTextboxes, "", String.valueOf(GenerateRandomNumber(6)), password, password, readData("testData", "pmsName"));
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
		
		//___________Click on Confirmation PopUp button______________
		signUpPage.clickSignupConfirmationPopupButtons("continue");
		smsVerificationPage.validateSmsVerificationPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsSmsVerificationPageExistsAfterSignup" })
	public void verifySmsVerificationWithBlankOtp() throws InterruptedException
	{
		// _____________validate OTP with empty_____________
		smsVerificationPage.validateSmsVerificationPage();
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
	public void verifyWhetherSmsVerificationPageBackIconRedirectToSignupPage() throws InterruptedException
	{
		// ________________validate back button_____________
		smsVerificationPage.clickcBackIconSmsVerificationPage();
		Thread.sleep(5000);
		IsElementPresent(driver, signUpPage.labelPMSDropdown, "PMS Dropdown field");
		ExtentManager.logInfoDetails("<b>User is back to Signup page as expected");
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyWhetherSmsVerificationPageBackIconRedirectToSignupPage" })
	public void verifySmsVerificationWithValidOtp() throws IOException, InterruptedException
	{
		// Go to sms verification page again
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
		signUpPage.clickSignupConfirmationPopupButtons("continue");
		smsVerificationPage.validateSmsVerificationPage();
		// To fill the otp
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		// verify whether practice info page opened
		practiceInfoPage.validatePracticeInfoPage();
	}
}
