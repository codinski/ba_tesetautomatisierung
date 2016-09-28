/**
 * 
 */
package de.metro.im.core.applications.utils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.metro.im.core.applications.webdriver.DriverSession;


/**
 * @author christoph.zabinski This class provides recurring methods which are
 *         more of a utility type than a function of a web applikation.
 */
public abstract class DriverUtils {
	//NOTIZ: gutes how to hier: http://toolsqa.com/selenium-webdriver/advance-webdriver-waits/
	@Deprecated
	public static WebElement isVisible(By webElementLocator, WebDriver driver) {
		WebElement element = new WebDriverWait(driver, 2)
			.ignoring(TimeoutException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOfElementLocated(webElementLocator));
		return element;
	}
	
	public static boolean isVisible(WebElement webElement, WebDriver driver) {
		try {
			 new WebDriverWait(driver, 2)
						.ignoring(NoSuchElementException.class)
						.until(ExpectedConditions.visibilityOf(webElement));
			return true;
		} catch (TimeoutException te) {
			return false;
		}
	}
	
	
	public static WebElement fluentWaitIgnoring(WebElement webelement, int timeout, Collection<Class<? extends Throwable>> exceptionTypToIgnore) {
		new FluentWait<WebElement>(webelement)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoreAll(exceptionTypToIgnore);
		return webelement;
	}
	
	
	public static WebElement waitForWebelementAndGet(WebElement webElement, int seconds, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void refreshPage(WebDriver driver){
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			System.out.println("Something went wrong during refreshing the website.");
			e.printStackTrace();
		}
	}
	
	//TODO kann auch mit expected condition gemacht werden ExpectedConditions.alertIsPresent()
	public static String closeAlertAndGetMessage(boolean acceptNextAlert, WebDriver driver) {
		//TODO hier definitiv einen wait einbauen. dialoge brauchen manchmal bis sie angezeigt werden
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      System.out.println(alertText);
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    }catch (NoAlertPresentException nap){
	    	return "";
	    } finally {
	      acceptNextAlert = true;
	    }
	}
	 
	//TODO andere methode verwenden ohne try catch
	public static void clickAndRetry(By locator, WebDriver driver){
		  try {
			WebElement element = driver.findElement(locator);
			element.click();
		} catch (NoSuchElementException nse) {
			refreshPage(driver);
			WebElement element = driver.findElement(locator);
			element.click();
		}
	  }
	
	//TODO andere methode verwenden ohne try catch 
	public static void clickAndRetry(WebElement element, WebDriver driver){
		  try {
			element.click();
		} catch (NoSuchElementException nse) {
			refreshPage(driver);
			element.click();
		}
	 }
	
	public static void switchToNewWindow() {
		
		for(String winHandle : DriverSession.getWindowIds()){
			DriverSession.getDriver().switchTo().window(winHandle);
		}
	}
	
	public static void getDefaultWindow(){
		DriverSession.getDriver().switchTo().defaultContent();
	}
	
	
class Conditions{
		
		Function <WebElement, Boolean>isVisible = new Function<WebElement, Boolean>(){
			@Override
			public Boolean apply(WebElement element) {
				return element.isDisplayed();
			}
			
		};
}

	public static Alert isAlertPresent(WebDriver driver) {
		try {
			 @SuppressWarnings("unused")
			Alert alert = driver.switchTo().alert();
			return driver.switchTo().alert();
		} catch (NoAlertPresentException Ex) {
			return null;
		}
	}


	
}
