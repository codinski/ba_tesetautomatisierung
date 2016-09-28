package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public class nka_login extends BaseTestConfig {
	
	public nka_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the webpage url. ", priority=0)
	public void openWebApp(String url, String pageTitle){
		assertThat(pp.nka_Login.open(url)).contains(pageTitle);
		
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
		assertThat(pp.nka_Login.isLoginFormAvailable()).isTrue();
	}
	
	@Test(dataProvider = "user_pw_bkz", dataProviderClass = TestDataProvider.class, description="Trying to log in the webapplikation.", dependsOnMethods="isLoginAppletAvailable")
	public void loginUser(String user, String pw, String bkz){
		pp.nka_Login.loginAs(bkz, user, pw);
		assertThat(pp.nka_Landing.isServiceAreaAvailable()).isTrue();
	}
	
	@Test(dataProvider = "landingPageVerificationArgument", dataProviderClass = TestDataProvider.class, description="Verifying the landing page after login is the application for page. Cerification is done by looking for the application form.", priority=3, dependsOnMethods={"loginUser"})
	public void positionIsLandingPage(String landingPageVerificationArgument){
		assertThat(pp.nka_Landing.checkPageTitle()).contains(landingPageVerificationArgument);
			
	}
	
	
}
