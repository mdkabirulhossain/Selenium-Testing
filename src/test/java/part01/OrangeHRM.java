package part01;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import junit.framework.Assert;

public class OrangeHRM {
	
	public String baseUrl = "http://localhost/orangehrm-5.6/web/index.php/auth/login";
	public WebDriver driver;
	
	@BeforeTest
	public void setup() {
		
		System.out.println("Before Test case Executed"); 
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}
		@Test(priority = 2)
		public void loginTestWithValidCredential() {
			//user name
			driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Kabirul");
			//user password
			driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Md_Kabirul1234");
			//login
			driver.findElement(By.xpath("//button[@type='submit']")).submit();
			
			//verify login
			String pageTitle = driver.getTitle();
			
//			if(pageTitle.equals("OrangeHRM")) {
//				System.out.println("Login Successfully");
//			}else {
//				System.out.println("Login failed");
//			}
			
			Assert.assertEquals("OrangeHRM", pageTitle);
		}
		
		@Test(priority = 1)
		public void LoginTestWithInValidCredential() throws InterruptedException {
			//user name
			driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Kabirul");
			//user password
			driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Md_Kabirul");
			//login
			driver.findElement(By.xpath("//button[@type='submit']")).submit();
			
			//message
			String excepted_message = "Invalid credentials";
			
			String actual_message = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
			
			Assert.assertTrue(actual_message.contains(excepted_message));
			
			Thread.sleep(1500);
			
			
		}
		@Test(priority =3)
		public void addEmployee() throws InterruptedException, IOException
		{
			
		}
		
		
		public void logOut() throws InterruptedException {
			driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
			//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
			List <WebElement> elementlist = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
			for(int i=0; i<elementlist.size(); i++) {
				Thread.sleep(1000);
				System.out.println(i+":"+ elementlist.get(i).getText());
			}
			elementlist.get(3).click();
		}
		
		@AfterTest
		public void tearDown() throws InterruptedException {
			logOut();
			Thread.sleep(1000);
			driver.close();
			driver.quit();
		}
}
