package com.dentscribe.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.github.javafaker.Faker;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

//This class is used for common methods
public class CommonMethods {
	public AppiumDriverLocalService service;
	static Random randomGenerator;
	static CommonMethods utils;
	static Faker faker = new Faker();
	static int MAX = 26;
	public static String otpTextboxes = "android.widget.EditText";

	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {

		if (contains(System.getProperty("os.name"), "Windows")) {
			service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).withIPAddress(ipAddress).usingPort(port).build();
		} else {
			service = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress(ipAddress).usingPort(port).build();
		}
		service.start();
		return service;
	}

	// To return element present
	public boolean IsElementPresent(AppiumDriver driver, By locator, String expectedElement) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
			driver.findElement(locator);
			ExtentManager.logInfoDetails("Expected element found :: - <b>'" + expectedElement + "'<b>.");
			return true; // Success!
		} catch (Exception e) {
			ExtentManager.logFailureDetails("Please check because expected element not found ::- <b>'" + expectedElement + "'<b>.");
			e.getMessage();
			return false;
		}
	}
	
	// To return element present
	public boolean IsElementNotPresentThenContinue(AppiumDriver driver, By locator, String expectedElement) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
			driver.findElement(locator);
			ExtentManager.logInfoDetails("Expected element found :: - <b>'" + expectedElement + "'<b>.");
			return true; // Success!
		} catch (Exception e) {
			return false;
		}
	}
	
	// To return element present in sikka webview
	public boolean IsAgreeButtonPresent(AppiumDriver driver, By locator, String expectedElement) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
			driver.findElement(locator);
			ExtentManager.logInfoDetails("Expected element found :: - <b>'" + expectedElement + "'<b>.");
			return true; // Success!
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void fillOTPBoxes(AppiumDriver driver, String otp)
	{
		char[] ch = otp.toCharArray();
		List<WebElement> elements = driver.findElements(By.className(otpTextboxes));
//		ExtentManager.logInfoDetails(String.valueOf(elements.size()));
		for (int i = 0; i < elements.size();) 
        {
			elements.get(i).click();
			elements.get(i).sendKeys(String.valueOf(ch));
			ExtentManager.logInfoDetails("SMS OTP Entered successfully");
			break;
        }
	}
	
	public String getAttribute(By locator) {
		return AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("name");
	}

	public void waitForElementToAppear(AppiumDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains((element), "text", "Cart"));
	}

	public void click(AppiumDriver driver, By locator, String buttonName) {
		AndroidBase.wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator))).click();
		ExtentManager.logInfoDetails("Clicked on <b>" + buttonName + "<b>");
	}

	// To perform clear operation
	public static void clear(By element) {
		AndroidBase.wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
	}

	// return text of element
	public String getText(By locator) {
		String actualText = null;
		try {
			actualText = AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
			ExtentManager.logInfoDetails("Actual text found <b>" + actualText);
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Either expected element not found or not exists. Please check");
			Assert.fail();
		}
		return actualText;
	}
	
	// verify actual and expected text
	public void verifyTexts(String actualText, String expectedText) {
		if (actualText.trim().equalsIgnoreCase(expectedText.trim())) 
		{
			ExtentManager.logInfoDetails("Actual and Expected text matched as expected.");
		} else {
			ExtentManager.logFailureDetails("Expected text is :- " + expectedText + " but actual text found :- " + actualText);
			Assert.fail();
		}
	}
	
	// verify given text input in list and then click on it
	public void verifyListOption(AppiumDriver driver, By optionsLocator, String optionText) {
		int flag = 0;
		List<WebElement> options = driver.findElements(optionsLocator);
		for (WebElement option : options) 
		{
			String optionName = option.getText();
//			ExtentManager.logInfoDetails("OPTION NAME ::- " + optionName);
			if (optionName.equalsIgnoreCase(optionText))
			{
				flag = 1;
				ExtentManager.logInfoDetails("<b>" + optionText + "<b> exists in list as expected.");
				break;
			}
		}
		
		if(flag == 0)
		{
			ExtentManager.logFailureDetails(optionText + " not available in list. please check");
			Assert.fail();
		}
	}
	
	// verify given text input in list and then click on it
	public void verifyClickListOption(AppiumDriver driver, By optionsLocator, String optionText) {
		int flag = 0;
		List<WebElement> options = driver.findElements(optionsLocator);
		for (WebElement option : options) 
		{
			String optionName = option.getText();
//			System.out.println("OPTION NAME ::- " + optionName);
			if (optionName.equalsIgnoreCase(optionText))
			{
				flag = 1;
				option.click();
				break;
			}
		}
		
		if(flag == 0)
		{
			ExtentManager.logFailureDetails(optionText + " not available in list. please check");
			Assert.fail();
		}
	}

	// To perform send keys operation (i.e to enter text in a field)
	public void sendKeys(AppiumDriver driver, By locator, String fieldName, String value) {
		AndroidBase.wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator))).sendKeys(value);
		ExtentManager.logInfoDetails("Entered value into textbox <b>" + fieldName + "<b> ::- <b>" + value + "<b>");
	}

	public void explicitWait(AppiumDriver driver, By locator, int duration) {
		new WebDriverWait(driver, Duration.ofSeconds(duration)).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void back(AppiumDriver driver) {
		driver.navigate().back();
	}

	public boolean contains(String actual, String expected) {
		boolean flag = false;
		if (actual.contains(expected)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

	public static String readData(String file, String key) {
		String value = null;
		try {
			FileInputStream in = new FileInputStream(CommonVariables.configPath + file);
			Properties properties = new Properties();
			properties.load(in);
			in.close();

			FileReader reader = new FileReader(CommonVariables.configPath + file);
			properties.load(reader);
			value = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Configuration file not readable");
		}
		return value;
	}

	public static void writeData(String file, String key, String value) {
		try {
			FileInputStream in = new FileInputStream(CommonVariables.configPath + file);
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(CommonVariables.configPath + file);
			props.setProperty(key, value);
			props.store(out, "properties");
			out.close();
		} catch (Exception e) {
			System.out.println("Configuration file not readable");
		}
	}

	// To create random integer
	public static int getRandomNumber() {
		int randomInt = 0;
		randomInt = randomGenerator.nextInt(1000);
		return randomInt;
	}

	public static String GenerateRandomNumber(int length) {
		Random random = new Random();

		// Generate 10 random digits and concatenate them
		StringBuilder randomNumber = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int digit = random.nextInt(10); // Generate a random digit (0-9)
			randomNumber.append(digit);
		}

		// Convert the StringBuilder to a String
		String no = randomNumber.toString();
		return no;
	}

	// To create random firstName
	public static String genrateRandomFirstName() {
		String firstName = faker.name().firstName();
		return firstName;
	}

	// To create random middleName
	public static String genrateRandomMiddleName() {
		String middleName = faker.name().nameWithMiddle();
		return middleName;
	}

	// To create random lastName
	public static String genrateRandomLastName() {
		String lastName = faker.name().lastName();
		return lastName;
	}

	// To create random city
	public static String genrateRandomCity() {
		String city = faker.address().city();
		return city;
	}

	// To create random state
	public static String genrateRandomState() {
		String state = faker.address().state();
		return state;
	}

	// function to generate a random string of length n
	public static String getAlphaNumericString(int n) {
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	// To create random phone number
	public static String genrateRandomPhoneNumber() {
		String number = faker.phoneNumber().cellPhone();
		return number;
	}

	public static String selectTodaysDate(String format) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = formatter.format(date);
		System.out.println(strDate);
		return strDate;
	}
	
	public static long getSecondsMinutes(long milliseconds, String requiredValue)
    {
		long time = 0; 
		if(requiredValue == "minutes") {
			// milliseconds to minutes.
			time = (milliseconds / 1000) / 60;
		}
		else if(requiredValue == "seconds") {
			// milliseconds to seconds
	        time = (milliseconds / 1000) % 60;
		}
		else {
			Assert.fail("Given parameters not matched. please check");
		}
		return time;
    }
	
	public static String currentMonthYear()
	{
		 DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
	     String formattedDate = df.format(new Date());
	     System.out.println("Formated Date:" + formattedDate);
	     
	     String[] seperateDate = formattedDate.split("-");
	     String day = seperateDate[0];
	     String month = seperateDate[1];
	     String year = seperateDate[2];
	     
		String monthYear = month + " " + year;
		return monthYear;
	}
	
	//Wait for minutes
	public void doRecordingForMinutes(AppiumDriver driver, int minutes) throws InterruptedException
	{
		ExtentManager.logInfoDetails("<b>Recording is running for " + minutes + " minutes");
		int seconds = minutes * 60000;
		Thread.sleep(seconds);
		
	}
}
