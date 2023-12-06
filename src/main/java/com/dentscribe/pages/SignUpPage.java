package com.dentscribe.pages;

import static org.testng.Assert.assertTrue;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class SignUpPage extends AndroidActions {
	AndroidDriver driver;
	public boolean flag;
	
	public SignUpPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public By signupButton = By.xpath("//android.widget.TextView[@text='Sign Up']");
	public By textPhone = By.xpath("//android.widget.TextView[@text='Phone Number']");
	public By inputFirstName = By.xpath("//android.widget.TextView[@text='First Name']" + CommonLocators.fixPath);
	public By inputTitle = By.xpath("//android.widget.TextView[@text='Title']" + CommonLocators.fixPath);
	public By inputLastName = By.xpath("//android.widget.TextView[@text='Last Name']" + CommonLocators.fixPath);
	public By inputEmail = By.xpath("//android.widget.TextView[@text='Email']" + CommonLocators.fixPath);
	public By inputCountryCode = By.xpath("//android.widget.TextView[@text='Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	public By dentistDropdown = By.xpath("//android.widget.TextView[@text='Your Role']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView");
	public By valueDentistDropdown = By.xpath("//android.widget.TextView[@text='Your Role']//parent::android.view.ViewGroup//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView");
	public By inputLicenseNumber = By.xpath("//android.widget.TextView[@text='License Number']" + CommonLocators.fixPath);
	public By inputPhoneNumber = By.xpath("//android.widget.TextView[@text='Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.EditText");
	public By textPasswordIsRequired = By.xpath("//android.widget.TextView[@text='Password is required.']");
	public By textCpasswordIsRequired = By.xpath("//android.widget.TextView[@text='Confirm password is required.']");
	public By textAskForContinue = By.xpath("//android.widget.TextView[@text='Do you wish to continue?']");
	public By buttonBack = By.xpath("//android.widget.TextView[@text='Back']");
	public By buttonContinue = By.xpath("//android.widget.TextView[@text='Continue']");
	public By textSmsVerification = By.xpath("//android.widget.TextView[@text='SMS Verification']");
	public By inputTelephone = By.xpath("android.widget.EditText[@content-desc='Telephone input']");
	public By textIndia = By.xpath("//android.widget.TextView[contains(@text,'India')]");
	public By pmsDrpdwn = By.xpath("//android.widget.TextView[@text='Select Your Practice Management Software']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.TextView");
	public By txtSorry = By.xpath("//android.widget.TextView[contains(@text,'Sorry!')]");
	public By buttonOkay = By.xpath("//android.widget.TextView[@text='Okay']");
	public By textEmailAlreadyExist = By.xpath("//android.widget.TextView[@text='User with email id already exists.']");
	public By textAccountSuccessfullyCreated = By.xpath("//android.widget.TextView[@text='Your account has been successfully verified!']");
	public By labelPMSDropdown = By.xpath("//android.widget.TextView[@text='Select Your Practice Management Software']");
	public By ErrorMsgInvalidEmail = By.xpath("//android.view.ViewGroup[@resource-id='email-input']//android.view.ViewGroup[@index=2]//android.widget.TextView[@index=0]");
	public By ErrorMsgInvalidPassword = By.xpath("//android.view.ViewGroup[@resource-id='password-input']//android.view.ViewGroup[@index=3]//android.widget.TextView[@index=0]");
	public By ErrorMsgInvalidConfirmPassword = By.xpath("//android.view.ViewGroup[@resource-id='confirmpassword-input']//android.view.ViewGroup[@index=3]//android.widget.TextView[@index=0]");
	
	// validation messages
	public By validationMsgFirstName = By.xpath("//android.widget.TextView[@text='First name is required.']");
	public By validationMsgPhoneNumber = By.xpath("//android.widget.TextView[@text='Phone number is required.']");
	public By validationMsgEmail = By.xpath("//android.widget.TextView[@text='Username is required.']");
	public By validationMsgLicenseNumber = By.xpath("//android.widget.TextView[@text='License number  is required.']");
	public By validationMsgPassword = By.xpath("//android.widget.TextView[@text='Password is required.']");
	public By validationMsgConfirmPassword = By.xpath("//android.widget.TextView[@text='Confirm password is required.']");
	

	// _________verify signup note to validate whether user is on signup page or not_______
	public boolean verifySignupLandingPage()
	{
		click(driver, signupButton, "Signup tab");
		if (IsElementPresent(driver, CommonLocators.signupNote, "text - " + CommonVariables.signupNoteText)) {
			ExtentManager.logInfoDetails("User is now on <b> Signup page <b> as expected");
			return true;
		} else {
			ExtentManager.logFailureDetails("Either expected Signup page verified element not found or not exists. please check");
			return false;
		}
	}
	
	// __________verifying validation message for mandatory fields________________
	public void validateMandatoryFieldsErrorMessages() throws InterruptedException
	{
		scrollUntilElementIsVisible("First Name");
		IsElementPresent(driver, validationMsgFirstName, getText(validationMsgFirstName));
		IsElementPresent(driver, validationMsgPhoneNumber, getText(validationMsgPhoneNumber));
		scrollUntilElementIsVisible("Title");
		IsElementPresent(driver, validationMsgEmail, getText(validationMsgEmail));
		scrollUntilElementIsVisible("License Number");
		IsElementPresent(driver, validationMsgLicenseNumber, getText(validationMsgLicenseNumber));
		scrollUntilElementIsVisible("Password");
		IsElementPresent(driver, validationMsgPassword, getText(validationMsgPassword));
		scrollUntilElementIsVisible("Continue");
		IsElementPresent(driver, validationMsgConfirmPassword, getText(validationMsgConfirmPassword));
	}
		
	// __________________fill signup form_____________________
	public void fillSignupForm(String[] userDetail, String existingEmail) throws InterruptedException 
	{
		// First name and Last name
		sendKeys(driver, inputFirstName, "First Name", userDetail[0]);
		sendKeys(driver, inputLastName, "Last Name", userDetail[1]);
		// Phone code, country, number
		click(driver, inputCountryCode, "Phone number code");
		scrollToPartialText(userDetail[2]);
		click(driver, By.xpath("//android.widget.TextView[contains(@text,'" + userDetail[2] + "')]"), userDetail[2] + " code and selected");
		Thread.sleep(2000);
		sendKeys(driver, inputPhoneNumber, "Phone number", userDetail[4]);
		// Email
		scrollUntilElementIsVisible("Title");
		if(existingEmail == "")
		{
			CommonVariables.email = userDetail[5];
		}
		else {
			CommonVariables.email = existingEmail;
		}
		sendKeys(driver, inputEmail, "Email", CommonVariables.email);
		//Title
		sendKeys(driver, inputTitle, "Title", userDetail[6]);
		//License number
		scrollUntilElementIsVisible("License Number");
		sendKeys(driver, inputLicenseNumber, "License number", userDetail[7]);
		//Password, confirm password
		scrollUntilElementIsVisible("Password");
		CommonVariables.actualPass = userDetail[8];
		sendKeys(driver, CommonLocators.inputTxtPassword, "Password", CommonVariables.actualPass);
		scrollUntilElementIsVisible("Confirm Password");
		sendKeys(driver, CommonLocators.txtConfirmPassword, "Confirm password", CommonVariables.actualPass);
		Thread.sleep(3000);
		// PMS, Continue button
		scrollToText("Continue");
		click(driver, pmsDrpdwn, "PMS dropdown");
		scrollableClick(userDetail[9]);
	}

	// _________getting random FName, LName, LicenseNo, Password, CountryCode, CountryName, MobileNumber, PMS_____________
	public String[] getSignupDetail() {
		String fname = genrateRandomFirstName();
		String lname = genrateRandomLastName();
		String countryCode = readData("testData", "countryCode");
		String countryName = readData("testData", "countryName");
		String mobileNumber = readData("testData", "mobile");
		String emailId = CommonVariables.generateEmailId;
		String title = readData("testData", "title");
		String licenseNo = String.valueOf(GenerateRandomNumber(6));
		String pass = "Pass@" + GenerateRandomNumber(4);
		String pms = readData("testData", "pmsName");
		return new String[] { fname, lname, countryCode, countryName, mobileNumber, emailId, title, licenseNo, pass,  pms };
	}

	// __________________fill signup form_____________________
	public void fillSignupForm(String firstName, String lastName, String countryCode, String phoneNumber, String email, String title, String role, String licenseState, String licenseNumber, String password, String confirmPassword, String pms) throws InterruptedException 
	{
		try {
			if (firstName != "")
				sendKeys(driver, inputFirstName, "First Name", firstName);
			sendKeys(driver, inputLastName, "Last Name", lastName);
			
			// Phone code, country, number
			if(countryCode != "" && phoneNumber != "") {
				click(driver, inputCountryCode, "Phone number code");
				scrollToPartialText(countryCode);
				click(driver, By.xpath("//android.widget.TextView[contains(@text,'" + countryCode + "')]"), countryCode + " code and selected");
				Thread.sleep(2000);
				sendKeys(driver, inputPhoneNumber, "Phone number", phoneNumber);
			}
			// Email
			if(email != "")
				scrollUntilElementIsVisible("Title");
				sendKeys(driver, inputEmail, "Email", email);
			//Title
			sendKeys(driver, inputTitle, "Title", title);
			//License number
			if(licenseNumber != "")
				scrollUntilElementIsVisible("License Number");
				sendKeys(driver, inputLicenseNumber, "License number", licenseNumber);
			
			//Password, confirm password
			if (password != "" && confirmPassword != "")
			{
				scrollUntilElementIsVisible("Password");
				sendKeys(driver, CommonLocators.inputTxtPassword, "Password", password);
				scrollUntilElementIsVisible("Confirm Password");
				sendKeys(driver, CommonLocators.txtConfirmPassword, "Confirm password", confirmPassword);
			}
			Thread.sleep(3000);
			// PMS, Continue button
			scrollToText("Continue");
			click(driver, pmsDrpdwn, "PMS dropdown");
			scrollableClick(pms);
			click(driver, CommonLocators.continueButton, "Continue button");
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Mandatory fields cannot be empty. please check any field value is missing");
			Assert.fail();
		}
	}
	
	// ________________verify sorry popup for dentrix__________
	public boolean verifySorryPopup() {
		return IsElementPresent(driver, txtSorry, "Sorry popup");
	}

	// ____________verify all mandatory fields________
	public boolean verifySignupMandatoryFields() {
		if (IsElementPresent(driver, textPasswordIsRequired, "Password") && IsElementPresent(driver, textCpasswordIsRequired, "Confirm Password")) {
			return true;
		} else {
			return false;
		}
	}

	// _____________verify confirmation popup button____________
	public boolean verifyConfirmationPopupButton() {
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(buttonBack));
		if (IsElementPresent(driver, buttonBack, "Back button") && IsElementPresent(driver, buttonContinue, "Continue button")) {
			ExtentManager.logInfoDetails("Expected <b>Back<b> and <b> Continue<b> appearing on Confirmation popup");
			return true;
		} else {
			ExtentManager.logInfoDetails("Expected <b>Back<b> and <b> Continue<b> buttons but not found on Confirmation popup. please check");
			return false;
		}
	}

	// ___________verify signup confirmation popup______
	public boolean clickSignupConfirmationPopupButtons(String buttonName) throws InterruptedException {
		switch (buttonName) {
		case "back":
			click(driver, buttonBack, "Back button on Confimation popup");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(labelPMSDropdown));
			flag = IsElementPresent(driver, labelPMSDropdown, "PMS Field");
			ExtentManager.logInfoDetails("User come back to <b>Signup<b> page as expected");
			break;
		case "continue":
			click(driver, buttonContinue, "Continue button on Confimation popup");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));
			flag = IsElementPresent(driver, textSmsVerification, "SMS Verification screen");
			ExtentManager.logInfoDetails("User is now on <b>SMS Verification<b> page as expected");
			break;
		default:
			break;
		}
		return flag;
	}
	
	public void clickVerifySignUpContinueButton(String expectedResult) throws InterruptedException 
	{
//		scrollToText("Continue");
		click(driver, CommonLocators.continueButton, "Continue button");
		if (expectedResult == "confirmation popup")
		{
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textAskForContinue));
			ExtentManager.logInfoDetails("Expected confirmation popup opened");
		}
		else if (expectedResult == "signup page")
		{
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(labelPMSDropdown));
			flag = IsElementPresent(driver, labelPMSDropdown, "PMS Field");
			ExtentManager.logInfoDetails("User come back to <b>Signup<b> page as expected");
		}
		else if (expectedResult == "duplicate email")
		{
			scrollUntilElementIsVisible("User with email id already exists.");
			assertTrue(IsElementPresent(driver, textEmailAlreadyExist, "Duplicate email validation message"));
			ExtentManager.logInfoDetails("Error message found - <b>" + getText(textEmailAlreadyExist) + "<b>");
		}
		else {
			ExtentManager.logFailureDetails("Expected result on continue button click could be either 'Confirmation popup' or 'error message'");
			Assert.fail();
		}
	}
	
	// _________verify wrong email id format_______
	public void validateWrongEmailId(String email) throws InterruptedException
	{
		scrollUntilElementIsVisible("Title");
		sendKeys(driver, inputEmail, "Email", email);
		if (getText(ErrorMsgInvalidEmail).equals(CommonVariables.errorMsgTextInvalidEmail))
		{
			ExtentManager.logInfoDetails("Expected validation message found : <b>" + CommonVariables.errorMsgTextInvalidEmail);
		}
		else {
			ExtentManager.logFailureDetails("Expected validation message is : <b>" + CommonVariables.errorMsgTextInvalidEmail + " but actual message found : <b>" + getText(ErrorMsgInvalidEmail));
			Assert.fail();
		}
	}
	
	// _________verify validation message appeared or not when password is weak_________
	public void validatePasswordStrength(String password) throws InterruptedException
	{
		scrollUntilElementIsVisible("Password");
		sendKeys(driver, CommonLocators.inputTxtPassword, "Password", password);
		if (getText(ErrorMsgInvalidPassword).equals(CommonVariables.errorMsgTextInvalidPassword))
		{
			ExtentManager.logInfoDetails("Expected validation message found : <b>" + CommonVariables.errorMsgTextInvalidPassword);
			clear(CommonLocators.inputTxtPassword);
		}
		else {
			ExtentManager.logFailureDetails("Expected validation message is : <b>" + CommonVariables.errorMsgTextInvalidPassword + " but actual message found : <b>" + getText(ErrorMsgInvalidPassword));
			Assert.fail();
		}
	}
	
	// _________verify validation message appeared or not when password and confirm password not macthed_________
	public void validateConfirmPassword(String password) throws InterruptedException
	{
		scrollUntilElementIsVisible("Continue");
		sendKeys(driver, CommonLocators.txtConfirmPassword, "Confirm Password", password);
		if (getText(ErrorMsgInvalidConfirmPassword).equals(CommonVariables.errorMsgTextWrongConfirmPassword))
		{
			ExtentManager.logInfoDetails("<b>Password and Confirm password not matched validation appeared as expected.");
			clear(CommonLocators.txtConfirmPassword);
		}
		else {
			ExtentManager.logFailureDetails("<b>Confirm password validation not found or not as expected. please check");
			Assert.fail();
		}
	}
}
