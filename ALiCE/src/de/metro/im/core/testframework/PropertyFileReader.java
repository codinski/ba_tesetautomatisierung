package de.metro.im.core.testframework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This methods acts as an interface to external configuration/property files for for the webdriver as well as for test data. 
 * Requested properties are checked eagerly: If a property can not be found (returns null) error will be printed and logged will be thrown. 
 * This is behaviour is chose because it makes debugging easier in case of missing or faulty test-data due to early error detection.. 
 * @author Christoph Zabniski
 */
public class PropertyFileReader {
	private static Logger log = LogManager.getLogger();
	private Properties properties = new Properties();
	
	public PropertyFileReader(String configFilePath){
		setConfig(configFilePath);
	}
	
	public PropertyFileReader(){
	}
	
	protected void setConfig(String configFilePath) {
		
		try (InputStreamReader propertiesInputStream = new InputStreamReader(new FileInputStream(configFilePath), "UTF-8")) {
			properties.load(propertiesInputStream);
		} catch (FileNotFoundException fnf) {
			log.fatal("Cant find the configuration file. Please check your path: " + configFilePath + " " + fnf.getMessage());
		} catch (IOException e) {
			log.fatal("Problem with configuration file. Please check: " + configFilePath + " " + e.getMessage());
		}
	}
	
	protected Properties getProperties(){
		return this.properties;
	}
		
	public String getProp(String property){
		String value = properties.getProperty(property);
		if(value == null || value.isEmpty()) {
			log.fatal("The property you tried to read ("+property+") does NOT EXIST! -> " + value);
		}
		return properties.getProperty(property);
	}
	
	public String getPropOr(String key, String altDefault){
		return (String) properties.getOrDefault(key, altDefault);
	}

}
