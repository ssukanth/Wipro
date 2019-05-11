package com.ebay.wipro.utilities.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ebay.wipro.testcases.TestBase;

public class ExtentUtils extends TestBase  {
    private static ExtentReports extent;
    private static String reportFileName = "Report.html";
     private static String reportFileLoc = resLoc + "\\" + reportFileName;
 
     public ExtentUtils() {
    	 this.extent=extent;
     }
     
     
     /* Method Name= getInstance
    	Parameters =null;
     	return type= Extenr Report class objects
     */
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
    
    /*Method Name= createInstance
     * Objective: To create an instance of extent reports 
   	  Parameters =null;
     return type= ExtentReportsclass objects
     */
    public static ExtentReports createInstance() {
    	System.out.println("THE REPORT FILE LOCATION IS :"+reportFileLoc);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFileLoc);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileLoc);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileLoc); 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter); 
        return extent;
    }   
  
}
