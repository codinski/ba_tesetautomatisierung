package de.metro.im.core.applications.pages.soe.nav;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import de.metro.im.core.applications.utils.DriverUtils;

/**
 * Diese Klasse repräsentiert die Settings-Leiste. 
 * @author christoph.zabinski
 *
 */
public class SettingsBar {

	private WebDriver driver;
	protected static String pageBaseUrl;
	
	private WebElement lnkUserSettings;
	@SuppressWarnings("unused")
	private WebElement lnkStockCheck;
	private WebElement lnkKeyboardHelp;
	private WebElement lnkLogout;
	@FindBy(css="p.logout")
	private WebElement logoutMsg;
	
	public SettingsBar(WebDriver driver ) {
		this.driver = driver;
//		this.pageBaseUrl = baseUrl;
//		SettingsBar.pageBaseUrl = baseUrl;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(factory, this);

	}

	/*Methods representing the four options available in the settings bar*/
	public SettingsDialogue settings(){
		lnkUserSettings.click();
		return new SettingsDialogue();
	}
	
	public KeyboadLayoutInfo keyboardLayout(){
		lnkKeyboardHelp.click();
		return new KeyboadLayoutInfo(driver);
	}
	
	/**
	 * Logs out the user 
	 * @return Returns the logout message presented on the logout page.
	 */
	public String logout(){
		lnkLogout.click();
		return logoutMsg.getText();
	}
//	
//	public StockPage openStockPage(){
//		lnkStockCheck.click();
//		return new StockPage(driver);
//	}
	
	/**
	 * Wrapper method to quickly set the usage of subsys numbers. 
	 */
	public void quickSetSubsys(){
		settings().setSubSysNumbers();
	}
	
	/**
	 * Wrapper method to quickly set the usage of ean numbers. 
	 */
	public void quickSetEAN(){
		settings().setEANNumbers();
	}
	
	/**
	 * Wrapper method to quickly set the usage of mgb numbers. 
	 */
	public void quickSetMGB(){
		settings().setMGBNumbers();
	}
	
	
	/*FIRST INNER CLASS representing the windows that can be opened by two of the settings options*/
	public class SettingsDialogue extends SettingsBar{
		
		@FindBy(xpath="//div[5]/div[11]/div/button")
		private WebElement closeButton;
		@FindBy(xpath="//div[5]/div[1]/a/span")
		private WebElement closeX;
		@FindBy(xpath="//div[5]/div[2]/table/tbody/tr/td[2]/label[1]")
		private WebElement optM;
		@FindBy(xpath="//div[5]/div[2]/table/tbody/tr/td[2]/label[2]")
		private WebElement optS;
		@FindBy(xpath="//div[5]/div[2]/table/tbody/tr/td[2]/label[3]")
		private WebElement optE;
		
		/**
		 * Constructor for the user settings dialogue
		 * @param driver
		 */
		public SettingsDialogue(){
			super(driver);
		}
		
		public void closeWindowByX(){
			closeX.click();
		}
		
		public void closeWindowByButton(){
			closeButton.click();
		}
		
		public void setMGBNumbers(){
			optM.click();
			closeWindowByButton();
		}
		
		public void setSubSysNumbers(){
				optS.click();
				closeWindowByButton();

		}
		
		public void setEANNumbers(){
			optE.click();
			closeWindowByButton();
		}
	}
	
	/* *****SECOND INNER CLASS***** */
	public class KeyboadLayoutInfo extends SettingsBar {
		
		@FindBy(xpath="//div[4]/div[11]/div/button")
		WebElement closeButton;
		@FindBy(xpath="//div[4]/div[1]/a/span")
		WebElement closeX;
		
		public KeyboadLayoutInfo(WebDriver driver){
			super(driver);
		}
		
		public void closeWindowByX(){
			closeX.click();
		}
		
		public void closeWindowByButton(){
			closeButton.click();
		}
		
	}
		
}