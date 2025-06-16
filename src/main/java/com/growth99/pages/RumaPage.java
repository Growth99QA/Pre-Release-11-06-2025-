package com.growth99.pages;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.growth99.utils.WebUtils;

public class RumaPage {

	WebDriver driver;
	WebUtils webUtils;

	public RumaPage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
		webUtils = new WebUtils(driver);
	}

	@FindBy (xpath="//span[contains(text(),'About')]")
	WebElement aboutUsMenu;

	@FindBy (xpath="//span[text()='Careers']")
	WebElement careersSubMenu;

	@FindBy (xpath="//h1")
	WebElement pageTitle;

	@FindBy (xpath="//header//img[@title='RUMA Aesthetics']")
	WebElement rumaLogo;

	@FindBy (xpath="//span[@class='menu-item-text']//span[contains(text(),'Contact')]")
	WebElement contactMenu;

	@FindBy (xpath="//iframe[@title='Contact Form']")
	WebElement contactFormIframe;

	@FindBy (xpath="//input[@name='First Name']")
	WebElement firstName;

	@FindBy (xpath="//input[@name='Last Name']")
	WebElement lastName;

	@FindBy (xpath="//input[@name='Email']")
	WebElement email;

	@FindBy (xpath="//input[@name='Phone Number']")
	WebElement phoneNumber;

	@FindBy (xpath="//input[@value='Facebook']")
	WebElement faceBookCheckBox;

	@FindBy (xpath="//input[@value='Botox/Dysport']")
	WebElement botoxCheckBox;

	@FindBy (xpath="//input[@name='I Consent']")
	WebElement consentCheckbox;

	@FindBy (xpath="//button[@type='submit']")
	WebElement submitButton;

	//webelements related to services
	@FindBy (xpath="//nav[@class='navigation']//span[contains(text(),'Services')]")
	WebElement serviesMenu;

	@FindBy (xpath="//img[contains(@data-dt-location,'attachment/1-22/')]")
	WebElement botoxServiceImage;

	@FindBy (xpath="//nav[@class='navigation']//span[contains(text(),'Blog')]")

		WebElement BlogMenu;

	public void ClickOnBlog() {
			webUtils.clickOnElement(BlogMenu);
		}
	public void switchBackToDefaultContent()
	{
		webUtils.switchToDefaultContent();
	}

	public void clickOnSubmitButton() {
		webUtils.clickOnElement(submitButton);
	}

	public void clickOnFaceBookCheckBox() {
		webUtils.clickOnElement(faceBookCheckBox);
	}

	public void clickOnBotoxCheckBox() {
		webUtils.clickOnElement(botoxCheckBox);
	}

	public void clickOnConsentCheckbox() {
		webUtils.clickOnElement(consentCheckbox);
	}

	public void enterPhone(String phone) {
		webUtils.enterText(phoneNumber, phone);
	}

	public void enterEmail(String emailAddress) {
		webUtils.enterText(email, emailAddress);
	}

	public void enterLastName(String lname) {
		webUtils.enterText(lastName, lname);
	}

	public void enterFirstName(String fname) {
		webUtils.enterText(firstName, fname);
	}

	public void switchToContactFormIframe() {
		driver.switchTo().frame(contactFormIframe);
	}

	public void clickOnContactMenu() {
		webUtils.clickOnElement(contactMenu);

	}

	public void clickOnAboutUsMenu() {
		webUtils.clickOnElement(aboutUsMenu);
	}

	public void mouseHoverOnAboutUs() {
		Actions myActions = new Actions(driver);
		myActions.moveToElement(aboutUsMenu).build().perform();
	}

	public void clickOnCareersSubMenu() {
		webUtils.clickOnElement(careersSubMenu);
	}

	public String getPageTitle() {
		String actualPageTitle = pageTitle.getText();
		return actualPageTitle;
	}

	public void navigateToHomePage() {
		webUtils.clickOnElement(rumaLogo);
		
	}

	public void validateThankYouPageAfterSubmittingContactForm() {

	}

	public void submitContactForm(String firstname, String lastname, String email, String mobile) {
		clickOnContactMenu();
		switchToContactFormIframe();
		enterFirstName(firstname);
		enterLastName(lastname);
		enterEmail(email);
		enterPhone(mobile);
		clickOnFaceBookCheckBox();
		clickOnBotoxCheckBox();
		clickOnConsentCheckbox();
		clickOnSubmitButton();
	}

	public void submitContactForm(HashMap<String, String> data) {
		clickOnContactMenu();
		switchToContactFormIframe();
		enterFirstName(data.get("firstname"));
		enterLastName(data.get("lastname"));
		enterEmail(data.get("email"));
		enterPhone(data.get("mobile"));
		clickOnFaceBookCheckBox();
		clickOnBotoxCheckBox();
		clickOnConsentCheckbox();
		clickOnSubmitButton();
	}

	// methods related to service page
	public void clickOnServicesMenu() {
		webUtils.clickOnElement(serviesMenu);
	}

	public void clickOnBotoxServiceImage() {
		webUtils.clickOnElement(botoxServiceImage);
	}

	public boolean validateUrlForBotoxServicePage(String expectedUrl)
	{
		boolean status = webUtils.validateCurrentURL(expectedUrl);
		return status;
	}

	public boolean validateBotoxServiceImage() {
		boolean isValid = webUtils.isImageValid(botoxServiceImage, "Botox service Image");
		return isValid;
	}

}
