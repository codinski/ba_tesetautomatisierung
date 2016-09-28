package de.metro.im.core.applications.pages.kwk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Nka_Landing extends ABasePage {
	
	@FindBy(css="#userinfo > a")
	private WebElement logout;
	private WebElement userinfo;
	private WebElement content;
	
	public Nka_Landing(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public Nka_Login logout() {
		logout.click();
		return new Nka_Login(driver);
	}

	public boolean isServiceAreaAvailable(){
		return DriverUtils.isVisible(content, driver);
	}
	

	public Nka_Landing goTo() {
		return new Nka_Landing(driver);
	}

	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(userinfo, 3 , driver).getText();
	}

}
