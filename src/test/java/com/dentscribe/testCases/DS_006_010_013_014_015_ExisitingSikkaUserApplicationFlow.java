package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class DS_006_010_013_014_015_ExisitingSikkaUserApplicationFlow extends AndroidBase {

	@Test(priority = 0)
	public void signupTest() throws InterruptedException {

		GenerateOtp getOtp = new GenerateOtp(driver);
		try {

			// ___________Application launched_______________
			ExtentManager.logInfoDetails("Application is launched successfully");
			click(signUpPage.signupButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Signup</b> button");

			// ____________________Fill signup form and verify confirmation popup button_________________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), CommonMethods.genrateRandomFirstName() + "@gmail.com", readData("testData", "pmsName"));
			assertTrue(signUpPage.verifyConfirmationPopupButton());
			ExtentManager.logInfoDetails("<b>Back</b> and <b>Continue</b> button on confirmation popup is displayed as expected");

			// ________________Enter OTP__________________
			assertTrue(signUpPage.clickConfirmationPopupButton("continue"));
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> Button on confirmation popup and user is redirected to SMS verification page as expected");
			getOtp.fillOtp();
			click(CommonLocators.continueButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
			wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.textWelcome));

			// _________________fill Practice form and Navigate to sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
			Thread.sleep(20000);
			actions.scrollableClick("Proceed");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.buttonRegister));
			click(practiceInfoPage.buttonRegister, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Register</b> button");
			explicitWait(driver, practiceInfoPage.textYourOrder, 30);

			practiceInfoPage.scrollToNextButton();
			Thread.sleep(2000);
			click(practiceInfoPage.buttonNext, driver);
//			actions.scrollUntilElementIsVisible("Yes");
			actions.scrollByCordinate(567, 1262, 567, 2182, 4);
			Thread.sleep(10000);

			click(practiceInfoPage.RadioYes, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Yes</b> Radio Button");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.textTermsOfService));

			// ___________________Scroll Terms of Service page____________________________
			signUpPage.termsOfServiceScroll();
			ExtentManager.logInfoDetails("Scrolling Terms of Service page");
			actions.scrollableClick("Agree");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.textConfirmation));

			// __________________Fill the confirmation page______________________________
			signUpPage.enterExisistingSikkaCredentials(readData("userDetails", "existingSikkaUser"), readData("userDetails", "existingSikkaPwd"));
			explicitWait(driver, CommonLocators.txtUsername, 30);
			assertTrue(IsElementPresent(CommonLocators.txtUsername, driver));
			ExtentManager.logInfoDetails("Account has been created successfully");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(priority = 1, dependsOnMethods = { "signupTest" })
	public void buyPlan() throws IOException, InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		try {
			// Static wait is given for refreshing location from sikka side
			Thread.sleep(30000);
			assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "valid"));
//			assertTrue(loginPage.loginApplication("Russel@gmail.com", "#A1apXDrH1", "valid"));
			ExtentManager.logInfoDetails("user should be redirected to Biometric popup after successfully login as expected");

			// ______________verify Eula Screen_______________
//			click(loginPage.buttonSkip, driver);
			loginPage.clickSkipOnBasisOfBiometric();
			
			getOtp.fillOtp();
			ExtentManager.logInfoDetails("SMS OTP Entered successfully");
			click(CommonLocators.continueButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
			
			explicitWait(driver, manageSubscription.textEula, 20);
			assertTrue(manageSubscription.verifyEulaScreen());
			ExtentManager.logInfoDetails("Clicked on <b>Skip</b> button ,<b>" + getText(manageSubscription.textEula) + "</b> is displayed as expected");
			actions.scrollableClick("Continue");

			// _____________select paid plan_____________
			explicitWait(driver, manageSubscription.textSelectSubscription, 10);
			click(manageSubscription.text699Month, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button ,<b>" + getText(manageSubscription.text699Month) + "</b> is displayed as expected");
			click(manageSubscription.buttonAddPayment, driver);
			explicitWait(driver, manageSubscription.textCardDetail, 10);

			// ______________Fill payment details__________________
			manageSubscription.addPaymentMethod();
			actions.scrollToText("Continue");
			click(CommonLocators.continueButton, driver);
			explicitWait(driver, tourPages.textCalendarSchecule, 30);
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button ,<b>" + getText(tourPages.textCalendarSchecule) + "</b> is displayed as expected");

			// ___________________Navigate to setting page_____________
			tourPages.skipTourPages();
			wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));
			click(calendarPage.iconSetting, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

			// ____________________Verify cancel button_______________
			assertTrue(IsElementPresent(settingPage.buttonCancel, driver) && IsElementPresent(settingPage.text699Selected, driver));
			ExtentManager.logInfoDetails("<b>Cancel</b> button and <b>" + getText(settingPage.text699Selected) + " </b>plan is displayed as expected");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(priority = 2, dependsOnMethods = { "buyPlan" })
	public void cancelSubscription() throws IOException, InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "valid"));
//		assertTrue(loginPage.loginApplication("Leta@gmail.com", "#A1aDyQAXZ", "valid"));

		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		// ______________skip tour pages________________________
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		getOtp.fillOtp();
		ExtentManager.logInfoDetails("SMS OTP Entered successfully");
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

		// ________________Cancel subscription________________
		click(settingPage.buttonCancel, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Cancel</b> button");

		click(manageSubscription.dropdownReason, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Reason</b> Dropdown");

		click(manageSubscription.listOfReason, driver);
		ExtentManager.logInfoDetails("Able to select the reason from reason dropdown successfully");

		sendKeys(manageSubscription.inputAddDescription, "Description ", driver);
		Thread.sleep(2000);
		ExtentManager.logInfoDetails("Entered value in description field : " + getAttribute(manageSubscription.inputAddDescription));

		click(manageSubscription.buttonSubmit, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Submit</b> button");

		assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "valid"));

		// ___________________skip tour pages________________
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));
		ExtentManager.logInfoDetails("Skipped the tour pages");

		// __________________Navigate to setting________________
		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

		assertTrue(IsElementPresent(manageSubscription.buttonManageSubscription, driver) && IsElementPresent(settingPage.text699Selected, driver));
		ExtentManager.logInfoDetails("<b>Manage subscription</b> button and <b>" + getText(settingPage.text699Selected) + " </b>plan is displayed as expected");

	}

	@Test(priority = 3, dependsOnMethods = { "cancelSubscription" })
	public void buyPaidPlanAfterCancel() throws IOException, InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "valid"));
//		assertTrue(loginPage.loginApplication("Leta@gmail.com", "#A1aDyQAXZ", "valid"));

		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		// ______________skip tour pages________________________
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		getOtp.fillOtp();
		ExtentManager.logInfoDetails("SMS OTP Entered successfully");
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");
		click(manageSubscription.buttonManageSubscription, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Manage Subscription</b> button");

		click(manageSubscription.text699Month, driver);
		ExtentManager.logInfoDetails("Clicked on <b>" + getText(manageSubscription.text699Month) + "</b> plan");

		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button");
		explicitWait(driver, settingPage.buttonLogOut, 20);

		// ____________________Verify cancel button_______________
		assertTrue(IsElementPresent(settingPage.buttonCancel, driver) && IsElementPresent(settingPage.text699Selected, driver));
		ExtentManager.logInfoDetails("<b>Cancel</b> button and <b>" + getText(settingPage.text699Selected) + " </b>plan is displayed as expected");

	}

}
