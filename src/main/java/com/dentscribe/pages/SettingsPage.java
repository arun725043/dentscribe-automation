package com.dentscribe.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class SettingsPage extends AndroidActions {

	AndroidDriver driver;

	public SettingsPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public String phoneNumber = "91" + GenerateRandomNumber(8);

	// _____________practice info variables__________
	String name;
	String address1;
	String address2;

	// Settings page locators
	public By headerSettingsPage = By.xpath("//android.view.ViewGroup[@resource-id='setting screen-view']//android.widget.TextView[@text='Settings']");
	public By backIconSettingsPage = By.xpath("//android.widget.TextView[@text='Settings']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By iconHelp = By.xpath("//android.widget.TextView[@text='Settings']//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	public By buttonHelp = By.xpath("//android.widget.TextView[@text='Help']");
	public By buttonLogOut = By.xpath("//android.widget.TextView[@text='Log Out']");
	public By textFreeTrial = By.xpath("//android.widget.TextView[contains(@text,'30-Day Free Trial Selected')]");
	public By buttonManageSubscription = By.xpath("//android.widget.TextView[@text='Manage Subscription']");
	public By text699Selected = By.xpath("//android.widget.TextView[contains(@text,'$699/Month Selected')]");
	public By buttonCancel = By.xpath("//android.widget.TextView[@text='Cancel']");
	public By inputPhoneNumber = By.xpath("//android.view.ViewGroup[@resource-id='phone-input']//android.widget.EditText");
	public By inputCurrentPassword = By
			.xpath("//android.widget.TextView[@text='Current Password']" + CommonLocators.fixPath);
	public By inputNewPassword = By.xpath("//android.widget.TextView[@text='New Password']" + CommonLocators.fixPath);	
	public By buttonSave = By.xpath("//android.widget.TextView[@text='Save']");

	// error message locators
	public By ErrorMsgNewPassword = By.xpath("//android.view.ViewGroup[@resource-id='new-password-input']//android.view.ViewGroup[@index=2]//android.widget.TextView[@index=0]");
	
	// practice info locators
	public By inputPracticeName = By.xpath("//android.view.ViewGroup[@resource-id='name-input']//android.widget.EditText");
	public By inputAddressLine1 = By.xpath("//android.view.ViewGroup[@resource-id='address-line-1-input']//android.widget.EditText");
	public By inputAddressLine2 = By.xpath("//android.view.ViewGroup[@resource-id='address-line-2-input']//android.widget.EditText");
	public By inputCity = By.xpath("//android.widget.TextView[@text='City']" + CommonLocators.fixPath);
	public By dropdownState = By.xpath("(//android.widget.TextView[@text='State'])[2]");
	public By inputZip = By.xpath("//android.widget.TextView[@text='Zip Code']" + CommonLocators.fixPath);
	public By dropdownCountry = By.xpath("(//android.widget.TextView[@text='Country'])[2]");
	public By officeNoCountryCode = By.xpath("//android.widget.TextView[@text='Office Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	public By inputOfficeMobile = By.xpath("//android.widget.TextView[@text='Office Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.EditText");
	public By textIndia = By.xpath("//android.widget.TextView[contains(@text,'India')]");
	public By inputOfficeId = By.xpath("//android.widget.TextView[@text='Office Id']" + CommonLocators.fixPath);

	// Notification
	public By switchPushNotification = By.xpath("//android.widget.TextView[contains(@text,'Push Notifications')]//following-sibling::android.widget.Switch");
	public By switchSmsNotification = By.xpath("//android.widget.TextView[contains(@text,' SMS Notifications')]//following-sibling::android.widget.Switch");
	public By switchEmailNotification = By.xpath("//android.widget.TextView[contains(@text,' Email Notifications')]//following-sibling::android.widget.Switch");
	
	// Help page locators
	public By headerHelpPage = By.xpath("//android.view.ViewGroup[@resource-id='help-screen-view']//android.widget.TextView[@text='Help']");
	public By backIconHelpPage = By.xpath("//android.widget.TextView[@text='Help']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By textFaqs = By.xpath("//android.widget.TextView[@text='FAQs']");

	// ________________Cancel subscription____________
	public By dropdownReason = By.xpath("//android.widget.TextView[contains(@text,'Reason')]//following-sibling::android.view.ViewGroup//android.widget.EditText");
	public By listOfReason = By.xpath("//android.widget.TextView[contains(@text,'Reason')]//parent::android.view.ViewGroup//android.widget.ScrollView//android.widget.TextView");
	public By inputAddDescription = By.xpath("//android.widget.EditText[contains(@text,'Add description here')]");
	public By buttonSubmit = By.xpath("//android.widget.TextView[@text='Submit']");
	public By headerCancelSubscriptionPage = By.xpath("//android.widget.TextView[@text='Cancel Subscriptions']");
	public By buttonSubmitCancelSubscription = By.xpath("//android.view.View//android.widget.TextView[@text='Submit']");
		
	
	// _________verify settings page exists or not_______
	public boolean validateSettingsPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(headerSettingsPage));
		String headerText = getText(headerSettingsPage);
		if(headerText.trim().equalsIgnoreCase("settings"))
		{
			ExtentManager.logInfoDetails("User is now on <b> Settings page <b> as expected");
			return true;
		}
		else {
			ExtentManager.logFailureDetails("Expected Settings page either not found or header not matched. please check");
			return false;
		}
	}
	
	// _________verify help page exists or not_______
	public void validateHelpPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFaqs));
		String headerText = getText(headerHelpPage);
		if(headerText.trim().equalsIgnoreCase("help"))
		{
			ExtentManager.logInfoDetails("User is now on <b> Help page <b> as expected");
		}
		else {
			ExtentManager.logFailureDetails("Expected Help page either not found or header not matched. please check");
			Assert.fail();
		}
	}
	
	// click logout link
	public void clickOnLogout() {
		click(driver, buttonLogOut, "Logout button");
	}

	// ____________verify whether selected plan and its related button exists or not_____________
	public boolean verifyBuyPlanOnSettingsPage(String plan) {
		switch (plan) {
		case "free":
			assertTrue(IsElementPresent(driver, buttonManageSubscription, "Manage Subscription button"));
			assertTrue(IsElementPresent(driver, textFreeTrial, "30-Day Free Trial option"));
			break;
		case "paid":
			assertTrue(IsElementPresent(driver, buttonCancel, "Cancel button"));
			assertTrue(IsElementPresent(driver, text699Selected, "$699/Month plan option"));
			break;
		default:
			break;
		}
		return IsElementPresent(driver, textFreeTrial, "Free Trial text");
	}

	// _______generate new password_________
	public String generatePassword() {
		return new String("Pass@" + CommonMethods.GenerateRandomNumber(4));
	}

	// ___________update phone number____________
	public void editPhoneNumber() {
		clear(inputPhoneNumber);
//		String phoneNumber = "91" + GenerateRandomNumber(8);
		sendKeys(driver, inputPhoneNumber, phoneNumber, phoneNumber);
	}
	
	// __________Enable disable setting notification___
	public void enableDisableNotifications(String switchOnOff, By elementLocator, String elementName, By expectMessageLocator) throws InterruptedException {
		scrollUntilElementIsVisible("Email Notifications");
		if (switchOnOff.equalsIgnoreCase("on"))
		{
			if(getText(elementLocator).equalsIgnoreCase("ON"))
			{
				ExtentManager.logInfoDetails(elementName + " switch button is already ON");
			}
			else
			{
				ExtentManager.logInfoDetails(elementName + " switch button is OFF");
				click(driver, elementLocator, elementName);
				getText(expectMessageLocator);
				Thread.sleep(5000);
				verifyTexts(getText(elementLocator), "ON");
				ExtentManager.logInfoDetails("<b>" + elementName + " switch button is ON as expected");
			}
		}
		else if (switchOnOff.equalsIgnoreCase("off"))
		{
			if(getText(elementLocator).equalsIgnoreCase("OFF"))
			{
				ExtentManager.logInfoDetails(elementName + " switch button is already OFF");
			}
			else
			{
				ExtentManager.logInfoDetails(elementName + " switch button is ON");
				click(driver, elementLocator, elementName);
				getText(expectMessageLocator);
				Thread.sleep(5000);
				verifyTexts(getText(elementLocator), "OFF");
				ExtentManager.logInfoDetails("<b>" + elementName + " switch button is OFF as expected");
			}
		}
		else {
			ExtentManager.logFailureDetails("Notifications switch could be only On/Off");
			Assert.fail();
		}
	}

	// Updating practice info
	public void fillPracticeInfo(String practiceName, String address1, String address2, String city, String state, String zipCode, String country, String countryCode, String phoneNumber) throws InterruptedException 
	{
		if (practiceName != "")
		{
			scrollToText("Address Line 1");
			clear(inputPracticeName);
			sendKeys(driver, inputPracticeName, "Practice name", practiceName);
		}
		else {
			ExtentManager.logInfoDetails("Practice name value not given for update");
		}
		
		if (address1 != "")
		{
//			scrollToPartialText("Address Line 1");
			clear(inputAddressLine1);
			sendKeys(driver, inputAddressLine1, "Address Line 1", address1);
		}
		else {
			ExtentManager.logInfoDetails("Address1 value not given for update");
		}

		if (address2 != "")
		{
			scrollToPartialText("Address Line 2");
			clear(inputAddressLine2);
			sendKeys(driver, inputAddressLine2, "Address Line 2", address2);
		}
		else {
			ExtentManager.logInfoDetails("Address2 value not given for update");
		}
		
		if (city != "")
		{
			scrollUntilElementIsVisible("Zip Code");
			sendKeys(driver, inputCity, "City", city);
		}
		else {
			ExtentManager.logInfoDetails("City value not given for update");
		}
		
		if (state != "")
		{
			click(driver, dropdownState, "State dropdown");
			scrollToPartialText(state);
//			click(driver, By.xpath("//android.widget.TextView[@text='" + state + "']"), state);
		}
		else {
			ExtentManager.logInfoDetails("State value not given for update");
		}
		
		if(country != "")
		{
			click(driver, dropdownCountry, "Country dropdown");
			click(driver, By.xpath("//android.widget.TextView[@text='" + country + "']"), country);
		}
		else {
			ExtentManager.logInfoDetails("Country value not given for update");
		}
		
		if (countryCode != "" && phoneNumber != "")
		{
			scrollToText("Continue");
			click(driver, officeNoCountryCode, "Country code");
			scrollToPartialText(countryCode);
			click(driver, textIndia, "India");
			scrollUntilElementIsVisible("Office Phone Number");//
			sendKeys(driver, inputOfficeMobile, "Phone Number", phoneNumber);
		}
		else {
			ExtentManager.logInfoDetails("Either countrycode or phonemuber not given for update");
		}
	}
	
	public void clickContinueButtonPracticeInfo()
	{
		scrollableClick("Continue");
		explicitWait(driver, CommonLocators.continueButton, 10);
	}

	// _____verify practice info after updation________
	public void verifyUpdatedPracticeInfo(String practiceName, String address1, String address2, String city, String state, String zipCode, String country, String countryCode, String phoneNumber) {
		if (practiceName != "")
		{
			scrollToPartialText("Address Line 1");
			verifyTexts(getText(inputPracticeName), practiceName);
		}
		else {
			ExtentManager.logInfoDetails("Practice name value not updated");
		}
		if(address1 != "")
		{
			verifyTexts(getText(inputAddressLine1), address1);
		}
		else {
			ExtentManager.logInfoDetails("Address Line1 value not updated");
		}
		if (address2 != "")
		{
			verifyTexts(getText(inputAddressLine2), address2);
		}
		else {
			ExtentManager.logInfoDetails("Address Line2 value not updated");
		}
	}

}
