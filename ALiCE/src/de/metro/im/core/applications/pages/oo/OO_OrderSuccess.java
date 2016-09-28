package de.metro.im.core.applications.pages.oo;


import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;

public class OO_OrderSuccess extends ABasePage {

	@FindBy(css="div.alert.alert-success")
	private WebElement successMessage;
	@FindBy(xpath="//p[3]")
	private WebElement orderNumber;
	
	private WebDriver driver;
	
	public OO_OrderSuccess(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(factory, this);
		
	}
	
	
	@Override
	public IPage logout() {
		throw new NotImplementedException("This page conceptually has no possibility to log you out.");
				
	}
		
	/**
	 * Gets the success message from the website.
	 * @return Returns the order transmission success message from the page followed by <br>
	 * the use if the "submitOrder" button.
	 */
	public String getSuccessMessage() {
		return successMessage.getText();
	}
	
	/**
	 * Checks the message iforming the customer about the order number. 
	 * @return Returns the order number as a string.
	 */
	public String getOrderNumber(){
		String substr, srchstr, msg = "";
		int idx;
		msg = orderNumber.getText();
		srchstr = "OO2";
		idx = msg.indexOf(srchstr); 
		substr = msg.substring(idx);
		return substr;
		
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("this page does not provide this information. Please proceed to the landing page.");
	}

}
