package de.metro.im.core.applications.webdriver;

import de.metro.im.core.testframework.PropertyFileReader;

public class DriverSessionConfigurator extends PropertyFileReader {

	public DriverSessionConfigurator(String configFilePath) {
		super(configFilePath);
	}

	public DriverSessionConfigurator() {
	}
	
	protected String getDefaultImplicitWait(){
		return getProp("wait.implicitly");
	}
	
	protected String getDefaultPageLoadWait(){
		return getProp("wait.pageload");
	}
	
	protected boolean doDeleteTemporaryFiles(){
		return getProp("delete.temporaryfiles_afterrun").contains("true")? true : false;
	}
	
	protected boolean doDeleteCookies(){
		return getProp("browser.deletecookies_beforetest").contains("true")? true : false;
	}
}
