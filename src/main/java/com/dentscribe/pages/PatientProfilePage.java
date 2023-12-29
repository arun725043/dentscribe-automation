package com.dentscribe.pages;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class PatientProfilePage extends AndroidActions {

	AndroidDriver driver;

	public PatientProfilePage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// ________locators___________________
	public By profilePageHeader = By.xpath("//android.widget.TextView[@text='Patient Profile']");
	public By pastReportHeader = By.xpath("//android.widget.TextView[contains(@text,'Past SOAP Report')]");
	public By currentStatus = By.xpath("//android.widget.TextView[contains(@text,'Current')]");
	public By dobValue = By.xpath("//android.widget.TextView[@text='DOB: ']//following-sibling::android.widget.TextView[@index=2]");
	public By lastVisitValue = By.xpath("//android.widget.TextView[@text='Last Visit: ']//following-sibling::android.widget.TextView[@index=2]");
	public By iconBackPatientProfile = By.xpath("//android.widget.TextView[@text='Patient Profile']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	
	// _________verify patient profile exists or not_______
	public void validatePatientProfilePage()
	{
		if (IsElementPresent(driver, profilePageHeader, "Profile page header")) {
			ExtentManager.logInfoDetails("User is now on expected page - <b>" + getText(profilePageHeader) + "<b>");
		} else {
			ExtentManager.logFailureDetails("Either expected page not opened or not found. please check");
			Assert.fail();
		}
	}
	
	// _______________click back icon patient search page______________
	public void clickBackIconPatientProfilePage()
	{
		click(driver, iconBackPatientProfile, "Back button of patient profile page");
	}

	// _________verify patient profile page for patient name, dob, last visit_______
	public void verifyProfilePageDetails(String name, String dob, String lastVisitDate) 
	{
		String nameXPath = "//android.widget.TextView[@text='" + name + "']";
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nameXPath)));
		//read and verify patient name
		String patientName = getText(By.xpath(nameXPath));
		assertEquals(patientName, name);
		
		//read and verify patient dob
		String patientDOB = getText(dobValue);
		assertEquals(patientDOB, dob);
		
		//read and verify patient last visit date
		String patientLastVisitDate = getText(lastVisitValue);
		assertEquals(patientLastVisitDate, lastVisitDate);
		
		//verify whether 'Past SOAP Report' heading
		IsElementPresent(driver, pastReportHeader, "Past SOAP Report list heading");
	}
}
