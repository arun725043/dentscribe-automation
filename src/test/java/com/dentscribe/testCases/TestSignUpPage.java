package com.dentscribe.testCases;

import org.testng.annotations.Test;

import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class TestSignUpPage extends AndroidBase 
{
	@Test (priority = 1)
	public void verifyIsSignupPageExists()
	{
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignupPage();
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupWithoutMandatoryFields() throws InterruptedException {
		// _________don't fill any values and verify continue button click_________
		actions.scrollableClick("Continue");
		signUpPage.validateMandatoryFieldsErrorMessages();
	}

	@Test(priority = 3, dependsOnMethods = { "verifySignupWithoutMandatoryFields" })
	public void verifySignupPageForInvalidEmail() throws InterruptedException {
		// __________verify invalid email error should display______________
		signUpPage.validateWrongEmailId("kapoor.arun@gmail.c");
	}

	@Test(priority = 4, dependsOnMethods = { "verifySignupPageForInvalidEmail" })
	public void verifySignupPageForWeakPassword() throws InterruptedException {
		//For weak password validation
		signUpPage.validatePasswordStrength("pass");
		sendKeys(driver, signUpPage.inputPassword, "Password", "Test@1234");
	}

	@Test(priority = 5, dependsOnMethods = { "verifySignupPageForWeakPassword" })
	public void verifySignupPageForConfirmPassowrd() throws InterruptedException {
		//For confirm password validation when not matched with password
		signUpPage.validateConfirmPassword("pass@123");
		sendKeys(driver, signUpPage.inputConfirmPassword, "Confirm Password", "Test@1234");
	}

	@Test(priority = 6, dependsOnMethods = { "verifySignupPageForConfirmPassowrd" })
	public void verifyExistingAndDuplicateEmailId() throws InterruptedException {
		// __________verify duplicate email error should display______________
		actions.scrollUntilElementIsVisible("First Name");
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), readData("userDetails", "username"));
		signUpPage.clickVerifySignUpContinueButton("duplicate email");
	}

	@Test(priority = 7, dependsOnMethods = { "verifyExistingAndDuplicateEmailId" })
	public void verifySignupWithOnlyMandatoryFields() throws InterruptedException {
		// __________refreshing signup page______________
		click(driver, loginPage.buttonLogin, "Login tab");
		click(driver, loginPage.buttonSignup, "Sign Up tab");
		
		// _________fill only mandatory values and verify continue button click_________
		String password = CommonVariables.generatePassword;
		signUpPage.fillSignupForm(genrateRandomFirstName(), "", readData("testData", "countryCode"), readData("testData", "mobile"), CommonVariables.generateEmailId, 
				"", otpTextboxes, "", String.valueOf(GenerateRandomNumber(6)), password, password, readData("testData", "pmsName"));
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
	}
	
	@Test(priority = 8, dependsOnMethods = { "verifySignupWithOnlyMandatoryFields" })
	public void verifySignupConfirmationPopupButtons() throws InterruptedException
	{
		// _____________verify back and continue button_______________
		signUpPage.clickSignupConfirmationPopupButtons("back");
		signUpPage.clickVerifySignUpContinueButton("confirmation popup");
		signUpPage.clickSignupConfirmationPopupButtons("continue");
		smsVerificationPage.validateSmsVerificationPage();
	}
}