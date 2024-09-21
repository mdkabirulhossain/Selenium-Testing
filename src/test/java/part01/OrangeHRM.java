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
		public void loginTestWithValidCredential() throws InterruptedException {
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
			
			logOut();
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
			logIn();
			//find PIM Menu and click on PIM Menu
			driver.findElement(By.xpath("//span[text()='PIM']")).click();

			//find Add employee and click on Add Employee option
			driver.findElement(By.xpath("//a[text()='Add Employee']")).click();

			//enter first name
			driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Md.");

			//enter last name
			driver.findElement(By.xpath(" //input[@placeholder='Last Name']")).sendKeys("Joy");
			
			//click save button
			driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
			
			Thread.sleep(5000);
			// Verify if the employee is successfully added by checking the employee list personal details
			String confirmationMessage = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();


			if (confirmationMessage.contains("Personal Details")) {
				System.out.println("Employee added successfully!");
			} else {
				System.out.println("Failed to add employee!");
			}
			
			logOut();
			Assert.assertEquals("Personal Details", confirmationMessage);
		}
		
		@Test(priority=4)
		public void searchEmployeeNyName() throws InterruptedException
		{
			logIn();

			//find PIM Menu and click on PIM Menu
			driver.findElement(By.xpath("//span[text()='PIM']")).click();

			//Select Employee List
			driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();

			driver.findElements(By.tagName("input")).get(1).sendKeys("Kabirul");

			//Click the search button.
			driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

			//    //span[@class='oxd-text oxd-text--span']
			Thread.sleep(5000)	;
			List<WebElement> element=	driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

			String expected_message = "Record Found";
			String message_actual = element.get(0).getText();
			System.out.println(message_actual);

			logOut();

			Assert.assertTrue(message_actual.contains(expected_message));


		}
		
		public void logIn()
		{
			//user name
			driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Kabirul");
			//user password
			driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Md_Kabirul1234");
			//login
			driver.findElement(By.xpath("//button[@type='submit']")).submit();

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
//			logOut();
			Thread.sleep(1000);
			driver.close();
			driver.quit();
		}
}
