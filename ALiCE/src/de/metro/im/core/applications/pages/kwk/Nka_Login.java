package de.metro.im.core.applications.pages.kwk;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;


public class Nka_Login extends ABaseLoginPage {

	private WebElement username;
	private WebElement password;
	private WebElement login;
	private WebElement bkz;
	private WebElement content;
	@FindBy(css="input[name=\"checkerNumber\"]")
	private WebElement checkerNumber;
	
	public Nka_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public ABasePage loginSubmit() {
		login.click();
		return null;
	}

	@Override
	public void typePassword(String _password) {
		password.clear();
		password.sendKeys(_password);

	}

	@Override
	public void typeUsername(String _username) {
		//wrapped up in a try catch block to work around the fact that 
		//sometimes the login elements are not accessible by webdriver. 
		//probably some kind of bug.
		try {
			username.clear();
			username.sendKeys(_username);
		} catch (Exception e) {
			//from the password field go back with shift + tab to the username vield 
			password.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
			//clear username
			username.clear();
			//send actual password
			username.sendKeys(_username);
		}
	}
	
	public boolean isLoginFormAvailable() {
		return content != null;
	}

	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(content, driver)) valErrMsg = content.getText();
		return valErrMsg;
	}
	
	public void loginAs(String storeno, String username, String password) {
		bkz.clear();
		bkz.sendKeys(storeno);
		super.loginAs(username, password);
		
	}


	@Override @Deprecated
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page.");
	}


	@Override 
	public String getCurrentUserInfo() {
		System.out.println(checkerNumber.getText());
		return checkerNumber.getText();
	}

}
