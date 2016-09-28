/**
 * 
 */
package de.metro.im.core.applications.pages.base;

import org.openqa.selenium.WebDriver;


public abstract class ABaseLoginPage extends ABasePage implements ILoginPage {

	public ABaseLoginPage(WebDriver driver) {
		 super(driver);
	}
	
	public String open(String url){
		driver.get(url);
		return checkPageTitle();
	}	
	
	public IPage loginAs(String username, String password){
		typeUsername(username);
		typePassword(password);
		return loginSubmit();
	}
	

}
