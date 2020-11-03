package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class base {

	public WebDriver driver;
	public Properties prop;

	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\{USER}\\new\\eclipse-workspace\\Practical\\MakeMyTrip\\src\\main\\java\\resources\\data.properties");//set your path

		prop.load(fis);
		String browserName = prop.getProperty("browser");
		System.out.println(browserName);
		// For ChromeDriver

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D://chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=C:/Users/{USER}/AppData/Local/Google/Chrome/User Data");//set your path
			options.addArguments("--start-maximized");
			driver = new ChromeDriver(options);

		} // For FirefoxDriver
		else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D://geckodriver.exe");
			driver = new FirefoxDriver();

		} // For IEDriver
		else if (browserName.equals("IE")) {
			System.setProperty("webdriver.ie.driver", "D://IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.manage().window().maximize();

		return driver;

	}

}
