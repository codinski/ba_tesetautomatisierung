package de.metro.im.core.applications.pages.oo;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;

public class OO_NewOrder extends ABasePage {
	protected static WebDriver driver;
	
	@FindBy(linkText="Abmelden")
	private WebElement logoutButton;
	@FindBy(id="artSearchGrid_wrapper")
	private WebElement artSearchGrid_wrapper;
	private WebElement txtArtNo;
	private WebElement btnSearch;
	@FindBy(css="button.btn.incrementQtyForSearchGrid")
	private WebElement incQty;
	@FindBy(id="sb-title-inner") 
	private WebElement sbtitleinner;
	@FindBy(xpath="//li[@id='CreateOrdersArea']/a") 
	private WebElement CreateOrdersArea;
	private WebElement addItem;
	@FindBy (css="i.icon-remove") 
	private WebElement searchoverlayclose;
	@FindBy(id="lnkNext")
	private WebElement orderNowButton;
	@FindBy(css="button.btn.incrementQtyForSearchGrid")
	private WebElement increaseQuantity;
	private WebElement orderItems;
	@FindBy(css="td.dataTables_empty")
	private WebElement articleNotFound;
	@FindBy(id="cart-item-count")
	private WebElement cartitemcount;
	@FindBy(id="current-item-count")
	private WebElement currentitemcount;
	@FindBy(css="div.well.yellowDotted > h3")
	private WebElement userInfo;
	@FindBy(css="td.dataTables_empty")
	private WebElement noResultsMessage;
	
	public OO_NewOrder(WebDriver _driver) {
		super(_driver);
		OO_NewOrder.driver = _driver;
		
		PageFactory.initElements(OO_NewOrder.driver, this);
	}
	
	public OO_NewOrder goTo() {
		CreateOrdersArea.click();	
		return this;
	}
	
	public OO_OrderDetails finalizeOrder() {
		DriverUtils.waitForWebelementAndGet(orderNowButton, 4, driver).click();
		return new OO_OrderDetails(driver);
	}
	
	public Cart cart(){
		if(isSearchGridOpened()) closeSearchGrid();
		return new Cart();
	}

	public OO_Login logout() {
		logoutButton.click();
		return new OO_Login(driver);		
	}
	
	public OO_NewOrder addProductToCart(String productInfo, int orderQuantity){

		typeProductSearchTerm(productInfo);
		submitSearch();
		incQuantity(orderQuantity);
		addItem();
		return this;
	}
	
//	private NotFoundException interceptProductNotfound(String productInfo) {
//		if(! DriverUtils.isVisible(searchResultMessage, driver)){
//			if(noResultsMessage.isDisplayed()) throw new NotFoundException("No search results: " + noResultsMessage.getText() + " for " + productInfo );
//		}
//		return null; 
//
//	}

	private void addItem() {
		addItem.click();
	}

	private void typeProductSearchTerm(String productInfo){
		txtArtNo.clear();
		txtArtNo.sendKeys(productInfo);
	}
	
	private void incQuantity(int qty){
		for (int i = 0; i < qty; i++) {
//			increaseQuantity.click();
			DriverUtils.waitForWebelementAndGet(increaseQuantity, 2, driver).click();
		}
	}
	
	private OO_NewOrder submitSearch(){
		btnSearch.click();
		return this;
	}
	
	public String getSearchResults(){
		return DriverUtils.waitForWebelementAndGet(artSearchGrid_wrapper, 5, driver).getText();
	}
	
	public OO_NewOrder closeSearchGrid(){
		orderItems.click();
		return this;
	}
	
	public boolean isSearchGridOpened(){
		return searchoverlayclose.isDisplayed();
	}
	
	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(userInfo, 3, driver).getText();
	}
	
	public class Cart extends OO_NewOrder{
		List<WebElement> cartContentList = new ArrayList<WebElement>();
		
		@FindBy(id="clear-cart") 	private WebElement clearcart;
		@FindBy(linkText="Löschen") private WebElement delConfirm;
		@FindBy(xpath="//tbody")	private WebElement cartContent;
		@FindAll({@FindBy(css="tr.odd"),
				  @FindBy(css="tr.even")}) private List<WebElement> openOrders;
		@FindBy(id="sb-nav-close")
		private WebElement sbnavclose;
		@FindBy(css="tt") private WebElement detailView;
		/**
		 * Constructor for the cart
		 */
		public Cart() {
			super(driver);
			PageFactory.initElements(OO_NewOrder.driver, this);
//			createArticleList();
		}
				
			private void createArticleList() {
				//search grid has to be closed otherwie this may lead to unexpected errors
				closeSearchGrid(); //TODO suchfenster schließt sich nicht: alternative zum schließen: einen click auf ein anderes clement simulieren. gfs irgendein text
				
				for (WebElement orderItem : openOrders){
					cartContentList.add(orderItem);
				}
			}
	
			public List<WebElement> getProductsList(){
				createArticleList();
				return cartContentList;
			}
			
			public List<String> getCartProductNames(){
				createArticleList();
				List<String> itemNames = new ArrayList<String>();
				
				for (WebElement element : cartContentList){
					itemNames.add(element.getText());
				}
				
				System.out.println(itemNames);
				return itemNames;
			}
			
			public List<String> lineArticleNames(){
				List<String> articleNames = new ArrayList<String>();
				for (WebElement e : openOrders){
					WebElement desc = DriverUtils.waitForWebelementAndGet(e, 3, OO_NewOrder.driver).findElement(By.cssSelector("td.artDesc"));
					articleNames.add(desc.getText());
				}
				
				return articleNames;
			}
			
			private WebElement getProductFromCart(String productInfo){
				DriverUtils.refreshPage(driver);
				for (WebElement item : getProductsList()){
					if (item.getText().contains(productInfo)){
						System.out.println("Product found! " + "( " + productInfo + " )");
						return item;
					} 
				}
				return cartContent;
			}
			
		public void delete(){
			clearcart.click();
			DriverUtils.isVisible(delConfirm, driver);
			delConfirm.click();
		}
			
		public void openDetailView(String _productInfo){
			DriverUtils.waitForWebelementAndGet(detailView, 3, driver);
			getProductFromCart(_productInfo).findElement(By.className("tt")).click();
		}
	
		public int getProductCountFromHeader() {	
			return Integer.parseInt(cartitemcount.getText());
		}
	
		public String getCartBubble() {
			if (currentitemcount.isDisplayed()){
			return currentitemcount.getText();
			} else {
				return "Wasn't able to retrieve bubble text. It either did not appear or test was too slow to intercept it.";
			}
		}

	}

}

