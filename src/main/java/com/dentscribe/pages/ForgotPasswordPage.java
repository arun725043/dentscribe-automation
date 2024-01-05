package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

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
	
	public String usernameField = "Username";
	// _________locators______
	public By registeredEmailHeading = By.xpath("//android.widget.TextView[@text='Please enter your registered email with us']");
	public By inputEmail = By.xpath("//android.view.ViewGroup[@resource-id='username-input']//android.widget.EditText");
	public By msgUsernameIsRequired = By.xpath("//android.widget.TextView[@text='Username is required.']");
	public By invalidEmailMessage = By.xpath("//android.widget.TextView[@text='Please enter valid email.']");
	public By successMessage = By.xpath("//android.widget.TextView[@text='Reset password link has been sent to your email']");
	public By errorMessage = By.xpath("//android.widget.TextView[@text='No record found']");
	public By iconBackForgotPassword = By.xpath("//android.widget.ImageView[@index=0]");
	
	// _______verify forgot password landing page________
	public void validateForgotPasswordPage() 
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(registeredEmailHeading));
		if(IsElementPresent(driver, registeredEmailHeading, "Forgot password page header note"))
		{
			ExtentManager.logInfoDetails("Header note is - <b>" + getText(registeredEmailHeading) + "<b>");
		}
		else {
			ExtentManager.logFailureDetails("Either expected forgot page not exists or its verified element not found/exists. please check");
			Assert.fail();
		}
	}

	// _________verify 'user name is required' message without filling the email_______
	public void verifyForgotPasswordWithoutEmail() {
		clickContinueButton();
		if(IsElementPresent(driver, msgUsernameIsRequired, "username validation message"))
		{
			ExtentManager.logInfoDetails("Validation message found - <b>" + getText(msgUsernameIsRequired) + "<b>");
		}
		else {
			ExtentManager.logFailureDetails("Either expected error message not found or not matched with expected. please check");
			Assert.fail();
		}
	}
	
	//_________verify 'Please enter valid email' message with invalid email_______
	public boolean verifyForgotPasswordWithWrongEmail(String emailId) {
		sendKeys(driver, inputEmail, usernameField, emailId);
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
		sendKeys(driver, inputEmail, usernameField, emailId);
		clickContinueButton();
		ExtentManager.logInfoDetails("Error message found - <b>" + getText(errorMessage) + "<b>");
	}
	
	//_________verify whether send forgot password link to valid email id or not_______
	public void verifyForgotPasswordWithExistingEmail(String emailId) throws InterruptedException
	{
		sendKeys(driver, inputEmail, usernameField, emailId);
		clickContinueButton();
		ExtentManager.logInfoDetails("Success message found - <b>" + getText(successMessage) + "<b>");
	}
	
	//click continue button given in forgot password page
	public void clickContinueButton()
	{
		click(driver, CommonLocators.continueButton, "Continue button");
	}
	
	//Click Back icon and validate
	public void clickBackIconForgotPasswordPage()
	{
		click(driver, iconBackForgotPassword, "Back icon");
	}
}
