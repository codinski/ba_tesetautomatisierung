package de.metro.im.core.testframework.config.testng;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestNGException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.zeroturnaround.zip.ZipUtil;

import de.metro.im.core.applications.webdriver.DriverSession;
import de.metro.im.core.applications.webdriver.PagePool;
import de.metro.im.core.runner.SuiteConfiguratorException;
import de.metro.im.core.testframework.config.reporting.ReportUtilConfigurator;
import de.metro.im.core.testframework.config.reporting.listener.ListenerHelper;
import de.metro.im.core.util.Time;

public abstract class BaseTestConfig {

	protected static Logger testlog = LogManager.getLogger();
	protected static PagePool pp; //must be static else webdriver can not be shared across test classes. 
	
	
	@BeforeSuite
	public void loadSession() throws Exception {
			try {
				zipPreviousReport();
				removeOldReports();
				
				pp = new PagePool(DriverSession.withFirefoxDriver());
				testlog.info("Page objecs initiated with " + pp.getDriverTypeName() );
			} catch (Exception e) {
				testlog.fatal("Major exception in before suite method. " + e.getMessage());
				throw e;
			}
	}
 
	@BeforeTest (description="Prepares the test case before it's run. Makes sure the driver session is re-loaded in case browser crashed.")
	public void testCasePrep() {
		try {
			if (DriverSession.isDriverNull()){
				reNewSession();
				testlog.info("BeforeMethod encountered an null driver. Thus driver session was reloaded.");
			}
			DriverSession.deleteOldCookies();
		} catch (Exception e) {
			testlog.fatal("Major exception in before test method, reason: " + e.toString());
			throw e;
		}
	}
			
	//HTML Reports does not yet exists at this point. 
	@AfterSuite(alwaysRun = true) 
	public void tearDown(ITestContext context){
		undloadPages();
		cleanTemporaryfiles();
		testlog.info("Teardown executed.");
	}

	
	private void reNewSession() {	
		testlog.info("Reloading pages!");
		DriverSession.closeAndQuitDriver();
		pp = new PagePool(DriverSession.withFirefoxDriver());
	}
	
	private void cleanTemporaryfiles(){
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		for(File temporaryFile: tempDir.listFiles()){
		    if(temporaryFile.getName().startsWith("anonymous") || temporaryFile.getName().startsWith("testng-eclipse") )
				try {
					FileDeleteStrategy.FORCE.delete(temporaryFile);
				} catch (IOException e) {
					testlog.info(e.getMessage());
				}
		}
	}
	
	public void undloadPages() {
		try { 		
			pp.destroyPages(); 
//			pp = null;
			DriverSession.closeAndQuitDriver();
			testlog.info("Driver session closed cleanly.");
		} catch (	WebDriverException we) { 
			testlog.error("WebDriver run into a problem during unloading pageobjects. \n" + we.getMessage());
		} 
	}
	
	public void logRecentTestResults(ITestResult result) {
	    int status = result.getStatus();
	    switch (status) {
	        case ITestResult.SUCCESS:
	        	testlog.info("++SUCCESS_: " + ListenerHelper.getCurrentMethodName(result));
	            break;
	        case ITestResult.FAILURE:
	        	testlog.error("--FAILURE_: " + ListenerHelper.getCurrentMethodName(result) + " in " + ListenerHelper.getCurrentTestName(result));
	            break;
	        case ITestResult.SKIP:
	        	testlog.info("::SKIPPING_: " + ListenerHelper.getCurrentMethodName(result));
	            break;
	        default:
	            throw new TestNGException("Invalid status");
	    }
	}
	
	//Quelle: https://mvnrepository.com/artifact/org.zeroturnaround/zt-zip/1.9
	public static void zipPreviousReport() throws SuiteConfiguratorException {
		ReportUtilConfigurator config = new ReportUtilConfigurator("config/suites.properties"); //FIXME keine saubere lösung, muss hier weg
		File reportOutputdir = new File(config.getReportOutputPath());
		File compressedReportDestination = new File(reportOutputdir.getParent(), "report_" + Time.readableTimestamp(true) +".zip");
		
		if(FileUtils.sizeOfDirectory(reportOutputdir) > 0) //pack schmeißt sonst exception
		ZipUtil.pack(reportOutputdir, compressedReportDestination);	
	}

	public static void removeOldReports() throws SuiteConfiguratorException {
		ReportUtilConfigurator config = new ReportUtilConfigurator("config/suites.properties"); //FIXME keine saubere lösung, muss hier weg
		File compressedReportsDir = new File(config.getReportOutputPath()).getParentFile();
		int maxAge = config.getReportMaxAge();
		deteleFilesOderThan(maxAge, compressedReportsDir.getPath(), ".zip");
		
	}
	
   private static void deteleFilesOderThan(int ageInDays, String dirToDeleteFileIn, String fileFilterByExtension) {
        File targetDir = new File(dirToDeleteFileIn);
        Date fileAgeThreshold = DateUtils.addDays(new Date(), -ageInDays);
        Iterator<File> reportList = FileUtils.iterateFiles(targetDir, new AgeFileFilter(fileAgeThreshold), null); 
      
        while (reportList.hasNext()) { 
        	File deleteCandidate = reportList.next();
        	if(deleteCandidate.getName().endsWith(fileFilterByExtension)) {
        		FileUtils.deleteQuietly(deleteCandidate); //ruft keine exception auf fall löschen fehlschlägt
        	}
        		
        }  
    }


	
}


