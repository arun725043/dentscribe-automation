package com.dentscribe.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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
	public boolean IsElementPresent(By by, AppiumDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
			driver.findElement(by);
			return true; // Success!
		} catch (Exception ignored) {
			return false;
		}
	}

	public String getAttribute(By locator) {
		return AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("name");
	}

	public void waitForElementToAppear(WebElement ele, AppiumDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains((ele), "text", "Cart"));
	}

	public void click(By element, AppiumDriver driver) {
		AndroidBase.wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(element))).click();
	}

	// To perform clear operation
	public static void clear(By element) {
		AndroidBase.wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
	}

	// return text of element
	public String getText(By locator) {
		return AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}

	// To perform send keys operation (i.e to enter text in a field)
	public static void sendKeys(By element, String value, AppiumDriver driver) {
		AndroidBase.wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(element))).sendKeys(value);
	}

	public void explicitWait(AppiumDriver driver, By locator, int d) {
		new WebDriverWait(driver, Duration.ofSeconds(d)).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void back(AppiumDriver driver) {
		driver.navigate().back();
	}

	public boolean equals(String actual, String expected) {
		boolean flag = false;
		if (actual.equals(expected)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

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

	static String value;

	public static String readData(String file, String key) {
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

//	 To create random integer of length
//	public static int GenerateRandomNumber(int length) {
//		try {
//
//			String RandomNumber = RandomStringUtils.randomNumeric(length);
//			int no = Integer.parseInt(RandomNumber);
//			return no;
//		} catch (Exception ex) {
//
//		}
//		return -1;
//	}

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

}
