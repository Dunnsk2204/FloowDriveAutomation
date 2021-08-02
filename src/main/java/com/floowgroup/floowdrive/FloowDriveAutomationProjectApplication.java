package com.floowgroup.floowdrive;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import com.floowgroup.floowdrive.test.utils.AutomationTestService;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

@PropertySource("classpath:application.properties")
@SpringBootApplication
public class FloowDriveAutomationProjectApplication {
	
	@Value("${platform.name}")
	private String platformName;
	
	@Value("${platform.version}")
	private String platformVersion;
	
	@Value("${device.name}")
	private String deviceName;
	
	@Value("${automation.name}")
	private String automationName;
	
	@Value("${udid}")
	private String udid;
	
	@Value("${bundle.Id}")
	private String bundleId;
	
	@Value("${appium.url}")
	private String appiumUrl;
	

	public static void main(String[] args) {
		SpringApplication.run(FloowDriveAutomationProjectApplication.class, args);
	}

	@Bean
	public AppiumDriver<WebElement> webDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("automationName", automationName);
		capabilities.setCapability("udid", udid);
		capabilities.setCapability("bundleId", bundleId);

		URL url = new URL(appiumUrl);
		
		return new IOSDriver<WebElement>(url, capabilities);
	}
	
	@Bean
	public AutomationTestService buildAutomationTestUtils() {
		return new AutomationTestService();
		
	}

}
