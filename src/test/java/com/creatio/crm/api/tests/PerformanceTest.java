package com.creatio.crm.api.tests;

import java.io.IOException;

import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.testng.annotations.Test;

import com.creatio.crm.framework.api.commons.JmeterCommons;

public class PerformanceTest {
	
	@Test
	public void runJmeterTest() throws IOException, ConfigurationException, GenerationException {
		JmeterCommons.runJmeterScript("PerformanceTest.jmx");
		
	}

}
