package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class EulaAgreementPage extends AndroidActions {

	AndroidDriver driver;

	public EulaAgreementPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	} 
	
	// _________locators______
	String textHeader = "End-User License Agreement (EULA)";
	public By headerTextEulaAgreementPage = By.xpath("//android.widget.TextView[@text='" + textHeader + "']");
	public By buttonContinue = By.xpath("//android.widget.TextView[@text='Continue']");
	
	// _______verify whether Eula Agreement page exists or not________
	public boolean validateEulaAgreementPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(headerTextEulaAgreementPage));
		String headerText = getText(headerTextEulaAgreementPage);
		if(headerText.trim().equalsIgnoreCase(textHeader))
		{
			ExtentManager.logInfoDetails("<b>User is now on Practice Info page as expected");
			return true;
		}
		else {
			ExtentManager.logFailureDetails("Either expected Practice Info page verified element not found or page not exists. please check");
			return false;
		}
	}
	
	//click continue button given in forgot password page
	public void clickContinueButtonEulaAgreementPage()
	{
		scrollToText("Continue");
		click(driver, buttonContinue, "Continue button EULA Agreement page");
	}
}
