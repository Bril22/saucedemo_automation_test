package saucedemo_automation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class CheckoutAllProducts {
	ExtentReports extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("report_all_products.html");
	WebDriver driver = new ChromeDriver();
	
	@Test
	  public void launchBrowser() throws InterruptedException {
		 ExtentTest launch = extent.createTest("Launch Browser");
		 driver.get("https://www.saucedemo.com/");
		  
		 launch.log(Status.PASS, "User Launched Browser");
		 String open_website =  driver.getCurrentUrl();
		  
		 if (open_website.equals("https://www.saucedemo.com/")) {
			System.out.println("User Successfully Launched Browser");
			launch.pass("User Successfully Launched Browser");
		 } else {
			System.out.println("User Failed Launched Browser");
			launch.fail("User Failed Launched Browser");
		 }
	}

	@Test
	public void login() throws InterruptedException {
		ExtentTest login = extent.createTest("User Login");
		
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		login.log(Status.PASS, "User input username");
		
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		login.log(Status.PASS, "User input username");
		
		driver.findElement(By.id("login-button")).click();
		try {
			driver.findElement(By.id("inventory_container"));
			login.pass("User Successfully Login");
		}
		catch(NoSuchElementException e) {
			login.fail("User Failed Login");
		}		

		ExtentTest allprod = extent.createTest("User Add All Products");
		
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
		driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
		driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
		driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
		driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
		if (driver.findElement(By.className("shopping_cart_badge")).isDisplayed()) {
			allprod.pass("User Successfully Add products");
		} else {
			allprod.fail("User Failed Add products");
		}
		
		driver.findElement(By.id("shopping_cart_container")).click();
		try {
			driver.findElement(By.xpath("//*[text()='Sauce Labs Backpack']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Bike Light']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Bolt T-Shirt']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Fleece Jacket']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Onesie']"));
			driver.findElement(By.xpath("//*[text()='Test.allTheThings() T-Shirt (Red)']"));
			allprod.pass("Cart page is displayed");
		} catch(NoSuchElementException e) {
			allprod.fail("Cart page isn't displayed");
		}
		
		driver.findElement(By.id("checkout")).click();
		try {
			driver.findElement(By.xpath("//*[text()='Checkout: Your Information']"));
			allprod.pass("Checkout page is displayed");
		} catch(NoSuchElementException e) {
			allprod.fail("Checkout page isn't displayed");
		}
		
		driver.findElement(By.id("first-name")).sendKeys("Bril");
		allprod.log(Status.PASS, "User input first name");
		driver.findElement(By.id("last-name")).sendKeys("Natan");
		allprod.log(Status.PASS, "User input last name");
		driver.findElement(By.id("postal-code")).sendKeys("1234");
		allprod.log(Status.PASS, "User input postal code");
		
		driver.findElement(By.id("continue")).click();
		try {
			driver.findElement(By.xpath("//*[text()='Checkout: Overview']"));
			allprod.pass("User successfully fill the checkout data");
		} catch(NoSuchElementException e) {
			allprod.fail("User failed fill the checkout data");
		}
		
		try {
			driver.findElement(By.xpath("//*[text()='Sauce Labs Backpack']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Backpack']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Bike Light']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Bolt T-Shirt']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Fleece Jacket']"));
			driver.findElement(By.xpath("//*[text()='Sauce Labs Onesie']"));
			driver.findElement(By.xpath("//*[text()='Test.allTheThings() T-Shirt (Red)']"));
			allprod.pass("The items is loaded correctly");
		} catch(NoSuchElementException e) {
			allprod.fail("The items is loaded incorrectly");
		}
		
		driver.findElement(By.id("finish")).click();
		try {
			driver.findElement(By.xpath("//*[text()='Checkout: Complete!']"));
			allprod.pass("The items was successfully purchased");
		} catch(NoSuchElementException e) {
			allprod.fail("The items was failed purchased");
		}
	}
	
	@BeforeTest
	  public void beforeTest() {
		  extent.attachReporter(spark);
	  }

	 @AfterTest
	  public void afterTest() {
		  extent.flush();
		  driver.quit();
	  }

}

