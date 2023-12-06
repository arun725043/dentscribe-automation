package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;

public class DS_004_SignupSmsVerificationTest extends AndroidBase {

	@Test
	public void validateSMSscreenTest() throws InterruptedException {

		//Launch application and go to Signup page and verify
		ExtentManager.logInfoDetails("Application launched successfully");
		signUpPage.verifySignupLandingPage();

		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), "");
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
		assertTrue(signUpPage.verifyConfirmationPopupButton());
		assertTrue(signUpPage.clickSignupConfirmationPopupButtons("continue"));

		// _____________validate OTP with empty_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP("");

		// ____________validate OTP with wrong value_____________
		smsVerificationPage.validateErrorMessageForBlankWrongOTP(readData("testData", "otp"));
		Thread.sleep(5000);

		// ___________validate Resend Code link_______________
		smsVerificationPage.verifyResendCodeLink();
		

		// ________________validate back button_____________
		smsVerificationPage.verifyBackIcon("signup");
	}

}
