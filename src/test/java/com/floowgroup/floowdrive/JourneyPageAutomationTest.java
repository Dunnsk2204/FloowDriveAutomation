package com.floowgroup.floowdrive;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JourneyPageAutomationTest {
	
	@Autowired
	private AppiumDriver<WebElement> driver;
	
	@Autowired
	private AutomationTestService automationTestService;

	@Before
	public void initTestSuite() throws MalformedURLException { }
	
	/**
	 * This test will gather all data elements on the home page and then mock an API call (hardcoded values) and ensure they match
	 * @throws InterruptedException
	 */
	@Test
	public void testJourneysLabelMatchesNumberOfJourneys() throws InterruptedException {
		if (automationTestService.isLoggedIn()) {
			this.checkJourneysLabelMatchesNumberOfJourneysInList();	
		} else {
			automationTestService.loginUser();
			this.checkJourneysLabelMatchesNumberOfJourneysInList();
		}
	}

	private void checkJourneysLabelMatchesNumberOfJourneysInList() {
		// Gather initial data on page load	
		String totalJourneys = automationTestService.getNumberOfJourneys().getText();
		MobileElement journeysTabButton = automationTestService.getJourneysTabButton();
		journeysTabButton.click();
		
		driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
		
		// Get number of cells in list
		int numJourneysInList = driver.findElements(By.className("XCUIElementTypeCell")).size();
		
		// Assert journeys label on home screen matches the number of items in the journey view list
		assertEquals(totalJourneys, String.valueOf(numJourneysInList));
	}
}
