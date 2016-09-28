package de.metro.im.core.applications.pages.oo;


import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import de.metro.im.core.applications.pages.base.ABasePage;

public class OO_Catalog extends ABasePage {

	@FindBy(name="SaveHeaderAndNext") private WebElement confirmOrder;
	@FindBy(css="#CataloguesArea > a")
	private WebElement pageLink;
	private WebElement btnCustCatalog;
	private WebElement btnStoreCatalog;
	private WebElement btnAllCatalog;
	@FindAll({@FindBy(className="odd"), @FindBy(className="even")})
	private List<WebElement> catalogs;
	
	
	private WebDriver driver;
	
	public OO_Catalog(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(factory, this);
		
	}
	
	@Override
	public OO_Catalog logout() {
		throw new NotImplementedException("This page conceptually has no possibility to log you out.");
				
	}
	
	public OO_OrderSummary confimOrder(){
		confirmOrder.click();
		return new OO_OrderSummary(driver);
	}

	public OO_Catalog goTo() {
		pageLink.click();
		return new OO_Catalog(driver);
	}
	
	public boolean displayCustomerCatalogues(){
		boolean works;
	    if (ExpectedConditions.elementToBeClickable(btnCustCatalog) != null){
	    	btnCustCatalog.click();
	    	works = true;
	    }else{
	    	works = false;
	    }
		return works;
	}
	
	/**
	 * This method simulates a click on the according catalog button. 
	 * @return returns a boolean if the element was clickable.
	 */
	public boolean displayStoreCatalogues(){
		boolean works;
	    
	    if (ExpectedConditions.elementToBeClickable(btnStoreCatalog) != null){
	    	btnStoreCatalog.click();
	    	works = true;
	    }else{
	    	works = false;
	    }
		return works;
	}
	
	public boolean displayAllCatalogues(){
		boolean works;
	    
	    if (ExpectedConditions.elementToBeClickable(btnAllCatalog) != null){
	    	btnAllCatalog.click();
	    	works = true;
	    }else{
	    	works = false;
	    }
		return works;
	}

	public List<WebElement> getAllCatalogs(){
		return catalogs;
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("this page does not provide this information. Please proceed to the landing page.");
	} 
}
