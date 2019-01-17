package demo.VodaSWDTTask;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Task {

	// operatingSystem if Window write "W" else if Linux Ubuntu write "U"
	String operatingSystem = "U";
	// chose one of them "Chrome"  "Firefox" "Explorer" , then type it in String
	String BrowserDrive = "Chrome";

	WebDriver driver;
	Controller OurMethods = new Controller(driver);
	// Mails info
	String firstMail = "Write your First Mail ";
	String firstPassword = "password ";

	String secondMail = "Write your Second Mail "; 
	String secondPassword = "Password"; 



	@BeforeTest
	public void openURL() throws MalformedURLException
	{
		
		if (operatingSystem == "U") {
			// For Linux 
			driver = OurMethods.OpenChromeDriverOnUbuntuLinux();
		}
		else if (operatingSystem == "W") {
			// for Windows
			if (BrowserDrive == "Chrome") {
				// for Chrome
				driver = OurMethods.OpenChromeDriver();
			}
			else if (BrowserDrive == "Firefox") {
				// for Firefox
				driver = OurMethods.OpenFirefoxDriver();
			}
			else if (BrowserDrive == "Explorer") {
				// for Explorer
				driver = OurMethods.OpenExplorerDriver();
			}
		}

		// Navigate to URL
		driver.navigate().to("https://mail.yahoo.com");
	}

	@Test(priority = 1)
	public void loginFirstMail() 
	{
		OurMethods.loginYahoo(firstMail, firstPassword);
	}

	@Test(priority = 2)
	public void sendMail() throws AWTException 
	{
		OurMethods.waitForLoading(15);

		WebElement composeInBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/nav/div/div[1]/a"));
		composeInBtn.click();

		WebElement msgToMailInpt = driver.findElement(By.xpath("//*[@id=\"message-to-field\"]"));
		WebElement msgSubjectInpt = driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[1]/div[3]/div/div/input"));
		WebElement msgBodyInpt = driver.findElement(By.xpath("//*[@id=\"editor-container\"]/div[1]"));

		msgToMailInpt.sendKeys(secondMail);
		msgSubjectInpt.sendKeys("Voda-SWDT" + new Random().nextInt(1000));
		//msgSubjectInpt.startsWith("Voda_SWDT")
		msgBodyInpt.sendKeys("Hello World");

		// Start of Upload File
		String fileName = "file.txt";
		String filePath;
		if (operatingSystem == "U") {
			filePath = System.getProperty("user.dir")+"/uploads/"+fileName;
		} else {
			filePath = System.getProperty("user.dir")+"\\uploads\\"+fileName;
		}

		WebElement attachBtn = driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[2]/div[2]/div/span[1]/div/input"));
		attachBtn.click();	

		//  using robot class to upload
		Robot robot = new Robot();
		// CTRL + C 
		StringSelection selection = new StringSelection(filePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, null);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(2000);
		// CTRL + V 
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(15000);
		// End Of File Upload

		// Send Message
		WebElement sendBtn = driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[2]/div[2]/div/button"));
		sendBtn.click();
		OurMethods.waitForLoading(10);


	}



	@Test(priority = 3)
	public void logOut() 
	{
		WebElement accountMenuBtn = driver.findElement(By.xpath("//*[@id=\"ybar\"]/div[3]/div[1]"));
		WebElement logOutBtn = driver.findElement(By.xpath("//*[@id=\"ybarAccountMenuBody\"]/a[3]"));

		accountMenuBtn.click();
		logOutBtn.click();

	}

	@Test(priority = 4)
	public void loginSecondMail() 
	{
		WebElement loginBtn = driver.findElement(By.id("uh-signin"));
		loginBtn.click();

		WebElement addAccountBtn = driver.findElement(By.xpath("//*[@id=\"manage-account\"]/a"));
		addAccountBtn.click();


		OurMethods.loginYahoo(secondMail, secondPassword);
		// navigate to mail from home page
		OurMethods.waitForLoading(5);
		WebElement mailBtn = driver.findElement(By.id("mega-bottombar-mail"));
		mailBtn.click();
	}

	@Test(priority = 5)
	public void receivedMail() 
	{
		//	List<WebElement> mails = driver.findElements(By.xpath("//*[@id=\"mail-app-component\"]/div[1]/div/div[2]/div/div[2]/div/div[2]/div[2]/ul[1]"));
		List<WebElement> mails = driver.findElements(By.tagName("a"));

		boolean checker = false;
		for (WebElement mail : mails) {
			if(mail.getText().contains("Voda-SWDT")){
				checker = true;
				System.out.println("Yes we have got mail form " + firstMail);
				mail.click();
				break;
			}
		}
		// Download File
		if (checker){
			WebElement fileDownloadBtn = driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div[2]/div[2]/div[2]/ul/li/div/div/div[1]/div[2]/div/ul/li/div/div[2]/div/a"));
			fileDownloadBtn.click();
		}else {
			System.out.println("No mail form " + firstMail);
		}


	}

	@AfterTest
	public void tearDown() 
	{
		OurMethods.waitForLoading(20000);
		driver.quit();
	}




}
