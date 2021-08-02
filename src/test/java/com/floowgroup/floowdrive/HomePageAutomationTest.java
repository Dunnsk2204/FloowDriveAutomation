package com.floowgroup.floowdrive;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.floowgroup.floowdrive.model.CustomerMockedApiData;
import com.floowgroup.floowdrive.model.CustomerScoreData;
import com.floowgroup.floowdrive.test.utils.AutomationTestService;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(OrderAnnotation.class)
public class HomePageAutomationTest {
	
	@Autowired
	private AppiumDriver<WebElement> driver;
	
	@Autowired
	private AutomationTestService automationTestService;
	
	
	@Before
	public void initTestSuite() throws MalformedURLException { 
		

	}
	
	@Test
	/**
	 * This test will gather all data elements on the home page and then mock an API call (hardcoded values) and ensure they match
	 * @throws InterruptedException
	 */
	public void testUserDataDisplaysOnHomeScreen() throws InterruptedException {
			
		// Gather initial data on page load		
		String totalJourneys = automationTestService.getNumberOfJourneys().getText();
		String totalMiles = automationTestService.getTotalMilesTravelled().getText();
		String userScore = automationTestService.getScoreDialValue().getText();
		MobileElement nextDialButton = automationTestService.getNextDialButton();
		
		// Click through slides to get data displayed in app.
		moveToNextSlide(nextDialButton);
		String smoothDriving = automationTestService.getScoreDialValue().getText();
		
		moveToNextSlide(nextDialButton);
		String mobileUse = automationTestService.getScoreDialValue().getText();

		moveToNextSlide(nextDialButton);
		String speed = automationTestService.getScoreDialValue().getText();
		
		moveToNextSlide(nextDialButton);
		String fatigue = automationTestService.getScoreDialValue().getText();
		
		moveToNextSlide(nextDialButton);
		String timeOfDay = automationTestService.getScoreDialValue().getText();
		
		// Build a customerScoreData obj to hold data for mocked API Comparison 
		CustomerScoreData customerScoreData = new CustomerScoreData(userScore, smoothDriving, mobileUse, speed, fatigue, timeOfDay, totalJourneys, totalMiles);

		// Get (mocked) API data - I would generally be using real data from an API call to get data for a particular logged in user via a customerId etc.
		CustomerMockedApiData mockedApiData = automationTestService.getMockedApiCustomerData();
		
		// Assertions
		assertEquals(customerScoreData.getFatigueScore(), mockedApiData.getFatigueScore());
		assertEquals(customerScoreData.getMobileUseScore(), mockedApiData.getMobileUseScore());
		assertEquals(customerScoreData.getSmoothDrivingScore(), mockedApiData.getSmoothDrivingScore());
		assertEquals(customerScoreData.getSpeedScore(), mockedApiData.getSpeedScore());
		assertEquals(customerScoreData.getTimeOfDayScore(), mockedApiData.getTimeOfDayScore());
		assertEquals(customerScoreData.getTotalJourneys(), mockedApiData.getTotalJourneys());
		assertEquals(customerScoreData.getTotalMiles(), mockedApiData.getTotalMiles());
		assertEquals(customerScoreData.getTotalScore(), mockedApiData.getTotalScore());
	}

	private void moveToNextSlide(MobileElement nextDialButton) {
		nextDialButton.click();
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.MILLISECONDS);
	}

}
