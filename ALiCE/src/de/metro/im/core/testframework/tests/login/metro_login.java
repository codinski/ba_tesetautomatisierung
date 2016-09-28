package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public class metro_login extends BaseTestConfig {
	
	public metro_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url", dataProviderClass = TestDataProvider.class, description="Calling the Metro webpage. ", priority=0)
	public void openWebApp(String url){
		List<String> pageTileFailureMessages = Arrays.asList("Fehler", "Seiten-Ladefehler", "Nicht verfügbar", "404");
		assertThat(pp.metro_login.open(url)).isNotIn(pageTileFailureMessages);
	}
	
	@Test(description="Checking if the login applet is available.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
		assertThat(pp.metro_login.openLoginApplet().isLoginAppletVisible()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, description="Performs a successfull login.", dependsOnMethods={"isLoginAppletAvailable"})
	public void loginUser(String user, String pw){
		pp.metro_login.loginAs(user, pw);
		assertThat(pp.metro_login.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(description="Verifying that the landing page is the mymetro customer page. ", dependsOnMethods="loginUser")
	public void positionIsLandingPage( ){
		assertThat(pp.metro_landing.getGreeting()).isNotEmpty();
			
	}
	
}
