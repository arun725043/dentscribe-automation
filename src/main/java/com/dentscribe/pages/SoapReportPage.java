package com.dentscribe.pages;

import org.testng.Assert;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class SoapReportPage extends AndroidActions {

	AndroidDriver driver;

	public SoapReportPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// _________SOAP Report page common variables______
	public String nameValue;
	public String titleValue;
	public String licenseValue;

	// _________locators______
	public By iconBackSoapReport = By.className("android.widget.ImageView");
	public By iconEditSoapReport = By.className("android.widget.ImageView");
	public By iconSaveSoapReport = By.className("android.widget.ImageView");

	// Soap report locators
	public By buttonAdoptSignature = By.xpath("//android.widget.TextView[@text='Adopt Signature']");
	public By thirdSignature = By.xpath("//android.view.ViewGroup[@index=4]//android.widget.RadioButton[@index=0]//android.view.ViewGroup[@index=0]");
	public By buttonDraw = By.xpath("//android.widget.TextView[contains(@text,'Draw')]");
	public By buttonSubmitSignaturePopup = By.xpath("//android.widget.TextView[@text='Submit']");
	public By textCreateYourSignature = By.xpath("//android.widget.TextView[@text='Create Your Signature']");

	public By textSoapReport = By.xpath("//android.widget.TextView[@text='SOAP Report']");
	public By signatureArea = AppiumBy.className("android.widget.Image");
	public By buttonRedraw = By.xpath("//android.widget.TextView[contains(@text,'Redraw')]");

	public By labelSignature = By.xpath("//android.view.ViewGroup[@resource-id='singnature-view']//android.widget.TextView[@text='Signature']");
	public By buttonSubmitReport = By.xpath("//android.view.ViewGroup[@resource-id='report-submit-button']//android.widget.TextView[@text='Submit']");
	public By name = By.xpath("//android.widget.TextView[@text='Name']//preceding-sibling::android.widget.EditText");
	public By title = By.xpath("//android.widget.TextView[@text='Title']//preceding-sibling::android.widget.EditText");
	public By license = By.xpath("//android.widget.TextView[@text='License']//preceding-sibling::android.widget.EditText");
	public By headerSubjective = By.xpath("//android.widget.TextView[@text='Subjective']");

	// _________verify soap report page exists or not_______
	public void validateSoapReportPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.soapReportHeader));
		if(IsElementPresent(driver, CommonLocators.soapReportHeader, "SOAP Report header"))
		{
			ExtentManager.logInfoDetails("User is now on <b>SOAP Report<b> page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either expected SOAP report not opened or not exists or not found. Please check");
			Assert.fail();
		}
	}

	// ___________click adopt signature___________
	public void clickAdoptSignature()
	{
		try {
			Thread.sleep(10000);
			click(driver, headerSubjective, "header subjective to scroll down");
			scrollUntilElementIsVisible("Adopt Signature");
			scrollableClick("Adopt Signature");
			IsElementPresent(driver, textCreateYourSignature, "Create Signature popup");
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Either expected 'Adopt Signature' button not found or UI enabled to scrolled to this button. Please check");
			Assert.fail();
		}
	}

	public void clickVerifyEditSaveIconSoapReport(String iconName)
	{
		switch (iconName) {
		case "edit":
			click(driver, iconEditSoapReport, "Edit icon on Soap report page");
			IsElementPresent(driver, iconSaveSoapReport, "Save icon on Soap report page");
			ExtentManager.logInfoDetails("Save icon visible on Edit icon click as expected");
			break;
		case "save":
			click(driver, iconSaveSoapReport, "Save icon on Soap report page");
			IsElementPresent(driver, iconEditSoapReport, "Edit icon on Soap report page");
			ExtentManager.logInfoDetails("Edit icon visible on Save icon click as expected");
			break;

		default:
			ExtentManager.logFailureDetails("User provided icon name - <b>" + iconName + "<b> but icon name could be only edit or save. please check");
			Assert.fail();
		}
	}
	
	// ___________Draw signature___________
	public void addSignature()
	{
		try {
			Thread.sleep(3000);
			click(driver, thirdSignature, "3rd Signature in list");
			click(driver, buttonSubmitSignaturePopup, "Submit button signature popup");
			scrollUntilElementIsVisible("Signature");
			explicitWait(driver, labelSignature, 10);
			ExtentManager.logInfoDetails("Signature added successfully and appearing on SOAP Report");
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Either expected 'Signature Popup' not found/opened or Signatures not added to SOAP Report. Please check");
			Assert.fail();
		}
	}
	
	// _______________click back icon_______________
	public void clickBackIconSoapReport()
	{
		click(driver, iconBackSoapReport, "Back icon on Soap report");
	}
	
	// ___________submit SOAP Report___________
	public void submitSoapReport()
	{
		try {
			scrollUntilElementIsVisible("Submit");
			click(driver, buttonSubmitReport, "Submit button soap report");
			getText(CommonLocators.successMessageSoapReportSubmit);
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Either expected 'Submit' button not found/available or its disabled. Please check");
			Assert.fail();
		}
	}

	public void updateUserDetails() {
		clear(name);
		nameValue = CommonMethods.genrateRandomFirstName();
		sendKeys(driver, name, "Name field", nameValue);

		clear(title);
		sendKeys(driver, title, "Title field", "BDS");

		clear(license);
		licenseValue = CommonMethods.GenerateRandomNumber(5);
		sendKeys(driver, license, "License field", licenseValue);
	}

	public void verifyUpdatedUserDetails() {
		scrollToText("Submit");
		if (nameValue.equals(getAttribute(name)) && licenseValue.equals(getAttribute(license))
				&& titleValue.equals(getAttribute(title))) {
			ExtentManager.logInfoDetails("All values updated successfully as expected");
		} else {
			ExtentManager.logFailureDetails("Either name, title, license number not updated or all not updated. please check");
			Assert.fail();
		}
	}
}
