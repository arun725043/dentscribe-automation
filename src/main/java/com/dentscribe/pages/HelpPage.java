package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class HelpPage extends AndroidActions {

	AndroidDriver driver;

	public HelpPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	// Help page locators
	public By headerHelpPage = By.xpath("//android.view.ViewGroup[@resource-id='help-screen-view']//android.widget.TextView[@text='Help']");
	public By backIconHelpPage = By.xpath("//android.widget.TextView[@text='Help']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By textFaqs = By.xpath("//android.widget.TextView[@text='FAQs']");
	
	// FAQs question locators
	public By question1By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question1 + "']");
	public By question2By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question2 + "']");
	public By question3By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question3 + "']");
	public By question4By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question4 + "']");
	
	public By listQuestions = By.xpath("//android.view.ViewGroup[@resource-id='help-screen-view']//android.widget.ScrollView//android.widget.ImageView//parent::android.view.ViewGroup//following-sibling::android.widget.TextView[@index=1]");
	
	// _________verify help page exists or not_______
	public void validateHelpPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFaqs));
		String headerText = getText(headerHelpPage);
		if(headerText.trim().equalsIgnoreCase("help"))
		{
			ExtentManager.logInfoDetails("<b>User is now on Help page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Expected Help page either not found or header not matched. please check");
			Assert.fail();
		}
	}
	
	// click back icon 
	public void clickBackIconHelpPage()
	{
		click(driver, backIconHelpPage, "Back icon on help page");
	}

	//verify whether all expected questions are available or not
	public void verifyFAQsQuestions() throws InterruptedException
	{
		scrollToText(CommonVariables.question1);
		verifyListOption(driver, listQuestions, CommonVariables.question1);
		scrollToText(CommonVariables.question2);
		verifyListOption(driver, listQuestions, CommonVariables.question2);
		scrollToText(CommonVariables.question3);
		verifyListOption(driver, listQuestions, CommonVariables.question3);
//		scrollToText(CommonVariables.question4);
//		Thread.sleep(5000);
		scrollToPartialText("pause your subscription");
		verifyListOption(driver, listQuestions, CommonVariables.question4);
		ExtentManager.logInfoDetails("All questions are available in Help page as expected");
	}

}
