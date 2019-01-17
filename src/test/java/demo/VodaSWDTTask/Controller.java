package demo.VodaSWDTTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Controller {

	WebDriver driver;
	public Controller(WebDriver d) {
		// TODO Auto-generated constructor stub
		driver = d; 
	}
	

	public WebDriver OpenChromeDriver() {
		String path =  System.getProperty("user.dir") + "\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver",path);
		driver = new ChromeDriver();
		return driver;
	}

	public WebDriver OpenFirefoxDriver() {
		String firefoxPath = System.getProperty("user.dir")+"\\resources\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", firefoxPath);
		driver = new FirefoxDriver(); 
		return driver;
	}

	public WebDriver OpenExplorerDriver() {
		String ieDriverPath = System.getProperty("user.dir")+"\\Resources\\IEDriverServer.exe" ;
		System.setProperty("webdriver.ie.driver", ieDriverPath);
		driver = new InternetExplorerDriver(); 
		return driver;
	}
	
	public WebDriver OpenChromeDriverOnUbuntuLinux() throws MalformedURLException {
		new DesiredCapabilities();
		URL serverurl = new URL("http://localhost:9515");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		driver = new RemoteWebDriver(serverurl,capabilities);
		return driver;
	}

	public void waitForLoading(int longTime) {
		driver.manage().timeouts().implicitlyWait(longTime, TimeUnit.SECONDS);
	}

	public void loginYahoo(String _Mail, String _Password) {
		WebElement mail = driver.findElement(By.id("login-username"));
		WebElement nextBtn = driver.findElement(By.id("login-signin"));
		mail.sendKeys(_Mail);
		nextBtn.click();

		waitForLoading(15);

		WebElement password = driver.findElement(By.id("login-passwd"));
		WebElement signInBtn = driver.findElement(By.id("login-signin"));
		password.sendKeys(_Password);
		signInBtn.click();
	}
}
