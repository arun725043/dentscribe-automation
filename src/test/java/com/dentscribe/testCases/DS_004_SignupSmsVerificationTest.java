package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;

public class DS_004_SignupSmsVerificationTest extends AndroidBase {

	@Test (priority = 0)
	public void verifyIsSmsVerificationScreenExistsAfterSignup() throws InterruptedException {

		//Launch application and go to Signup page and verify
		ExtentManager.logInfoDetails("Application launched successfully");
		signUpPage.verifySignupLandingPage();

		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), "");
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
		assertTrue(signUpPage.verifyConfirmationPopupButton());
		assertTrue(signUpPage.clickSignupConfirmationPopupButtons("continue"));
	}
	
	@Test (priority = 1, dependsOnMethods = { "verifyIsSmsVerificationScreenExistsAfterSignup" })
	public void verifySmsVerificationWithBlankOtp() throws InterruptedException
	{
		// _____________validate OTP with empty_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP("");
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsSmsVerificationScreenExistsAfterSignup" })
	public void verifySmsVerificationWithWrongOtp() throws InterruptedException
	{
		// ____________validate OTP with wrong value_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP(readData("testData", "otp"));
		Thread.sleep(5000);
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyIsSmsVerificationScreenExistsAfterSignup" })
	public void verifySmsVerificationResendCodeLink() throws InterruptedException
	{
		// ___________validate Resend Code link_______________
		smsVerificationPage.verifyResendCodeLink();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsSmsVerificationScreenExistsAfterSignup" })
	public void verifySmsVerificationBackIcon() throws InterruptedException
	{
		// ________________validate back button_____________
		smsVerificationPage.verifyBackIcon("signup");
	}

}
