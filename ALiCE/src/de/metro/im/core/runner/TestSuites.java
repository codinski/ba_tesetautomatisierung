package de.metro.im.core.runner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.uncommons.reportng.HTMLReporter;
import org.zeroturnaround.zip.commons.FileUtils;

import de.metro.im.core.testframework.PropertyFileReaderException;


/**
 * @author christoph.zabinski
 * This is a container of all xmlSuitePathList that can be run. 
 * Every test suite consists of exacly one test-configuration file (xml). Adding more that one file to a test session will lead to double-reporting this test. Seems to be a bug with test-ng.
 */
@SuppressWarnings("unused")
public class TestSuites {
	private static Logger log = LogManager.getLogger();
	protected SuiteConfigurator config;
	
	public TestSuites() throws  SuiteConfiguratorException {
		config = new SuiteConfigurator("config/suites.properties");
		log.info(config.getClass().getSimpleName() + " loaded.");
		createReportOutputDir();
	}
	
	private static List<String> xmlSuitePathList = Lists.newArrayList();
	private static HTMLReporter htmlRep = new HTMLReporter(); 
	private static TestListenerAdapter tla = new TestListenerAdapter();
	
	public void setUpDebugSuite() throws IOException, SuiteConfiguratorException {	
		runCustomTestNGInstance(config.getDebugTestSuiteXml());
	}
	
	public void setUpAliveChecks() throws IOException, SuiteConfiguratorException {
		runCustomTestNGInstance(config.getLoginTestsSuiteXml());
	}

	public void addTestExtension_MOBS_DailyOrder() throws IOException, SuiteConfiguratorException {
		runCustomTestNGInstance(config.getExtensionMobsDailyOrderSuite());
	}

	public void addTestExtension_NRV_FailingJobs() throws IOException, SuiteConfiguratorException {
		runCustomTestNGInstance(config.getExtensionNrvFailingJobsSuite());
	}
	

	
	private void runCustomTestNGInstance(String suiteXmlPath) throws SuiteConfiguratorException {
		xmlSuitePathList.clear();
		xmlSuitePathList.add(suiteXmlPath);
		
		if(!checkSuiteExists(xmlSuitePathList)){
			throw new SuiteConfiguratorException("Suite file " + new File(xmlSuitePathList.get(xmlSuitePathList.size()-1)).getPath() + " does not exist.");
		}
		
		log.info("XML Suite added: " + xmlSuitePathList.get(xmlSuitePathList.size()-1));
		
		TestNG testng = new TestNG();
		testng.setOutputDirectory(config.getReportOutputPath());
		testng.setTestSuites(xmlSuitePathList); 
		testng.setUseDefaultListeners(false);
		testng.addListener(htmlRep);
		testng.run();
	}
	
	

	private boolean checkSuiteExists(List<String> xmlSuitePathList2) {
		return new File(xmlSuitePathList.get(xmlSuitePathList.size()-1)).isFile();
	}
	
	private void createReportOutputDir(){
		File reportOuput = new File(config.getReportOutputPath());
		if(! reportOuput.isDirectory()) reportOuput.mkdirs();
	}
	
}
