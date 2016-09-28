package de.metro.im.core.applications.pages.betty;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import de.metro.im.core.applications.pages.base.ABasePage;

public class Betty_Landing extends ABasePage {
	
	@FindBy(css="a[href*='#/logout']")
	private WebElement logoutButton;
	@FindBy(className="panel-body")
	private WebElement welcomeHeader;
	
	public Betty_Landing(WebDriver driver) {
		super(driver);
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(this.driver, 20);
		PageFactory.initElements(factory, this);
	}

	@Override
	public Betty_Login logout() {
		logoutButton.click();
		return new Betty_Login(driver);
	}

	@Override
	public String getCurrentUserInfo() {
		return welcomeHeader.getText();
	}

}
