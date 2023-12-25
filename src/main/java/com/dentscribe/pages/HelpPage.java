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
	public static By question1By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question1 + "']");
	public static By question2By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question2 + "']");
	public static By question3By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question3 + "']");
	public static By question4By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question4 + "']");
	
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
	public void verifyFAQsQuestions()
	{
		scrollByElement(question1By);
		IsElementPresent(driver, question1By, CommonVariables.question1);
		scrollByElement(question2By);
		IsElementPresent(driver, question2By, CommonVariables.question2);
		scrollByElement(question3By);
		IsElementPresent(driver, question3By, CommonVariables.question3);
		scrollByElement(question4By);
		IsElementPresent(driver, question4By, CommonVariables.question4);
	}

}
