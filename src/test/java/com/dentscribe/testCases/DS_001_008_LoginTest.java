package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_001_008_LoginTest extends AndroidBase {
	GenerateOtp getOtp = new GenerateOtp(driver);
	@Test
	public void loginTest() throws IOException, InterruptedException {
		try {
			// ______________verify home page_____________________
			assertTrue(loginPage.verifyHomePageElement());
			ExtentManager.logInfoDetails("<b>Login, Signup and Forgot password</b> button is displayed as expected");

			// _______________verify mandatory fields_____________
			actions.scrollToText("Continue");
			assertTrue(loginPage.verifyLoginMandatoryField());
			ExtentManager.logInfoDetails("<b>username</b> and <b>password</b> is required message is displayed as expected");

			// ______________verify login with invalid credentials______________
			assertTrue(loginPage.loginApplication(readData("userdetails", "invalidUsername"), readData("userdetails", "invalidPassword"), "invalid"));
			ExtentManager.logInfoDetails("User is not logged in as expected");

			// ______________verify login with valid credentials______________
			assertTrue(loginPage.loginApplication(readData("userdetails", "username"), readData("userdetails", "password"), "valid"));
			assertTrue(loginPage.verifyBiometricPopupButton());

			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			ExtentManager.logInfoDetails("Clicked on skip on biometric popup");

			getOtp.fillOtp();
			click(CommonLocators.continueButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
			
			assertTrue(IsElementPresent(tourPages.textCalendarSchecule, driver));
			ExtentManager.logInfoDetails("<b>Calendar Schedule</b> page is displayed as expected");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
