package FernPOM.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.InternetExplorerDriverManager;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
public class WebDriverFactory {
    /* Browsers constants */
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "ie";
    public static final String SAFARI = "safari";

    private WebDriverFactory(){}

    /*
     * Factory method to return a WebDriver instance given the browser to hit
     *
     * @param browser : Browser representing the local browser to hit
     *
     * @return WebDriver instance
     */
    public static WebDriver getInstance(String browserName) {

        WebDriver webDriver = null;
        
        if (CHROME.equals(browserName)) {
	    	WebDriverManager.chromedriver().setup();
	    	DesiredCapabilities dc = new DesiredCapabilities();
	    	dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            webDriver = new ChromeDriver(dc);
            
        }
        else if (FIREFOX.equals(browserName)) {
	    	WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();

        }
        else if (INTERNET_EXPLORER.equals(browserName)) {
	    	WebDriverManager.iedriver().setup();
            webDriver = new InternetExplorerDriver();
        }
        else {
            throw new IllegalArgumentException(" Unsupported browser!");
        }

        return webDriver;
    }
}
