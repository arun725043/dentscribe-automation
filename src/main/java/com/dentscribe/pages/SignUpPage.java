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

	public By labelPMS = By.xpath("//android.widget.TextView[@text='Select Your Practice Management Software']");
	public By signupNote = By.xpath("//android.widget.TextView[@text='" + CommonVariables.signupNoteText + "']");
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
	public By inputPassword = By.xpath("//android.view.ViewGroup[@resource-id='password-input']//android.widget.EditText[@index=1]");
	public By inputConfirmPassword = By.xpath("//android.view.ViewGroup[@resource-id='confirmpassword-input']//android.widget.EditText[@index=1]");
	
	public By textPasswordIsRequired = By.xpath("//android.widget.TextView[@text='Password is required.']");
	public By textCpasswordIsRequired = By.xpath("//android.widget.TextView[@text='Confirm password is required.']");
	public By textAskForContinue = By.xpath("//android.widget.TextView[@text='Do you wish to continue?']");
	public By buttonBack = By.xpath("//android.widget.TextView[@text='Back']");
	public By buttonContinue = By.xpath("//android.widget.TextView[@text='Continue']");
	public By textSmsVerification = By.xpath("//android.widget.TextView[@text='SMS Verification']");
	public By inputTelephone = By.xpath("android.widget.EditText[@content-desc='Telephone input']");
	public By textIndia = By.xpath("//android.widget.TextView[contains(@text,'India')]");
	public By roleDrpdwn = By.xpath("//android.view.ViewGroup[@resource-id='role-input']//android.view.ViewGroup[@index=2]//android.widget.TextView");
	public By licenseStateDrpdwn = By.xpath("//android.view.ViewGroup[@resource-id='licensestate-input']//android.widget.TextView[@index=2]");
	public By pmsDrpdwn = By.xpath("//android.widget.TextView[@text='Select Your Practice Management Software']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.TextView");
	public By optionsListPMS = By.xpath("//android.view.ViewGroup[@resource-id='pms-dropdown']//android.view.ViewGroup[@resource-id='select-pms']//android.widget.TextView");
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
	

	// _______________verify whether signup page exists or not_______________
	public void validateSignupPage()
	{
		click(driver, CommonLocators.buttonSignup, "Signup tab");
		if (IsElementPresent(driver, signupNote, "text - " + CommonVariables.signupNoteText)) {
			ExtentManager.logInfoDetails("<b>User is now on Signup page as expected");
		} else {
			ExtentManager.logFailureDetails("Either expected Signup page verified element not found or page not exists. please check");
			Assert.fail();
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
		sendKeys(driver, inputPassword, "Password", CommonVariables.actualPass);
		scrollUntilElementIsVisible("Confirm Password");
		sendKeys(driver, inputConfirmPassword, "Confirm password", CommonVariables.actualPass);
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
	public void fillSignupForm(String firstName, String lastName, String countryCode, String phoneNumber, String email, String title, 
			String role, String licenseState, String licenseNumber, String password, String confirmPassword, String pms) throws InterruptedException 
	{
		try {
			if (firstName != "") {
				sendKeys(driver, inputFirstName, "First Name", firstName);
			}
			else {
				ExtentManager.logFailureDetails("First Name is mandatory. please fill");
				Assert.fail();
			}
			if (lastName != "") {
				sendKeys(driver, inputLastName, "Last Name", lastName);
			}
			else {
				ExtentManager.logInfoDetails("Last name value not given by user");
			}
			// Country code
			if(countryCode != "") {
				click(driver, inputCountryCode, "Phone number code");
				scrollToPartialText(countryCode);
				click(driver, By.xpath("//android.widget.TextView[contains(@text,'" + countryCode + "')]"), countryCode + " code and selected");
				Thread.sleep(2000);
			}
			else {
				ExtentManager.logInfoDetails("Contry code not given by user therefore by default its <b>+1");
			}
			// Phone number
			if(phoneNumber != "") {
				sendKeys(driver, inputPhoneNumber, "Phone number", phoneNumber);
			}
			else {
				ExtentManager.logFailureDetails("Phone number is mandatory. please fill");
				Assert.fail();
			}
			// Email
			if(email != "") {
				scrollUntilElementIsVisible("Title");
				sendKeys(driver, inputEmail, "Email", email);
			}
			else {
				ExtentManager.logFailureDetails("Email is mandatory. please fill");
				Assert.fail();
			}
			//Title
			if(title != "") {
				sendKeys(driver, inputTitle, "Title", title);
			}
			else {
				ExtentManager.logInfoDetails("Title value not given by user");
			}
			//Role
			if(role != "") {
				click(driver, roleDrpdwn, "Role dropdown");
				scrollableClick(role);
			}
			else {
				ExtentManager.logInfoDetails("Role value not given by user therefore by default its <b>Dentist");
			}
			//License State
			if(licenseState != "") {
				click(driver, licenseStateDrpdwn, "License State dropdown");
				scrollableClick(licenseState);
			}
			else {
				ExtentManager.logInfoDetails("License State value not given by user therefore by default its <b>California");
			}
			//License number
			if(licenseNumber != "") {
				scrollUntilElementIsVisible("License Number");
				sendKeys(driver, inputLicenseNumber, "License number", licenseNumber);
			}
			else {
				ExtentManager.logFailureDetails("License number is mandatory. please fill");
				Assert.fail();
			}
			//Password
			if (password != "")
			{
				scrollUntilElementIsVisible("Password");
				sendKeys(driver, inputPassword, "Password", password);
			}
			else {
				ExtentManager.logFailureDetails("Password is mandatory. please fill");
				Assert.fail();
			}
			if (confirmPassword != "")
			{
				scrollUntilElementIsVisible("Confirm Password");
				sendKeys(driver, inputConfirmPassword, "Confirm password", confirmPassword);
			}
			else {
				ExtentManager.logFailureDetails("Confirm password is mandatory. please fill");
				Assert.fail();
			}
			Thread.sleep(3000);
			// PMS, Continue button
			if (pms != "")
			{
				scrollToText("Continue");
				click(driver, pmsDrpdwn, "PMS dropdown");
				scrollableClick(pms);
			}
			else {
				ExtentManager.logInfoDetails("PMS value not given by user therefore by default its <b>Eaglesoft");
			}
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
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textAskForContinue));
		if (IsElementPresent(driver, buttonBack, "Back button") && IsElementPresent(driver, buttonContinue, "Continue button")) {
			ExtentManager.logInfoDetails("Expected <b>Back<b> and <b> Continue<b> appearing on Confirmation popup");
			return true;
		} else {
			ExtentManager.logFailureDetails("Expected <b>Back<b> and <b> Continue<b> buttons but not found on Confirmation popup. please check");
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
			ExtentManager.logInfoDetails("<b>User come back to Signup page as expected");
			break;
		case "continue":
			click(driver, buttonContinue, "Continue button on Confimation popup");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));
			flag = IsElementPresent(driver, textSmsVerification, "SMS Verification screen");
			ExtentManager.logInfoDetails("<b>User is now on SMS Verification page as expected");
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
			ExtentManager.logInfoDetails("<b>Expected confirmation popup opened");
		}
		else if (expectedResult == "signup page")
		{
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(labelPMSDropdown));
			flag = IsElementPresent(driver, labelPMSDropdown, "PMS Field");
			ExtentManager.logInfoDetails("<b>User come back to Signup page as expected");
		}
		else if (expectedResult == "duplicate email")
		{
			scrollUntilElementIsVisible("User with email id already exists.");
			assertTrue(IsElementPresent(driver, textEmailAlreadyExist, "Duplicate email validation message"));
			ExtentManager.logInfoDetails("Error message found - <b>" + getText(textEmailAlreadyExist) + "<b>");
		}
		else if (expectedResult == "sorry popup")
		{
			verifySorryPopup();
			click(driver, buttonOkay, "Okay button on Confirmation popup");
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
		sendKeys(driver, inputPassword, "Password", password);
		if (getText(ErrorMsgInvalidPassword).equals(CommonVariables.errorMsgTextInvalidPassword))
		{
			ExtentManager.logInfoDetails("Expected validation message found : <b>" + CommonVariables.errorMsgTextInvalidPassword);
			clear(inputPassword);
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
		sendKeys(driver, inputConfirmPassword, "Confirm Password", password);
		if (getText(ErrorMsgInvalidConfirmPassword).equals(CommonVariables.errorMsgTextWrongConfirmPassword))
		{
			ExtentManager.logInfoDetails("<b>Password and Confirm password not matched validation appeared as expected.");
			clear(inputConfirmPassword);
		}
		else {
			ExtentManager.logFailureDetails("<b>Confirm password validation not found or not as expected. please check");
			Assert.fail();
		}
	}
}
