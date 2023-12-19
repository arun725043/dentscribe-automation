package com.dentscribe.pages;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class LoginPage extends AndroidActions {

	AndroidDriver driver;
	public boolean flag = false;

	public LoginPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// _________locators______
	public By buttonLogin = By.xpath("//android.widget.TextView[@text='Login']");
	public By buttonSignup = By.xpath("//android.widget.TextView[@text='Sign Up']");
	public By linkSetUpBiometric = By.xpath("//android.widget.TextView[@text='Set Up Biometrics']");
	public By buttonContinue = By.xpath("//android.widget.TextView[@text='Continue']");
	public By buttonSkip = By.xpath("//android.widget.TextView[@text='Skip']");
	public By buttonEnable = By.xpath("//android.widget.TextView[@text='Enable']");	
	public By textCalendarSchecule = By.xpath("//android.widget.TextView[@text='Calendar Schedule View']");
	public By spuPopupText = By.xpath("//android.widget.TextView[@text='Please install SPU and refresh the data']");
	public By spuPopupOkButton = By.xpath("//android.widget.TextView[@text='Ok']");
	
	//validation messages
	public By validationMsgUsername = By.xpath("//android.view.ViewGroup[@resource-id='username-input']//android.view.ViewGroup[@index=2]//android.widget.TextView[@index=0]");
	public By validationMsgPassword = By.xpath("//android.view.ViewGroup[@resource-id='password-input']//android.view.ViewGroup[@index=3]//android.widget.TextView[@index=0]");
	
	
	// __________verify whether application launched or not__________
	public void verifyIsApplicationLaunched() {
		if(IsElementPresent(driver, CommonLocators.textWelcomeDentScribe, "Welcome to Dentscribe!"))
		{
			ExtentManager.logInfoDetails("Application launched successfully");
		}
		else {
			ExtentManager.logFailureDetails("Either application not opened or verifying Welcome text not matched. please check");
			Assert.fail();
		}
	}

	// __________verify whether login page exists or not__________
	public boolean validateLoginPage() 
	{
		if (IsElementPresent(driver, CommonLocators.loginNote, "text - " + CommonVariables.loginNoteText)) {
			ExtentManager.logInfoDetails("<b>User is now on Login page as expected");
			return true;
		} else {
			ExtentManager.logFailureDetails("Either expected Login page verified element not found or not exists. please check");
			return false;
		}
	}
	
	public void enterUsernamePassword(String username, String password)
	{
		clear(CommonLocators.inputTxtUsername);
		sendKeys(driver, CommonLocators.inputTxtUsername, "Username", username);
		clear(CommonLocators.inputTxtPassword);
		sendKeys(driver, CommonLocators.inputTxtPassword, "Password", password);
	}
	
	// login application
	public boolean loginApplication(String username, String password, String operation) throws IOException, InterruptedException {
		enterUsernamePassword(username, password);
		scrollToText("Continue");
		click(driver, buttonContinue, "Continue button on login page");
		switch (operation) {
		case "valid":
			if (readData("Config", "emmulator").equalsIgnoreCase("no") && readData("Config", "biometric").equalsIgnoreCase("on")) {
				explicitWait(driver, buttonSkip, 30);
				explicitWait(driver, buttonEnable, 30);
				ExtentManager.logInfoDetails("Biometric popup appearing as expected");
				return true;
			} else {
				if (readData("Config", "emmulator").equalsIgnoreCase("no") && readData("Config", "biometric").equalsIgnoreCase("yes")) 
				{
					explicitWait(driver, SmsVerification.textSmsVerification, 30);
					ExtentManager.logInfoDetails("SMS Verification page appearing as expected");
					return true;
				}
				else {
					verifyBiometricPopupButton();
					ExtentManager.logInfoDetails("Biometric popup appearing as expected.");
				}				
			}
		case "invalid":
			IsElementPresent(driver, CommonLocators.invalidCredentialsError, "Error message");
			ExtentManager.logInfoDetails("Expected error message found - <b>" + getText(CommonLocators.invalidCredentialsError) + "<b>");
			IsElementPresent(driver, CommonLocators.labelUsername, "Username field");
			ExtentManager.logInfoDetails("User not logged in as expected and still on Login page");
			return true;
		case "no record":
			IsElementPresent(driver, CommonLocators.errorMessageNoRecordFound, "Error message");
			ExtentManager.logInfoDetails("Expected error message found - <b>" + getText(CommonLocators.errorMessageNoRecordFound) + "<b>");
			IsElementPresent(driver, CommonLocators.labelUsername, "Username field");
			ExtentManager.logInfoDetails("User not logged in as expected and still on Login page");
			return true;
		case "error":
			IsElementPresent(driver, CommonLocators.errorMessageWithoutPractice, "Error message");
			ExtentManager.logInfoDetails("User not able to login because expected error message found - <b>" + getText(CommonLocators.errorMessageWithoutPractice) + "<b>");
			return true;
		case "spu popup":
			verifyCloseSpuInstallPopup();
			return true;
		default:
			ExtentManager.logFailureDetails("Operation name could be valid or invalid. ");
			return false;
		}
	}
	
	// ________verify SPU install PopUp and close it_______
    public void verifyCloseSpuInstallPopup() 
    {
    	IsElementPresent(driver, spuPopupText, "SPU Install Popup");
        assertEquals(getText(spuPopupText), "Please install SPU and refresh the data", "SPU Install Popup");
        ExtentManager.logInfoDetails("Message found on SPU popup is - <b>" + getText(spuPopupText) + "<b>");
        click(driver, spuPopupOkButton, "SPU Install Popup OK button");
    }

	// ________verify home page elements_______
	public void verifyHomePageElement() {
		IsElementPresent(driver, buttonLogin, "Login tab");
		IsElementPresent(driver, buttonSignup, "Sign Up tab");
		IsElementPresent(driver, CommonLocators.labelUsername, "Username");
		IsElementPresent(driver, CommonLocators.labelPassword, "Password");
		IsElementPresent(driver, CommonLocators.linkForgotPassword, "Forgot Password link");
		if(readData("Config", "biometric").equalsIgnoreCase("on"))
		{
			IsElementPresent(driver, linkSetUpBiometric, "Set Up Biometrics");
		}
		else {
			ExtentManager.logInfoDetails("To check 'Biometrics' link please set 'on' in config file");
		}
	}
	
	// _________verify wrong email id format_______
	public void validateWrongEmailId(String email) throws InterruptedException
	{
		sendKeys(driver, CommonLocators.inputTxtUsername, "Username", email);
		if (getText(validationMsgUsername).equals(CommonVariables.errorMsgTextInvalidEmail))
		{
			ExtentManager.logInfoDetails("Expected validation message found : <b>" + CommonVariables.errorMsgTextInvalidEmail);
		}
		else {
			ExtentManager.logFailureDetails("Expected validation message is : <b>" + CommonVariables.errorMsgTextInvalidEmail + " but actual message found : <b>" + getText(validationMsgUsername));
			Assert.fail();
		}
	}

	// ____________verify user name and password is required should display after clicking on continue_________
	public void verifyLoginMandatoryField() {
		click(driver, buttonContinue, "Continue button");
		IsElementPresent(driver, validationMsgUsername, "Username validation");
		getText(validationMsgUsername);
		IsElementPresent(driver, validationMsgPassword, "Password validation");
		getText(validationMsgPassword);
	}

	// ______verify visibility of skip and enable button_____
	public boolean verifyBiometricPopupButton() {
		if (readData("Config", "emmulator").equalsIgnoreCase("yes")) {
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(SmsVerification.textSmsVerification));
			return IsElementPresent(driver, SmsVerification.textSmsVerification, "<b> SMS Verification screen <b>");
		} else {
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(buttonEnable));
			if (IsElementPresent(driver, buttonSkip, "Skip button") && IsElementPresent(driver, buttonEnable, "Enable button")) {
				ExtentManager.logInfoDetails("<b>Skip</b> and <b>Enable</b> buttons displayed as expected on Biometric popup");
				return true;
			} else {
				return false;
			}
		}
	}

	// ___________click skip button and verify calendar schedule screen______
	public boolean clickBiometricPopupButton(String buttonName) throws InterruptedException {

		switch (buttonName) {
		case "skip":
			if (readData("Config", "emmulator").equalsIgnoreCase("yes") && readData("Config", "biometric").equalsIgnoreCase("no")) {
				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(SmsVerification.textSmsVerification));
				return IsElementPresent(driver, SmsVerification.textSmsVerification, "SMS Verification screen");
			} else {
				click(driver, buttonSkip, "Skip button biometric popup");
				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(SmsVerification.textSmsVerification));
				flag = IsElementPresent(driver, SmsVerification.textSmsVerification, "SMS Verification screen");
				Thread.sleep(1000);
			}
			break;
		case "enable":
			System.out.println("Biometric login not allowed through automation. please pass skip option ");
			break;
		default:
			break;
		}
		return flag;
	}
}