package de.metro.im.core.applications.pages.soe.nav;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;

public class StockPage extends ABasePage {

	public StockPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
		PageFactory.initElements(this.driver, this);
	}
	
	private WebElement txtStoreNo;
	private WebElement txtArtNo;
	private WebElement lblArtDesc;
	
	public void setMarketNumber(){
		txtStoreNo.click();
	}
	
	public String setProductNumber(){
		txtArtNo.click();
		return submitSearch();	
	}

	public String submitSearch() {
		txtArtNo.submit();
		return lblArtDesc.getText();
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page.");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("This function is not supposed to be used at this position. This page position does not provide any customer information. Please proceed to another page first.");
		
	}

}
