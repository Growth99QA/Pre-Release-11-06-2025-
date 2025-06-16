package com.growth99.testcases;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.growth99.base.BaseClass;
import com.growth99.pages.GooglePage;
import com.growth99.pages.RumaPage;
import com.growth99.utils.Utilities;

public class RumaTestCase extends BaseClass {

	WebDriver driver;
	public JsonNode rumaJsonData;
	RumaPage rumaPage;
	GooglePage googlPage;

	//	setup-->career-->tearDown, setup-->contact-->tearDown

	RumaTestCase(){
		super();
		rumaJsonData = getJsonData("RumaTestData.json");
	}

	@BeforeClass
	public void setup() {
		driver=initilazeBrowserWithUrl("rumaUrl");
		rumaPage = new RumaPage(driver);
		googlPage = new GooglePage(driver);
	}


//	@Test(priority = 0)
	public void validateCareerSubMenu(){
		JsonNode testData=	rumaJsonData.get("validateCareerSubMenu");
		rumaPage.navigateToHomePage();
		String expectedCareerTitle = testData.get("expectedCareerTitle").asText();
		rumaPage.mouseHoverOnAboutUs();
		rumaPage.clickOnCareersSubMenu();
		String actualCareerTitle =	rumaPage.getPageTitle();
		System.out.println("Actual Career title : " +actualCareerTitle );
		Assert.assertEquals(actualCareerTitle, expectedCareerTitle, testData.get("assertValidationText").asText());
	}

	//@Test(priority = 1)
	public void submitContactForm() throws InterruptedException {
		JsonNode testData=	rumaJsonData.get("submitContactForm");
		String firstname = testData.get("firstName").asText();
		String lastname = testData.get("lastName").asText();
		String email = testData.get("email").asText();
		String mobile = Utilities.generateRandomNumber();
		rumaPage.navigateToHomePage();	
		rumaPage.submitContactForm(firstname, lastname, email, mobile);
		rumaPage.switchBackToDefaultContent();
		rumaPage.validateThankYouPageAfterSubmittingContactForm();
	}
	
//	@Test(priority = 1)
	public void submitContactFormDuplicate() throws InterruptedException {
		JsonNode testData=	rumaJsonData.get("submitContactForm");
		
		HashMap<String, String> mapObj = new HashMap<String, String>();
		mapObj.put("firstname",testData.get("firstName").asText());
		mapObj.put("lastname",testData.get("lastName").asText());
		mapObj.put("email",testData.get("email").asText());
		mapObj.put("mobile", Utilities.generateRandomNumber());
		rumaPage.navigateToHomePage();	
		rumaPage.submitContactForm(mapObj);
		rumaPage.switchBackToDefaultContent();
		rumaPage.validateThankYouPageAfterSubmittingContactForm();
	}
	
// testcases related to services	
//	@Test
	public void validateBotoxAndDysportServiceSection() {
		JsonNode testData=	rumaJsonData.get("validateBotoxAndDysportServiceSection");
		rumaPage.navigateToHomePage();	
		rumaPage.clickOnServicesMenu();
		boolean isValidImage = rumaPage.validateBotoxServiceImage();
		Assert.assertEquals(isValidImage, true,"Botox and Dysport image validation failed");
		
	}
	
	@Test
	public void validateBotoxAndDysportServiceImageClickFn() {
		JsonNode testData=	rumaJsonData.get("validateBotoxAndDysportServiceSection");
		String actualUrl = testData.get("botoxServiceUrl").asText();
		rumaPage.navigateToHomePage();	
		rumaPage.clickOnServicesMenu();
		rumaPage.clickOnBotoxServiceImage();
		rumaPage.validateUrlForBotoxServicePage(actualUrl);
		
//		Assert.assertEquals(actualStatus, true,"Botox and Dysport Url validation failed");
		//Assert.assertTrue(actualStatus, "Botox and Dysport Url validation failed");
		
		
	}
@Test(priority = 0)

	public void validateBlogMenu(){
		
		JsonNode testData=	rumaJsonData.get("validateBlogMenu");
		rumaPage.navigateToHomePage();
		String expectedBlogTitle = testData.get("expectedBlogTitle").asText();
		rumaPage.ClickOnBlog();
		String actualBlogTitle =	rumaPage.getPageTitle();
		System.out.println("Actual Blog title : " +actualBlogTitle );
		Assert.assertEquals(actualBlogTitle, expectedBlogTitle, testData.get("assertValidationText").asText());
	}
	
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}


}
