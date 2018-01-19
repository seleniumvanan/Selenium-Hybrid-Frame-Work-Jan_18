package com.omayo.hybrid.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.omayo.hybrid.Keywords;
import com.omayo.hybrid.util.Constants;
import com.omayo.hybrid.util.DataUtil;
import com.omayo.hybrid.util.ExtentManager;
import com.omayo.hybrid.util.MyXLSReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GmailLogin {
	
	ExtentReports rep = ExtentManager.getInstance();
	
	String testName = "GmailTest";
	
	MyXLSReader xls = new MyXLSReader(Constants.SUITEA_XLS);

	ExtentTest test;
	
	Keywords app = null;
	
	@Test(dataProvider="getData")
	public void doLogin(Hashtable<String,String> data) throws IOException{
		
		/*
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://www.google.com");
		//Condition to validate the presence of the object
		//Read from excel/properties file instead of hardcoding
		//Want to execute on different browsers
		driver.findElement(By.linkText("Gmail")).click();
		driver.findElement(By.id("Email")).sendKeys("seleniumbyarun99@gmail.com");
		driver.findElement(By.id("next")).click();
		*/
		
		
		test = rep.startTest(testName);
		
		if(!DataUtil.isRunnable(xls, testName) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping as the Runmode is set to N");
			throw new SkipException("Skipping as the Runmode is set to N");
		}
		
		test.log(LogStatus.INFO, "Staring Test: "+testName );
		app = new Keywords(test);
		test.log(LogStatus.INFO, "Executing Keywords" );
		app.executeKeyWords(testName, xls, data);
		//app.returnGenericKeywords().reportFailure("xxxxx");
		//test.log(LogStatus.PASS, "Test Passed");
		//app.returnGenericKeywords().takeScreenshot();
		
	}

	
	@DataProvider
	public Object[][] getData(){
		
		return DataUtil.getData(xls, testName);
		
	}
	
	@AfterTest
	public void quit(){
		
	   if(rep!=null){
		rep.endTest(test);
		rep.flush();
	   }
	   
	   if(app!=null)
		   app.returnGenericKeywords().closeBrowser();
		
	}
	

}
