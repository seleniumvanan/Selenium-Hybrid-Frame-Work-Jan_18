package com.omayo.hybrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.omayo.hybrid.util.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenericKeywords {
	
	public WebDriver driver = null;
	public Properties prop = null;
	public WebElement e = null;
	ExtentTest test;
	
	public GenericKeywords(ExtentTest test){
		
		this.test = test;
		
		
		prop = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String openBrowser(String browserType){
		
		test.log(LogStatus.INFO, "Opening Browser "+browserType);
	
		if(browserType.equalsIgnoreCase("Firefox")){
			
			driver = new FirefoxDriver();
		   
		}else if(browserType.equalsIgnoreCase("Chrome")){
			
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
		}else if(browserType.equalsIgnoreCase("ie")){
			
			System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		return Constants.PASS;
		
	}
	
	public String navigate(String urlKey){
		test.log(LogStatus.INFO,"Navigating to "+prop.getProperty(urlKey));
		driver.get(prop.getProperty(urlKey));
		return Constants.PASS;
	}
	
	public String click(String locatorKey){
		
		test.log(LogStatus.INFO,"Clicking on "+prop.getProperty(locatorKey));
		getElement(locatorKey).click();
		return Constants.PASS;
	}
	
	public String input(String locatorKey,String data){
		
		test.log(LogStatus.INFO, "Typing in "+prop.getProperty(locatorKey));
		getElement(locatorKey).sendKeys(data);
		return Constants.PASS;
	}
	
	public String closeBrowser(){
		test.log(LogStatus.INFO,"Closing Browser");
		driver.quit();
		return Constants.PASS;
	}
	
	/*****************Validation Keywords**************/
    
	public String verifyText(String locatorKey, String expectedText){
		String actualText = getElement(locatorKey).getText();
		if(actualText.equals(expectedText))
			return Constants.PASS;
		else
			return Constants.FAIL+" - "+actualText+" and "+expectedText+" didnt match";
	}	
	
	public String verifyElementPresent(String locatorKey){
		
		boolean result = isElementPresent(locatorKey);
		
		if(result)
			return Constants.PASS;
		else
			return Constants.FAIL+" - Could not find element "+locatorKey;
	}
	
	public String verifyElementNotPresent(String locatorKey){
		
		boolean result = isElementPresent(locatorKey);
		
		if(result)
			return Constants.FAIL+" - Able to find element "+locatorKey;
		else
			return Constants.PASS;
		
	}
	
	
	/*****************Utility Functions**************/
	public WebElement getElement(String locatorKey){
		
		try{
		
			if(locatorKey.endsWith("id")){
				
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
				
			}else if(locatorKey.endsWith("name")){
				
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
				
			}else if(locatorKey.endsWith("classname")){
				
				e = driver.findElement(By.className(prop.getProperty(locatorKey)));
				
			}else if(locatorKey.endsWith("linktext")){
				
				e = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
				
			}else if(locatorKey.endsWith("xpath")){
				
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				
			}else if(locatorKey.endsWith("cssselector")){
				
				e = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
				
			}else{
				
				Assert.fail("The provided Locator - "+locatorKey+" is not found");
			}
		
		}catch(Exception ex){
			
			reportFailure("Failure in Element extration - "+locatorKey);
			Assert.fail("Failure in element extraction: "+locatorKey);
			
		}
		
		return e;
		
	}
	
	public boolean isElementPresent(String locatorKey){
		List<WebElement> e = null;
		if(locatorKey.endsWith("id")){
			
			e = driver.findElements(By.id(prop.getProperty(locatorKey)));
			
		}else if(locatorKey.endsWith("name")){
			
			e = driver.findElements(By.name(prop.getProperty(locatorKey)));
			
		}else if(locatorKey.endsWith("classname")){
			
			e = driver.findElements(By.className(prop.getProperty(locatorKey)));
			
		}else if(locatorKey.endsWith("linktext")){
			
			e = driver.findElements(By.linkText(prop.getProperty(locatorKey)));
			
		}else if(locatorKey.endsWith("xpath")){
			
			e = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
			
		}else if(locatorKey.endsWith("cssselector")){
			
			e = driver.findElements(By.cssSelector(prop.getProperty(locatorKey)));
			
		}else{
			
			Assert.fail("The provided Locator - "+locatorKey+" is not found");
		}
		
		if(e.size()==0)
			return false;
		else
			return true;
	}
	
	/*****************Reporting Functions**************/
	
	public void reportPass(){
		
		
	}
	
	public void reportFailure(String failureMessage){
		
		takeScreenshot();
		test.log(LogStatus.FAIL, failureMessage);
		
	}
	
	public void takeScreenshot(){
		
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//put screenshot file in reports
		test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		
	}
}
