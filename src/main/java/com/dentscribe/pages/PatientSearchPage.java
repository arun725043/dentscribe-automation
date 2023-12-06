package com.dentscribe.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class PatientSearchPage extends AndroidActions {

	AndroidDriver driver;

	public PatientSearchPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// ________locators___________________
	public By inputSearchTextbox = By.xpath("//android.widget.EditText[@text='Search by name, phone number, insurance, or DOB']");
	public By inputSearch = AppiumBy.className("android.widget.EditText");
	public String fetchingPatients = "//*[contains(@text,'Fetching Patient')]";
	public By listOfPatient = By.xpath("//android.widget.TextView[@index=0]");
	public By iconSetting = By.xpath("(//android.widget.TextView[contains(@text,'Welcome,')]//following-sibling::android.view.ViewGroup/android.view.ViewGroup)[2]/android.widget.ImageView");
	public By searchNote = By.xpath("//android.widget.TextView[@text='Format:- Date (yyyy-mm-dd), Ph No. (1234567890)']");
	public By noRecordMessage = By.xpath("//android.widget.TextView[@text='No Matches found']");
	public By iconBackPatientSearch = By.xpath("//*[contains(@text,'Welcome,')]//parent::android.view.ViewGroup//preceding-sibling::android.view.ViewGroup/android.view.ViewGroup//android.widget.ImageView");
	

	// _________verify calendar page exists or not_______
	public boolean validatePatientSearchPage()
	{
		fetchingPatientLoader();
		if (IsElementPresent(driver, inputSearchTextbox, "Search patient textbox")) {
			ExtentManager.logInfoDetails("User is now on <b> Patient Search page <b> as expected");
			return true;
		} else {
			ExtentManager.logFailureDetails("Either expected search patient textbox not found or not exists. please check");
			return false;
		}
	}
	
	// ___________fetch patient loader_______
	public void fetchingPatientLoader() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fetchingPatients)));
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(fetchingPatients)));
		} catch (Exception e) {
			System.out.println("Loaded");
		}
	}
	
	// ______________verify user is able to search patient or not______________

	// ________searching the patient___________________
	public void enterSearchValue(String value) throws InterruptedException {
		clear(inputSearch);
		sendKeys(driver, inputSearch, "Search textbox", value);
		Thread.sleep(10000);
	}

	// ______________verify patient is searched or not_________
	public boolean verifySearchedPatient(String... info) {
		return IsElementPresent(driver, By.xpath("//android.widget.TextView[@text='" + info[0] + "']"), "Searched patient");
	}

	public String[] changeDateFormat(String date) {
		String[] fullDate = date.split("-");
		String y = fullDate[0];
		String d = fullDate[1];
		String m = fullDate[2];

		return new String[] { y, d, m };

	}

	public void searchPatient(String searchBy, String searchValue) throws InterruptedException
	{
		// Search By patientName and verify
		if (searchBy == "name")
		{
			ExtentManager.logInfoDetails("Searching Patient by its name : <b> " + searchValue + "</b>");
			enterSearchValue(searchValue);
	
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + searchValue + "']")));
			assertTrue(verifySearchedPatient(searchValue));
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
		}
		else if (searchBy == "phone")
		{
			// Search By patientMobile and verify
			ExtentManager.logInfoDetails("Searching Patient by its phone number : <b> " + searchValue + "</b>");
			enterSearchValue(searchValue);
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'" + searchValue + "')]")));
	
			String num = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + searchValue + "')]")).get(1).getText();
			assertEquals(num.trim(), searchValue);
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
		}
		else if (searchBy == "dob")
		{
			// Search By dob and verify
			ExtentManager.logInfoDetails("Searching Patient by its DOB : <b> " + searchValue + "</b>");
			enterSearchValue(searchValue);
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + searchValue + "']")));
	
			assertTrue(verifySearchedPatient(searchValue));
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
		}
		else if (searchBy == "insurance")
		{
			// Search By insurance and verify
			ExtentManager.logInfoDetails("Searching Patient by its insurance : <b> " + searchValue + "</b>");
			enterSearchValue(searchValue);
			
			By insuranceValuePath = By.xpath("//android.view.ViewGroup[@index=1]//android.view.ViewGroup[@resource-id='patient-info-card-view'][@index=0]//android.view.ViewGroup[@index=1]//android.view.ViewGroup[@index=1]//android.widget.TextView[@text=' Insurance: ']//following-sibling::android.widget.TextView");
			verifyTexts(getText(insuranceValuePath), searchValue);
		}
		else {
			enterSearchValue(searchValue);
			IsElementPresent(driver, searchNote, "Search not");
			ExtentManager.logInfoDetails("Note message found - <b>" + getText(searchNote) + "<b>");
		}
	}
	
	public void clickPatientDetailsToOpenProfilePage(String patientName) throws InterruptedException
	{		
		List<WebElement> elements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + patientName + "')]"));
//		ExtentManager.logInfoDetails(String.valueOf(elements.size()));
		elements.get(0).click();
		ExtentManager.logInfoDetails("Clicked patient name to open patient profile page - <b>" + patientName + "<b>");
		Thread.sleep(10000);
	}

}
