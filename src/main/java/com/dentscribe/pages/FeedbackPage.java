package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class FeedbackPage extends AndroidActions {
	
	AndroidDriver driver;
	
	public FeedbackPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// Feedback page locators
	public By headerTextFeedbackPageBy = By.xpath("//android.widget.TextView[@text='Feedback']");
	public By iconBackFeedbackPageBy = By.xpath("//android.widget.TextView[@text='Feedback']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By iconNewFeedbackBy = By.xpath("(//android.widget.TextView[@text='New Feedback'])");
	public By msgNoFeedbacksBy = By.xpath("//android.widget.TextView[@text='No feedback found.']");
	
	// New feedback page locators
	public By headerTextNewFeedbackPageBy = By.xpath("(//android.widget.TextView[@text='New Feedback'])");
	public By iconBackNewFeedbackPageBy = By.xpath("//android.widget.TextView[@text='New Feedback']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By buttonSubmitNewFeedbackPageBy = By.xpath("//android.widget.TextView[@text='Submit']");
	
	public By labelTitleBy = By.xpath("//android.widget.TextView[@text='Title*']");
	public By inputTitleBy = By.xpath("//android.widget.EditText[@text='Title']");
	public By labelDescriptionBy = By.xpath("//android.widget.TextView[@text='Description*']");
	public By inputDescriptionBy = By.xpath("//android.widget.EditText[@text='Add description here..']");
	public By linkAddAttachmentBy = By.xpath("//android.widget.TextView[@text='Add attachment']");
	public By successMessageBy = By.xpath("//android.widget.TextView[@text='Feedback has been successfully created']");
	
	// Verify whether Feedback page exists or not
    public void validateFeedbackPage()
    {
    	if(IsElementPresent(driver, headerTextFeedbackPageBy, "Feedback page header") && IsElementPresent(driver, iconNewFeedbackBy, "New feedback icon"))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Feedback page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either Feedback page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
    // Verify whether New Feedback page exists or not
    public void validateNewFeedbackPage()
    {
    	if(IsElementPresent(driver, headerTextNewFeedbackPageBy, "New Feedback page header") && IsElementPresent(driver, linkAddAttachmentBy, "Attachment link"))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on New Feedback page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either New Feedback page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
	// to click on back icon 
	public void clickBackIconFeedbackPage() {
		click(driver, iconBackFeedbackPageBy, "Back icon on feedback page");
	}
	
	// to click on New Feedback icon 
	public void clickNewFeedbackIcon() {
		click(driver, iconNewFeedbackBy, "New Feedback icon on feedback page");
	}
	
	// to click on back icon 
	public void clickBackIconNewFeedbackPage() {
		click(driver, iconBackNewFeedbackPageBy, "Back icon on new feedback page");
	}
	
	// to click on submit button 
	public void clickSubmitButtonNewFeedbackPage() {
		click(driver, labelTitleBy, "Title text");
		click(driver, buttonSubmitNewFeedbackPageBy, "Submit button on new feedback page");
	}
	
	// Fill feedback form
	public void fillFeedbackFormAndSubmit(String title, String description) throws InterruptedException
	{
		sendKeys(driver, inputTitleBy, "Title field textbox", title);
		sendKeys(driver, inputDescriptionBy, "Description field textbox", description);
		Thread.sleep(10000);
		clickSubmitButtonNewFeedbackPage();
//		explicitWait(driver, successMessageBy, 30);
//		verifyText(getText(driver, successMessageBy), "Feedback has been successfully created", "success message");
	}
	
	// verify submitted feedback
	public void verifyNewlyAddedFeedback(String title, String description)
	{
		String titleTextString = "(//android.widget.TextView[@text='" + title +"'])";
		String descriptionTextString = "(//android.widget.TextView[@text='" + description +"'])";
		if (IsElementPresent(driver, By.xpath(titleTextString), "Title") && IsElementPresent(driver, By.xpath(descriptionTextString), "Description"))
		{
			ExtentManager.logInfoDetails("Both title and description found successfully in feedback list i.e. Feedback submitted successfully as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either title or description text not found in feedback list. please check");
			Assert.fail();
		}
	}
	
}
