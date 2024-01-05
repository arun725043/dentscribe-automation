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

public class TestCreateUserTakeFreePlanAndBuyPaidPlan extends AndroidBase 
{
	String email = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";
	String getOtp = null;
	
	@Test(priority = 0)
	public void createNewUserAndGoToManageSubscriptionPage() throws InterruptedException {
		try 
		{
			// ___________Application launched_______________
			loginPage.verifyIsApplicationLaunched();

			// _______________Click Signup tab and verify it_______________
			click(driver, CommonLocators.buttonSignup, "Signup tab");
			signUpPage.validateSignupPage();

			// ____________________Fill signup form and verify confirmation popup button_________________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), email);
			signUpPage.clickVerifySignUpContinueButton("confirmation popup");
			assertTrue(signUpPage.verifyConfirmationPopupButton());

			// ________________Enter OTP__________________
			assertTrue(signUpPage.clickSignupConfirmationPopupButtons("continue"));
			getOtp = GetOtp.generateOTP(readData(CommonVariables.inputFileTestData, "countryCode"), readData(CommonVariables.inputFileTestData, "mobile"));
			smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
			practiceInfoPage.validatePracticeInfoPage();
			
			// _________________fill Practice form and Navigate to Sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData(CommonVariables.inputFileTestData, "state"), readData(CommonVariables.inputFileTestData, "country"));
			practiceInfoPage.clickContinueButtonPracticeInfo();
			Thread.sleep(20000);
			sikkaWebviewPage.validateSikkaWebViewPage();
			
			// _________________register practice on sikka________________________
			click(driver, sikkaWebviewPage.buttonRegister, "Register button");
			explicitWait(driver, sikkaWebviewPage.textYourOrder, 30);

			actions.scrollUntilElementIsVisible("Next");
			Thread.sleep(2000);
			actions.scrollableClick("Next");
//			click(driver, sikkaWebviewPage.buttonNext, "Next button");
			actions.scrollUntilElementIsVisible("Yes");
			Thread.sleep(10000);

			click(driver, sikkaWebviewPage.RadioYes, "Yes radio button");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(sikkaWebviewPage.textTermsOfService));

			// ___________________Scroll Terms of Service page____________________________
			sikkaWebviewPage.termsOfServiceScroll();
			explicitWait(driver, sikkaWebviewPage.buttonAgree, 60);
			actions.scrollableClick("Agree");
			Thread.sleep(5000);

			// __________________Fill the confirmation page______________________________
			sikkaWebviewPage.enterExistingSikkaCredentials(readData(CommonVariables.inputFileUserDetails, "existingSikkaUser"), readData(CommonVariables.inputFileUserDetails, "existingSikkaPwd"));
			explicitWait(driver, loginPage.labelUsername, 60);
			loginPage.validateLoginPage();
			ExtentManager.logInfoDetails("<b>Practice created successfully");
			
			// _______________login application and verify SPU Install popup_______________
			loginPage.loginApplication(email, CommonVariables.actualPass, "spu popup");
			
			// _______________By pass the manual sikka refresh steps and refreshing sikka data_______________
			GetOtp.updateOfficeId(email, readData(CommonVariables.inputFileTestData, "dentrix"));
			ExtentManager.logInfoDetails("Sikka refresh done");
			
			// ___________login application again and go to subscription page_________________
			loginPage.loginApplication(email, CommonVariables.actualPass, "valid");
			assertTrue(loginPage.verifyBiometricPopupButton());
			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			
			getOtp = GetOtp.generateOTP(readData(CommonVariables.inputFileTestData, "countryCode"), readData(CommonVariables.inputFileTestData, "mobile"));
			smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
			eulaPage.validateEulaAgreementPage();
			eulaPage.clickContinueButtonEulaAgreementPage();
			manageSubscriptionPage.validateManageSubscriptionPage();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 1, dependsOnMethods = { "createNewUserAndGoToManageSubscriptionPage" })
	public void verifyIsAddPaymentMethodPageExists() throws InterruptedException
	{
		manageSubscriptionPage.validateManageSubscriptionPage();
		manageSubscriptionPage.clickPaymentMethodButton("add");
		addPaymentMethodPage.validateAddPaymentMethodPage();
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsAddPaymentMethodPageExists" })
	public void verifyAddPaymentMethodMandatoryFields() throws InterruptedException
	{
		addPaymentMethodPage.validateMandatoryFieldsErrorMessages();
		String cardHolderName = CommonMethods.genrateRandomFirstName();
		addPaymentMethodPage.addPaymentMethod(cardHolderName, "4242 4242 4242 4243", readData(CommonVariables.inputFileTestData, "expiry"), readData(CommonVariables.inputFileTestData, "cvc"), readData(CommonVariables.inputFileTestData, "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.errorMessageCardDetailsNotAdded));
		verifyTexts(getText(CommonLocators.errorMessageCardDetailsNotAdded), CommonVariables.errorMsgTextCardDetailsNotAdded);
		
		addPaymentMethodPage.addPaymentMethod(cardHolderName, readData(CommonVariables.inputFileTestData, "cardNo"), "12/75", readData(CommonVariables.inputFileTestData, "cvc"), readData(CommonVariables.inputFileTestData, "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		ExtentManager.logInfoDetails(CommonVariables.errorMsgTextInvalidCardExpiry);
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyAddPaymentMethodMandatoryFields" })
	public void verifyBackIconAddPaymentMethodPage() throws InterruptedException
	{
		click(driver, addPaymentMethodPage.iconBackAddPayemntMethodPage, "Back icon on Add payment method page");
		manageSubscriptionPage.validateManageSubscriptionPage();
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyBackIconAddPaymentMethodPage" })
	public void verifyCanUserTake30DaysFreeTrialPlan() throws InterruptedException
	{
		// _____________select paid plan_____________
		manageSubscriptionPage.validateManageSubscriptionPage();
		manageSubscriptionPage.selectPlan("free");
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();
		tourPages.validateTourPageCalendarScheduleView();

		// ___________________Navigate to setting page_____________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		calendarPage.clickSettingsIconCalendarPage();
		settingPage.validateSettingsPage();
		settingPage.verifyBuyPlanOnSettingsPage("free");
	}

	@Test(priority = 5, dependsOnMethods = { "verifyCanUserTake30DaysFreeTrialPlan" })
	public void verifyCanUserBuyPaidPlanAfterFreeTrialPlan() throws IOException, InterruptedException {

		// ________________buy plan subscription________________
		click(driver, settingPage.buttonManageSubscription, "Manage Subscription button");
		
		// _____________select paid plan_____________
		manageSubscriptionPage.validateManageSubscriptionPage();
		manageSubscriptionPage.selectPlan("paid");
		manageSubscriptionPage.clickPaymentMethodButton("add");
		addPaymentMethodPage.validateAddPaymentMethodPage();

		// ______________Fill payment details__________________
		addPaymentMethodPage.addPaymentMethod(CommonMethods.genrateRandomFirstName(), readData(CommonVariables.inputFileTestData, "cardNo"), readData(CommonVariables.inputFileTestData, "expiry"), 
				readData(CommonVariables.inputFileTestData, "cvc"), readData(CommonVariables.inputFileTestData, "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		ExtentManager.logInfoDetails("payment successful! subscription active.");
		manageSubscriptionPage.validateManageSubscriptionPage();
		
		// _______________buy plan and verify it_______________
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();		
		settingPage.validateSettingsPage();
		settingPage.verifyBuyPlanOnSettingsPage("paid");
	}
}
