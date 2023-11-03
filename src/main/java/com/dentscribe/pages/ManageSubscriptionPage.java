package com.dentscribe.pages;

import org.openqa.selenium.By;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
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
	public By textEula = By.xpath("//*[contains(@text,'End-User License')]");
	public By textSelectSubscription = By.xpath("//android.widget.TextView[@text='Select Subscription Tier']");
	public By textFreeTrial = By.xpath("//android.widget.TextView[@text='30-Day Free Trial']");
	public By buttonManageSubscription = By.xpath("//android.widget.TextView[@text='Manage Subscription']");
	public By text699Month = By.xpath("//android.widget.TextView[@text='$699/Month']");
	public By buttonAddPayment = By.xpath("//android.widget.TextView[@text='Add Payment Method']");
	public By textCardDetail = By.xpath("//android.widget.TextView[contains(@text,'Card Details')]");

	// ________________Cancel subscription____________
	public By dropdownReason = By.xpath("//android.widget.TextView[contains(@text,'Reason')]//following-sibling::android.view.ViewGroup//android.widget.EditText");
	public By listOfReason = By.xpath("//android.widget.TextView[contains(@text,'Reason')]//parent::android.view.ViewGroup//android.widget.ScrollView//android.widget.TextView");
	public By inputAddDescription = By.xpath("//android.widget.EditText[contains(@text,'Add description here')]");
	public By buttonSubmit = By.xpath("//android.widget.TextView[@text='Submit']");

	//___________verify eula screen____________
	public boolean verifyEulaScreen() {
		return IsElementPresent(textEula, driver);
	}

	//___________fill payment detail______________
	public void addPaymentMethod() throws InterruptedException {
		Thread.sleep(3000);
		sendKeys(inputCardholderName, CommonMethods.genrateRandomFirstName(), driver);
		String cardHolderName = getAttribute(inputCardholderName);
		ExtentManager.logInfoDetails("Entered CardHolder name : <b>" + cardHolderName + "</b>");
		sendKeys(inputCardNo, readData("testData", "cardNo"), driver);
		sendKeys(inputExpiry, readData("testData", "expiry"), driver);
		sendKeys(inputCvc, readData("testData", "cvc"), driver);
		sendKeys(inputZipCode, readData("testData", "zipcode"), driver);
		scrollToText("Continue");
		click(CommonLocators.continueButton, driver);
		explicitWait(driver, By.xpath("//android.widget.TextView[@text='" + cardHolderName + "']"), 30);
	}

}
