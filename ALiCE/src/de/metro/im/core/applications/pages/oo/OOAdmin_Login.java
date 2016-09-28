/**
 * 
 */
package de.metro.im.core.applications.pages.oo;


import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

/**
 * @author christoph.zabinski
 *
 */
public class OOAdmin_Login extends ABaseLoginPage {
	

	private WebElement UserName;
	private WebElement Password;
	@FindBy(css="input.btn")
	private WebElement loginSubmitbutton;
	@FindBy(css="div.validation-summary-errors")
	private WebElement loginValidationError;
	private Alert alert = null;
	
	
	public OOAdmin_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}


	@Override
	public OOAdmin_Login loginSubmit() {
		loginSubmitbutton.click();
		return this;
	}

	@Override
	public void typePassword(String password) {
		Password.clear();
		Password.sendKeys(password);
	}

	@Override
	public void typeUsername(String _username) {
		UserName.clear();
		UserName.sendKeys(_username);
	}
	
	
	public String unloadSecurityDialogue(){
		String alertTxt = "";
		driver.get("javascript:document.getElementById('overridelink').click();");
		if(isAlertPresent() != null){
			alertTxt = this.alert.getText();
			this.alert.accept();
		}
		return alertTxt;
	}
	
	private Alert isAlertPresent() {
		try {
			this.alert = driver.switchTo().alert();
			return driver.switchTo().alert();
		} catch (NoAlertPresentException Ex) {
			return null;
		}
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
		throw new NotImplementedException("Nit implemented for this page.");
	}


	@Override
	public String getCurrentUserInfo() {
		return "Not implemented";
	}


	public boolean isLoginFormAvailable() {
		return (DriverUtils.isVisible(UserName, driver)  && DriverUtils.isVisible(UserName, driver));
	}

}
