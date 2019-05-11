package com.ebay.wipro.utilities.reports;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ebay.wipro.testcases.TestBase;
import com.ebay.wipro.utilities.SeleniumUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
///import com.kfcsaudi.selutils.SeleniumUtils;

 	
 
public class TestngListener extends TestBase implements ITestListener {
	private static AppiumDriver<MobileElement> driver;	
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public synchronized void onStart(ITestContext context) {
        context.setAttribute("driver", driver);
        
    } 
 
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }
 
   
    public synchronized void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
    }
 

    public synchronized void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }
 

    public synchronized void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
       }
 

    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable());
      }
 

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
   }
  
 
}