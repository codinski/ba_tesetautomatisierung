package de.metro.im.core.applications.webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import de.metro.im.core.applications.pages.betty.Betty_Landing;
import de.metro.im.core.applications.pages.betty.Betty_Login;
import de.metro.im.core.applications.pages.cms.CMS_Landing;
import de.metro.im.core.applications.pages.cms.CMS_Login;
import de.metro.im.core.applications.pages.crs.CRS_Login;
import de.metro.im.core.applications.pages.kwk.Kwk_Landing;
import de.metro.im.core.applications.pages.kwk.Kwk_Login;
import de.metro.im.core.applications.pages.kwk.Nka_Landing;
import de.metro.im.core.applications.pages.kwk.Nka_Login;
import de.metro.im.core.applications.pages.m24.Metro_Landing;
import de.metro.im.core.applications.pages.m24.Metro_Login;
import de.metro.im.core.applications.pages.nrv.Nrv_Admin;
import de.metro.im.core.applications.pages.nrv.Nrv_Landing;
import de.metro.im.core.applications.pages.nrv.Nrv_Login;
import de.metro.im.core.applications.pages.oo.OOAdmin_Login;
import de.metro.im.core.applications.pages.oo.OO_Catalog;
import de.metro.im.core.applications.pages.oo.OO_Landing;
import de.metro.im.core.applications.pages.oo.OO_Login;
import de.metro.im.core.applications.pages.oo.OO_NewOrder;
import de.metro.im.core.applications.pages.oo.OO_OrderDetails;
import de.metro.im.core.applications.pages.oo.OO_OrderSuccess;
import de.metro.im.core.applications.pages.oo.OO_OrderSummary;
import de.metro.im.core.applications.pages.sitecore.Sitecore_Landing;
import de.metro.im.core.applications.pages.sitecore.Sitecore_Login;
import de.metro.im.core.applications.pages.soe.Soe_DeliveryDetailsPage;
import de.metro.im.core.applications.pages.soe.Soe_LandingPage;
import de.metro.im.core.applications.pages.soe.Soe_LoginPage;
import de.metro.im.core.applications.pages.soe.Soe_OrderPage;

public class PagePool {
	private static Logger log = LogManager.getLogger();
	private WebDriver driver;
	
	public OO_Login oo_login;
	public OO_Landing oo_landing;
	
	public OOAdmin_Login ooadmin_login;
	
	public OO_NewOrder oo_newOrderPage;
	public OO_OrderDetails oo_orderDetail;
	public OO_OrderSuccess oo_orderSuccess;
	public OO_OrderSummary oo_orderSummary;
	public OO_Catalog oo_catalogs;
	
	public Soe_LoginPage soe_loginpg;
	public Soe_LandingPage soe_landinpg;
	public Soe_DeliveryDetailsPage soe_delDetails;
	public Soe_OrderPage soe_orderpg;
	
	public CMS_Login cms_loginpg;
	public CMS_Landing cms_landinpg;
	
	public CRS_Login crs_loginpg;
	
	public Nrv_Admin nrv_admin;
	public Nrv_Login nrv_Loginpg;
	public Nrv_Landing nrv_Landing;
	
	public Nka_Login nka_Login;
	public Nka_Landing nka_Landing;
	
	public Kwk_Login kwk_Loginpg;
	public Kwk_Landing kwk_Landing;
	
	public Metro_Login metro_login;
	public Metro_Landing metro_landing;
	
//	public Shaper_Login sh_login;
		
//	public MM_Login mm_loginpg;
//	public MM_MyPage mm_mypage;
	
	public Sitecore_Login sc_prod_login;
	public Sitecore_Landing sc_prod_land;
	
	public Sitecore_Login sc_pp_login;
	public Sitecore_Landing sc_pp_land;
	
	public Betty_Login betty_login;
	public Betty_Landing betty_landing;
		
	public PagePool(WebDriver driver){
		this.driver = driver;
		initPages();
	}
	
	public boolean hasDriver(){
		return this.driver != null;
	}
	
	public boolean initPages(){

		try {
			
			oo_login = new OO_Login(driver);
			oo_landing = new OO_Landing(driver);

			oo_orderDetail = new OO_OrderDetails(driver);
			oo_orderSuccess = new OO_OrderSuccess(driver);
			oo_orderSummary = new OO_OrderSummary(driver);
			
			oo_newOrderPage = new OO_NewOrder(driver);
			oo_catalogs = new OO_Catalog(driver);
			
			ooadmin_login = new OOAdmin_Login(driver);
			
			soe_loginpg = new Soe_LoginPage(driver);
			soe_landinpg = new Soe_LandingPage(driver);
			soe_delDetails = new Soe_DeliveryDetailsPage(driver);
			soe_orderpg = new Soe_OrderPage(driver);

			cms_loginpg = new CMS_Login(driver);
			cms_landinpg = new CMS_Landing(driver);

			crs_loginpg = new CRS_Login(driver);

			nrv_admin = new Nrv_Admin(driver);
			nrv_Loginpg = new Nrv_Login(driver);
			nrv_Landing = new Nrv_Landing(driver);
			
			nka_Login = new Nka_Login(driver);
			nka_Landing = new Nka_Landing(driver);

			kwk_Loginpg = new Kwk_Login(driver);
			kwk_Landing = new Kwk_Landing(driver);

			metro_login = new Metro_Login(driver);
			metro_landing = new Metro_Landing(driver);

//			sh_login = new Shaper_Login(driver);
//			
//			mm_loginpg = new MM_Login(driver);
//			mm_mypage = new MM_MyPage(driver);
			
			sc_prod_login = new Sitecore_Login(driver);
			sc_prod_land = new Sitecore_Landing(driver);
			
			sc_pp_login = new Sitecore_Login(driver);
			sc_pp_land = new Sitecore_Landing(driver);
			
			betty_login = new Betty_Login(driver);
			betty_landing = new Betty_Landing(driver);
			
			log.info("Pages initiated.");
			
			return true;
		} catch (NullPointerException npe) {
			log.info("Pages initiation failed." + npe.getMessage());
			npe.printStackTrace();
			return false;
		} 
	}
	
	public void destroyPages(){
		oo_login = null;
		oo_landing = null;

		oo_orderDetail = null;
		oo_orderSuccess = null;
		oo_orderSummary = null;
		
		ooadmin_login = null;

		oo_newOrderPage = null;
		oo_catalogs = null;
		
		soe_loginpg = null;
		soe_landinpg = null;
		soe_delDetails = null;
		soe_orderpg = null;

		cms_loginpg = null;
		cms_landinpg = null;

		crs_loginpg = null;

		nrv_Loginpg = null;
		nrv_Landing = null;
		nrv_admin = null;
		
		nka_Login = null;
		nka_Landing = null;

		kwk_Loginpg = null;
		kwk_Landing = null;

		metro_login = null;
		metro_landing = null;
		
		betty_login = null;
		betty_landing = null;

//		sh_login = null;
		
		sc_prod_login = null;
		sc_prod_land = null;
		
		sc_pp_login = null;
		sc_pp_land = null;
		
		log.info("Pages destroyed.");
		
	}
	
	public void reloadPages() {
		destroyPages();
		initPages();
	}

	public String getDriverTypeName() {
		return this.driver.getClass().getSimpleName();
	}
	
}
