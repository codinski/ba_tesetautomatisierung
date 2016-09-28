package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;


public class onlineordering_admin_login extends BaseTestConfig {
	
	public onlineordering_admin_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());	
	}

	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the webpage url. ", priority=0, groups="iflogin")
	public void openWebApp(String url, String pageTitle){
		assertThat(pp.ooadmin_login.open(url)).containsIgnoringCase(pageTitle);
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp", groups="iflogin")
	public void isLoginAppletAvailable(){
		pp.ooadmin_login.unloadSecurityDialogue();
		assertThat(pp.ooadmin_login.isLoginFormAvailable()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, description="Trying to log in the webapplikation.",  dependsOnMethods="isLoginAppletAvailable", groups="login")
	public void loginUser(String user, String pw){
		pp.ooadmin_login.loginAs(user, pw);
		assertThat(pp.ooadmin_login.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(dataProvider = "landingPageVerificationArgument", dataProviderClass = TestDataProvider.class, description="Verifying the landing page after login is the welcome page.", dependsOnMethods="loginUser", groups="login")
	public void positionIsLandingPage(String landingPageVerificationArgument){
		assertThat(pp.ooadmin_login.checkPageTitle()).containsIgnoringCase(landingPageVerificationArgument);
			
	}

	
	
}
