package de.metro.im.core.applications.pages.cms;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class CMS_Login extends ABaseLoginPage {
	
	private WebElement name;
	private WebElement password;
	@FindBy(css="input.greenButton")
	private WebElement loginButton;
	@FindBy(css="fieldset.lightBlueBox")
	private WebElement lightBlueBox;
	@FindBy(css="td.header") private WebElement header;
//	private WebElement header;
	@FindBy(css="html body table tbody tr td.contentBox center form")
	private WebElement loginForm;
	@FindBy(css="html body table tbody tr td.contentBox center form fieldset.lightBlueBox center span")
	private WebElement loginValidationError;
	
	public CMS_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}
	
	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(loginValidationError, driver)) valErrMsg = loginValidationError.getText();
		return valErrMsg;
	}

	
	public CMS_Landing loginSubmit() {
		loginButton.click();
		return new CMS_Landing(driver);
	}

	@Override
	public void typePassword(String _password) {
		password.clear();
		password.sendKeys(_password);

	}

	@Override
	public void typeUsername(String _username) {
		name.clear();
		name.sendKeys(_username);
	}

	public String getHeaderTitle() {
		return DriverUtils.waitForWebelementAndGet(header, 10, driver).getText();
	}


	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}


	@Override
	public String checkPageTitle() {
		return driver.getTitle();
	}


	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page"); 
	}

	public boolean isLoginFormAvailable() {
		return loginForm != null;
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implemented for this page"); 
	}
}
