package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_002_ForgotPasswordTest extends AndroidBase {

	@Test
	public void verifyForgotPassword() throws InterruptedException {

		click(forgotPasswordPage.linkForgotPassword, driver);
		assertTrue(forgotPasswordPage.verifyForgotPasswordLandingPage());

		click(CommonLocators.continueButton, driver);
		assertTrue(forgotPasswordPage.verifyEmailIptWithoutFillingEmail());

		sendKeys(CommonLocators.txtUsername, readData("userdetails", "forgotPasswordUser"), driver);
		click(CommonLocators.continueButton, driver);
		
		//Add back button functionality

		Thread.sleep(10000);

	}

}
