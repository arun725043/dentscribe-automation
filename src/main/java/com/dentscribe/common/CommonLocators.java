package com.dentscribe.common;

import org.openqa.selenium.By;

public class CommonLocators {

	public static String fixPath = "//parent::android.view.ViewGroup//following-sibling::android.widget.EditText";
	
	public static By textWelcomeUser = By.xpath("//*[contains(@text,'Welcome,')]");
	public static By continueButton = By.xpath("//android.widget.TextView[@text='Continue']");
	public static By textWelcomeDentScribe = By.xpath("//android.widget.TextView[@text='Welcome to Dentscribe!']");
	public static By buttonLogin = By.xpath("//android.widget.TextView[@text='Login']");
	public static By buttonSignup = By.xpath("//android.widget.TextView[@text='Sign Up']");
	public static By loginNote = By.xpath("//android.widget.TextView[@text='" + CommonVariables.loginNoteText + "']");
	
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
	
	//SOAP Report page locators
	public static By soapReportHeader = By.xpath("//android.widget.TextView[@text='SOAP Report']");
	public static By soapReportSubjectiveHeader = By.xpath("//android.widget.TextView[@text='Subjective']");
	
	//validation messages
	


}
