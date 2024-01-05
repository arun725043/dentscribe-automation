package com.dentscribe.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class PlanPage extends AndroidActions {
	
	AndroidDriver driver;
	
	public PlanPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// Plan page locators
	public By headerTextPlanPageBy = By.xpath("//android.view.ViewGroup[@resource-id='header-view']//android.view.ViewGroup[@index=1]//android.widget.TextView[@text='Plan']");
	public By iconBackPlanPageBy = By.xpath("//android.view.ViewGroup[@resource-id='header-view']//android.view.ViewGroup[@index=0]//android.widget.android.widget.ImageView");
	public By iconNewPlanBy = By.xpath("//android.view.ViewGroup[@resource-id='header-view']//android.view.ViewGroup[@index=2]//android.view.ViewGroup[@resource-id='new-plan-icon']");
	public By iconResetPlanBy = By.xpath("//android.view.ViewGroup[@resource-id='header-view']//android.view.ViewGroup[@index=2]//android.view.ViewGroup[@resource-id='reset-plan-icon']");
	public By inputSearchBoxBy = By.xpath("//android.view.ViewGroup[@resource-id='header-view']//following-sibling::android.view.ViewGroup[@index=1]//android.view.ViewGroup//android.widget.EditText[@resource-id='search-input']");
	
	public By textMasterPLan = By.xpath("//android.widget.TextView[@resource-id='Master Plan']");
	public By textCustomPLan = By.xpath("//android.widget.TextView[@resource-id='Custom Plan']");
	
	// New Plan page locators
	public By headerTextNewPlanPageBy = By.xpath("(//android.widget.TextView[@text='Add New Plan'])");
	public By iconBackNewPlanPageBy = By.xpath("//android.widget.TextView[@text='Add New Plan']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By buttonSubmitNewPlanPageBy = By.xpath("//android.view.ViewGroup[@resource-id='plan-submit-button']//android.widget.TextView[@text='Submit']");
	
	public By labelProcedureCodeBy = By.xpath("//android.widget.TextView[@text='Procedure Code*']");
	public By inputProcedureCodeBy = By.xpath("//android.view.ViewGroup[@resource-id='procedure-code-input']//android.widget.EditText");
	public By labelProcedureKeyBy = By.xpath("//android.widget.TextView[@text='Procedure Key*']");
	public By inputProcedureKeyBy = By.xpath("//android.view.ViewGroup[@resource-id='procedure-key-input']//android.widget.EditText");
	public By labelDescriptionBy = By.xpath("//android.widget.TextView[@text='Description*']");
	public By inputDescriptionBy = By.xpath("//android.view.ViewGroup[@resource-id='description-input']//android.widget.EditText");
	public By labelProcedurePlanBy = By.xpath("//android.widget.TextView[@text='Procedure Code*']");
	public By inputProcedurePlanBy = By.xpath("//android.view.ViewGroup[@resource-id='plan-input']//android.widget.EditText");
	public By successMessageBy = By.xpath("//android.widget.TextView[@text='Plan has been successfully created']");
	
	// Verify whether Plan page exists or not
    public void validatePlanPage()
    {
    	if(IsElementPresent(driver, headerTextPlanPageBy, "Plan page header") && IsElementPresent(driver, iconNewPlanBy, "New Plan icon"))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Plan page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either Plan page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
    // Verify whether New Plan page exists or not
    public void validateNewPlanPage()
    {
    	if(IsElementPresent(driver, headerTextNewPlanPageBy, "New Plan page header"))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on New Plan page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either New Plan page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
	// to click on back icon 
	public void clickBackIconPlanPage() {
		click(driver, iconBackPlanPageBy, "Back icon on Plan page");
	}
	
	// to click on New Plan icon 
	public void clickNewPlanIcon() {
		click(driver, iconNewPlanBy, "New Plan icon on Plan page");
	}
	
	// to click on back icon 
	public void clickBackIconNewPlanPage() {
		click(driver, iconBackNewPlanPageBy, "Back icon on new Plan page");
	}
	
	// to click on submit button 
	public void clickSubmitButtonNewPlanPage() {
		scrollToText("Submit");
		click(driver, buttonSubmitNewPlanPageBy, "Submit button on new Plan page");
	}
	
	// Fill Plan form
	public void fillPlanFormAndSubmit(String procedureCode, String procedureKey, String description, String plan) throws InterruptedException
	{
		sendKeys(driver, inputProcedureCodeBy, "Procedure Code textbox", procedureCode);
		sendKeys(driver, inputProcedureKeyBy, "Procedure Key textbox", procedureCode);
		sendKeys(driver, inputDescriptionBy, "Description field textbox", description);
		sendKeys(driver, inputProcedurePlanBy, "Procedure Plan textbox", procedureCode);
		Thread.sleep(10000);
		clickSubmitButtonNewPlanPage();
//		explicitWait(driver, successMessageBy, 30);
//		verifyText(getText(driver, successMessageBy), "Plan has been successfully created", "success message");
	}
	
	// verify submitted Plan
	public void verifyNewlyAddedPlan(String procedureCode, String procedureKey, String description)
	{
		String titleTextString = "(//android.widget.TextView[@text='" + procedureCode +"'])";
		String descriptionTextString = "(//android.widget.TextView[@text='" + description +"'])";
		if (IsElementPresent(driver, By.xpath(titleTextString), "Title") && IsElementPresent(driver, By.xpath(descriptionTextString), "Description"))
		{
			ExtentManager.logInfoDetails("Both title and description found successfully in Plan list i.e. Plan submitted successfully as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either title or description text not found in Plan list. please check");
			Assert.fail();
		}
	}
	
}
