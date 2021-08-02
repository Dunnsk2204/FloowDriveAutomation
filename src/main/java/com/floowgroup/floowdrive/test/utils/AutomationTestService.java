package com.floowgroup.floowdrive.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.floowgroup.floowdrive.model.CustomerMockedApiData;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.functions.ExpectedCondition;


public class AutomationTestService {
	
	@Autowired
	private AppiumDriver<WebElement> driver;
	
	@Value("${total.score}")
	private String totalScore;
	
	@Value("${smooth.driving.score}")
	private String smoothDrivingScore;
	
	@Value("${mobile.use}")
	private String mobileUse;
	
	@Value("${speed}")
	private String speedScore;
	
	@Value("${fatigue}")
	private String fatigueScore;
	
	@Value("${time.of.day}")
	private String timeOfDayScore;
	
	@Value("${journeys}")
	private String totalJourneys;
	
	@Value("${miles}")
	private String miles;
	
	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	private WebDriverWait wait;
	
	private static Logger LOGGER = LoggerFactory.getLogger(AutomationTestService.class);

	
	/**
	 * 
	 * Logs user in
	 */
	public void loginUser() throws InterruptedException {
		
		try {
			MobileElement alreadyRegisteredButton = (MobileElement) driver.findElementByAccessibilityId("loginButton");
			alreadyRegisteredButton.click();
			
			MobileElement emailAddressField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_addressTextInput")));
			MobileElement passwordField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordTextInput")));
			MobileElement submitButton = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitButton")));
			
			// When
			emailAddressField.sendKeys(username);
			passwordField.sendKeys(password);
			submitButton.click();		
			
			MobileElement journeyCountLabel = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("journeyCountLabel")));
			MobileElement totalMiles = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalDistanceLabel")));
			
