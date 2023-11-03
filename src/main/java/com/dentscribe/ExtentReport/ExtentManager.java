package com.dentscribe.ExtentReport;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	public static ExtentReports extent;
	public static String path;


	public static ExtentReports createInsatance() {
		//To set the path of the report
		String fileNames = getReportName();			
		String directory = System.getProperty("user.dir")+ "\\reports\\";
		new File(directory).mkdir();
		path = directory + fileNames;

		ExtentSparkReporter extentSparkReporter= new ExtentSparkReporter(path);

		extentSparkReporter.config().setReportName("Report: Automation Test Results");
		extentSparkReporter.config().setDocumentTitle("Title : Auomation Testing");
		extentSparkReporter.config().setTheme(Theme.STANDARD);
		extentSparkReporter.config().setEncoding("utf-8");
		extent = new ExtentReports(); 		
		extent.attachReporter(extentSparkReporter);
		extent.setSystemInfo("Organisation", "ThinkSys"); // General information related to application		
		return extent;
	}

	//To get the latest date and time for setting file name 
	public static String getReportName() 
	{
		Date d = new Date();
		String fileName = "AutomationReport_" + d.toString().replace(":", "_").replace(" ", "_")+".html";
		return fileName;		
	}

	public static void logPassDetails(String log) {
		TestListeners.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
	}
	public static void logSkipDetails(String log) {
		TestListeners.extentTest.get().skip(MarkupHelper.createLabel(log, ExtentColor.ORANGE));
	}
	public static void logFailureDetails(String log) {
		TestListeners.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
	}

	public static void logExceptionDetails(String log) {
		TestListeners.extentTest.get().fail(log);
	}

	public static void logInfoDetails(String log) {
		TestListeners.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.WHITE));
	}

	public static void logJson(String json) {
		TestListeners.extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
	}
}
