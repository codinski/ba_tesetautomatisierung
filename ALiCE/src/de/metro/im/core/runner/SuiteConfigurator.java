package de.metro.im.core.runner;

import de.metro.im.core.testframework.PropertyFileReader;

public class SuiteConfigurator extends PropertyFileReader {
	
	public SuiteConfigurator(String configFilePath) throws SuiteConfiguratorException {
		try {
			setConfig(configFilePath);
		} catch (Exception e) {
			throw new SuiteConfiguratorException(e.getMessage());
		}
	}
	
	public String getReportOutputPath(){
		return getProp("report.output.path");
	}
	
	protected String getReportDebugOutputPath(){
		return getProp("report.debug.output.path");
	}
	
	protected String getLoginTestsSuiteXml(){
		return getProp("suite.xml.config");
	}
	
	protected String getDebugTestSuiteXml(){
		return getProp("suite.debug.xml.config");
	}
	
	protected String getExtensionNrvFailingJobsSuite(){
		return getProp("suite.extension.nrv.failingjobs");
	}
		
	protected String getExtensionMobsDailyOrderSuite(){
		return getProp("suite.extension.mobs.dailyorder");
	}
		
	public String getMobsFirstRunTime(){
		return getProp("suite.extension.mobs.dailyorder.firstrun");
	}
	
	public String getMobsSecondRunTime(){
		return getProp("suite.extension.mobs.dailyorder.secondrun");
	}
	
	public String getNrvRunTime(){
		return getProp("suite.extension.nrv.failingjobs.executionhour");
	}

}
