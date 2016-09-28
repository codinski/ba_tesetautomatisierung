package de.metro.im.core.applications.pages.soe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.soe.nav.MainNavigation;
import de.metro.im.core.applications.utils.DriverUtils;

public class Soe_OrderPage extends ABasePage {
		
		private WebElement txtArtNo;
		private WebElement txtQty;
		private WebElement chkQuickAdd;
//		@FindBy(css="div.grid-canvas")
		@FindAll({@FindBy (css="div.ui-widget-content.slick-row.even"), @FindBy (css="div.ui-widget-content.slick-row.odd")})
		private List<WebElement> itemList;
		@FindBy (xpath="/html/body/div[2]/div[3]/div[2]/div")
		private WebElement itemListHeaders;
		
		@FindBy(xpath="//div[@id='divContent']/div/ul/li[2]/a")
		private WebElement orderCreationPagelnk;
		
		@SuppressWarnings("unused")
		private WebElement lnkSaveOrder;
		public MainNavigation nav;
		
		boolean acceptNextAlert = true;
		
	public Soe_OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
		nav = new MainNavigation(driver);
		
		PageFactory.initElements(this.driver, this);
	}

	public Soe_OrderPage goTo() {
		orderCreationPagelnk.click();
		return new Soe_OrderPage(driver);
		
	}
	
	/**
	 * Wrapper method for common recurring operations. <br>
	 * Simulates adding an item to the line items list.  
	 * @param itemFromDataProvider The Item you want to add. 
	 * @param qty The amount you want to add.
	 * @param quickAdd Set true if you want to enable the quickadddoption.
	 * @param useSubsysNumbers Set true if you want to use subsysnumbers to search a product.
	 * @return 
	 */
	public Soe_OrderPage addItemToList(String itemFromDataProvider, String qty, boolean quickAdd, boolean useSubsysNumbers) {
		if (useSubsysNumbers) {
			nav.settings().quickSetSubsys();
		}
		setQuickAdd(quickAdd);
		setItem(itemFromDataProvider);
		setQty(qty);
		submitItem();
		return this;
	}
	
	/**
	 * Wrapper method for common recurring operations. <br>
	 * Simulates adding an item to the line items list.  <br>
	 * <b>This method will skip setting quick add mode or setting and article number type.</b>
	 * @param itemFromDataProvider The Item you want to add. 
	 * @param qty The amount you want to add.
	 * @return 
	 */
	public Soe_OrderPage addItemToList(String itemFromDataProvider, String qty) {
		setItem(itemFromDataProvider);
		setQty(qty);
		submitItem();
		return this;
	}
	
	
	public void setItem(String itemFromDataProvider) {
		txtArtNo.sendKeys(itemFromDataProvider);
	}

	public void setQty(String qty) {
		txtQty.sendKeys(qty);
		
	}

	public Soe_OrderPage submitItem() {
		txtQty.sendKeys(Keys.RETURN);
		DriverUtils.closeAlertAndGetMessage(acceptNextAlert, driver);
		return new Soe_OrderPage(driver);
	}
	
	public Soe_OrderPage submitItem(int waitTimeAfterSubmit) {
		txtQty.sendKeys(Keys.RETURN);
		DriverUtils.closeAlertAndGetMessage(acceptNextAlert, driver);
		return new Soe_OrderPage(driver);
	}
	
	public void setQuickAdd(boolean _on) {
		if (_on) {
			if (getQuickAddStatus()) {
				return;
			} else if (_on) {
				if (!getQuickAddStatus()) {
					chkQuickAdd.click();
				} else if (!_on) {
					if (getQuickAddStatus()) {
						chkQuickAdd.click();
					} else if (!_on) {
						if (!getQuickAddStatus()) {
							return;
						}
					}
				}
			}
		}
	}
	
	private boolean getQuickAddStatus(){
		String val = chkQuickAdd.getAttribute("value");
		if (val.equals("false")) {
			return false;
		} else{
			return true;
		}
	}

	public void saveForLater() {
		nav.subNavi().saveForLater();
		//lnkSaveOrder.click();	
	}

	public List<WebElement> getItemList() {
		return itemList; 		
	}

	public List<String> getItemListHeaders2() {
		
		String headersStr = itemListHeaders.getText();
		
		List<String> headerNames = Arrays.asList(headersStr.split("\n"));
		for (String head : headerNames){
			System.out.println(head);
		}
		return headerNames;
	}
	
	//TODO muss noch finalisiert werden. baut keine liste auf weil das webelement nicht richtig gefunden wird
	public List<String> getItemListHeaders1() {
		System.out.println((itemListHeaders.getText()));
		List<WebElement> divChildren = itemListHeaders.findElements(By.xpath(".//*"));
		List<String> headerNames = new ArrayList<String>();
		for (WebElement childItem : divChildren){
			System.out.println(childItem.getText());
			headerNames.add(childItem.getText());
		}
		return headerNames;
	}
	

	@Override
	public Soe_LogoutPage logout() {
		nav.settings().logout();
		return new Soe_LogoutPage(driver) ;

	}

	public Soe_LandingPage deleteCurrentOrder() {
		nav.subNavi().delete();
		return new Soe_LandingPage(driver);
		
	}

	@Override
	public String getCurrentUserInfo() {
		Soe_DeliveryDetailsPage delpg = new Soe_DeliveryDetailsPage(driver);
		return delpg.getCurrentCustomer();
		
	}
	
	/**
	 * This method reads a csv file line by line and adds a product for each one of them until the line limit is met by either the end of the file or the limit the user has set. 
	 * @param _sourcePath The path of the source file containing the product numbers or description.
	 * @param startingLine The index of the product so start adding at.
	 * @param endingLine The index of the product so end the adding at.
	 */
	public Soe_OrderPage addProductsToListFromThisList(String _sourcePath, Integer _startingLine, Integer _endingLine) {
		String filePath = _sourcePath;
		Integer startHere = _startingLine;
		Integer endHere = _endingLine;
		Integer lineCount=1;
		String line;
		
		nav.settings().quickSetSubsys();
		setQuickAdd(true);
		
		try {
			BufferedReader bufferedFileReader = new BufferedReader(new FileReader(new File(filePath)));

			while(bufferedFileReader.ready()){ //while there are lines to read
	              line = bufferedFileReader.readLine(); //do read lines
	              
				if (lineCount >= startHere && lineCount <= endHere) { //if the loop is between these lines
					System.out.println(line + " || Zeile: " + lineCount);
					setItem(line);
					setQty("1");
					submitItem(1);
				}
				if(lineCount == endHere){break;} //end the loop when the last desired line was processed 
				
				lineCount++;
			}
			
			bufferedFileReader.close();
			
		} catch (NullPointerException npe){
			npe.printStackTrace();
			System.out.println("lala");
		} catch (FileNotFoundException e) {
			System.out.println("The source file can't be found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading the line stream!");
			e.printStackTrace();
		}
		return new Soe_OrderPage(driver);
	}
	
	/**
	 * Simple loop that checks if the stock column of each item is empty or not.
	 * @return Will return true as soon as an empty column is found.
	 */
	public boolean hasEmptyStocks(){
		for (WebElement item : getItemList()){
			if (getStockInfo(item).isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Simple loop that check if the stock colum is: Empty, contains 'Fehler' or 'Nan'. <br>
	 * @return If wither of the keywords/conditions if foun dto be true, the method will immediately return true.
	 */
	public boolean hasErrornousStocks(){
		for (WebElement item : getItemList()){
			if (getStockInfo(item).isEmpty() || getStockInfo(item).contains("Fehler") || getStockInfo(item).contains("Nan")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the stock column of the given item is empy or not.
	 * @param element The item as Webelement you want to check. 
	 * @return Returns the contents of the stock column from the passed item.
	 */
	private String getStockInfo(WebElement element){
		
		String stock= "";
		String name = "";
		String artNr = "";
		
		stock = element.findElement(By.xpath(".//div[10]")).getText();
		name = element.findElement(By.xpath(".//div[7]")).getText();
		artNr = element.findElement(By.xpath(".//div[4]")).getText();
		
		System.out.println("Stock of: " + name + " " + "(" + artNr + ") " + "is " + stock);
	
		return stock;
	}

}
