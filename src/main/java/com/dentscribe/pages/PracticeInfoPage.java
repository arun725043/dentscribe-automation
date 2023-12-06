package com.dentscribe.pages;

import org.junit.Assert;
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
	
	// validation messages
	public By validationMsgName = By.xpath("//android.widget.TextView[@text='Name is required.']");
	public By validationMsgAddressLine1 = By.xpath("//android.widget.TextView[@text='Address line 1 is required.']");
	public By validationMsgCity = By.xpath("//android.widget.TextView[@text='City is required.']");
	public By validationMsgState = By.xpath("//android.widget.TextView[@text='State is required.']");
	public By validationMsgZipCode = By.xpath("//android.widget.TextView[@text='Zip code is required.']");
	public By validationMsgCountry = By.xpath("//android.widget.TextView[@text='Country is required.']");
	public By validationMsgOfficePhoneNumber = By.xpath("//android.widget.TextView[@text='Phone number is required.']");

	// ______________fill practice info_______________
	public void fillPracticeInfo(String state, String country) throws InterruptedException {
		sendKeys(driver, inputName, "Name", CommonMethods.genrateRandomFirstName());
		String address1 = "AddressOne" + CommonMethods.GenerateRandomNumber(5);
		sendKeys(driver, inputAddress1, "Address Line 1", address1);
		String address2 = "AddressTwo" + CommonMethods.GenerateRandomNumber(5);
		sendKeys(driver, inputAddress2, "Address Line 2", address2);
//		scrollByCordinate(515, 2139, 561, 1000, 1); // added extra
		scrollUntilElementIsVisible("Zip Code");
//		scrollableClick("City");
		sendKeys(driver, inputCity, "City", CommonMethods.genrateRandomCity());
		click(driver, dropdownState, "State dropdown");
		click(driver, By.xpath("//android.widget.TextView[@text='" + state + "']"), state);
		sendKeys(driver, inputZip, "Zip Code", CommonMethods.GenerateRandomNumber(6));
		click(driver, dropdownCountry, "Country dropdown");
		click(driver, By.xpath("//android.widget.TextView[@text='" + country + "']"), country);
		scrollToText("Continue");
		click(driver, officeNoCountryCode, "Country code");
		scrollToPartialText("+91");
		click(driver, textIndia, "India");
		scrollUntilElementIsVisible("Office Phone Number");//
		sendKeys(driver, inputOfficeMobile, "Office phone number", readData("userdetails", "mobile"));
	}

	public void scrollToNextButton() {
		if (readData("Config", "emmulator").equalsIgnoreCase("no")) {
			scrollByCordinate(542, 2097, 552, 425, 1);

		} else {
			scrollByCordinate(542, 2097, 552, 425, 1);
			scrollByCordinate(542, 2097, 552, 1800, 1);// added
		}
	}
	
	public void clickPracticeContinueButton()
	{
		scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button");
	}
	
	// __________verifying validation message for mandatory fields________________
	public void validateMandatoryFieldsErrorMessages() throws InterruptedException
	{
		try {
			scrollUntilElementIsVisible("Practice Info");
			IsElementPresent(driver, validationMsgName, getText(validationMsgName));
			IsElementPresent(driver, validationMsgAddressLine1, getText(validationMsgAddressLine1));
			scrollUntilElementIsVisible("Office Phone Number");
			IsElementPresent(driver, validationMsgCity, getText(validationMsgCity));
			IsElementPresent(driver, validationMsgState, getText(validationMsgState));
			IsElementPresent(driver, validationMsgZipCode, getText(validationMsgZipCode));
			IsElementPresent(driver, validationMsgCountry, getText(validationMsgCountry));
			scrollUntilElementIsVisible("Continue");
			IsElementPresent(driver, validationMsgOfficePhoneNumber, getText(validationMsgOfficePhoneNumber));
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Any mandatory validation is missing please check logs.");
			Assert.fail();
		}
	}
}
