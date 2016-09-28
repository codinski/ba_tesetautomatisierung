package de.metro.im.core.applications.pages.shaper;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.metro.im.core.applications.pages.base.ABasePage;

public class Shaper_Landing extends ABasePage {

	@FindBy(xpath="//div[@id='header-metanavigation']/ul/li/a/u")
	private WebElement logout;
	
	
	public Shaper_Landing(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
//		this.extUrl = "/profiles/" + DriverHelper.m24name;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
//		PageFactory.initElements(factory, this);
	}

	@Override
	public Shaper_Login logout() {
		logout.click();
		return new Shaper_Login(driver);
	}

	public boolean isThisTheStartPage() {
		if (getCurrentUrl().equalsIgnoreCase("Meine Seite")){
			return true;
		} else {
			return false;
		}
	}

	public ABasePage goTo() {
		throw new NotImplementedException("I have no use yet.");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Yet to be implemented.");
	}

}
