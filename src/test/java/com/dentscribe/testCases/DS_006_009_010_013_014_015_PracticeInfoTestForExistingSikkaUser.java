package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class DS_006_009_010_013_014_015_PracticeInfoTestForExistingSikkaUser extends AndroidBase {

	String getOtp = null;
	String email = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";
	
	@Test(priority = 0)
	public void signupUserTest() throws InterruptedException {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(priority = 1, dependsOnMethods = { "signupUserTest" })
	public void practiceInfoTest() throws IOException, InterruptedException {
		try {
			// _________________fill Practice form and Navigate to Sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
			practiceInfoPage.clickPracticeContinueButton();
			Thread.sleep(20000);
			actions.scrollableClick("Proceed");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.buttonRegister));	
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 2, dependsOnMethods = { "practiceInfoTest" })
	public void verifyWebviewAndRegisterUser() throws InterruptedException
	{
		click(driver, practiceInfoPage.buttonRegister, "Register button");
		explicitWait(driver, practiceInfoPage.textYourOrder, 30);

		actions.scrollUntilElementIsVisible("Next");
		Thread.sleep(2000);
		click(driver, practiceInfoPage.buttonNext, "Next button");
		actions.scrollUntilElementIsVisible("Yes");
		Thread.sleep(10000);

		click(driver, practiceInfoPage.RadioYes, "Yes radio button");
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.textTermsOfService));
//		actions.scrollToPartialText("Agree");

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
	}
	
	@Test(priority = 3, dependsOnMethods = { "practiceInfoTest" })
	public void verifyLoginAcceptEulaAgreement() throws IOException, InterruptedException {
		try {
			// ___________refreshing sikka data and login_________________
			assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "spu popup"));	
			GetOtp.updateOfficeId(email, "D46381");
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
	
	@Test(priority = 4, dependsOnMethods = { "verifyLoginAcceptEulaAgreement" })
	public void verifyUserCanBuyPaidPlan() throws InterruptedException
	{
		// _____________select paid plan_____________
		manageSubscriptionPage.verifySubscriptionLandingPage();
		manageSubscriptionPage.selectPlan("paid");

		// ______________Fill payment details__________________
		String cardHolderName = CommonMethods.genrateRandomFirstName();
		manageSubscriptionPage.addPaymentMethod(cardHolderName, readData("testData", "cardNo"), readData("testData", "expiry"), 
				readData("testData", "cvc"), readData("testData", "zipcode"));
		manageSubscriptionPage.clickContinueButtonAddPaymentMethod();
		explicitWait(driver, By.xpath("//android.widget.TextView[@text='" + cardHolderName + "']"), 30);
		if (IsElementPresent(driver, By.xpath("//android.widget.TextView[@text='" + cardHolderName + "']"), "Cardholder Name"))
		{
			ExtentManager.logInfoDetails("<b>paymentMethod updated successfully.");
		}
		else {
			ExtentManager.logFailureDetails("<b>Either payment method not added or cardholder name not matched. please check");
			Assert.fail();
		}
		
		actions.scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button");
		tourPages.verifyTourLandingPage();

		// ___________________Navigate to setting page_____________
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.textWelcomeUser));
		click(driver, calendarPage.iconSetting, "Settings icon");
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

		// ____________________Verify cancel button and plan_______________
		assertTrue(IsElementPresent(driver, settingPage.buttonCancel, "Cancel button") && IsElementPresent(driver, settingPage.text699Selected, "$699/Month plan option"));
	}

	@Test(priority = 5, dependsOnMethods = { "verifyUserCanBuyPaidPlan" })
	public void verifyUserCanCancelSubscription() throws IOException, InterruptedException {

		// ________________Cancel subscription________________
		click(driver, settingPage.buttonCancel, "Cancel button");
		
		click(driver, manageSubscriptionPage.dropdownReason, "Reason dropdown");

		click(driver, manageSubscriptionPage.listOfReason, "Reason dropdownlist");
		ExtentManager.logInfoDetails("Able to select the reason from reason dropdown successfully");
		
		sendKeys(driver, manageSubscriptionPage.inputAddDescription, "Reason details field", "Cancelling subscription for testing");
//		ExtentManager.logInfoDetails("Entered value in description field : " + getAttribute(manageSubscription.inputAddDescription));

		click(driver, manageSubscriptionPage.buttonSubmit, "Submit button cancel subscription popup");
		ExtentManager.logInfoDetails(getText(CommonLocators.successMessageCancelSubscription));
		Thread.sleep(5000);
		// ___________________login, skip tour pages and verify subscription cancelled or not________________
		assertTrue(loginPage.loginApplication(email, CommonVariables.actualPass, "valid"));
		// Click skip and verify tour page
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.validateValidOTP(getOtp, "Tour Screen");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.textWelcomeUser));
		ExtentManager.logInfoDetails("Skipped the tour pages");

		// __________________Navigate to setting________________
		click(driver, calendarPage.iconSetting, "Settings icon");

		assertTrue(IsElementPresent(driver, manageSubscriptionPage.buttonManageSubscription, "Manage Subscription button") && 
				IsElementPresent(driver, settingPage.text699Selected, "$699/Month plan option"));
	}

	@Test(priority = 6, dependsOnMethods = { "verifyUserCanCancelSubscription" })
	public void verifyUserCarBuyPaidPlanAfterCancelOnce() throws IOException, InterruptedException {
		click(driver, manageSubscriptionPage.buttonManageSubscription, "Manage Subscription button");

		manageSubscriptionPage.selectPlan("paid");
		click(driver, CommonLocators.continueButton, "Continue button");
		getText(CommonLocators.successMessageBuyPaidPlan);
		
		explicitWait(driver, settingPage.buttonLogOut, 20);

		// ____________________Verify cancel button_______________
		assertTrue(IsElementPresent(driver, settingPage.buttonCancel, "Cancel button") && IsElementPresent(driver, settingPage.text699Selected, "$699/Month plan option"));
	}
}
