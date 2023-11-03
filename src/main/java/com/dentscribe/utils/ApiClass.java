package com.dentscribe.utils;
//package org.dentscribe.utils;
//
//import static io.restassured.RestAssured.baseURI;
//import static io.restassured.RestAssured.given;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.simple.JSONObject;
//import org.openqa.selenium.By;
//
//import com.dentscribe.pages.SignUpPage;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.pagefactory.bys.ContentType;
//
//public class ApiClass extends AndroidActions{
//	AndroidDriver driver;
//	SignUpPage signUpPage = new SignUpPage(driver);
//
//	public ApiClass(AndroidDriver driver) {
//		super(driver);
//		this.driver = driver;
//	}
//
//	public void fillOtp(String option) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("phoneNumber", "+91" + readData("Config", "mobile"));
//		System.out.println(map);
//		JSONObject json = new JSONObject(map);
//		System.out.println(json);
//		System.out.println(json.toJSONString());
//		String result = null;
//		if (option == "sms") {
//			baseURI = "https://api-dev.dentscribe.ai/api/otp/sendSms";
//			Response response = given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toJSONString()).when().post(baseURI);
//			System.out.println(response.jsonPath().getString("data.otp"));
//			result = response.jsonPath().getString("data.otp");
//			sendKeys(By.className("android.widget.EditText"), result, driver);
//
//		} else if (option == "call") {
//			baseURI = "https://api-dev.dentscribe.ai/api/otp/call";
//			Response response = given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toJSONString()).when().post(baseURI);
//			System.out.println(response.jsonPath().getString("data.otp"));
//			result = response.jsonPath().getString("data.otp");
//			char[] ch = result.toCharArray();
//			sendKeys(signUpPage.inputFirstLetter, String.valueOf(ch[0]), driver);
//			sendKeys(signUpPage.inputSecondLetter, String.valueOf(ch[1]), driver);
//			sendKeys(signUpPage.inputThirdLetter, String.valueOf(ch[2]), driver);
//			sendKeys(signUpPage.inputFourthLetter, String.valueOf(ch[3]), driver);
//			sendKeys(signUpPage.inputFifthLetter, String.valueOf(ch[4]), driver);
//			sendKeys(signUpPage.inputSixthLetter, String.valueOf(ch[5]), driver);
//		}
//
//	}
//
//}
