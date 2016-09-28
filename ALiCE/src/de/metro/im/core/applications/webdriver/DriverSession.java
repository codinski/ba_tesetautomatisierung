package de.metro.im.core.applications.webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSession {
	static DriverSessionConfigurator config = new DriverSessionConfigurator("config/webdriver.properties");
	
	private static WebDriver driver = null;
	
	private DriverSession(){
	}
		
	public static WebDriver withFirefoxDriver(){
		if(driver != null) throw new WebDriverException("The driver is already set!");
		driver = new FirefoxDriver();
//		enableImplicitWaitDefault();
		return driver;
	}
		
	public static void closeAndQuitDriver(){
		driver.close();
		//its important that the cleanup runs before driver.quit() because else the driver instance will be null. 
		DriverHelper.removeFirefoxDriverLeftovers();
		driver.quit();
	}
	
	public static void deleteOldCookies(){
		if(config.doDeleteCookies()) driver.manage().deleteAllCookies();
	}
		
	public static WebDriver getDriver(){
		return driver;
	}
	
	public static boolean isDriverNull(){
		return getDriver() == null;
	}
	
	public static Set<String> getWindowIds(){
		return driver.getWindowHandles();
	}
	
	public static void getWindow(String windowName){
		driver.switchTo().window(windowName);
	}
	
	public static void disableImplicitWait(){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	public static void enableImplicitWaitDefault(){
		driver.manage().timeouts().implicitlyWait(new Integer(config.getDefaultImplicitWait()), TimeUnit.SECONDS);
	}
	
}
