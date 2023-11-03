package com.dentscribe.pages;

import org.openqa.selenium.By;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class PracticeInfoPage extends AndroidActions {

	AndroidDriver driver;

	public PracticeInfoPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// __________________________________locators_______________________
	public By inputName = By.xpath("//android.widget.TextView[@text='Name']" + CommonLocators.fixPath);
	public By inputAddress1 = By.xpath("//android.widget.TextView[@text='Address Line 1']" + CommonLocators.fixPath);
	public By inputAddress2 = By.xpath("//android.widget.TextView[@text='Address Line 2']" + CommonLocators.fixPath);
	public By inputCity = By.xpath("//android.widget.TextView[@text='City']" + CommonLocators.fixPath);
	public By dropdownState = By.xpath("(//android.widget.TextView[@text='State'])[2]");
	public By inputZip = By.xpath("//android.widget.TextView[@text='Zip Code']" + CommonLocators.fixPath);
	public By dropdownCountry = By.xpath("(//android.widget.TextView[@text='Country'])[2]");
	public By officeNoCountryCode = By.xpath("//android.widget.TextView[@text='Office Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	public By inputOfficeMobile = By.xpath("//android.widget.TextView[@text='Office Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.EditText");
	public By textIndia = By.xpath("//android.widget.TextView[contains(@text,'India')]");
	public By inputOfficeId = By.xpath("//android.widget.TextView[@text='Office Id']" + CommonLocators.fixPath);

	// _______________________________sikka________________________
	public By buttonRegister = By.xpath("//*[@text='Register']");
	public By buttonNext = By.xpath("//*[@text='Next']");
	public By RadioNo = By.xpath("//android.widget.TextView[@text='No']");
	public By RadioYes = By.xpath("//android.widget.TextView[@text='Yes']");
	public By textYourOrder = By.xpath("//*[@text='Your Order']");
	public By textTermsOfService = By.xpath("//*[contains(@text,'Terms of Service')]");
	public By textConfirmation = By.xpath("//*[@text='Confirmation']");

	// ______________fill practice info_______________
	public void fillPracticeInfo(String state, String country) throws InterruptedException {
		sendKeys(inputName, CommonMethods.genrateRandomFirstName(), driver);
		ExtentManager.logInfoDetails("Entered first name : <b>" + getAttribute(inputName) + "</b>");
		String address1 = "AddressOne" + CommonMethods.GenerateRandomNumber(5);
		sendKeys(inputAddress1, address1, driver);
		ExtentManager.logInfoDetails("Entered Address line 1: <b>" + address1 + "</b>");
		String address2 = "AddressTwo" + CommonMethods.GenerateRandomNumber(5);
		sendKeys(inputAddress2, address2, driver);
		ExtentManager.logInfoDetails("Entered Address line 2: <b>" + address2 + "</b>");
		scrollByCordinate(515, 2139, 561, 1000, 1); // added extra
		sendKeys(inputCity, CommonMethods.getAlphaNumericString(5), driver);
		ExtentManager.logInfoDetails("Entered City: <b>" + getAttribute(inputCity) + "</b>");
		click(dropdownState, driver);
		click(By.xpath("//android.widget.TextView[@text='" + state + "']"), driver);
		ExtentManager.logInfoDetails("<b>" + state + "</b> is selected from state dropdown");
		scrollToText("Continue");
		sendKeys(inputZip, CommonMethods.GenerateRandomNumber(6), driver);
		ExtentManager.logInfoDetails("Entered zip : " + getAttribute(inputZip) + "</b>");
		click(dropdownCountry, driver);
		click(By.xpath("//android.widget.TextView[@text='" + country + "']"), driver);
		ExtentManager.logInfoDetails("<b>" + country + "</b> is selected from country dropdown");
		click(officeNoCountryCode, driver);
		scrollToPartialText("+91");
		click(textIndia, driver);
		scrollUntilElementIsVisible("Office Phone Number");//
		sendKeys(inputOfficeMobile, readData("Config", "mobile"), driver);
		ExtentManager.logInfoDetails("Entered Mobile Number : <b>" + readData("Config", "mobile") + "</b>");
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Continue </b>Button");
	}

	public void scrollToNextButton() {

		if (readData("Config", "emmulator").equalsIgnoreCase("no")) {
			scrollByCordinate(542, 2097, 552, 425, 1);

		} else {
			scrollByCordinate(542, 2097, 552, 425, 1);
			scrollByCordinate(542, 2097, 552, 1800, 1);// added
		}

	}
}
