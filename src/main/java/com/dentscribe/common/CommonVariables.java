package com.dentscribe.common;

public class CommonVariables 
{
	// Common Paths
	public static String directoryPath = System.getProperty("user.dir");
	public static String configPath = directoryPath + "\\properties\\";
	public static String reportsPath = System.getProperty("user.dir")+ "\\reports\\";
	public static String audioFilePath = System.getProperty("user.dir") + "//audio_files//";
	public static String generateSMS_OtpApi = "https://api-dev.dentscribe.ai/api/otp/sendSms";
	public static String updateOfficeIdApi = "https://api-dev.dentscribe.ai/api/practice/emailId";
	
	//Common variables
	public static String email = null;
	public static String actualPass = null;
	public static String actualErrorMessage = null; 
	public static String textUsername = "//android.widget.TextView[@text='Username']";
	public static String generateEmailId = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(3) + "@thinksys.com";
	public static String generatePassword = "Pass@" + CommonMethods.GenerateRandomNumber(4);
	public static String patientDeatilsPopupPatientName = null;
	
	//Expected Error Messages
	public static String otpErrorMessageBlank = "Please enter the valid code.";
	public static String errorMsgTextWithoutFillPracticeForm = "Currently the practice is not authorized. Please contact support.";
	public static String errorMsgTextForThreeInvalidAttempts = "You have attempted 3 times. Your account is locked for next 15 minutes.";
	public static String errorMsgTextLockedUser = "Your account is locked due to invalid login attempt. You can login your account after 15 minutes";
	public static String errorMsgTextNoRecordFound = "No record found for this user";
	public static String errorMsgTextInvalidCredentails = "Invalid username or password";
	public static String errorMsgTextInvalidCountryCode = "Please enter valid country code and mobile number";
	public static String errorMsgTextInvalidEmail = "Please enter valid email."; 
	public static String errorMsgTextInvalidPassword = "Password is too weak,it should have a digit and a special character, password must be longer than or equal to 8 characters.";
	public static String errorMsgTextWrongConfirmPassword = "confirm password does not match with password.";
	public static String errorMsgTextCurrentPassword = "current password does not matched";
	public static String errorMsgTextCardholderName = "Cardholder name  is required.";
	public static String errorMsgTextWrongCardholderName = "Allow alphabets only.";
	public static String errorMsgTextCardDetailsNotAdded = "Card details not complete";
	public static String errorMsgTextInvalidCardExpiry = "Your card's expiration year is invalid.";
	
	//Expected Success Messages
	public static String successMsgTextCancelSubscription = "Unsubscription Successful. Thank you for being a part of our community!";
	public static String successMsgTextBuyPaidPlan = "Payment Successful! Subscription active.";
	public static String successMsgTextSubmitSoapReport = "Report has been updated";
	public static String successMsgTextPracticeInfo = "Practice information updated!";
	public static String successMsgTextPushNotification = "Push notification updated!";
	public static String successMsgTextSMSNotification = "Sms notification updated!";
	public static String successMsgTextEmailNotification = "Email notification updated!";
	public static String successMsgTextAccountInfoUpdate = "User details updated!";
	
	//Note messages
	public static String loginNoteText = "Sign in with your username and password or use biometrics/multifactor authentication for enhanced security.";
	public static String signupNoteText = "Set up your new account. You can also add payment information next if you choose to subscribe to our service. ";
	
	//FAQs list questions
	public static String question1 = "How do I record patient notes?";
	public static String question2 = "Can I edit my SOAP report?";
	public static String question3 = "Can I integrate my patient management system with Dentscribe?";
	public static String question4 = "How do I cancel or pause my subscription?";
}
