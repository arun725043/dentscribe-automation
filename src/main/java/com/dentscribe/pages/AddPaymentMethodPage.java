package com.dentscribe.pages;

import org.openqa.selenium.By;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class AddPaymentMethodPage extends AndroidActions {

	AndroidDriver driver;

	public AddPaymentMethodPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// __________locators________________
	public By headerTextPaymentMethodPage = By.xpath("//android.widget.TextView[@text='Add Payment Method']");
	public By headerCardDetailsSection = By.xpath("//android.widget.TextView[@text='Card Details ']");
	public By inputCardholderName = By.xpath("//android.widget.TextView[contains(@text,'Cardholder Name')]" + CommonLocators.fixPath);
	public By inputCardNo = By.id("com.dentscribe.dev:id/et_card_number");
	public By inputExpiry = By.id("com.dentscribe.dev:id/et_expiry");
	public By inputCvc = By.id("com.dentscribe.dev:id/et_cvc");
	public By inputZipCode = By.id("com.dentscribe.dev:id/postal_code");
	public By textCardDetail = By.xpath("//android.widget.TextView[contains(@text,'Card Details')]");
	public By iconBackAddPayemntMethodPage = By.xpath("//android.widget.TextView[@text='Add Payment Method']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView");
	
	public By iconEditPaymentMethod = By.xpath("//android.widget.TextView[@text='Payment Method']//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	
	//validation messages locators
	public By validationMsgCardHolderName = By.xpath("//android.view.ViewGroup[@resource-id='cardholder-name-input']//android.view.ViewGroup[@index=2]//android.widget.TextView");

	
	// _________verify whether user is on Add Payment Method page or not_______
	public boolean validateAddPaymentMethodPage()
	{
		if (IsElementPresent(driver, headerTextPaymentMethodPage, "Add Payment Method header") && IsElementPresent(driver, headerCardDetailsSection, "Card Details section header")) 
		{
			ExtentManager.logInfoDetails("<b>User is now on Add Payement Method page as expected");
			return true;
		} else {
			ExtentManager.logFailureDetails("Either expected Add Payement Method verified element not found or not exists. please check");
			return false;
		}
	}
	
	//___________fill payment detail______________
	public void addPaymentMethod(String cardHolderName, String cardNumber, String cardExpiryDate, String cardCVC, String zipcode) throws InterruptedException {
		scrollUntilElementIsVisible("Card Info*");
		sendKeys(driver, inputCardholderName, "Cardholder Name", cardHolderName);
		String name = getAttribute(inputCardholderName);
		ExtentManager.logInfoDetails("Entered CardHolder name : <b>" + name + "</b>");
		sendKeys(driver, inputCardNo, "Card Number", cardNumber);
		sendKeys(driver, inputExpiry, "Card Expiry", cardExpiryDate);
		sendKeys(driver, inputCvc, "Card CVC", cardCVC);
		sendKeys(driver, inputZipCode, "Postal Code", zipcode);
	}
	
	//_________click Add Payment Method page Continue button
	public void clickContinueButtonAddPaymentMethod()
	{
		scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button");
	}

	// __________verifying validation message for mandatory fields________________
	public void validateMandatoryFieldsErrorMessages() throws InterruptedException
	{
		scrollableClick("Continue");
		scrollUntilElementIsVisible("Cardholder Name*");
		IsElementPresent(driver, validationMsgCardHolderName, "Cardholder validation message");
		verifyTexts(getText(validationMsgCardHolderName), CommonVariables.errorMsgTextCardholderName);
		sendKeys(driver, inputCardholderName, "Cardholder Name", "Test Name123");
		IsElementPresent(driver, validationMsgCardHolderName, "Cardholder validation message");
		verifyTexts(getText(validationMsgCardHolderName), CommonVariables.errorMsgTextWrongCardholderName);
		sendKeys(driver, inputCardholderName, "Cardholder Name", "Test Name");
		scrollableClick("Continue");
		IsElementPresent(driver, CommonLocators.errorMessageCardDetailsNotAdded, getText(CommonLocators.errorMessageCardDetailsNotAdded));
	}
	
	// ___________click continue button_______________
	public void clickContinueButtonSubscriptionPage()
	{
		scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button on manage subscription page");
	}
}
