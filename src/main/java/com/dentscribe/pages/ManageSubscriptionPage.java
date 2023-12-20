package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class ManageSubscriptionPage extends AndroidActions {

	AndroidDriver driver;

	public ManageSubscriptionPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// __________locators________________
	public By textSelectSubscription = By.xpath("//android.widget.TextView[@text='Select Subscription Tier']");
	public By textFreeTrial = By.xpath("//android.widget.TextView[@text='30-Day Free Trial']");
	public By text699Month = By.xpath("//android.widget.TextView[@text='$699/Month']");
	public By buttonAddPayment = By.xpath("//android.widget.TextView[@text='Add Payment Method']");
	public By iconEditPaymentMethod = By.xpath("//android.widget.TextView[@text='Payment Method']//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	
	// _________verify signup note to validate whether user is on signup page or not_______
	public boolean validateManageSubscriptionPage()
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
	
	//________________select plan___________________
	public void selectPlan(String planName)
	{
		if (planName.equalsIgnoreCase("FREE TRIAL"))
		{
			scrollToText("30-Day Free Trial");
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
	
	public void verifyCardHolderName(String cardHolderName)
	{
		explicitWait(driver, By.xpath("//android.widget.TextView[@text='" + cardHolderName + "']"), 30);
		if (IsElementPresent(driver, By.xpath("//android.widget.TextView[@text='" + cardHolderName + "']"), "Cardholder Name"))
		{
			ExtentManager.logInfoDetails("<b>paymentMethod updated successfully.");
		}
		else {
			ExtentManager.logFailureDetails("<b>Either payment method not added or cardholder name not matched. please check");
			Assert.fail();
		}
	}
	
	//________click Add Payment Method button and verify 'Add Payment Method' screen_____
	public void clickPaymentMethodButton(String buttonName) throws InterruptedException
	{
		if (buttonName == "add")
		{
			scrollToText("Add Payment Method");
			click(driver, buttonAddPayment, "Add Payment Method button clicked");
		}
		else if (buttonName == "edit")
		{
			click(driver, iconEditPaymentMethod, "Edit icon payment method on subscription page");
		}
		else {
			ExtentManager.logFailureDetails("Button name could be add or edit only. please check");
			Assert.fail();
		}
	}
	
	// ___________click continue button_______________
	public void clickContinueButtonSubscriptionPage()
	{
		scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button on manage subscription page");
	}
}
