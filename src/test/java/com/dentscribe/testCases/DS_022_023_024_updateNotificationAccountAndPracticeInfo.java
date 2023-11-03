package com.dentscribe.testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_022_023_024_updateNotificationAccountAndPracticeInfo extends AndroidBase {
	GenerateOtp getOtp = new GenerateOtp(driver);

//	@Test(priority = 0)
	public void validateNotificationTest() throws IOException, InterruptedException {

		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "password"), "valid"));
		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		getOtp.fillOtp();
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");
		
		actions.scrollToPartialText("Push Notifications");
		String outPut = settingPage.enableDisableNotification(settingPage.checkedPushNotification, settingPage.unCheckedPushNotification);
		if (outPut == "Unchecked") {
			assertTrue(IsElementPresent(settingPage.unCheckedPushNotification, driver));
			ExtentManager.logInfoDetails("Push Notification switch button is OFF as expected");
		} else if (outPut == "Checked") {
			assertTrue(IsElementPresent(settingPage.checkedPushNotification, driver));
			ExtentManager.logInfoDetails("Push Notification switch button is ON as expected");
		}
		
		actions.scrollToPartialText("SMS Notifications");
		outPut = settingPage.enableDisableNotification(settingPage.checkedSmsNotification, settingPage.unCheckedSmsNotification);
		if (outPut == "Unchecked") {
			assertTrue(IsElementPresent(settingPage.unCheckedSmsNotification, driver));
			ExtentManager.logInfoDetails("SMS Notification switch button is OFF as expected");
		} else if (outPut == "Checked") {
			assertTrue(IsElementPresent(settingPage.checkedSmsNotification, driver));
			ExtentManager.logInfoDetails("SMS Notification switch button is ON as expected");
		}

		actions.scrollToPartialText("Email Notifications");
		outPut = settingPage.enableDisableNotification(settingPage.checkedEmailNotification, settingPage.unCheckedEmailNotification);
		if (outPut == "Unchecked") {
			assertTrue(IsElementPresent(settingPage.unCheckedEmailNotification, driver));
			ExtentManager.logInfoDetails("Email Notification switch button is OFF as expected");
		} else if (outPut == "Checked") {
			assertTrue(IsElementPresent(settingPage.checkedEmailNotification, driver));
			ExtentManager.logInfoDetails("Email Notification switch button is ON as expected");
		}
	}

	@Test(priority = 1)
	public void updateAccountAndPracticeInfo() throws InterruptedException, IOException 
	{
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "password"), "valid"));
		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		// ______________skip tour pages________________________
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

		// __________________Click setting icon_______________
		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");
		explicitWait(driver, settingPage.buttonHelp, 10);
		actions.scrollByCordinate(515, 2139, 561, 324, 1);

		// ____________________Edit phone ______________________
		settingPage.editPhoneNumber();
		actions.scrollToPartialText("Save");
		click(settingPage.buttonSave, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Save</b> button");

		Thread.sleep(3000);
		actions.scrollByCordinate(561, 2157, 510, 1041, 1);

		// __________________update info__________________
		settingPage.updatePracticeInfo();
		click(settingPage.backButton, driver);
		click(calendarPage.iconSetting, driver);
		explicitWait(driver, settingPage.buttonHelp, 10);
		actions.scrollByCordinate(515, 2139, 561, 324, 1);

		// _____________________verify number_______________________
		assertEquals(getAttribute(settingPage.inputPhoneNumber), settingPage.phNumber);
		ExtentManager.logInfoDetails("Actual : " + getAttribute(settingPage.inputPhoneNumber) + " expected : " + settingPage.phNumber + " are same");

		// ______________________verify info___________________
		Thread.sleep(2000);
		actions.scrollByCordinate(561, 2157, 510, 1041, 2);
		assertTrue(settingPage.verifyUpdatedPracticeInfo());
		ExtentManager.logInfoDetails("Account and Practice Info has been updated successfully");

	}

}
