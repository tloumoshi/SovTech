package FernPOM.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class Browser {

	    private static String baseUrl = "";
	    private static String BrowserName = "";
	    private static String BrowserVersion = "";
	    private static WebDriver webDriver;

	    public static void initialize(String browser)
	    {
	        webDriver = WebDriverFactory.getInstance(browser);
	        webDriver.manage().window().maximize();
	        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	     
	    }

	    public static String getTitle()
	    {
	        return webDriver.getTitle();
	    }

	    public static WebDriver Driver()
	    {
	        return webDriver;
	    }

	    public static void goTo(String url)
	    {
	        webDriver.get(baseUrl + url);
	    }

	    public static void close()
	    {
	        webDriver.close();
	    }
}
