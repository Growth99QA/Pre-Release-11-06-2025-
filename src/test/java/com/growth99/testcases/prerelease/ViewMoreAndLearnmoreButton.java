package com.growth99.testcases.prerelease;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.growth99.base.BaseClass;
import com.growth99.pages.ChatBotLogoValidationPage;
import com.growth99.pages.GooglePage;
import com.growth99.pages.RumaPage;
import com.growth99.pages.ViewMoreButtonPage;
import com.growth99.utils.Utilities;

public class ViewMoreAndLearnmoreButton extends BaseClass {

	WebDriver driver;
	public JsonNode PreReleaseDataJsonData;
	ViewMoreButtonPage validateViewmoreButtonpage;


	ViewMoreAndLearnmoreButton(){
		super();
		PreReleaseDataJsonData = getJsonData("PreReleaseData.json");
	}

	@BeforeClass
	public void setup() {
		driver=initilazeBrowserWithUrl("thornwood-livaestheticsmedspaUrl");
		validateViewmoreButtonpage = new ViewMoreButtonPage(driver);

	}


	@Test(priority = 0)
	public void validateViewmoreLearnmorebutton(){
		JsonNode testData=	PreReleaseDataJsonData.get("validateViewmoreLearnmorebutton");
		validateViewmoreButtonpage.validateViewmoreLearnmorebutton();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}


}
