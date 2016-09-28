package de.metro.im.core.testframework.config.reporting;

import de.metro.im.core.testframework.PropertyFileReader;

public class ReportUtilConfigurator extends PropertyFileReader {
	public ReportUtilConfigurator(String configFilePath) {
		super(configFilePath);
	}

	public int getReportMaxAge() {
		return Integer.parseInt(getPropOr("report.maxage", "0")); 
	}

	public String getReportOutputPath(){
		return getProp("report.output.path");
	}

	public boolean doDBReporting() {
		return Boolean.parseBoolean(getProp("reporter.database.doReport"));
	}
	
	

}
