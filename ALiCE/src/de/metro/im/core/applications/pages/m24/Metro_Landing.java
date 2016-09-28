package de.metro.im.core.applications.pages.m24;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import de.metro.im.core.applications.pages.base.ABasePage;

public class Metro_Landing extends ABasePage {

	@FindBy(xpath="//div[@id='header-metanavigation']/ul/li/a/u")
	private WebElement logout;
	@FindBy(css="h1")
	private WebElement welcomeMessage;
	
	
	public Metro_Landing(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(factory, this);
	}

	@Override
	public Metro_Landing logout() {
		logout.click();
		return new Metro_Landing(driver);
	}

	public boolean isThisTheStartPage() {
		if (getCurrentUrl().equalsIgnoreCase("Meine Seite")){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implemented yet.");
	}

	public String getGreeting() {
		return welcomeMessage.getText();
	}
	


}
