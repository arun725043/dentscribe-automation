package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import javax.swing.Scrollable;

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
			click(driver, signUpPage.signupButton, "Signup tab");
			signUpPage.validateSignupPage();

			// ____________________Fill signup form and verify confirmation popup button_________________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), email);
			signUpPage.clickVerifySignUpContinueButton("confirmation popup");
			assertTrue(signUpPage.verifyConfirmationPopupButton());

			// ________________Enter OTP__________________
			assertTrue(signUpPage.clickSignupConfirmationPopupButtons("continue"));
			getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
			assertTrue(practiceInfoPage.validatePracticeInfoPage());
			
			// _________________fill Practice form and Navigate to Sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
			practiceInfoPage.clickContinueButtonPracticeInfo();
			Thread.sleep(20000);
			assertTrue(sikkaWebviewPage.validateSikkaWebViewPage());
			
			// _________________register practice on sikka________________________
			click(driver, sikkaWebviewPage.buttonRegister, "Register button");
			explicitWait(driver, sikkaWebviewPage.textYourOrder, 30);

			actions.scrollUntilElementIsVisible("Next");
			Thread.sleep(2000);
			click(driver, sikkaWebviewPage.buttonNext, "Next button");
			actions.scrollUntilElementIsVisible("Yes");
			Thread.sleep(10000);

			click(driver, sikkaWebviewPage.RadioYes, "Yes radio button");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(sikkaWebviewPage.textTermsOfService));

			// ___________________Scroll Terms of Service page____________________________
			sikkaWebviewPage.termsOfServiceScroll();
			ExtentManager.logInfoDetails("<b>Scrolling Terms of Service page");
			actions.scrollableClick("Agree");
			Thread.sleep(5000);

			// __________________Fill the confirmation page______________________________
			sikkaWebviewPage.enterExistingSikkaCredentials(readData("userDetails", "existingSikkaUser"), readData("userDetails", "existingSikkaPwd"));
			explicitWait(driver, CommonLocators.inputTxtUsername, 60);
			assertTrue(loginPage.validateLoginPage());
			ExtentManager.logInfoDetails("<b>Practice created successfully");
			
			// _______________login application and verify SPU Install popup_______________
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "spu popup"));
			
			// _______________By pass the manual sikka refresh steps and refreshing sikka data_______________
			GetOtp.updateOfficeId(email, readData("testData", "dentrix"));
			ExtentManager.logInfoDetails("Sikka refresh done");
			
			// ___________login application again and go to subscription page_________________
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "valid"));
			assertTrue(loginPage.verifyBiometricPopupButton());
			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			
			getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
			eulaPage.validateEulaAgreementPage();
			eulaPage.clickContinueButtonEulaAgreementPage();
			assertTrue(manageSubscriptionPage.validateManageSubscriptionPage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 1, dependsOnMethods = { "createNewUserAndGoToManageSubscriptionPage" })
	public void verifyIsAddPaymentMethodPageExists() throws InterruptedException
	{
		assertTrue(manageSubscriptionPage.validateManageSubscriptionPage());
		manageSubscriptionPage.clickPaymentMethodButton("add");
		assertTrue(addPaymentMethodPage.validateAddPaymentMethodPage());
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsAddPaymentMethodPageExists" })
	public void verifyAddPaymentMethodMandatoryFields() throws InterruptedException
	{
		addPaymentMethodPage.validateMandatoryFieldsErrorMessages();
		String cardHolderName = CommonMethods.genrateRandomFirstName();
		addPaymentMethodPage.addPaymentMethod(cardHolderName, "4242 4242 4242 4243", readData("testData", "expiry"), readData("testData", "cvc"), readData("testData", "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.errorMessageCardDetailsNotAdded));
		verifyTexts(getText(CommonLocators.errorMessageCardDetailsNotAdded), CommonVariables.errorMsgTextCardDetailsNotAdded);
		
		addPaymentMethodPage.addPaymentMethod(cardHolderName, readData("testData", "cardNo"), "12/75", readData("testData", "cvc"), readData("testData", "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		Thread.sleep(5000);
		verifyTexts(getText(CommonLocators.errorMessageInvalidCardExpiry), CommonVariables.errorMsgTextInvalidCardExpiry);
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
		assertTrue(manageSubscriptionPage.validateManageSubscriptionPage());
		manageSubscriptionPage.selectPlan("free trial");
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();
		assertTrue(tourPages.validateTourPageCalendarScheduleView());

		// ___________________Navigate to setting page_____________
		tourPages.skipTourPages();
		assertTrue(calendarPage.validateCalendarPage());
		calendarPage.clickCalendarPageSettingsIcon();
		assertTrue(settingPage.validateSettingsPage());
		settingPage.verifyBuyPlanOnSettingsPage("free");
	}

	@Test(priority = 5, dependsOnMethods = { "verifyCanUserTake30DaysFreeTrialPlan" })
	public void verifyCanUserBuyPaidPlanAfterFreeTrialPlan() throws IOException, InterruptedException {

		// ________________buy plan subscription________________
		click(driver, settingPage.buttonManageSubscription, "Manage Subscription button");
		
		// _____________select paid plan_____________
		assertTrue(manageSubscriptionPage.validateManageSubscriptionPage());
		manageSubscriptionPage.selectPlan("paid");

		// ______________Fill payment details__________________
		addPaymentMethodPage.addPaymentMethod(CommonMethods.genrateRandomFirstName(), readData("testData", "cardNo"), readData("testData", "expiry"), 
				readData("testData", "cvc"), readData("testData", "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		ExtentManager.logInfoDetails("payment successful! subscription active.");
		assertTrue(settingPage.validateSettingsPage());

		// ____________________Verify cancel button and plan_______________
		settingPage.verifyBuyPlanOnSettingsPage("paid");
	}
}
