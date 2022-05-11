package FernPOM.utilities;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.openqa.selenium.OutputType.FILE;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utility {
	private ExtentTest extentTest;
	public static XSSFWorkbook wk ;
	public static XSSFSheet sheet;
	static String path;
	static String dirPath = System.getProperty("user.dir");

	
	public static void clickElement(WebElement element, WebDriver driver, Utility util) {
		
		try {
			
			// wait for element and the perform the click
			waitForVisibility(element, driver);
			
			if (element.isDisplayed()) {
				String txt = element.getText();
				element.click();
				
				
				util.passStep("Clicked "+txt,true, false);
				Thread.sleep(3000);
				
			}
			
		} catch (Exception e) {

		}
	}
	
	public static void selectDropdown(String text,WebElement element, WebDriver driver, Utility util) throws IOException {
		
		try {
			
			// wait for element and the perform the click
			waitForVisibility(element, driver);
			
			if (element.isDisplayed() && !text.isEmpty()) {
				
				Select option = new Select(element);
			
				option.selectByVisibleText(text);
				
				
				util.passStep("Selected "+text,true, false);
				Thread.sleep(3000);
				
			}else {
				util.passStep("Nothing selected, Empty option passed ",true, false);
			}
			
		} catch (Exception e) {
			util.failStep("Failed to select option "+text+"\n Reason: "+e, false); 
		}
	}
	
	public static void sendKeys(WebElement element, WebDriver driver, String keys, Utility util) throws IOException {
		try {
			// wait for element and the perform the click
			waitForVisibility(element, driver);
			if (element.isDisplayed() && !keys.isEmpty()) {
				element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			
				element.sendKeys(keys);
				Thread.sleep(3000);
				util.passStep(keys+" Entered",true,false);
				
			}else {
				util.passStep("Nothing entered, Empty keys passed ",true, false);
			}
		} catch (Exception e) {
			util.failStep("Failed to enter "+keys+"\n Reason: "+e, false);
		}
	}
	
	public static void checkMessage(WebElement element, WebDriver driver, String expected, Utility util, boolean errorIsExpected) throws IOException {
		try {
			// wait for element and the perform the click
			waitForVisibility(element, driver);
			if (element.isDisplayed() && !expected.isEmpty() && expected.equalsIgnoreCase(element.getText()) && errorIsExpected) {	
				
				util.passStep("Expected: "+expected+" \n Actual: "+element.getText(),true,false);
				
			}else if(errorIsExpected && !element.isDisplayed()){
				util.failStep("No Error displayed",true);
			}else if(!errorIsExpected && !element.isDisplayed()){
				util.failStep("No Error displayed",true);
			}else {
				util.failStep("Step failed",true);
			}
		} catch (Exception e) {
			util.failStep("Failed : "+e, false);
		}
	}
	
	private static void waitForVisibility(WebElement element, WebDriver driver) throws Error {
		new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
	}
	
	private static ExtentReports extent;

    public ExtentReports getInstance(){
        return extent;
    }
    
    public void createInstance(String fileName){
    	
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setDocumentTitle("Extent Report");
        htmlReporter.config().setReportName("Tlou Moshi Demo");
        
        htmlReporter.viewConfigurer().viewOrder().as(
                new ViewName[] {
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                }).apply();

        
        htmlReporter.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss a");
       
        extent = new ExtentReports();
        extent.setSystemInfo("Created By", "Tlou");
        extent.setSystemInfo("Autmation Type", "Website Automation");
        extent.attachReporter(htmlReporter);
        
       
    }
    
    public ExtentTest createTest(String scenario) {
    	
    	return extent.createTest(scenario);
    	
    }
    
    public void setTest(ExtentTest test) {
    	this.extentTest = test;
    }
    
    public ExtentTest getTest() {
    	return extentTest;
    }
    
    public void createNode(ExtentTest test, String scenario) {
    	setTest(test.createNode(scenario));
    	
    }
    
    public void passStep(String message, boolean attachScreenshot, boolean Arlet) throws IOException {
    	
    	try {
    		if(Arlet) {
    			
    			if(attachScreenshot) {
    	    		String scrnsht = screenshotArlet();
    	    		getTest().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(scrnsht).build());
    	    	}else {
    	    		getTest().pass(message);
    	    	}
    		}else {
    	    	if(attachScreenshot) {
    	    		String scrnsht = takeScreenshot(Browser.Driver());
    	    		getTest().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(scrnsht).build());
    	    	}else {
    	    		getTest().pass(message);
    	    	}
    		}
			

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

    	

    }
    
    public void failStep(String message, boolean attachScreenshot) throws IOException {
    	try {
			
	    	if(attachScreenshot) {
	    		String scrnsht = takeScreenshot(Browser.Driver());
	    		getTest().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(scrnsht).build());
	    	}else {
	    		getTest().fail(message);
	    	}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}


    }
    
    public void infoStep(String message, boolean attachScreenshot) throws IOException {
    	try {
			
	    	if(attachScreenshot) {
	    		String scrnsht = takeScreenshot(Browser.Driver());
	    		
	    		getTest().info(message, MediaEntityBuilder.createScreenCaptureFromPath(scrnsht).build());
	    	}else {
	    		getTest().info(message);
	    	}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}


    }

    public void  createReport(){
        System.out.println("Initialize Extent report was called");
        if(getInstance() == null){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
            String formattedDate = dateFormat.format(date);
            createInstance("reports/" + "Extent_Report_Demo_" + formattedDate + ".html");
        }
    }
    
    public String takeScreenshot(WebDriver driver) throws IOException {
    	
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        File filetest = Paths.get(".").toAbsolutePath().normalize().toFile();
        String imgDir = filetest +"\\screenshots\\screenshot_"+ timestamp()+"_screenshot.png";
        ImageIO.write(img, "png", new File(imgDir));
        
        return imgDir;
    }
    
    
    public static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()).replace("-", "_").replace(" ", "_");
    }
    
	public int columnCount(Sheet sheet) throws Exception {
		return sheet.getRow(0).getLastCellNum();
	}

	public String getCellData(String excel, String sheetName, String strColumn, int rowNum) throws Exception {
		path = dirPath+"\\testData\\"+excel+".xlsx";
        
		FileInputStream fis = new FileInputStream(path);
		 wk = new XSSFWorkbook(fis);
		 sheet = wk.getSheet(sheetName);
		 
		String sValue = null;
		Row row = sheet.getRow(0);
		for (int i = 0; i < columnCount(sheet); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(strColumn)) {
				Row raw = sheet.getRow(1);
				Cell cell = raw.getCell(i);
				DataFormatter formater = new DataFormatter();
				sValue = formater.formatCellValue(cell);
				break;
			}
		}
		return sValue;
	}
	
	static public String handlePopUp(WebDriver driver, Utility util, WebElement element) throws InterruptedException, IOException {
		

		String alertText = "";
		try {
			waitForVisibility(element, driver);
			
			if (element.isDisplayed()) {
				
				element.click();
				
				Thread.sleep(3000);
				util.passStep("Clicked Signup button",true, true);
				Alert alert = driver.switchTo().alert();
				
		        alertText = alert.getText();
		        System.out.println("Alert data: " + alertText);
		        alert.accept();
		        Thread.sleep(3000);
		        util.passStep("Accepted Arlet with message: "+alertText,true, true);
		        
			}
		} catch (UnhandledAlertException f) {
		    try {
		        Alert alert = driver.switchTo().alert();
		        alertText = alert.getText();
		        System.out.println("Alert data: " + alertText);
		        alert.accept();
		    } catch (NoAlertPresentException e) {
		        e.printStackTrace();
		    }
		}
		return alertText;
	}
	
	static public String screenshotArlet() throws HeadlessException, AWTException, IOException {
		String img = dirPath+"\\screenshots\\screenshot_"+ timestamp()+"_screenshot.jpg";
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	    ImageIO.write(image, "jpg", new File(img));
	    
	    return img;
	 
	}


}
