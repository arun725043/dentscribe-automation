package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class DS_011_012_021_FreeTrialAndLogoutTest extends AndroidBase {

	String getOtp = null;
	String email = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";	
	
	@Test(priority = 0)
	public void signupUserTest() throws InterruptedException {
		try 
		{
			// ___________Application launched_______________
			ExtentManager.logInfoDetails("Application is launched successfully");
			click(driver, signUpPage.signupButton, "Signup tab");

			// ____________________Fill signup form and verify confirmation popup button_________________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), email);
			signUpPage.clickVerifySignUpContinueButton("confirmation popup");
			assertTrue(signUpPage.verifyConfirmationPopupButton());

			// ________________Enter OTP__________________
			assertTrue(signUpPage.clickSignupConfirmationPopupButtons("continue"));
			getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.validateValidOTP(getOtp, "Practice form");
		
			// _________________fill Practice form and Navigate to Sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
			practiceInfoPage.clickPracticeContinueButton();
			Thread.sleep(20000);
			actions.scrollableClick("Proceed");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.buttonRegister));	
		
			click(driver, practiceInfoPage.buttonRegister, "Register button");
			explicitWait(driver, practiceInfoPage.textYourOrder, 30);
	
			actions.scrollUntilElementIsVisible("Next");
			Thread.sleep(2000);
			click(driver, practiceInfoPage.buttonNext, "Next button");
			actions.scrollUntilElementIsVisible("Yes");
			Thread.sleep(10000);
	
			click(driver, practiceInfoPage.RadioYes, "Yes radio button");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.textTermsOfService));
			actions.scrollToPartialText("Agree");
	
			// ___________________Scroll Terms of Service page____________________________
			sikkaWebviewPage.termsOfServiceScroll();
			ExtentManager.logInfoDetails("<b>Scrolling Terms of Service page");
			actions.scrollableClick("Agree");
			Thread.sleep(5000);
	
			// __________________Fill the confirmation page______________________________
			sikkaWebviewPage.enterExistingSikkaCredentials(readData("userDetails", "existingSikkaUser"), readData("userDetails", "existingSikkaPwd"));
			explicitWait(driver, CommonLocators.inputTxtUsername, 30);
			assertTrue(IsElementPresent(driver, CommonLocators.inputTxtUsername, "Username label"));
			ExtentManager.logInfoDetails("<b>Practice created successfully");
			
			// ___________refreshing sikka data and login_________________
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "spu popup"));	
			GetOtp.updateOfficeId(email, readData("testdata", "dentrix"));
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "valid"));
			assertTrue(loginPage.verifyBiometricPopupButton());
			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			
			getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.validateValidOTP(getOtp, "EULA Screen");
			actions.scrollableClick("Continue");
			ExtentManager.logInfoDetails("Clicked on EULA agreement <b>Continue button");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 1, dependsOnMethods = { "signupUserTest" })
	public void verifyIsAddPaymentPageExists() throws InterruptedException
	{
		manageSubscriptionPage.clickVerifyAddPaymentMethodButton();
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsAddPaymentPageExists" })
	public void verifyPaymentMethodMandatoryFields() throws InterruptedException
	{
		manageSubscriptionPage.validateMandatoryFieldsErrorMessages();
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyIsAddPaymentPageExists" })
	public void verifyPaymentMethodWithInvalidCardDetails() throws InterruptedException
	{
		String cardHolderName = CommonMethods.genrateRandomFirstName();
		manageSubscriptionPage.addPaymentMethod(cardHolderName, "4242 4242 4242 4243", readData("testData", "expiry"), readData("testData", "cvc"), readData("testData", "zipcode"));
		manageSubscriptionPage.clickContinueButtonAddPaymentMethod();
		verifyTexts(getText(CommonLocators.errorMessageCardDetailsNotAdded), CommonVariables.errorMsgTextCardDetailsNotAdded);
		
		manageSubscriptionPage.addPaymentMethod(cardHolderName, readData("testData", "cardNo"), "12/75", readData("testData", "cvc"), readData("testData", "zipcode"));
		manageSubscriptionPage.clickContinueButtonAddPaymentMethod();
		verifyTexts(getText(CommonLocators.errorMessageInvalidCardExpiry), CommonVariables.errorMsgTextInvalidCardExpiry);
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyIsAddPaymentPageExists" })
	public void verifyBackIconAddPaymentMethodPage() throws InterruptedException
	{
		click(driver, manageSubscriptionPage.iconBackAddPayemntMethodPage, "Back icon on Add payment method page");
		manageSubscriptionPage.verifySubscriptionLandingPage();
	}
	
	
	@Test(priority = 5, dependsOnMethods = { "verifyBackIconAddPaymentMethodPage" })
	public void verifyUserCanTake30DaysFreeTrialPlan() throws InterruptedException
	{
		// _____________select paid plan_____________
		manageSubscriptionPage.verifySubscriptionLandingPage();
		manageSubscriptionPage.selectPlan("free trial");
		actions.scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button");
		tourPages.verifyTourLandingPage();

		// ___________________Navigate to setting page_____________
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.textWelcomeUser));
		click(driver, calendarPage.iconSetting, "Settings icon");
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

		// ____________________Verify manage subscription button and plan_______________
		assertTrue(IsElementPresent(driver, settingPage.buttonManageSubscription, "Manage Subscription button") && IsElementPresent(driver, settingPage.textFreeTrial, "30-Day Free Trial option"));
	}

	@Test(priority = 6, dependsOnMethods = { "verifyUserCanTake30DaysFreeTrialPlan" })
	public void verifyUserCanBuyPaidPlan() throws IOException, InterruptedException {

		// ________________buy plan subscription________________
		click(driver, settingPage.buttonManageSubscription, "Manage Subscription button");
		
		// _____________select paid plan_____________
		manageSubscriptionPage.verifySubscriptionLandingPage();
		manageSubscriptionPage.selectPlan("paid");

		// ______________Fill payment details__________________
		manageSubscriptionPage.addPaymentMethod(CommonMethods.genrateRandomFirstName(), readData("testData", "cardNo"), readData("testData", "expiry"), 
				readData("testData", "cvc"), readData("testData", "zipcode"));
		actions.scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button");
		settingPage.validateSettingsPage();

		// ____________________Verify cancel button and plan_______________
		assertTrue(IsElementPresent(driver, settingPage.buttonCancel, "Cancel button") && IsElementPresent(driver, settingPage.text699Selected, "$699/Month plan option"));
	}
	
	@Test(priority = 7)
	public void verifyLogout()
	{
		click(driver, settingPage.buttonLogOut, "Logout button");
		loginPage.validateLoginPageNote();
	}
}
