package de.metro.im.core.applications.pages.nrv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Nrv_Admin extends Nrv_Landing {
	
	private AdministrationTab administrationTab = null;
		
	public Nrv_Admin(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
		PageFactory.initElements(this.driver, this);
		this.administrationTab = new AdministrationTab(driver);	

	}
	
	public List<String> checkIfJobsFailing(){
		return administrationTab.Jobs().failingJobsList();
	}
	
	
	public class AdministrationTab extends Nrv_Landing  {

		@FindBy(linkText="AdministrationTab beenden")
		private WebElement endAdmin;
		@FindBy(linkText="Jobs")
		private WebElement jobs;
		
		
		
		public AdministrationTab(WebDriver driver) {
			super(driver);
			PageFactory.initElements(this.driver, this);
			
			
		}
		
		public Nrv_Landing endAdministration(){
			endAdmin.click();
			return new Nrv_Landing(this.driver);
		}
		
		public JobOptions Jobs(){
			jobs.click();
			return new JobOptions(this.driver);
		}
		
		
		
		public class JobOptions extends AdministrationTab{
			
			private List<WebElement> jobList = new ArrayList<WebElement>();
			private List<String> waitingJobsList = new ArrayList<String>();
			private List<String> failingJobsList = new ArrayList<String>();
			@SuppressWarnings("unused")
			private Map<String, String> jobStatus = new HashMap<String, String>();
			
			@FindAll({@FindBy(className="displaytag-row-odd"), 
			@FindBy(className="displaytag-row-even")}) 
			private List<WebElement> jobs;
			
			public JobOptions(WebDriver driver) {
				super(driver);
				PageFactory.initElements(this.driver, this);
				
				createJobList();
				
			}
			
			private void createJobList() {
				for (WebElement job : jobs){
					jobList.add(job);
				}
				
				System.out.println("There are " + jobs.size() + " jobs available.");
			}
			
			public List<String> failingJobsList(){
				String jobStr = "";
				for (WebElement job : jobList){
					jobStr = job.getText();
					if (jobStr.contains("FAILING")){
						System.out.println("Job Name: " + job.findElement(By.xpath(".//td[2]")).getText());
						System.out.println("Job Beschreibung: " + job.findElement(By.xpath(".//td[3]")).getText());
						failingJobsList.add(jobStr + "\n");
					}
					
				}
				System.out.println(failingJobsList.size() + " failing job(s) found.");
				return failingJobsList;
			}
			
			public List<String> waitingJobsList(){
				String jobStr = "";
				for (WebElement job : jobList){
					jobStr = job.getText();
					if (jobStr.contains("WAITING")){
						System.out.println("Job Name: " + job.findElement(By.xpath(".//td[2]")).getText());
						System.out.println("Job Beschreibung: " + job.findElement(By.xpath(".//td[3]")).getText());
						waitingJobsList.add(jobStr + "\n");
					}
				}
				System.out.println(waitingJobsList.size() + " waiting job(s) found.");
				return waitingJobsList;
			}
			
			public List<WebElement> getAllJobs(){
				return jobList;
			}
			
			public WebElement getJobByName(String jobname){
				for (WebElement job : getAllJobs()){
					if (job.findElement(By.xpath("./td[2]")).getText().contains(jobname)){
						return job;
						
					}
				}
				return null;
			}
			
			
			}
		}
		


}
