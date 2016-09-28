package de.metro.im.core.testframework.config.reporting.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;


public class DBReporter {
	private static Logger log = LogManager.getLogger();
	
	private final String driver = "oracle.jdbc.driver.OracleDriver";
	private final String user = "";
	private final String pw = "";
	private final String port = "";
	private final String sid = "";
	private final String ip = "";
	private final String url = "";
	
	private Connection conn = null;
	private PreparedStatement prpstmt = null;
	
	
	public DBReporter() {}
	
	private Connection getConnection() throws Exception {
		    String _driver = driver;
		    String _url = url; 
		    String _username = user;
		    String _password = pw;
		    Class.forName(_driver);
		    return DriverManager.getConnection(_url, _username, _password);
	 }
	 
	public void reportToDatabase(ITestContext context, String userComment, int logstatus) throws Exception {
			int rowsAffected = 0;
			String testIDstr = context.getCurrentXmlTest().getParameter("_id");
			int testID = Integer.parseInt(testIDstr);
			
			String testDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).format(new Date());
			
			if(logstatus==2) logstatus=0; //the DB know a fauliure as 0 but testng passes a 2 so this is remapped
			if (userComment.length() > 399) {userComment = userComment.substring(0, 399);}
			
			String sqlInsert = "INSERT INTO ALICE_RESULTS VALUES (?, ?, ?, ?)";
			//unidentified testcases wont be reported
			if (testID != 0) {
				try {
					open();
					//Reihenfolge: Datum, ID, Status, Kommentar
					this.prpstmt = conn.prepareStatement(sqlInsert);
					this.prpstmt.setInt(3, testID); // test ID
					this.prpstmt.setString(1, testDate); // date format dd.mm.yyyy hh:mm:ss
					this.prpstmt.setInt(2, logstatus); // succces = 1 / failure = 0
					this.prpstmt.setString(4, userComment); // date format dd.mm.yyyy hh:mm:ss

					rowsAffected = prpstmt.executeUpdate();

					log.info(context.getName() + ": ");
					log.info(rowsAffected + " Row(s) updated to database." + " (Test ID: " + testID + ")");

				} catch (SQLException e) {
//					e.printStackTrace();
					log.catching(e);
				} finally {
					shutdownAndCommit();
				}
			}
	}
	 
	 private void open() throws Exception{
		 try {
			conn = getConnection();
		} catch (SQLException e) {
//			e.printStackTrace();
			log.error("Error while opening SQL connection: " + e.getMessage());
			throw e;
		}
	 }
	 
	 private void shutdownAndCommit() throws SQLException{
		 try {
			conn.commit();	
			conn.close();
		} catch (SQLException e) {
			log.error("Error while commiting to database. Results probably not commmited: " + e.getMessage());
			throw e;
		}
	 }
	 
	 @SuppressWarnings("unused")
	private void shutdown(){
		 try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.catching(e);
		}
	 }

}
