package com.omayo.hybrid;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.Assert;

import com.omayo.hybrid.util.Constants;
import com.omayo.hybrid.util.MyXLSReader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {
	
	ExtentTest test;
	AppKeywords app;
	
	public Keywords(ExtentTest test){
		
		this.test = test;
		
	}
	
	public void executeKeyWords(String testName,MyXLSReader xls,Hashtable<String,String> testData) throws IOException{
		/*
		//Easy to undestand
		//Reusablem functions
		GenericKeywords app = new GenericKeywords();
		app.openBrowser("Firefox");
		app.navigate("url");
		app.click("gmailLink_linktext");
		app.input("emailLoginTextField_id", "seleniumbyarun99@gmail.com");
		app.click("nextButton_id");
		*/
		String testUnderExecution = testName;
		app = new AppKeywords(test);
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		
		for(int rNum=2;rNum<=rows;rNum++){
			
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET,Constants.TCID_COL, rNum);
			
			if(tcid.equals(testUnderExecution)){
				String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
				String object = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
				String key = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
				String data = testData.get(key);
				test.log(LogStatus.INFO, tcid+" -- "+keyword+" -- "+object+" -- "+data);
				
				String result="";
				if(keyword.equals("openBrowser")){
					result = app.openBrowser(data);	
				}else if(keyword.equals("navigate")){
					result = app.navigate(object);				
				}else if(keyword.equals("click")){
					result = app.click(object);
				}else if(keyword.equals("input")){
					result = app.input(object, data);
				}else if(keyword.equals("closeBrowser")){
					result = app.closeBrowser();
				}else if(keyword.equals("verifyLoginDetails")){
					result = app.verifyLoginDetails(testData);
				}else if(keyword.equals("verifyElementPresent")){
					result = app.verifyElementPresent(object);
				}else if(keyword.equals("verifyText")){
					result = app.verifyText(object, data);
				}else if(keyword.equals("verifyElementNotPresent")){
					result = app.verifyElementNotPresent(object);
				}
				
				if(!result.equals(Constants.PASS)){
					app.reportFailure(result);
					Assert.fail(result);
				}
					
			}
			
		}
		
	}
	
	public AppKeywords returnGenericKeywords(){
		
		return app;
		
	}

}
