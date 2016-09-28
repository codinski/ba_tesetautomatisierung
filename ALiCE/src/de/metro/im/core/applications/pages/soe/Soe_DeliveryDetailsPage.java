package de.metro.im.core.applications.pages.soe;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Soe_DeliveryDetailsPage extends ABasePage {

	private WebElement Comments;
	@FindBy(xpath="//div[@id='divContent']/div/ul/li/a")
    private WebElement delDetPagelnk;
	private WebElement lblCustomer;
	
	
	public Soe_DeliveryDetailsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
		PageFactory.initElements(this.driver, this);
	}

	public Soe_DeliveryDetailsPage goTo() {
		delDetPagelnk.click();
		return this;
	}

	/**
	 * Sets an internal coomment in the order details.
	 * @param _refresh True if you want to refresh the page after setting the comment. <br>
	 * This is necessary if you want to return the text you have typed.
	 * @param _commentText The text you want to put into the comment box. 
	 * @return If false returns the old comment box text . <True> if you want the new comment box text returned. <br> 
	 * Executes a de.metro.im.core.pages refresh if true.
	 */
	public String setInternalComment(String _commentText, boolean _refresh) {
		Comments.sendKeys(_commentText);
		Comments.sendKeys(Keys.TAB);
		waitForSavingOrderHeaderDone(2);
//		if (_refresh){DriverUtils.refreshPage(driver);}
		return Comments.getText();
	}

	public String getInternalComment(boolean _refresh) {
		if (_refresh){DriverUtils.refreshPage(driver);}
		return Comments.getText();
	}
	
	/**
	 * This method uses the default webelement for the saving lable being "lblOrderheaderSaving".<br>
	 * The loop will break as soon as the CssValue of the Saving label changes from "block" to another state.
	 * @param _seconds Maximum time to wait for the webelement to disappear.
	 */
	public void waitForSavingOrderHeaderDone(Integer _seconds) {
		for (int second = 0;; second++) {
			if (second >= _seconds)
				System.out.println("Waiting for changes to be saved...");
					try {
						WebElement lblOrderHeaderSaving = driver.findElement(By.id("lblOrderHeaderSaving"));
					if (("none".equals(lblOrderHeaderSaving.getCssValue("display")))){
						break;
					}	
				} catch (NoSuchElementException nse) {
					System.out.println("'Saving...' lable was not found! Cant execute this command.");
				}
			}
	}


	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page.");
	}
	
	public String getCurrentCustomer() {
		return lblCustomer.getText();
}

	@Override
	public String getCurrentUserInfo() {
		return getCurrentCustomer();
	}
}
