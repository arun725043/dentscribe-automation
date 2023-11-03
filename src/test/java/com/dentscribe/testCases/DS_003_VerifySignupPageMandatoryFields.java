package com.dentscribe.testCases;

import static org.testng.Assert.assertFalse;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class DS_003_VerifySignupPageMandatoryFields extends AndroidBase {

	@Test
	public void verifyMandatoryFields() {

		try {

			ExtentManager.logInfoDetails("Application is launched successfully");
			click(signUpPage.signupButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Signup</b> button");

			// __________fill form with existing email______________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), readData("userdetails", "username"), readData("testData", "pmsName"));
			actions.scrollUntilElementIsVisible("User with email id already exists.");
			assertTrue(IsElementPresent(signUpPage.textEmailAlreadyExist, driver));
			ExtentManager.logInfoDetails("<b>" + getText(signUpPage.textEmailAlreadyExist) + "</b> message is displayed as expected");

			// ___________fill form with new email________________
			clear(signUpPage.inputEmail);
			sendKeys(signUpPage.inputEmail, CommonMethods.genrateRandomFirstName() + CommonMethods.GenerateRandomNumber(2) + "@gmail.com", driver);
			CommonVariables.email = getAttribute(signUpPage.inputEmail);
			ExtentManager.logInfoDetails("Entered updated email : <b>" + getAttribute(signUpPage.inputEmail) + "</b>");

			// ___________change country code ______________________
			click(signUpPage.countryCode, driver);
			ExtentManager.logInfoDetails("Clicked on Country code");
			actions.scrollToPartialText("+93");
			click(By.xpath("//android.widget.TextView[contains(@text,'+93')]"), driver);
			ExtentManager.logInfoDetails("+93 country code is selected");

			actions.scrollableClick("Continue");
			wait.until(ExpectedConditions.visibilityOfElementLocated(signUpPage.textAskForContinue));
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button");

			// _______________user should not be land on sms page_________________
			click(CommonLocators.continueButton, driver);
			assertFalse(IsElementPresent(signUpPage.linkResendCode, driver));
			ExtentManager.logInfoDetails("User is not navigated to SMS verification page as expected");

			// _______________change country code_______________________
			actions.scrollUntilElementIsVisible("First Name");
			click(signUpPage.countryCode, driver);
			ExtentManager.logInfoDetails("Clicked on Country code");
			actions.scrollToPartialText(readData("testData", "countryCode"));
			click(By.xpath("//android.widget.TextView[contains(@text,'" + readData("testData", "countryCode") + "')]"), driver);
			ExtentManager.logInfoDetails(readData("testData", "countryCode") + " country code is selected");

			actions.scrollableClick("Continue");
			wait.until(ExpectedConditions.visibilityOfElementLocated(signUpPage.textAskForContinue));

			assertTrue(IsElementPresent(signUpPage.textAskForContinue, driver));
			ExtentManager.logInfoDetails("Do you wish to continue? message is displayed on confirmation popup as expected");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
