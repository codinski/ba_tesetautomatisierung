package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public final class betty_login extends BaseTestConfig {
			
	private betty_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url", dataProviderClass = TestDataProvider.class, description="Calling the Betty webpage . ", priority=0)
	public void openWebApp(String url) {
		List<String> pageTileFailureMessages = Arrays.asList("Fehler", "Seiten-Ladefehler", "Nicht verfügbar", "404");
		assertThat(pp.betty_login.open(url)).isNotIn(pageTileFailureMessages);
	}
	
	@Test(description="Verify availability of login applet.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
		assertThat(pp.betty_login.isLoginFormAvailable()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, 
			description="Trying to log in the webapplication. ", dependsOnMethods={"isLoginAppletAvailable"})
	public void loginUser(String user, String pw){
		pp.betty_login.loginAsEmployee(user, pw);
		assertThat(pp.betty_login.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(dataProvider = "landingPageVerificationArgument", dataProviderClass = TestDataProvider.class, 
			description="Verifying that the page tile contains specified keyword after login. ", dependsOnMethods={"loginUser"})
	public void positionIsLandingPage(String pageTitle){
		assertThat(pp.betty_landing.checkPageTitle()).contains(pageTitle);
	}

}


