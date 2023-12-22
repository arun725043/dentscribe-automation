package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertFalse;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;

import Api.GenerateOTP;

public class TestSmsVerificationPageAfterSignup extends iOSBase {
	
	@Test (priority = 1)
	public void DS_024_verifyIsSmsVerificationPageExistsAfterSignup() throws IOException, InterruptedException
	{
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignUpPage();
		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), CommonMethods.generateRandomFirstName() + "@gmail.com", "Dentrix");
		signUpPage.verifyConfirmationPopup();
		
		//___________Click on Confirmation popup button______________
		signUpPage.clickConfirmationPopupButton("continue");
	}
	
	@Test (priority = 2, dependsOnMethods = { "DS_024_verifyIsSmsVerificationPageExistsAfterSignup" })
	public void DS_025_verifySmsVerificationWithBlankOtp()
	{
		// _____________validate OTP with empty_____________
		signUpPage.validateSmsVerificationPage();
		signUpPage.verifyEmptyOTP();
	}
	
	@Test(priority = 3, dependsOnMethods = { "DS_024_verifyIsSmsVerificationPageExistsAfterSignup" })
	public void DS_026_verifySmsVerificationWithWrongOtp()
	{
		// ____________validate otp with wrong value_____________
		GenerateOTP.fillOtp(driver, signUpPage.inputWrongOtp());
		signUpPage.verifyWrongOtp();
	}
	
	@Test(priority = 4, dependsOnMethods = { "DS_024_verifyIsSmsVerificationPageExistsAfterSignup" })
	public void DS_027_verifySmsVerificationResendCodeLink() throws InterruptedException
	{
		// ___________validate resend link_______________
		click(driver, signUpPage.linkResendCode);
		Thread.sleep(5000);
		assertFalse(driver.getPageSource().contains("00:00"));
		ExtentManager.logInfoDetails("Timer is started again as expected");
	}
	
	@Test (priority = 5)
	public void DS_027_verifyWhetherSmsVerificationPageBackIconRedirectToSignupPage()
	{
		// ________________validate back button_____________
		signUpPage.verifyBackIconButton();
		IsElementPresent(driver, signUpPage.selectYourPracticeManagement);
		ExtentManager.logInfoDetails("<b>User is now back on Signup page as expected");
	}
	
	@Test (priority = 6, dependsOnMethods = { "DS_027_verifyWhetherSmsVerificationPageBackIconRedirectToSignupPage" })
	public void DS_028_verifySmsVerificationWithValidOtp() throws IOException, InterruptedException
	{
		// Go to sms verification page again
		signUpPage.clickContinueButtonSignupPage();
		signUpPage.clickConfirmationPopupButton("continue");
		// To fill the otp
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		// verify whether practice info page opened
		practiceInfoPage.validatePracticeInfoPage();
	}
}
