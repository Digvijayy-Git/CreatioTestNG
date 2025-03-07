package com.creatio.crm.framework.api.commons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JmeterCommons {

	public static void runJmeterScript(String jmxFile) throws IOException, ConfigurationException, GenerationException {

		//1. setup the jmeter home path
		String jmeterHome = "src/test/resources/jmeter";
		
		//2.setup the path for storing test results
		String resultsFilePath = Paths.get(jmeterHome, "results","TestResults.csv").toString();
		
		//3 Get the jmx filepath
		String testPlanPath = Paths.get(jmeterHome, jmxFile).toString();
		
		//4.Get property filepath
		String propertyFilePath = Paths.get(jmeterHome, "bin","jmeter.properties").toString();
		
		//5.Set Jmeter home directory
		JMeterUtils.setJMeterHome(jmeterHome);
		
		//6.Load all jmeter properties and configurations
		JMeterUtils.loadJMeterProperties(propertyFilePath);
		
		//7.Load jmeter scrip file
		File testPlanFile = new File(testPlanPath);
		HashTree testPlanTree = SaveService.loadTree(testPlanFile);
		
		//8.collect and save all test results coming from jmx file execution
		ResultCollector resultsCollector = new ResultCollector();
		resultsCollector.setFilename(resultsFilePath);
		
		//9 Run the Jmeter Script
		StandardJMeterEngine Jmeter = new StandardJMeterEngine();
		testPlanTree.add(testPlanTree.getArray(),resultsCollector);
		Jmeter.configure(testPlanTree);
		Jmeter.run();
		
		//10Genereate  HTML report for performance test results
		ReportGenerator report = new ReportGenerator(resultsFilePath,resultsCollector);
		report.generate();
	}

}
	    