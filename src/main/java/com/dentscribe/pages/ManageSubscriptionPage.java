package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class ManageSubscriptionPage extends AndroidActions {

	AndroidDriver driver;

	public ManageSubscriptionPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// __________locators________________
	public By inputCardholderName = By.xpath("//android.widget.TextView[contains(@text,'Cardholder Name')]" + CommonLocators.fixPath);
	public By inputCardNo = By.id("com.dentscribe.dev:id/et_card_number");
	public By inputExpiry = By.id("com.dentscribe.dev:id/et_expiry");
	public By inputCvc = By.id("com.dentscribe.dev:id/et_cvc");
	public By inputZipCode = By.id("com.dentscribe.dev:id/postal_code");
	public By textSelectSubscription = By.xpath("//android.widget.TextView[@text='Select Subscription Tier']");
	public By textFreeTrial = By.xpath("//android.widget.TextView[@text='30-Day Free Trial']");
	public By buttonManageSubscription = By.xpath("//android.widget.TextView[@text='Manage Subscription']");
	public By text699Month = By.xpath("//android.widget.TextView[@text='$699/Month']");
	public By buttonAddPayment = By.xpath("//android.widget.TextView[@text='Add Payment Method']");
	public By textCardDetail = By.xpath("//android.widget.TextView[contains(@text,'Card Details')]");
	public By iconBackAddPayemntMethodPage = By.xpath("//android.widget.TextView[@text='Add Payment Method']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView");

	// ________________Cancel subscription____________
	public By dropdownReason = By.xpath("//android.widget.TextView[contains(@text,'Reason')]//following-sibling::android.view.ViewGroup//android.widget.EditText");
	public By listOfReason = By.xpath("//android.widget.TextView[contains(@text,'Reason')]//parent::android.view.ViewGroup//android.widget.ScrollView//android.widget.TextView");
	public By inputAddDescription = By.xpath("//android.widget.EditText[contains(@text,'Add description here')]");
	public By buttonSubmit = By.xpath("//android.widget.TextView[@text='Submit']");
	public By headerCancelSubscriptionPage = By.xpath("//android.widget.TextView[@text='Cancel Subscriptions']");
	public By buttonSubmitCancelSubscription = By.xpath("//android.view.View//android.widget.TextView[@text='Submit']");
	
	//validation messages locators
	public By validationMsgCardHolderName = By.xpath("//android.view.ViewGroup[@resource-id='cardholder-name-input']//android.view.ViewGroup[@index=2]//android.widget.TextView");

	
	// _________verify signup note to validate whether user is on signup page or not_______
	public boolean verifySubscriptionLandingPage()
	{
		explicitWait(driver, textSelectSubscription, 10);
		if (IsElementPresent(driver, textSelectSubscription, "text - Select Subscription Tier")) {
			ExtentManager.logInfoDetails("User is now on <b> Manage Subscription page as expected");
			return true;
		} else {
			ExtentManager.logFailureDetails("Either expected Manage Subscription page verified element not found or not exists. please check");
			return false;
		}
	}
		
	public void selectPlan(String planName)
	{
		if (planName.equalsIgnoreCase("FREE TRIAL"))
		{
			click(driver, textFreeTrial, "$30Days Free Trial plan option");
		}
		else if (planName.equalsIgnoreCase("PAID")) {
			click(driver, text699Month, "$699/Month plan option");
		}
		else {
			ExtentManager.logFailureDetails("Plan could be FREE TRIAL or PAID");
			Assert.fail();
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
	
	//________click Add Payment Method button and verify 'Add Payment Method' screen_____
	public void clickVerifyAddPaymentMethodButton() throws InterruptedException
	{
		scrollableClick("Add Payment Method");
		ExtentManager.logInfoDetails("<b>Add Payment Method button clicked");
		if (IsElementPresent(driver, buttonAddPayment, "Add payment method header") && IsElementPresent(driver, textCardDetail, "Crad details header")) {
			ExtentManager.logInfoDetails("User is now on <b> Add payment method <b> as expected");
		} else {
			ExtentManager.logFailureDetails("Either expected Signup page verified element not found or not exists. please check");
			Assert.fail();
		}
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
}
