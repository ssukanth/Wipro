package com.ebay.wipro.testcases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.ebay.wipro.poms.CartPage;
import com.ebay.wipro.poms.ItemSearchPage;
import com.ebay.wipro.poms.LandingPage;
import com.ebay.wipro.poms.LoginPage;
import com.ebay.wipro.utilities.SeleniumUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EbayOrders extends TestBase  {
	public EbayOrders() {
		super();
	}
	@DataProvider(name="ebayTest")
	public String[][] getTestData() throws IOException{
		return readXl(new File("").getAbsolutePath()+"\\src\\test\\resources\\TestData\\ebay.xlsx");
	}
	@Test(dataProvider="ebayTest")
	  public void ebay_PlaceOrder(String uName, String pwd, String searchTerm, String selectIndex) throws IOException {
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		fa=new FileAppender(pl,fl.getAbsolutePath()+"\\\\"+name+".log");
		lg.addAppender(fa);	
		lg.debug("Effective Result File:"+new File(".").getAbsolutePath()+"/Results/");
		lg.debug("The cointext of the app:"+driver.getContext());
		driver.context("NATIVE_APP");
		LandingPage lnp= new LandingPage();
		lnp.clickIconHamburger();
		LoginPage loginPage= new LoginPage(driver);
		loginPage.loginEbay(uName, pwd);
		CartPage cart= new CartPage(driver);
		ItemSearchPage searchPage= new ItemSearchPage (driver);
		String searchPage_ItemPrice=searchPage.searchAndSelectRandomItem(searchTerm, Integer.parseInt(selectIndex)-1);
		lg.debug("The price of item in search page is:"+searchPage_ItemPrice);
		String pdp_ItemPrice=cart.buyNow(Integer.parseInt(selectIndex)-1);
		lg.debug("The price of item in search page is:"+pdp_ItemPrice);
		lg.debug("The price of the item in the search page :+"+searchPage_ItemPrice+"and the pdp page:"+pdp_ItemPrice);
		Assert.assertEquals(Float.parseFloat(searchPage_ItemPrice), Float.parseFloat(pdp_ItemPrice));
		String chkItem_Pricve=cart.checkout();
		Assert.assertEquals(Float.parseFloat(chkItem_Pricve), Float.parseFloat(pdp_ItemPrice));
		lg.debug("Test Completed");
	  }
	
	@Test//this test is for extent reports
	public void test4Report() {
		Assert.assertTrue(false);
	}
	

	

}
