package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class ForgotPasswordPage extends AndroidActions {

	AndroidDriver driver;

	public ForgotPasswordPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	} 
	
	// _________locators______
	public By registeredEmailHeading = By.xpath("//android.widget.TextView[@text='Please enter your registered email with us']");
	public By emailInput = By.xpath("//android.widget.TextView[@text='Email']");
	public By linkForgotPassword = By.xpath("//android.widget.TextView[@text='Forgot Password']");
	
	// _______verify forgot password landing page________
	public boolean verifyForgotPasswordLandingPage() {
		click(linkForgotPassword, driver);
		ExtentManager.logInfoDetails("Click on <b>Forgot Password</b> link");
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(registeredEmailHeading));
		ExtentManager.logInfoDetails("<b>" + getText(registeredEmailHeading) + "</b> is present as expected");
		return IsElementPresent(registeredEmailHeading, driver);
	}

	// _________verify username is required message without filling the email_______
	public boolean verifyEmailIptWithoutFillingEmail() {
		click(CommonLocators.continueButton, driver);
		return IsElementPresent(CommonLocators.msgUsernameIsRequired, driver);
	}
}
