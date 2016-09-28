package de.metro.im.core.applications.pages.soe;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;

public class Soe_LogoutPage extends ABasePage {
		
		@FindBy(css="a")
		private WebElement loginPageLink;
		@FindBy(css="p.logout")
		private WebElement logOffMessage;

		
	public Soe_LogoutPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public String getLogOffMessage(){
		return logOffMessage.getText();
	}
	
	public Soe_LoginPage goToLoginPage(){
		loginPageLink.click();
		return new Soe_LoginPage(this.driver);
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page.");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implemented for this page.");
	}

}
