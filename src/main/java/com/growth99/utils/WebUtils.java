package com.growth99.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebUtils {

	private WebDriver driver;
	private WebDriverWait wait;
	private Logger logger = Logger.getLogger(WebUtils.class.getName());

	public WebUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	private ExtentTest getExtentTest() {
		return ExtentTestManager.getTest();
	}

	private String getElementLocator(WebElement element) {
		String desc = element.toString();
		return desc.substring(desc.indexOf("->") + 2, desc.length() - 1).trim();
	}

	private void logSuccess(String message) {
		logger.log(Level.INFO, message);
		getExtentTest().log(Status.INFO, message);
	}

	private void logFailure(String error) {
		logger.log(Level.SEVERE, error);
		getExtentTest().log(Status.FAIL, error);
	}

	public void clickOnElement(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			logSuccess("Clicked on element: " + getElementLocator(element));
		} catch (Exception e) {
			logFailure("Failed to click on element: " + e.getMessage());
		}
	}

	public void enterText(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(text);
			logSuccess("Entered text '" + text + "' into element: " + getElementLocator(element));
		} catch (Exception e) {
			logFailure("Failed to enter text: " + e.getMessage());
		}
	}

	public void clearAndEnterText(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(text);
			logSuccess("Cleared and entered text '" + text + "' on element: "+getElementLocator(element));
		} catch (Exception e) {
			logFailure("Failed to enter text: " + e.getMessage());
		}
	}

	public String getText(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			String text = element.getText();
			logSuccess("Got text '" + text + "' from element: " +getElementLocator(element));

			return text;
		} catch (Exception e) {
			logFailure("Exception while getting text from element: " +e.getMessage());
			return null;
		}
	}

	public String getAttribute(WebElement element, String attribute) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			String attrVal = element.getDomAttribute(attribute);
			logSuccess("Got attribute '" + attribute + "' value '" + attrVal + "' from element: " +getElementLocator (element));
			return attrVal;
		} catch (Exception e) {
			logFailure("Exception while getting attribute '" + attribute + "' from element: " +e.getMessage());
			return null;
		}
	}

	public void hoverOverElement(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			wait.until(ExpectedConditions.visibilityOf(element));
			actions.moveToElement(element).perform();
			logSuccess( "Hovered over element: " +getElementLocator( element));
		} catch (Exception e) {
			//e.getMessage());
			logFailure("Exception while hovering over element: " +e.getMessage());

		}
	}

	public void doubleClick(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			actions.doubleClick(element).perform();
			logSuccess( "Double clicked element: "  +getElementLocator( element));

		} catch (Exception e) {
			logFailure("Exception while double clicking element: " +e.getMessage());
		}
	}

	public void rightClick(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			actions.contextClick(element).perform();
			logSuccess( "Right clicked element: "  +getElementLocator( element));
		} catch (Exception e) {
			logFailure("Exception while right clicking element: " +e.getMessage());
		}
	}

	// Dropdown / Select

	public void selectByVisibleText(WebElement element, String visibleText) {
		try {
			Select select = new Select(element);
			select.selectByVisibleText(visibleText);
			logSuccess("Selected dropdown option by visible text: " + visibleText +getElementLocator( element));
		} catch (Exception e) {
			logFailure("Exception whileselecting dropdown by visible text:  "+ visibleText +e.getMessage());

		}
	}

	public void selectByValue(WebElement element, String value) {
		try {
			Select select = new Select(element);
			select.selectByValue(value);
			logSuccess("Selected dropdown option by value: " + value +getElementLocator( element));

		} catch (Exception e) {
			logFailure("Exception while selecting dropdown by value: " + value  +e.getMessage()); 
		}
	}

	public void selectByIndex(WebElement element, int index) {
		try {
			Select select = new Select(element);
			select.selectByIndex(index);
			logSuccess("Selected dropdown option by value: " + index +getElementLocator( element));
		} catch (Exception e) {
			logFailure("Exception while selecting dropdown by index: " + index  +e.getMessage()); 
		}
	}

	public String getSelectedOption(WebElement element) {
		try {
			Select select = new Select(element);
			WebElement selected = select.getFirstSelectedOption();
			String selectedText = selected.getText();
			logSuccess("Got selected dropdown option: " + selectedText +getElementLocator( element));
			return selectedText;
		} catch (Exception e) {
			logFailure("Exception while getting selected dropdown option: "  +e.getMessage()); 
			return null;
		}
	}

	public List<String> getAllDropdownOptions(WebElement element) {
		List<String> optionsText = new ArrayList<>();
		try {
			Select select = new Select(element);
			List<WebElement> options = select.getOptions();
			for (WebElement option : options) {
				optionsText.add(option.getText());
			}
			logSuccess("Got all dropdown options ");
		} catch (Exception e) {
			logFailure("Exception while getting all dropdown options"  +e.getMessage()); 
		}
		return optionsText;
	}

	// Checkbox / Radio Buttons

	public void selectCheckbox(WebElement element) {
		try {
			if (!element.isSelected()) {
				element.click();
				logSuccess("Selected checkbox: "  +getElementLocator( element));
			}
		} catch (Exception e) {
			logFailure( "Exception while selecting checkbox: " +e.getMessage()); 
		}
	}

	public void deselectCheckbox(WebElement element) {
		try {
			if (element.isSelected()) {
				element.click();
				logSuccess("Deselected checkbox: "  +getElementLocator( element));
			}
		} catch (Exception e) {
			logFailure( "Exception while deselecting  checkbox: " +e.getMessage()); 
		}
	}

	public boolean isCheckboxSelected(WebElement element) {
		try {
			boolean selected = element.isSelected();
			logSuccess("Checkbox selected status: " + selected);
			return selected;
		} catch (Exception e) {
			logFailure( "Exception while checking checkbox status:  " +e.getMessage());
			return false;
		}
	}

	// Alert Handling

	public void acceptAlert() {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
			logSuccess( "Alert accepted" );
		} catch (Exception e) {
			logFailure("Exception while accepting alert: " +e.getMessage());
		}
	}

	public void dismissAlert() {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().dismiss();
			logSuccess("Alert dismissed");
		} catch (Exception e) {
			logFailure("Exception while dismissing alert: " +e.getMessage());
		}
	}

	public String getAlertText() {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			String text = driver.switchTo().alert().getText();
			logSuccess("Got alert text: " + text);
			return text;
		} catch (Exception e) { 
			logFailure("Exception while getting alert text" +e.getMessage());
			return null;
		}
	}

	public void sendKeysToAlert(String keys) {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().sendKeys(keys);
			logSuccess( "Sent keys to alert: " + keys);
		} catch (Exception e) {
			logFailure("Exception while sending keys to alert" +e.getMessage()); 
		}
	}

	// Frame & Window Handling
	


	public void switchToFrameByIndex(int index) {
		try {
			driver.switchTo().frame(index);
			logSuccess( "Switched to frame by index: " + index);
		} catch (Exception e) {
			logFailure("Exception while switching to frame by index: " + index +e.getMessage()); 
		}
	}

	public void switchToFrameByElement(WebElement element) {
		try {
			driver.switchTo().frame(element);
			logSuccess( "Switched to frame by element: " + element);
		} catch (Exception e) {
			logFailure("Exception while switching to frame by element: " + element+e.getMessage()); 
		}
		
	}

	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			logSuccess( "Switched to default content");
		} catch (Exception e) {
			logFailure("Exception while switching to default content" +e.getMessage()); 
		}
	}

	public void switchToWindowByIndex(int index) {
		try {
			List<String> windows = new ArrayList<>(driver.getWindowHandles());
			if (index < windows.size()) {
				driver.switchTo().window(windows.get(index));
				logSuccess( "Switched to window index: " + index);
			} else {
				logSuccess( "Window index out of bounds: " + index);
			}
		} catch (Exception e) {
			logFailure("Exception while switching to window index: " + index + e.getMessage()); 

			logger.log(Level.SEVERE, "Exception while switching to window index: " + index, e);
		}
	}

	public void closeCurrentWindow() {
		try {
			driver.close();
			logSuccess( "Closed current window");
		} catch (Exception e) {
			logFailure("Exception while closing current window" +e.getMessage()); 
		}
	}

	// Page Navigation
	

	public void navigateTo(String url) {
		try {
			driver.get(url);
			logSuccess( "Navigated to URL: " + url);
		} catch (Exception e) {
			logFailure("Exception while navigating to URL: " + url +e.getMessage()); 
		}
	}

	public void refreshPage() {
		try {
			driver.navigate().refresh();
			logSuccess( "Page refreshed");
		} catch (Exception e) {
			logFailure("Exception while refreshing page" +e.getMessage()); 
		}
	}

	public void goBack() {
		try {
			driver.navigate().back();
			logSuccess( "Navigated back");
		} catch (Exception e) {
			logFailure("Exception while navigating back" +e.getMessage()); 
		}
	}

	public void goForward() {
		try {
			driver.navigate().forward();
			logSuccess(  "Navigated forward");
		} catch (Exception e) {
			logFailure( "Exception while navigating forward" +e.getMessage()); 
		}
	}

	// JavaScript Utilities

	public void clickUsingJS(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			logSuccess("Clicked element using JavaScript: " + element);
		} catch (Exception e) {
			logFailure("Exception while clicking element using JS: " + element +e.getMessage()); 
		}
	}

	public void scrollToElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			logSuccess("Scrolled to element: " + element);
		} catch (Exception e) {
			logFailure(  "Exception while scrolling to element: " + element +e.getMessage()); 
		}
	}

	public void scrollToBottom() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			logSuccess("Scrolled to bottom of page");
		} catch (Exception e) {
			logFailure("Exception while scrolling to bottom"+e.getMessage()); 
		}
	}

	public void highlightElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='3px solid red'", element);
			logSuccess("Highlighted element: "  + element);
		} catch (Exception e) {
			logFailure( "Exception while highlighting element: " + element +e.getMessage()); 
		}

	}

	// File Upload
	//need to work from here on the logSuccess and logFaliure

	public void uploadFile(WebElement element, String filePath) {
		try {
			element.sendKeys(filePath);
			logSuccess("Uploaded file: " + filePath);
		} catch (Exception e) {
			logFailure( "Exception while uploading file: " + filePath +e.getMessage()); 
		}
	}

	// ----------- Waits ------------

	public void waitForElementToBeVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logSuccess("Element visible: " + locator);
		} catch (Exception e) {
			logFailure("Element not visible: " + locator +e.getMessage()); 
		}
	}

	public void waitForElementToBeClickable(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			logSuccess("Element clickable: " + locator);
		} catch (Exception e) {
			logFailure(  "Element not clickable: " + locator+e.getMessage()); 
		}
	}

	public void waitForElementToDisappear(By locator) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			logSuccess("Element disappeared: " + locator);
		} catch (Exception e) {
			logFailure( "Element did not disappear: " + locator +e.getMessage()); 
		}
	}

	public void waitForTextToBePresent(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));
			logSuccess("Text '" + text + "' is present in element: " + element);
		} catch (Exception e) {
			logFailure( "Text '" + text + "' not present in element: " + element +e.getMessage()); 
		}
	}

	public void waitForURLToContain(String fraction) {
		try {
			wait.until(ExpectedConditions.urlContains(fraction));
			logSuccess( "URL contains: " + fraction);
		} catch (Exception e) {
			logFailure( "URL does not contain: " + fraction +e.getMessage()); 
		}
	}

	public void waitForTitleToBe(String title) {
		try {
			wait.until(ExpectedConditions.titleIs(title));
			logSuccess("Title is: " + title);
		} catch (Exception e) {
			logFailure( "Title is not: " + title +e.getMessage()); 
		}
	}

	// ----------- Validations ------------

	public boolean isElementDisplayed(WebElement element) {
		try {
			boolean displayed = element.isDisplayed();
			logger.log(Level.INFO, "Element displayed: " + displayed);
			return displayed;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Element not displayed", e);
			return false;
		}
	}

	public boolean isElementEnabled(WebElement element) {
		try {
			boolean enabled = element.isEnabled();
			logger.log(Level.INFO, "Element enabled: " + enabled);
			return enabled;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Element not enabled", e);
			return false;
		}
	}

	public boolean isElementSelected(WebElement element) {
		try {
			boolean selected = element.isSelected();
			logger.log(Level.INFO, "Element selected: " + selected);
			return selected;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Element not selected", e);
			return false;
		}
	}

	public boolean validateText(WebElement element, String expectedText) {
		try {
			String actualText = getText(element);
			boolean match = expectedText.equals(actualText);
			logger.log(Level.INFO, "Text validation: expected='" + expectedText + "', actual='" + actualText + "', result=" + match);
			return match;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Text validation failed", e);
			return false;
		}
	}

	public boolean validateAttribute(WebElement element, String attribute, String expectedValue) {
		try {
			String actualValue = getAttribute(element, attribute);
			boolean match = expectedValue.equals(actualValue);
			logger.log(Level.INFO, "Attribute validation: " + attribute + ", expected='" + expectedValue + "', actual='" + actualValue + "', result=" + match);
			return match;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Attribute validation failed", e);
			return false;
		}
	}

	public boolean validatePageTitle(String expectedTitle) {
		try {
			String actualTitle = driver.getTitle();
			boolean match = expectedTitle.equals(actualTitle);
			logger.log(Level.INFO, "Page title validation: expected='" + expectedTitle + "', actual='" + actualTitle + "', result=" + match);
			return match;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Page title validation failed", e);
			return false;
		}
	}

	public boolean validateCurrentURL(String expectedURL) {
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        shortWait.until(ExpectedConditions.urlToBe(expectedURL));
			String actualURL = driver.getCurrentUrl();
			boolean match = expectedURL.equals(actualURL);
			String message = "URL validation - Expected: '" + expectedURL + "', Actual: '" + actualURL + "', Match: " + match;
			if (match) {
				logSuccess(message);
			} else {
				logFailure(message);
			}
			return match;
		} catch (Exception e) {
			logFailure("URL validation failed: " + e.getMessage());
			return false;
		}
	}

	public boolean validateElementCount(By locator, int expectedCount) {
		try {
			int actualCount = driver.findElements(locator).size();
			boolean match = actualCount == expectedCount;
			logger.log(Level.INFO, "Element count validation for " + locator + ": expected=" + expectedCount + ", actual=" + actualCount + ", result=" + match);
			return match;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Element count validation failed", e);
			return false;
		}
	}

	// ----------- Utility ------------

	public List<WebElement> findElements(By locator) {
		try {
			List<WebElement> elements = driver.findElements(locator);
			logger.log(Level.INFO, "Found " + elements.size() + " elements for locator: " + locator);
			return elements;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Exception while finding elements: " + locator, e);
			return new ArrayList<>();
		}
	}

	public WebElement findElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			logger.log(Level.INFO, "Found element for locator: " + locator);
			return element;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Exception while finding element: " + locator, e);
			return null;
		}
	}

	public boolean isLinkBroken(String link) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();
			conn.setRequestMethod("HEAD");
			conn.setConnectTimeout(5000);
			conn.connect();
			int status = conn.getResponseCode();
			return status >= 400;
		} catch (IOException e) {
			return true;
		}
	}

	public boolean isImageValid(WebElement imageElement, String imageName) {
		try {
			if (imageElement == null) {
				logger.log(Level.WARNING, imageName + " image element is null.");
				return false;
			}

			String imageUrl = imageElement.getDomAttribute("src");
			logger.log(Level.INFO, imageName + " image URL: " + imageUrl);

			if (imageUrl == null || imageUrl.trim().isEmpty()) {
				logger.log(Level.WARNING, imageName + " image URL is null or empty.");
				return false;
			}

			HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.connect();

			int responseCode = connection.getResponseCode();
			logger.log(Level.INFO, imageName + " image HTTP response code: " + responseCode);

			return responseCode == HttpURLConnection.HTTP_OK;

		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, "Malformed URL for " + imageName + " image.", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "IO Exception while validating " + imageName + " image.", e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error while validating " + imageName + " image.", e);
		}

		return false;
	}
	public boolean isImageValid(String imageUrl ,String imageName ) {
		try {

			HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.connect();

			int responseCode = connection.getResponseCode();
			logger.log(Level.INFO, imageName + " image HTTP response code: " + responseCode);

			return responseCode == HttpURLConnection.HTTP_OK;

		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, "Malformed URL for " + imageName + " image.", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "IO Exception while validating " + imageName + " image.", e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error while validating " + imageName + " image.", e);
		}

		return false;
	}
	public boolean captureScreenshot(String fileName) {
		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String destDir = "./Screenshots/Prerelease/";
			File destFile = new File(destDir + fileName + ".png");
			destFile.getParentFile().mkdirs(); // Create directory if not exist

			Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			getExtentTest().info("Screenshot captured",MediaEntityBuilder.createScreenCaptureFromPath(destFile.getAbsolutePath()).build());
			logger.log(Level.INFO, "Screenshot captured: " + destFile.getAbsolutePath());

			return true;
		} catch (Exception e) {
			logFailure("Failed to capture screenshot: " + e.getMessage());
			return false;
		}
	}
//Close Single ScriptPOPup
	public void closeHtmlPopup(By closeButtonLocator) {
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator)).click();
	        logSuccess("Popup closed successfully.");
	    } catch (Exception e) {
	        logFailure("Failed to close popup: " + e.getMessage());
	    }
	}
	
	
	
	//github
}
