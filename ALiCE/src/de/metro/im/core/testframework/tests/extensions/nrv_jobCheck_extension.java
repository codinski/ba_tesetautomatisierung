package de.metro.im.core.testframework.tests.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;

public class nrv_jobCheck_extension extends BaseTestConfig {
	
	public nrv_jobCheck_extension() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());			
	}
	
	@Test(description="Clicks on 'AdministrationTab' and navigates to 'Jobs' page.", priority=0, dependsOnGroups="nrvlogin", groups="nrvjobcheck")
	public void startAdminMode(){
		assertThat(
				pp
				.nrv_Landing
				.startAdministration()
				.checkIfJobsFailing())
				.isEmpty();
	}
	
}
