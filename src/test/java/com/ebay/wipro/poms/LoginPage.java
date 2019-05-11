package com.ebay.wipro.poms;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import com.ebay.wipro.utilities.SeleniumUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class LoginPage extends SeleniumUtils {

	MobileElement edit_UserName;
	MobileElement edit_Pwd;
	MobileElement btn_SignIn;
	MobileElement link_Nothanks;


	HamburgerMenu hMenu;
	AppiumDriver driver;
	public LoginPage(AppiumDriver dri) {
		//PageFactory.initElements(driver, this); page factory has compatability issues while creating proxy so alternative implemented below
		this.driver=dri;
		hMenu= new HamburgerMenu(dri);
	}
	
	public MobileElement getEdit_UserName() {
		return findEleById("edit_text_username");
	}

	public MobileElement getEdit_Pwd() {
		return findEleById("edit_text_password");
	}

	public MobileElement getBtn_SignIn() {
		return findEleByXPath("//*[@text='SIGN IN']");
	}
	
	public MobileElement getLink_Nothanks() {
		return findEleById("button_google_deny");
	}

	public void  loginEbay(String uName, String pwd) {
		hMenu.clickSignIn();
		getEdit_UserName().sendKeys(uName);
		getEdit_Pwd().sendKeys(pwd);
		getBtn_SignIn().click();
		
		try {
			getLink_Nothanks().click();
		}catch(NoSuchElementException nsee) {
			lg.debug("The nothanks button not available");
		}
		//return PageFactory.initElements(driver, ItemSearchPage.class);
		
	}
}
