package com.floowgroup.floowdrive;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.floowgroup.floowdrive.test.utils.AutomationTestService;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(OrderAnnotation.class)
public class LoginAutomationTests {

	@Autowired
	private AppiumDriver<WebElement> driver;
	
	@Autowired
	private AutomationTestService automationTestService;
	
	private WebDriverWait wait;
	
	private final int MAX_LOAD_TIME_FOR_VISIBLE_ELEMENT = 10; // seconds
	
	@Value("${username}")
	private String testUserUsername;
	
	@Value("${password}")
	private String testUserPassword;

	
	@Before
	public void initTestSuite() throws MalformedURLException { 
		

	}
	
	@Test
	@Order(1)
	public void testRegisterUserSuccess() throws InterruptedException {
		
		// Given		
		MobileElement emailAddressTextField = automationTestService.getEmailAddressTextField();
		MobileElement passwordAddressTextField = automationTestService.getPasswordAddressTextField();
		MobileElement repeatPasswordAddressTextField = automationTestService.getPasswordRepeatTextField();
		MobileElement firstNameTextField = automationTestService.getFirstNameTextField();
		MobileElement lastNameTextField = automationTestService.getLastNameTextField();
		MobileElement postcodeTextField = automationTestService.getPostcodeTextField();

		MobileElement termsOfUseButton = (MobileElement) driver.findElementByAccessibilityId("termsCheckboxButton");
		MobileElement submitButton = (MobileElement) driver.findElementByAccessibilityId("submitButton");
		
		// When
		emailAddressTextField.sendKeys("testie_" + new Date().toString() + "@gmail.com");
		passwordAddressTextField.sendKeys(testUserPassword);
		repeatPasswordAddressTextField.sendKeys(testUserPassword);
		firstNameTextField.sendKeys("Testie");
		lastNameTextField.sendKeys("McTestTest");
		postcodeTextField.sendKeys("OL5 0AG");
		termsOfUseButton.click();
		submitButton.click();
		
		// Then
		wait = new WebDriverWait(driver, MAX_LOAD_TIME_FOR_VISIBLE_ELEMENT);
		WebElement errMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Registration successful")));
		assertEquals("Registration successful", errMessage.getText());	
		automationTestService.continueToHomeScreen();	

	}
	
	@Test
	@Order(2)
	public void testLogoutUserSuccess() throws InterruptedException {
		
		// Given
		wait = new WebDriverWait(driver, MAX_LOAD_TIME_FOR_VISIBLE_ELEMENT);

		MobileElement moreButton = (MobileElement) driver.findElementByAccessibilityId("More");
		moreButton.click();
		
		MobileElement aboutListButton = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("About")));
		aboutListButton.click();
		
		MobileElement logoutButton = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutButton")));
		logoutButton.click();
		
		// When
		MobileElement okButton = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("OK")));
		okButton.click();
				
		// Then
		String titleLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("titleLabel"))).getText();
		assertEquals("Registration", titleLabel);

	}
	
	@Test
	@Order(3)
	public void testLoginUserUnSuccessful() {
		
		MobileElement alreadyRegisteredButton = (MobileElement) driver.findElementByAccessibilityId("loginButton");
		alreadyRegisteredButton.click();
		wait = new WebDriverWait(driver, MAX_LOAD_TIME_FOR_VISIBLE_ELEMENT);

		MobileElement emailAddressField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_addressTextInput")));
		MobileElement passwordField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordTextInput")));
		MobileElement submitButton = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitButton")));

		emailAddressField.sendKeys("joshuadunn175@gmail.com");
		passwordField.sendKeys("incorrectPassword");
		submitButton.click();
		
		String errMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Could not log in"))).getText();
		assertEquals("Could not log in", errMessage);		
	}
	
	@Test
	@Order(4)
	public void testLoginUserSuccess() throws InterruptedException {
		
		// Given
		automationTestService.loginUser();

		// Then - Check that the Journeys & Miles Labels are visible to prove we are now logged in and on the home screen.	
		automationTestService.checkHomeScreenPresent();
	}

	
	@After
	public void tearDown() {
		driver.quit();
	}


}
