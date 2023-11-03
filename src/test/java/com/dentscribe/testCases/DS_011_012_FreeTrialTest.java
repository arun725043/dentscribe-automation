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

public class DS_011_012_FreeTrialTest extends AndroidBase {

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

			// ____________________Select Yes Radio Button___________________________
			actions.scrollByCordinate(542, 2097, 552, 425, 1);
			actions.scrollByCordinate(542, 2097, 552, 1800, 1);// added

			Thread.sleep(2000);
			click(practiceInfoPage.buttonNext, driver);
			actions.scrollUntilElementIsVisible("Yes");
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
	public void getFreePlan() throws IOException, InterruptedException {

		try {
			// Static wait is given for refreshing location from sikka side
			Thread.sleep(30000);
			assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "valid"));
			ExtentManager.logInfoDetails("user should be redirected to Biometric popup after successfully login as expected");

			click(loginPage.buttonSkip, driver);
			explicitWait(driver, manageSubscription.textEula, 20);
			assertTrue(manageSubscription.verifyEulaScreen());
			ExtentManager.logInfoDetails("Clicked on <b>Skip</b> button ,<b>" + getText(manageSubscription.textEula) + "</b> is displayed as expected");

			actions.scrollableClick("Continue");
			explicitWait(driver, manageSubscription.textSelectSubscription, 10);
			click(manageSubscription.textFreeTrial, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button ,<b>" + getText(manageSubscription.textFreeTrial) + "</b> is displayed as expected");

			actions.scrollToText("Continue");
			click(CommonLocators.continueButton, driver);
			explicitWait(driver, tourPages.textCalendarSchecule, 30);
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button ,<b>" + getText(tourPages.textCalendarSchecule) + "</b> is displayed as expected");

			tourPages.skipTourPages();
			wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));
			ExtentManager.logInfoDetails("Clicked on skip tour pages , user is redirected to calendar page successfully");

			click(calendarPage.iconSetting, driver);
			explicitWait(driver, settingPage.buttonHelp, 10);

			assertTrue(IsElementPresent(settingPage.textFreeTrial, driver) && IsElementPresent(manageSubscription.buttonManageSubscription, driver));
			ExtentManager.logInfoDetails("<b>Manage Subscription</b> button and <b>" + getText(settingPage.textFreeTrial) + " </b>plan is displayed as expected");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
