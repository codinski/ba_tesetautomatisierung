package de.metro.im.core.applications.pages.oo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;

public class OO_Landing extends ABasePage {

	@FindBy(linkText="Abmelden")
	private WebElement logoutButton;
	@FindBy(id="anker1751281")
	private WebElement welcomeMessage;
	@FindBy(css="div.well.yellowDotted > h3")
	private WebElement userInfo;
	@FindBy(id="anker1751281")
	private WebElement headerText;

	public OO_Landing(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	public WebElement waitForWelcomeMsg() {
		return DriverUtils.waitForWebelementAndGet(welcomeMessage, 30, driver);
	}
	
	public OO_Login logout() {
		logoutButton.click();
		return new OO_Login(driver);	
	}
		
	public OO_Landing goTo() {
		return new OO_Landing(driver);
	}
	
	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(userInfo, 3, driver).getText();
	}

	public String getGreetingMessage() {
		return welcomeMessage.getText();
	}

}
