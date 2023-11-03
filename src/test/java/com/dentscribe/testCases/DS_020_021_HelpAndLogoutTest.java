package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_020_021_HelpAndLogoutTest extends AndroidBase {

	@Test
	public void verifyHelpAndLogoutFunctionality() throws InterruptedException, IOException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		// ______________Logged in application________________
		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		// ______________skip tour pages________________________
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		getOtp.fillOtp();
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

		// ________________validate Help button at Navbar_____________
		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");
		click(settingPage.iconHelp, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Help</b> icon at the Navbar");
		assertTrue(IsElementPresent(settingPage.textFaqs, driver));
		ExtentManager.logInfoDetails("Help page is displayed as expected");

		// ________________validate Help button in General Setting_____________
		click(settingPage.backButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Back</b> icon ");
		Thread.sleep(2000);
		click(settingPage.buttonHelp, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Help</b> button in General Setting");
		assertTrue(IsElementPresent(settingPage.textFaqs, driver));
		ExtentManager.logInfoDetails("Help page is displayed as expected");
		click(settingPage.backButton, driver);

		// ___________________click logout________________
		settingPage.clickOnLogout();
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.txtUsername));

		// Verify login page
		assertTrue(IsElementPresent(CommonLocators.txtUsername, driver));
		ExtentManager.logInfoDetails("Login page is displayed as expected");

	}
}
