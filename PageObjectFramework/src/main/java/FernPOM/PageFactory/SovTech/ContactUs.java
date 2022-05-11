package FernPOM.PageFactory.SovTech;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import FernPOM.utilities.Utility;




public class ContactUs {



/**



* All WebElements are identified by @FindBy annotation

*/



WebDriver driver;
Utility util;

@FindBy(xpath="//input[contains(@id, 'your_name')]")
WebElement NameField;

@FindBy(xpath="//label[contains(@data-reactid, '$your_name.3.$0.0')]")
WebElement NameFieldError;

@FindBy(xpath="//*[starts-with(@id,'email-')]")
WebElement EmailField;

@FindBy(xpath="//label[contains(@data-reactid, 'email.3.$0.0')]")
WebElement EmailFieldError;



@FindBy(xpath="//*[starts-with(@id,'mobilephone-')]")
WebElement mobilephoneField;

@FindBy(xpath="//select[contains(@name, 'numemployees')]")
WebElement companySizeNumField;

@FindBy(xpath="//label[contains(@data-reactid, '$numemployees.3.$0.0')]")
WebElement companySizeNumFieldError;

@FindBy(xpath="//select[contains(@id, 'what_kind_of_problem_is')]")
WebElement what_kind_of_problemField;

@FindBy(xpath="//*[starts-with(@id,'message-')]")
WebElement messageField;

@FindBy(xpath="//*[starts-with(@id,'LEGAL_CONSENT')]")
WebElement legalConsentButton;

@FindBy(xpath="//label[contains(@data-reactid, '.hbspt-forms-0.4.0.0.0')]")
WebElement AllFieldsError;

@FindBy(xpath="/html/body/div[1]/div/div[2]/div/div/div/div/div/div/section[1]/div/div/div/div/div/div[1]/div/div/h2")
WebElement successContactMessage;

@FindBy(xpath="//input[contains(@class, 'hs-button')]")
WebElement submitButton;
public ContactUs(WebDriver driver, Utility util){



this.driver = driver;
this.util = util;
//This initElements method will create all WebElements



PageFactory.initElements(driver, this);
}



//Set name in Field



public void setName(String strName) throws IOException{

Utility.sendKeys(NameField, driver, strName, this.util);
}



//Set email in email Field



public void setEmail(String workEmail) throws IOException{



Utility.sendKeys(EmailField, driver, workEmail, this.util);
}

//Set contact number in contact Field



public void setContact(String contactNumber) throws IOException{



Utility.sendKeys(mobilephoneField, driver, contactNumber, this.util);

}

//Set message



public void setMessage(String message) throws IOException{



Utility.sendKeys(messageField, driver, message, this.util);
}

//Set company size in company size Field



public void setCompanySize(String companySize) throws IOException{



Utility.selectDropdown(companySize,companySizeNumField, driver, this.util);

}
//Set service looking for



public void setServiceLookingFor(String service) throws IOException{



Utility.selectDropdown(service,what_kind_of_problemField, driver, this.util);
}



//Click on Legal consent button



public void clickLegalconsentButton() throws IOException{

Utility.clickElement(legalConsentButton, this.driver, this.util);



}

//Click on Legal consent button



public void clicksubmitButton(){

Utility.clickElement(submitButton, this.driver, this.util);



}




/**



* This POM method will be exposed in test case to login in the application



* @param strUserName



* @param strPasword



* @return
* @throws InterruptedException
* @throws IOException



*/



public void contactUs_Negative_EmptyFields(String strName,String strEmail,String strContactNum,String strCompanySize,String strService, String strMessage) throws InterruptedException, IOException{
driver.switchTo().frame(0);
//Fill name
this.setName(strName);





//Fill Email
this.setEmail(strEmail);




//Fill contact number
this.setContact(strContactNum);

//Fill contact number
this.setCompanySize(strCompanySize);

//Fill service
this.setServiceLookingFor(strService);

//Fill service
this.setMessage(strMessage);





//Click Submit button
this.clicksubmitButton();
Utility.checkMessage(companySizeNumFieldError, driver, "Please select an option from the dropdown menu.", util, true);
Utility.checkMessage(EmailFieldError, driver, "Please complete this required field.", util, true);
Utility.checkMessage(NameFieldError, driver, "Please complete this required field.", util, true);
Utility.checkMessage(AllFieldsError, driver, "Please complete all required fields.", util, true);

}

public void contactUs_Negative_InvalidEmail(String strName,String strEmail,String strContactNum,String strCompanySize,String strService, String strMessage) throws InterruptedException, IOException{

//Fill name
this.setName(strName);





//Fill Email
this.setEmail(strEmail);




//Fill contact number
this.setContact(strContactNum);

//Fill contact number
this.setCompanySize(strCompanySize);

//Fill service
this.setServiceLookingFor(strService);

//Fill service
this.setMessage(strMessage);



//Click Legal consent Button
this.clickLegalconsentButton();


//Click Submit button
this.clicksubmitButton();

Utility.checkMessage(EmailFieldError, driver, "Email must be formatted correctly.", util, true);

}

public void contactUs_validDetails(String strName,String strEmail,String strContactNum,String strCompanySize,String strService, String strMessage) throws InterruptedException, IOException{

//Fill name
this.setName(strName);





//Fill Email
this.setEmail(strEmail);




//Fill contact number
this.setContact(strContactNum);

//Fill contact number
this.setCompanySize(strCompanySize);

//Fill service
this.setServiceLookingFor(strService);

//Fill service
this.setMessage(strMessage);


//Click Submit button
this.clicksubmitButton();
driver.switchTo().defaultContent();

if(driver.getTitle().equalsIgnoreCase("Submission Contact Us - SovTech Custom Software")) {
util.passStep("Passed: "+driver.getTitle(),true, false);
}else {
util.failStep("Failed: "+driver.getTitle(),true);
}

}
}