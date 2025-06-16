package com.growth99.pages;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.growth99.utils.Utilities;
import com.growth99.utils.WebUtils;

public class ViewMoreButtonPage {

	WebDriver driver;
	WebUtils webUtils;

	public ViewMoreButtonPage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
		webUtils =  new WebUtils(driver);
	}


	public void validateViewmoreLearnmorebutton() {

		List<String> urls = Arrays.asList(
			    "https://thornwood-livaestheticsmedspa.gogroth.com/",
			    "https://thornwood-livaestheticsmedspa.gogroth.com/thornwood/services/",
			    "https://thornwood-livaestheticsmedspa.gogroth.com/thornwood/services/"
			);
			webUtils.checkMoreButtonsOnPages(driver, urls);
			webUtils.captureScreenshot("viewmore button");
			
	}

}
