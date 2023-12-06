package com.dentscribe.common;

import org.openqa.selenium.By;

public class CommonLocators {
	
	public static String textUsername = "//android.widget.TextView[@text='Username']";
	public static String textPassword = "//android.widget.TextView[@text='Password']";
	public static String fixPath = "//parent::android.view.ViewGroup//following-sibling::android.widget.EditText";
	
	public static By labelUsername = By.xpath(textUsername);
	public static By labelPassword = By.xpath(textPassword);
	
	public static By textWelcomeUser = By.xpath("//*[contains(@text,'Welcome,')]");
	public static By inputTxtUsername = By.xpath(textUsername + fixPath);
	public static By inputTxtPassword = By.xpath(textPassword + fixPath);
	public static By txtConfirmPassword = By.xpath("//android.widget.TextView[@text='Confirm Password']" + fixPath);
	public static By msgUsernameIsRequired = By.xpath("//android.widget.TextView[@text='Username is required.']");
	public static By msgPasswordIsRequired = By.xpath("//android.widget.TextView[@text='Password is required.']");
	public static By txtValidEmail = By.xpath("//android.widget.EditText[@text='Please enter valid email.']");
	public static By continueButton = By.xpath("//android.widget.TextView[@text='Continue']");
	public static By textWelcomeDentScribe = By.xpath("//android.widget.TextView[@text='Welcome to Dentscribe!']");
	public static By labelPMS = By.xpath("//android.widget.TextView[@text='Select Your Practice Management Software']");
	public static By linkForgotPassword = By.xpath("//android.widget.TextView[@text='Forgot Password']");
	public static By loginNote = By.xpath("//android.widget.TextView[@text='" + CommonVariables.loginNoteText + "']");
	public static By signupNote = By.xpath("//android.widget.TextView[@text='" + CommonVariables.signupNoteText + "']");
	
	// Error locators
	public static By errorMessageWithoutPractice = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextWithoutFillPracticeForm + "']");
	public static By errorMessageForThreeFailAttempts = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextForThreeInvalidAttempts + "']");
	public static By errorMessageLockedUser = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextLockedUser + "']");
	public static By invalidCredentialsError = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextInvalidCredentails + "']");
	public static By errorMessageNoRecordFound = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextNoRecordFound + "']");
	public static By errorMessageInvalidCountryCode = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextInvalidCountryCode + "']");
	public static By errorMessageCurrentPasswordNotMatched = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextCurrentPassword + "']");
	public static By errorMessageCardDetailsNotAdded = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextCardDetailsNotAdded + "']");
	public static By errorMessageInvalidCardExpiry = By.xpath("//android.widget.TextView[@text='" + CommonVariables.errorMsgTextInvalidCardExpiry + "']");
	
	// Success messages locators
	public static By successMessageCancelSubscription = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextCancelSubscription + "']");
	public static By successMessageBuyPaidPlan = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextBuyPaidPlan + "']");
	public static By successMessageSoapReportSubmit = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextSubmitSoapReport + "']");
	public static By successMessagePracticeInfo = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextPracticeInfo + "']");
	public static By successMessagePushNotifications = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextPushNotification + "']");
	public static By successMessageSmsNotifications = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextSMSNotification + "']");
	public static By successMessageEmailNotifications = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextEmailNotification + "']");
	public static By successMessageAccountInfo = By.xpath("//android.widget.TextView[@text='" + CommonVariables.successMsgTextAccountInfoUpdate + "']");
	
	// Page locators to verify its exists or not
	public static By pageCalendarScheduleViewText = By.xpath("//android.widget.TextView[@text='Calendar Schedule View']");
	public static By pageEulaAgreementHeader = By.xpath("//android.widget.TextView[@text='End-User License Agreement (EULA)']");
	public static By pagePracticeInfoHeader = By.xpath("//android.widget.TextView[@text='Practice Info ']");
	
	//SOAP Report page locators
	public static By soapReportHeader = By.xpath("//android.widget.TextView[@text='SOAP Report']");
	public static By soapReportSubjectiveHeader = By.xpath("//android.widget.TextView[@text='Subjective']");
	
	//validation messages
	


}
