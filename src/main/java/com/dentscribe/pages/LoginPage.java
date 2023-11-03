package com.dentscribe.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
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
	public By continueButton = By.xpath("//android.widget.TextView[@text='Continue']");
	public By linkForgotPassword = By.xpath("//android.widget.TextView[@text='Forgot Password']");
	public By textCalendarSchecule = By.xpath("//android.widget.TextView[@text='Calendar Schedule View']");
	public By signupButton = By.xpath("//android.widget.TextView[@text='Sign Up']");
	public By buttonLogin = By.xpath("//android.widget.TextView[@text='Login']");
	public By buttonSkip = By.xpath("//android.widget.TextView[@text='Skip']");
	public By buttonEnable = By.xpath("//android.widget.TextView[@text='Enable']");
	public By textSmsVerification = By.xpath("//android.widget.TextView[@text='SMS Verification']");

	// login application
	public boolean loginApplication(String username, String pass, String operation) throws IOException, InterruptedException {
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.textWelcome));
		clear(CommonLocators.txtUsername);
		sendKeys(CommonLocators.txtUsername, username, driver);
		ExtentManager.logInfoDetails("Entered value into username input field : " + username);
		clear(CommonLocators.txtPassword);
		sendKeys(CommonLocators.txtPassword, pass, driver);
		ExtentManager.logInfoDetails("Entered value into password input field : " + getAttribute(CommonLocators.txtPassword));
		scrollToText("Continue");
		click(continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Continue Button</b>");
		switch (operation) {
		case "valid":
			if (readData("Config", "emmulator").equalsIgnoreCase("no") && readData("Config", "biometric").equalsIgnoreCase("on")) {
				explicitWait(driver, buttonSkip, 30);
				explicitWait(driver, buttonEnable, 30);
				flag = true;
			} else {
				explicitWait(driver, textSmsVerification, 30);
				flag = true;
			}

			break;
		case "invalid":
			try {
				explicitWait(driver, buttonSkip, 10);
				System.out.println("try");
				flag = false;
			} catch (Exception e) {
				System.out.println("catch");
				flag = true;
			}
			break;
		default:
			System.out.println("please provide valid name");
		}
		return flag;
	}

	// ________verify home page_______
	public boolean verifyHomePageElement() {
		if (IsElementPresent(buttonLogin, driver) && IsElementPresent(signupButton, driver) && IsElementPresent(linkForgotPassword, driver) && IsElementPresent(CommonLocators.textWelcome, driver)) {
			return true;
		} else {
			return false;
		}
	}

	// __________verify welcome to dentscribe heading_________
	public boolean verifyLoginLandingPage() {
		return IsElementPresent(CommonLocators.textWelcome, driver);
	}

	// ____________verify username and password is required should display after clicking on continue_________
	public boolean verifyLoginMandatoryField() {
		click(continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button");
		if (IsElementPresent(CommonLocators.msgUsernameIsRequired, driver) && IsElementPresent(CommonLocators.msgPasswordIsRequired, driver)) {
			return true;
		} else {
			return false;
		}
	}

	// ______verify forgot password link is present or not__________
	public boolean verifyForgotPasswordLink() {
		return IsElementPresent(linkForgotPassword, driver);
	}

	// ______verify visibility of skip and enable button_____
	public boolean verifyBiometricPopupButton() {
		if (readData("Config", "emmulator").equalsIgnoreCase("yes")) {
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));
			return IsElementPresent(textSmsVerification, driver);
		} else {
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(buttonEnable));
			if (IsElementPresent(buttonSkip, driver) && IsElementPresent(buttonEnable, driver)) {
				return true;
			} else {
				return false;
			}
		}

	}

	public void clickSkipOnBasisOfBiometric() {
		if (readData("Config", "emmulator").equalsIgnoreCase("yes") && readData("Config", "biometric").equalsIgnoreCase("no")) {
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));
		} else {
			click(buttonSkip, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Skip</b> button on Biometric popup");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));

		}

	}

	// ___________click skip button and veridy calender schedule screen______
	public boolean clickBiometricPopupButton(String buttonName) throws InterruptedException {

		switch (buttonName) {
		case "skip":

			if (readData("Config", "emmulator").equalsIgnoreCase("yes") && readData("Config", "biometric").equalsIgnoreCase("no")) {
				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));
				return IsElementPresent(textSmsVerification, driver);
			} else {
//				click(buttonSkip, driver);
//				ExtentManager.logInfoDetails("Clicked on <b>Skip</b> button on Biometric popup");
				clickSkipOnBasisOfBiometric();
				AndroidBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(textSmsVerification));
				flag = IsElementPresent(textSmsVerification, driver);
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