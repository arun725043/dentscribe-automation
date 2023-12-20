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

public class TestCreateUserBuyPaidPlanAndCancelPaidPlan extends AndroidBase {

	String email = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";
	String getOtp = null;
	
	@Test(priority = 0)
	public void verifySignupNewUser() throws InterruptedException {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(priority = 1, dependsOnMethods = { "verifySignupNewUser" })
	public void verifyCreatePracticeInfo() throws IOException, InterruptedException {
		try {
			// _________________fill Practice form and Navigate to Sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
			practiceInfoPage.clickContinueButtonPracticeInfo();
			Thread.sleep(20000);
			assertTrue(sikkaWebviewPage.validateSikkaWebViewPage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyCreatePracticeInfo" })
	public void verifyWebViewRegistration() throws InterruptedException
	{
		click(driver, sikkaWebviewPage.buttonRegister, "Register button");
		explicitWait(driver, sikkaWebviewPage.textYourOrder, 30);

		actions.scrollUntilElementIsVisible("Next");
		Thread.sleep(2000);
		click(driver, sikkaWebviewPage.buttonNext, "Next button");
		actions.scrollUntilElementIsVisible("Yes");
		Thread.sleep(10000);

		click(driver, sikkaWebviewPage.RadioYes, "Yes radio button");
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(sikkaWebviewPage.textTermsOfService));
//		actions.scrollToPartialText("Agree");

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
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyWebViewRegistration" })
	public void verifySpuInstallPopupAndRefreshData() throws IOException, InterruptedException {
		try {
			// _______________login application and verify SPU Install popup_______________
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "spu popup"));
			
			// _______________By pass the manual sikka refresh steps and refreshing sikka data_______________
			GetOtp.updateOfficeId(email, readData("testData", "dentrix"));
			ExtentManager.logInfoDetails("Sikka refresh done");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifySpuInstallPopupAndRefreshData" })
	public void verifyEulaAgreementPageAndAcceptAgreement() throws IOException, InterruptedException {
		try {
			// ___________login application again_________________
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "valid"));
			assertTrue(loginPage.verifyBiometricPopupButton());
			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			
			getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
			eulaPage.validateEulaAgreementPage();
			eulaPage.clickContinueButtonEulaAgreementPage();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 5, dependsOnMethods = { "verifyEulaAgreementPageAndAcceptAgreement" })
	public void verifyCanUserBuyPaidPlan() throws InterruptedException
	{
		// _____________select paid plan_____________
		assertTrue(manageSubscriptionPage.validateManageSubscriptionPage());
		manageSubscriptionPage.selectPlan("paid");

		// ______________Fill payment details__________________
		manageSubscriptionPage.clickPaymentMethodButton("add");
		
		
		String cardHolderName = CommonMethods.genrateRandomFirstName();
		addPaymentMethodPage.addPaymentMethod(cardHolderName, readData("testData", "cardNo"), readData("testData", "expiry"), 
				readData("testData", "cvc"), readData("testData", "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		manageSubscriptionPage.verifyCardHolderName(cardHolderName);
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();
		assertTrue(tourPages.validateTourPageCalendarScheduleView());

		// ___________________Navigate to setting page_____________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		click(driver, calendarPage.iconSetting, "Settings icon on calendar page");
		settingPage.validateSettingsPage();

		// ____________________Verify cancel button and plan_______________
		settingPage.verifyBuyPlanOnSettingsPage("paid");
	}
	
	@Test(priority = 6, dependsOnMethods = { "verifyCanUserBuyPaidPlan" })
	public void verifyCanUserCancelSubscription() throws InterruptedException 
	{
		// ________________Cancel subscription________________
		click(driver, settingPage.buttonCancel, "Cancel button");
		
		click(driver, settingPage.dropdownReason, "Reason dropdown");

		click(driver, settingPage.listOfReason, "Reason dropdownlist");
		ExtentManager.logInfoDetails("Able to select the reason from reason dropdown successfully");
		
		sendKeys(driver, settingPage.inputAddDescription, "Reason details field", "Cancelling subscription for testing");

		click(driver, settingPage.buttonSubmit, "Submit button cancel subscription popup");
		ExtentManager.logInfoDetails(getText(CommonLocators.successMessageCancelSubscription));
		Thread.sleep(5000);
		loginPage.validateLoginPage();
	}

	@Test(priority = 7, dependsOnMethods = { "verifyCanUserCancelSubscription" })
	public void verifyCanUserUpdatePaymentMethod() throws IOException, InterruptedException {
		// ___________________login, skip tour pages and verify subscription cancelled or not________________
		assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "valid"));
		// Click skip and verify tour page
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		assertTrue(tourPages.validateTourPageCalendarScheduleView());
		
		tourPages.skipTourPages();
		assertTrue(calendarPage.validateCalendarPage());

		// __________________Navigate to setting page and verify cancelled plan________________
		click(driver, calendarPage.iconSetting, "Settings icon");
		settingPage.validateSettingsPage();
		assertTrue(IsElementPresent(driver, settingPage.buttonManageSubscription, "Manage Subscription button") && 
				IsElementPresent(driver, settingPage.text699Selected, "$699/Month plan option"));

		// _____________select paid plan_____________
		click(driver, settingPage.buttonManageSubscription, "Manage subscription button on settings page");
		assertTrue(manageSubscriptionPage.validateManageSubscriptionPage());

		// ______________Fill payment details__________________
		manageSubscriptionPage.clickPaymentMethodButton("edit");
		String cardHolderName = CommonMethods.genrateRandomFirstName();
		addPaymentMethodPage.addPaymentMethod(cardHolderName, readData("testData", "cardNo"), readData("testData", "expiry"), 
				readData("testData", "cvc"), readData("testData", "zipcode"));
		addPaymentMethodPage.clickContinueButtonAddPaymentMethod();
		manageSubscriptionPage.verifyCardHolderName(cardHolderName);
	}

	@Test(priority = 2, dependsOnMethods = { "verifyCanUserUpdatePaymentMethod" })
	public void verifyCanUserBuyPaidPlanAgainAfterCancelOnce() throws IOException, InterruptedException {
		manageSubscriptionPage.validateManageSubscriptionPage();
		manageSubscriptionPage.selectPlan("paid");
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();
		ExtentManager.logInfoDetails("payment successful! subscription active.");
		assertTrue(settingPage.validateSettingsPage());

		// ____________________Verify cancel button and plan_______________
		settingPage.verifyBuyPlanOnSettingsPage("paid");
	}
}
