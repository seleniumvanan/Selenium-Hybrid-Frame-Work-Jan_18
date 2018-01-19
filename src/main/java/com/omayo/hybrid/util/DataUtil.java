package com.omayo.hybrid.util;

import java.util.Hashtable;

public class DataUtil {
	

	public static Object[][] getData(MyXLSReader xls,String testName){
		
		String testCaseName=testName;
		String sheetName="Data";
		int testStartRowNum=1;
		
		while(!xls.getCellData(sheetName, 1, testStartRowNum).equals(testCaseName)){
			
			testStartRowNum++;
			
		}
		
		System.out.println("Test starts from row - "+testStartRowNum);
		
		int colStartRowNum=testStartRowNum+1;
		int dataStartRowNum=testStartRowNum+2;
		
		//Calculate rows of the Data
		int rows=0;
		while(!xls.getCellData(sheetName, 1, dataStartRowNum+rows).equals("")){
			rows++;
		}
		
		System.out.println("Total rows of data are: "+rows);
		
		//Calculate cols of the Data
		int cols=1;
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")){
			cols++;
		}
		System.out.println("Total cols of data are: "+(cols-1));
		
		Object[][] data = new Object[rows][1];
		int dataRow=0;
		Hashtable<String,String> table=null;
		//Read the data
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++){
			table = new Hashtable<String,String>();
			for(int cNum=1;cNum<cols;cNum++){
				String key = xls.getCellData(sheetName, cNum, colStartRowNum);
				String value = xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
			}
			
			data[dataRow][0]=table;
			dataRow++;
		
		}
		
		return data;
		
	}
	
	public static boolean isRunnable(MyXLSReader xls,String testName){
		
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		
		for(int rNum=2;rNum<=rows;rNum++){
			
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, 1, rNum);
			
			if(tcid.equals(testName)){
				
				String runMode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				
				if(runMode.equals("Y"))
					return true;
				else
					return false;
			}
			
			
			
		}
		
		return false;
		
		
	}
	
	
}
