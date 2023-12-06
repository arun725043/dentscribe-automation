package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class ForgotPasswordPage extends AndroidActions {

	AndroidDriver driver;

	public ForgotPasswordPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	} 
	
	public String usernameField = "Username";
	// _________locators______
	public By registeredEmailHeading = By.xpath("//android.widget.TextView[@text='Please enter your registered email with us']");
	public By emailInput = By.xpath("//android.widget.TextView[@text='Email']");
	public By invalidEmailMessage = By.xpath("//android.widget.TextView[@text='Please enter valid email.']");
	public By successMessage = By.xpath("//android.widget.TextView[@text='Reset password link has been sent to your email']");
	public By errorMessage = By.xpath("//android.widget.TextView[@text='No record found']");
	public By iconBackForgotPassword = By.xpath("//android.widget.ImageView[@index=0]");
	
	// _______verify forgot password landing page________
	public boolean verifyForgotPasswordLandingPage() {
		click(driver, CommonLocators.linkForgotPassword, "Forgot Password Link");
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(registeredEmailHeading));
		if(IsElementPresent(driver, registeredEmailHeading, "Forgot password page header note"))
		{
			ExtentManager.logInfoDetails("Header note is - <b>" + getText(registeredEmailHeading) + "<b>");
			return true;
		}
		else {
			return false;
		}
	}

	// _________verify 'user name is required' message without filling the email_______
	public boolean verifyForgotPasswordWithoutEmail() {
		clickContinueButton();
		if(IsElementPresent(driver, CommonLocators.msgUsernameIsRequired, "username validation message"))
		{
			ExtentManager.logInfoDetails("Validation message found - <b>" + getText(CommonLocators.msgUsernameIsRequired) + "<b>");
			return true;
		}
		else {
			ExtentManager.logFailureDetails("Either expected error message not found or not matched with expected. please check");
			return false;
		}
	}
	
	//_________verify 'Please enter valid email' message with invalid email_______
	public boolean verifyForgotPasswordWithWrongEmail(String emailId) {
		sendKeys(driver, CommonLocators.inputTxtUsername, usernameField, emailId);
		if(IsElementPresent(driver, invalidEmailMessage, "Wrong username validation message"))
		{
			ExtentManager.logInfoDetails("Validation message found - <b>" + getText(invalidEmailMessage) + "<b>");
			return true;
		}
		else {
			ExtentManager.logFailureDetails("Either expected error message not found or not matched with expected. please check");
			return false;
		}
	}
	
	//_________verify whether send forgot password link to non existing email id or not_______
	public void verifyForgotPasswordWithNonExistingEmail(String emailId)
	{
		sendKeys(driver, CommonLocators.inputTxtUsername, usernameField, emailId);
		clickContinueButton();
		ExtentManager.logInfoDetails("Error message found - <b>" + getText(errorMessage) + "<b>");
	}
	
	//_________verify whether send forgot password link to valid email id or not_______
	public void verifyForgotPasswordWithExistingEmail(String emailId) throws InterruptedException
	{
		sendKeys(driver, CommonLocators.inputTxtUsername, usernameField, emailId);
		clickContinueButton();
		ExtentManager.logInfoDetails("Success message found - <b>" + getText(successMessage) + "<b>");
	}
	
	//click continue button given in forgot password page
	public void clickContinueButton()
	{
		click(driver, CommonLocators.continueButton, "Continue button");
	}
	
	//Click Back icon and validate
	public boolean clickVerifyBackIcon()
	{
		click(driver, iconBackForgotPassword, "Back icon");
		if(IsElementPresent(driver, CommonLocators.loginNote, CommonVariables.loginNoteText))
		{
			ExtentManager.logInfoDetails("User successfully come back to Login page as expected");
			return true;
		}
		else {
			ExtentManager.logFailureDetails("Either login page not found or not opened or expected element not found. please check");
			return false;
		}
	}
}
