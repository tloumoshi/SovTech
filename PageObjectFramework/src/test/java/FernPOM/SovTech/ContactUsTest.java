package FernPOM.SovTech;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import FernPOM.PageFactory.SovTech.ContactUs;
import FernPOM.utilities.Browser;
import FernPOM.utilities.Utility;
import FernPOM.utilities.WebDriverFactory;

public class ContactUsTest {
private WebDriver driver;
	
	ExtentTest parentTest;

	ContactUs contact;
    private Utility util = new Utility();
   
    
    @BeforeTest
	public void setup() throws Exception{
    	
     	//obj.getCellData("BlazeDemo","Registration","TestID",0);
    	Browser.initialize(WebDriverFactory.CHROME);
        Browser.goTo("https://www.sovtech.co.za/contact-us/");
        driver = Browser.Driver();
        
        String className = this.getClass().getName();
        util.createReport();
        parentTest = util.createTest(className);
        
	        
	        
	}
    
    @Test
    public void test_contactUS_feature() throws IOException, InterruptedException{
    	
        //Create contact-us Page object
    	contact = new ContactUs(driver, util);
	    util.createNode(parentTest,"Negative test: Contact us Empty Required Fields");
	    
	    contact.contactUs_Negative_EmptyFields("", "", "", "", "", "");
	    
	    util.createNode(parentTest,"Negative test: Contact us Invalid Email");
	    
	    contact.contactUs_Negative_InvalidEmail("test", "test@gmail", "0753443343", "5-25", "Augment current engineering staff", "test message");
	    
	    util.createNode(parentTest,"Positve test: Valid details");
	    
	    contact.contactUs_validDetails("test", "test@sovtech.co.za", "0753443343", "5-25", "Augment current engineering staff", "test message");
	    
    }
    
    @AfterTest
    public void AterTest() {
    	
    	util.getInstance().flush();
    	Browser.close();
    }
}