			if (journeyCountLabel.isDisplayed() && totalMiles.isDisplayed()) {
				LOGGER.info("Logged in successfully with user " + username);

			}
					
		} catch (ElementNotVisibleException e) {
			LOGGER.info("There has been a problem with the login");	
		}
		
		
		
	}
	
	/**
	 * Login helper methods.
	 * @return
	 */
	
	public MobileElement getEmailAddressTextField() {
		MobileElement emailAddressTextField = (MobileElement) driver.findElementByAccessibilityId("email_addressTextInput");
		return emailAddressTextField;
	}
	
	public MobileElement getPasswordAddressTextField() {
		MobileElement passwordAddressTextField = (MobileElement) driver.findElementByAccessibilityId("passwordTextInput");
		return passwordAddressTextField;
	}
	
	public MobileElement getPasswordRepeatTextField() {
		MobileElement repeatPasswordAddressTextField = (MobileElement) driver.findElementByAccessibilityId("confirm_passwordTextInput");
		return repeatPasswordAddressTextField;
	}
	
	public MobileElement getFirstNameTextField() {
		MobileElement firstNameTextField = (MobileElement) driver.findElementByAccessibilityId("first_nameTextInput");
		return firstNameTextField;
	}
	
	public MobileElement getLastNameTextField() {
		MobileElement lastNameTextField = (MobileElement) driver.findElementByAccessibilityId("last_nameTextInput");
		return lastNameTextField;
	}
	
	public MobileElement getPostcodeTextField() {
		MobileElement postcodeTextField = (MobileElement) driver.findElementByAccessibilityId("zip_codeTextInput");
		return postcodeTextField;
	}
	
	// Functions to ensure new registered user goes to the Home View.	
	public MobileElement getChevronRightButtonElement() {
		MobileElement chevronRightButton = (MobileElement) driver.findElementByAccessibilityId("welcome chevron right");
		return chevronRightButton;
	}
	
	public MobileElement getGetStartedButtonELement() {
		MobileElement getStartedButton = (MobileElement) driver.findElementByName("Get Started");
		return getStartedButton;
	}
	
	/**
	 * Ensure that we continue to the home screen after a new user registers successfully.
	 * @throws InterruptedException 
	 */
	public void continueToHomeScreen() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		MobileElement chevronRightButton = this.getChevronRightButtonElement();
		chevronRightButton.click();
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		MobileElement getStartedButton = this.getGetStartedButtonELement();
		getStartedButton.click();
		this.checkHomeScreenPresent();
	}
	
	public void checkHomeScreenPresent() throws InterruptedException {
		Thread.sleep(1500);
		String journeyCountCaptionLabel = driver.findElementByAccessibilityId("journeyCountCaptionLabel").getText();
		String totalDistanceCaptionLabel = driver.findElementByAccessibilityId("totalDistanceCaptionLabel").getText();

		
		// Then - Check that the Journeys & Miles Labels are visible to prove we are now logged in and on the home screen.	
		assertEquals("Journeys", journeyCountCaptionLabel);
		assertEquals("miles", totalDistanceCaptionLabel);
	}
	
	public MobileElement getNumberOfJourneys() {
		MobileElement journeyCountLabel = (MobileElement) driver.findElementByAccessibilityId("journeyCountLabel");
		return journeyCountLabel;
	}
	
	public MobileElement getTotalMilesTravelled() {
		MobileElement totalMiles = (MobileElement) driver.findElementByAccessibilityId("totalDistanceLabel");
		return totalMiles;
	}
	
	public MobileElement getScoreDialValue() {
		MobileElement dialValue = (MobileElement) driver.findElementByAccessibilityId("dialValueLabel");
		return dialValue;
	}
	
	public MobileElement getNextDialButton() {
		MobileElement nextDialButton = (MobileElement) driver.findElementByAccessibilityId("nextDialButton");
		return nextDialButton;
	}
	
	public MobileElement getJourneysTabButton() {
		MobileElement journeysButton = (MobileElement) driver.findElementByAccessibilityId("Journeys");
		return journeysButton;
	}
	
	public MobileElement getMoreButton() {
		MobileElement moreButton = (MobileElement) driver.findElementByAccessibilityId("More");
		return moreButton;
	}
	
	
	
	/**
	 * Find multiple elements on a page at once
	 *  
	 */
	public static void waitTillAllVisible(WebDriverWait wait, By locator) {

	    wait.until(new ExpectedCondition<Boolean>() {

	        @Override
	        public Boolean apply(WebDriver driver) {

	            Iterator<WebElement> eleIterator = driver.findElements(locator).iterator();
	            while (eleIterator.hasNext()) {
	                boolean displayed = false;
	                try {
	                    displayed = eleIterator.next().isDisplayed();
	                }
	                catch (NoSuchElementException | StaleElementReferenceException e) {
	                    // 'No such element' or 'Stale' means element is not available on the page
	                    displayed = false;
	                }
	                if (displayed) {
	                    // return false even if one of them is displayed.
	                    return false;
	                }
	            }
	            // this means all are not displayed/invisible
	            return true;
	        }
	    });
	}
	
	public CustomerMockedApiData getMockedApiCustomerData() {
		CustomerMockedApiData mockedAPiData = new CustomerMockedApiData();
		mockedAPiData.setFatigueScore(fatigueScore);
		mockedAPiData.setMobileUseScore(mobileUse);
		mockedAPiData.setSmoothDrivingScore(smoothDrivingScore);
		mockedAPiData.setSpeedScore(speedScore);
		mockedAPiData.setTimeOfDayScore(timeOfDayScore);
		mockedAPiData.setTotalJourneys(totalJourneys);
		mockedAPiData.setTotalMiles(miles);
		mockedAPiData.setTotalScore(totalScore);
		return mockedAPiData;
	}
	
	/**
	 * Check logged in - Simple check to see if the user is logged in by checking if 
	 * the 'More' button is present on the lower pane of the app
	 */
	public boolean isLoggedIn() {
		MobileElement moreButton = this.getMoreButton();
		return moreButton.isDisplayed();
	}

}
