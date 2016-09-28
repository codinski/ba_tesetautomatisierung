package de.metro.im.core.applications.pages.soe;

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


public class Soe_LoginPage extends ABaseLoginPage {
	

	WebElement Username;

	WebElement Password;
	@FindBy(css="input[type=\"submit\"]")
	WebElement loginSubmitbutton;
	@FindBy(css="div.error")
	WebElement loginError;

	@FindBy(css="div.panel.subNavi.form")
	private WebElement subNavi;
	@FindBy(css="div.validation-summary-errors")
	private WebElement loginValidationError;

	
	public Soe_LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
		PageFactory.initElements(this.driver, this);
		
	}

	@Override
	public ABasePage loginSubmit() {
		loginSubmitbutton.click();
		return null;
	}

	@Override
	public void typePassword(String _password) {
		Password.clear();
		Password.sendKeys(_password);

	}

	@Override
	public void typeUsername(String _username) {
		//wrapped up in a try catch block to work around the fact that 
		//sometimes the login elements are not accessible by webdriver. 
		//probably some kind of bug.
		try {
			Username.clear();
			Username.sendKeys(_username);
		} catch (Exception e) {
			//from the password field go back with shift + tab to the username vield 
			Password.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
			//clear username
			Username.clear();
			//send actual password
			Username.sendKeys(_username);
		}
	}

	
	public boolean isLoginAppletVisible() {
		return DriverUtils.isVisible(subNavi, driver); 
	}

	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(loginValidationError, driver)) valErrMsg = loginValidationError.getText();
		return valErrMsg;
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
		throw new NotImplementedException("Metode nicht implementiert");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Metode nicht implementiert");
	}

	public boolean isLoginFormAvailable() {
		Username.isDisplayed();
		Password.isDisplayed();
		return true;
	}
	
}
