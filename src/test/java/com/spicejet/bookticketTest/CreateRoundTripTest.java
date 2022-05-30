package com.spicejet.bookticketTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateRoundTripTest {
	public static void main(String []args) throws IOException, InterruptedException {
		FileInputStream fis=new FileInputStream("./src/test/resources/SpiceJetCommonData.properties");
		Properties property=new Properties();
		property.load(fis);
		String timeOut=property.getProperty("timeOut");
		long longTimeOut=Long.parseLong(timeOut);
		
		FileInputStream fis1=new FileInputStream("./src/test/resources/SpiceJetTestData.xlsx");
		Workbook book=WorkbookFactory.create(fis1);
		String fromPlace=book.getSheet("SpiceJet").getRow(2).getCell(4).getStringCellValue();
		String toPlace=book.getSheet("SpiceJet").getRow(2).getCell(5).getStringCellValue();
		System.out.println(fromPlace);
		System.out.println(toPlace);
		book.close();
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions opt=new ChromeOptions();
		opt.addArguments("--disable-notifications");
		WebDriver driver=new ChromeDriver(opt);
		driver.get("https://www.spicejet.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(longTimeOut, TimeUnit.SECONDS);
		driver.findElement(By.xpath("(//*[name()='svg' and @data-testid='svg-img'])[10]")).click();
		WebElement from = driver.findElement(By.xpath("//div[text()='From']/following-sibling::div/input"));
		from.sendKeys(fromPlace);
		Thread.sleep(2000);
		WebElement to = driver.findElement(By.xpath("//div[text()='To']/following-sibling::div/input"));
		to.sendKeys(toPlace);
		
		driver.findElement(By.xpath("//div[text()='Fri, 20 May 2022']")).click();
		driver.findElement(By.xpath("")).click();
		
	}
}
