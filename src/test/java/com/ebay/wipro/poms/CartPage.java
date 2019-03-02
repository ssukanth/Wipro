package com.ebay.wipro.poms;

import java.util.List;

import com.ebay.wipro.utilities.SeleniumUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class CartPage extends SeleniumUtils {
	private AppiumDriver dri;
	private ItemSearchPage itemS;
	private String itemPrice;
	private String chk_ItemPrice;
	private MobileElement buyItNow;
	private MobileElement btnReview; 
	
	public CartPage(AppiumDriver driver) {
		this.dri=driver;
		itemS=new ItemSearchPage(dri);
	}	
	
	public String getChk_ItemPrice() {
		chk_ItemPrice=findEleById("textview_item_price").getText();
		return chk_ItemPrice;
	}
	public MobileElement getBtnReview() {
		btnReview=findEleById("take_action");
		return btnReview;
	}
	public String getitemPrice() {		
		itemPrice=findEleById("textview_item_price").getText();
		return itemPrice;
	}
	
	public MobileElement getBuyitNow() {
		buyItNow=  findEleByAccessibilityId("Buy It Now");
		return buyItNow;
	}
	
	public String buyNow(int itemIndex) {
		List<MobileElement> tList=itemS.getCards_ItemTitleSearchResults();
		lg.debug("clickeing on the item with id :"+tList.get(itemIndex).getAttribute("resourceId"));
		lg.debug("The x and y cordinates fo the ele is :"+tList.get(itemIndex).getLocation().getX()+ " : "+tList.get(itemIndex).getLocation().getX());
		moveToAndAction("tap", tList.get(itemIndex));		
		System.out.println("Clicked on the item");
		String mrp=getitemPrice().substring(getitemPrice().indexOf("$")+1);
		lg.debug("The price of the item is :"+mrp);
		getBuyitNow().click();
		return mrp;
	}
	
	public String checkout() {
		
		String cmrp=getChk_ItemPrice().substring(getChk_ItemPrice().indexOf("$")+1);
		lg.debug("The price  of the item in the checkout page :"+cmrp);
		moveToAndAction("tap", getBtnReview());
		return cmrp;
		//ignoring the remaining flow as we do not want to place an valid orderon to prod. Can proceed if given a test card
	}
	
	
	
}
