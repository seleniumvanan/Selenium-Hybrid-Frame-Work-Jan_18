package com.omayo.hybrid;

import java.util.Hashtable;
import com.omayo.hybrid.util.*;

import com.relevantcodes.extentreports.ExtentTest;

public class AppKeywords extends GenericKeywords {

	public AppKeywords(ExtentTest test) {
		super(test);
	}
	
	public String verifyLoginDetails(Hashtable<String,String> testData){
		
		String expectedName = testData.get("Name");
		
		String expectedUsername = testData.get("Username");
		
		//Write code for actual values here and compare
		
		return Constants.PASS;
		
	}
	

}
