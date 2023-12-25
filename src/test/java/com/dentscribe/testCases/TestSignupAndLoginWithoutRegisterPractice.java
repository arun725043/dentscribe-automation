package com.dentscribe.testCases;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class TestSignupAndLoginWithoutRegisterPractice extends AndroidBase {
	
	String emailString = "kapoor.arun+auto" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";
	String password = CommonVariables.generatePassword;
	
	@Test (priority = 1)
	public void verifySignupWithNonSupportedPMS() throws InterruptedException 
	{
		// ___________Application launched_______________
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignupPage();

		// ________fill form and verify confirmation popup___________
		signUpPage.fillSignupForm(genrateRandomFirstName(), "", readData("testData", "countryCode"), readData("testData", "mobile"), CommonVariables.generateEmailId, 
				"", otpTextboxes, "", String.valueOf(GenerateRandomNumber(6)), password, password, "Eaglesoft");
		signUpPage.clickVerifySignUpContinueButton("sorry popup");
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifySignupWithNonSupportedPMS" })
	public void verifySignupWithSupportedPMS()
	{
		verifyClickListOption(driver, signUpPage.optionsListPMS, "Dentrix");
		signUpPage.verifyConfirmationPopupButton();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifySignupWithSupportedPMS" })
	public void verifyWishToContinuePopupBackButton() throws InterruptedException
	{		
		// _____________verify back button_______________
		signUpPage.clickSignupConfirmationPopupButtons("back");
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyWishToContinuePopupBackButton" })
	public void verifyPracticeInfoMandatoryFields() throws InterruptedException
	{
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
		signUpPage.clickSignupConfirmationPopupButtons("Continue");
		// __________________validate OTP functionality_____________
		smsVerificationPage.validateSmsVerificationPage();
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		// verify whether practice info page opened
		practiceInfoPage.validatePracticeInfoPage();
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyPracticeInfoMandatoryFields" })
	public void verifyLoginWithoutRegisterPractice() throws IOException, InterruptedException
	{
		// ________________verify user should not be logged in with these credentials______________
		click(driver, CommonLocators.buttonLogin, "Login Button");
		loginPage.loginApplication(CommonVariables.email, password, "error");
	}
}
