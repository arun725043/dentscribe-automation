package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_028_SettingPageTest extends AndroidBase {

	@Test
	public void settingPageTest() throws IOException, InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		getOtp.fillOtp();
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		// ______skip tour pages__________
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));
		ExtentManager.logInfoDetails("User is landed on calendar page successfully");

		// ____clicked setting icon on calendar page and verify setting page_______
		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on Setting icon on Calendar page");
		explicitWait(driver, settingPage.buttonHelp, 10);
		assertTrue(IsElementPresent(settingPage.buttonHelp, driver));
		ExtentManager.logInfoDetails("User is landed on setting page successfuly");

		// ________clicked back button on setting page and verify calendar page_______
		click(settingPage.backButton, driver);
		ExtentManager.logInfoDetails("Clicked on Back button");
		assertTrue(IsElementPresent(calendarPage.iconSetting, driver));
		ExtentManager.logInfoDetails("User is landed on calendar page successfully");

		click(calendarPage.iconSearch, driver);
		ExtentManager.logInfoDetails("Clicked on Search Icon");

		// ____clicked setting icon on patient search page and verify setting page_______
		click(searchPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Cliced on Setting Icon on patient search page");
		explicitWait(driver, settingPage.buttonHelp, 10);
		ExtentManager.logInfoDetails("User is landed on setting page successfuly");

		// ________clicked back button on setting page and verify patient search page_______
		click(settingPage.backButton, driver);
		ExtentManager.logInfoDetails("Clicked on Back button");
		assertTrue(IsElementPresent(searchPage.iconSetting, driver));
		ExtentManager.logInfoDetails("User is landed on Patient search page successfully");

	}

}
