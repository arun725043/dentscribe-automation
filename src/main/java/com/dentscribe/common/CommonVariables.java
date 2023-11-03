package com.dentscribe.common;

public class CommonVariables 
{
	public static String directoryPath = System.getProperty("user.dir");
	public static String configPath = directoryPath + "\\properties\\";
	public static String reportsPath = System.getProperty("user.dir")+ "\\reports\\";
	
	public static String audioFilePath = System.getProperty("user.dir") + "//audio_files//";

	public static String email=null;
	public static String actualPass=null;
	
	public static String generateSMS_OtpApi = "https://api-dev.dentscribe.ai/api/otp/sendSms";
}
