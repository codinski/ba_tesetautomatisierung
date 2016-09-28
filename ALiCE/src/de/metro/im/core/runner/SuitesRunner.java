package de.metro.im.core.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.metro.im.core.applications.webdriver.DriverHelper;
import de.metro.im.core.testframework.config.reporting.ReportingUtility;
import de.metro.im.core.util.Time;

public class SuitesRunner {
//	http://logging.apache.org/log4j/2.x/manual/configuration.html
	
private static Logger log = LogManager.getRootLogger();

	public static void main(String[] args) {
		log.trace("ALiCE started. ");
		try {
			TestSuites suites = new TestSuites();
			//TODO IDEE: die entscheidung welche test wirklich laufen sollen in die tesetsuite klasse verlagern
			
			suites.setUpAliveChecks(); 
			
//			if (Time.isBetweenExactTime(suites.config.getNrvRunTime() + ":00:00", suites.config.getNrvRunTime() + ":05:00")) {
//				suites.addTestExtension_NRV_FailingJobs();
//			}
//			if (Time.isBetweenExactTime(suites.config.getMobsFirstRunTime() + ":00:00", suites.config.getMobsFirstRunTime() + ":05:00") || 
//				Time.isBetweenExactTime(suites.config.getMobsSecondRunTime() + ":00:00", suites.config.getMobsSecondRunTime() + ":05:00")) {
//				suites.addTestExtension_MOBS_DailyOrder();
//			}
				
			log.trace("ALiCE finished correctly. ");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.catching(ex);
			log.trace("ALiCE finished with programm errors. ");
			DriverHelper.removeFirefoxDriverLeftovers(); //placed here in case a major exception occurs before the driver cleanup can run
			log.fatal("ALICE: Error during startup. Trying to report to admin. ", ex.toString());
			ReportingUtility.notifyAdmin("ALICE: Error during startup. Check log file. ", ex.toString());
		}
	}
}

