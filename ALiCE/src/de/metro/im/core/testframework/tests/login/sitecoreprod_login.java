package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public class sitecoreprod_login extends BaseTestConfig {
	
	public sitecoreprod_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());			
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the webpage url. ", priority=0)
	public void openWebApp(String url, String expectedPageTitle){
		assertThat(pp.sc_prod_login.open(url)).containsIgnoringCase(expectedPageTitle);
	}
	
	@Test(description="Verify availability of login applet.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
		assertThat(pp.sc_prod_login.loginPanelAvailable()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, timeOut = 60000, description="Trying to log in the webapplication (timeout 60 sec).", dependsOnMethods="isLoginAppletAvailable")
	public void loginUser(String user, String pw){
		pp.sc_prod_login.loginAs(user, pw);
		assertThat(pp.sc_prod_login.getValidationErrorMessage().isEmpty());
	}
	
	@Test(description="Verifying the landing page after login is the welcome page. verification is done by looking for the search form.", dependsOnMethods="loginUser")
	public void positionIsLandingPage(){
		assertThat(pp.sc_prod_land.isMainPanelAvailable()).isTrue();
	}
	
	@Test(description="Ends session by logging out.", dependsOnMethods="positionIsLandingPage")
	public void logout(){
		assertThat(pp.sc_prod_land.logout().loginPanelAvailable()).isTrue();
	}
	
}
