package de.metro.im.core.applications.pages.crs;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class CRS_Login extends ABaseLoginPage {
	
	
	private WebElement customerSearch;
	private WebElement loginForm;
	@FindBy(id="login-error")
	private WebElement loginValidationError;
	public CRS_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}
	
	public boolean isLoginFormAvailable() {
		return loginForm != null;
	}

	@Override
	public ABasePage loginSubmit() {
		throw new NotImplementedException("A Login is not supposed to be implemented.");
	}

	@Override
	public void typePassword(String _password) {
		throw new NotImplementedException("Setting password is not supposed to be implemented.");
	}

	@Override
	public void typeUsername(String _username) {
		throw new NotImplementedException("Setting username not supposed to be implemented.");
	}

	public boolean checkIfCustomerSearchExists(){
		return DriverUtils.isVisible(customerSearch, driver);
	}


	@Override
	@Deprecated
	public String getValidationErrorMessage() {
		throw new NotImplementedException("This in not implemented for this page");
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
		throw new NotImplementedException("This in not implemented for this page");
	}


	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("This in not implemented for this page");
	}

}
