package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;

public class DS_003_SignupPageTest extends AndroidBase {


	
	@Test (priority = 2)
	public void verifyConfirmationPopupBackButton() throws InterruptedException {
		signUpPage.clickSignupConfirmationPopupButtons("back");
	}
	
	@Test(priority = 3)
	public void verifyEmailIdValidations() {

		try {
			// __________refreshing signup page______________
			click(driver, loginPage.buttonLogin, "Login tab");
			click(driver, loginPage.buttonSignup, "Sign Up tab");
			
			// __________verify invalid email error should display______________
			signUpPage.validateWrongEmailId("kapoor.arun@gmail.c");
			
			// __________verify duplicate email error should display______________
			actions.scrollUntilElementIsVisible("First Name");
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), readData("userDetails", "username"));
			signUpPage.clickVerifySignUpContinueButton("duplicate email");

			// ___________fill form with new email________________
			clear(signUpPage.inputEmail);
			CommonVariables.email = CommonVariables.generateEmailId;
			sendKeys(driver, signUpPage.inputEmail, "Email field", CommonVariables.email);
			ExtentManager.logInfoDetails("Entered updated email : <b>" + getAttribute(signUpPage.inputEmail) + "</b>");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 4)
	public void verifyPasswordAndConfirmPassword()
	{
		try 
		{
			//For weak password validation
			signUpPage.validatePasswordStrength("pass");
			sendKeys(driver, CommonLocators.inputTxtPassword, "Password", CommonVariables.actualPass);
			//For confirm password validation when not matched with password
			signUpPage.validateConfirmPassword("pass@123");
			sendKeys(driver, CommonLocators.txtConfirmPassword, "Confirm Password", CommonVariables.actualPass);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 5)
	public void verifyInvalidCountryCode()
	{
		try {
			// ___________verify error message and stay on Signup page if country code & phone number not matched______________________
			actions.scrollUntilElementIsVisible("Phone Number");
			click(driver, signUpPage.inputCountryCode, "Phone number country code");
			actions.scrollToPartialText("+93");
			click(driver, By.xpath("//android.widget.TextView[contains(@text,'+93')]"), "+93 country code");
			
			// ___________verify Confirmation popup and continue_________________
			actions.scrollableClick("Continue");
			wait.until(ExpectedConditions.visibilityOfElementLocated(signUpPage.textAskForContinue));
			click(driver, signUpPage.buttonContinue, "Continue button on Confimation popup");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.errorMessageInvalidCountryCode));
			ExtentManager.logInfoDetails("Error message found - <b>" + getText(CommonLocators.errorMessageInvalidCountryCode) + "<b>");
			assertTrue(IsElementPresent(driver, signUpPage.labelPMSDropdown, "PMS Field"));
			ExtentManager.logInfoDetails("<b>User is still on Signup page as expected");
		} catch (Exception e) {
			ExtentManager.logFailureDetails("<b>Expected Signup page not found. please check");
			Assert.fail();
		}
	}
}
