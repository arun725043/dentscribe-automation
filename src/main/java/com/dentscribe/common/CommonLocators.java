package com.dentscribe.common;

import org.openqa.selenium.By;

public class CommonLocators {
	public static String fixPath = "//parent::android.view.ViewGroup//following-sibling::android.widget.EditText";
	public static By txtUsername = By.xpath("//android.widget.TextView[@text='Username']" + fixPath);
	public static By txtPassword = By.xpath("//android.widget.TextView[@text='Password']" + fixPath);
	public static By txtConfirmPassword = By.xpath("//android.widget.TextView[@text='Confirm Password']" + fixPath);
	public static By msgUsernameIsRequired = By.xpath("//android.widget.TextView[@text='Username is required.']");
	public static By msgPasswordIsRequired = By.xpath("//android.widget.TextView[@text='Password is required.']");
	public static By txtValidEmail = By.xpath("//android.widget.EditText[@text='Please enter valid email.']");
	public static By continueButton = By.xpath("//android.widget.TextView[@text='Continue']");
	public static By textWelcome = By.xpath("//android.widget.TextView[@text='Welcome to Dentscribe!']");
}
