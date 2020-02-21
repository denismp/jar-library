/**
 * 
 */
package com.web;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
//import org.apache.maven.surefire.shade.java5.org.apache.maven.shared.utils.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.utility.ExcelTestInput;
import com.utility.ExcelTestReporter;
import com.utility.TestReporter;

/**
 * @author denis.putnam.ctr
 *
 */
public abstract class TestBase {
	protected WebDriver driver;
	private String baseUrl;
	private String userName;
	private String password;
	private boolean acceptNextAlert = true;
	private String browser;
	private ExcelTestReporter excelReporter;
	private ExcelTestInput excelInput;

	/**
	 * 
	 * @param el
	 * @throws InterruptedException
	 * @throws WebDriverException
	 */
	public void clickWithWait(WebElement el) throws InterruptedException, WebDriverException {
		long sleepTime = 5000;
		try {
			el.click();
		} catch (ElementNotInteractableException e) {
			Thread.sleep(sleepTime);
			el.click();
		} catch (WebDriverException e) {
			Thread.sleep(sleepTime);
			el.click();
		} catch (Exception e) {
			Thread.sleep(sleepTime);
			el.click();
		}
	}

	// org.openqa.selenium.ElementNotInteractableException
	/**
	 * 
	 * @param el
	 * @param sleepTime
	 * @throws WebDriverException
	 * @throws ElementNotInteractableException
	 * @throws InterruptedException
	 */
	public void clickWithWait(WebElement el, long sleepTime) throws WebDriverException, ElementNotInteractableException, InterruptedException {
		try {
			el.click();
		} catch (ElementNotInteractableException e) {
			Thread.sleep(sleepTime);
			el.click();
		} catch (WebDriverException e) {
			Thread.sleep(sleepTime);
			el.click();
		} catch (Exception e) {
			Thread.sleep(sleepTime);
			el.click();
		}
	}
	
	/**
	 * 
	 * @param browser
	 * @param local
	 * @throws Exception
	 */
	public void setUp(String browser, boolean local) throws Exception {
		AppWebDriver myDriver = new AppWebDriver(browser, local);
		this.browser = browser;
		driver = myDriver.getDriver();
//		driver = new FirefoxDriver();
		baseUrl = "https://www.katalon.com/";
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}
	
	/**
	 * Set up the given class to run the selenium test.
	 * @param browser - firefox, chrome, internet explorer, or edge
	 * @param local - if true, run the local browser via the local selenium grid server.
	 * @param className - name of the class extending this one
	 * @throws Exception
	 */
	public void setUp(String browser, boolean local, String className) throws Exception {
		AppWebDriver myDriver = new AppWebDriver(browser, local);
		this.browser = browser;
		driver = myDriver.getDriver();
//		driver = new FirefoxDriver();
		baseUrl = "https://www.katalon.com/";
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		this.excelReporter = new ExcelTestReporter( this.getBrowser() + ".xlsx", getClass().getSimpleName());
	}
	
	/**
	 * Set up the given class to run the selenium test
	 * @param browser - firefox, chrome, internet explorer, or edge
	 * @param local - if true, run the local browser via the local selenium grid server.
	 * @param className - name of the class extending this one
	 * @param excelInputFilePath - complete path of input excel file.
	 * @param worksheet - worksheet to read in the excel file.
	 * @throws Exception
	 */
	public void setUp(String browser, boolean local, String className, String excelInputFilePath, String worksheet) throws Exception {
		AppWebDriver myDriver = new AppWebDriver(browser, local);
		this.browser = browser;
		driver = myDriver.getDriver();
//		driver = new FirefoxDriver();
		baseUrl = "https://www.katalon.com/";
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		this.excelReporter = new ExcelTestReporter( this.getBrowser() + ".xlsx", getClass().getSimpleName());
		this.openExcelInput(excelInputFilePath, worksheet);
	}
	
	/**
	 * 
	 * @param filePath
	 * @param worksheet
	 * @throws Exception
	 */
	public void openExcelInput(String filePath, String worksheet) throws Exception {
		this.excelInput = new ExcelTestInput(filePath, worksheet, this.excelReporter);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


//	public void login() {
//	}
	
	/**
	 * 
	 * @param name
	 * @param sDate
	 * @param verificationErrors
	 * @return
	 */
	public boolean isDateFormatValid(String name, String sDate, StringBuffer verificationErrors) {
		boolean rVal = true;
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			Date date = simpleDateFormat.parse(sDate);
			TestReporter.info(" date =" + sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			verificationErrors.append(e.toString());
			TestReporter.fail(e.toString());
			rVal = false;
		}
		return rVal;		
	}
	
	/**
	 * 
	 * @param id
	 * @param listName
	 * @param verificationErrors
	 * @return
	 */
	public boolean isDropDownValid(String id, String listName, StringBuffer verificationErrors) {
		boolean rVal = true;
		String value = null;
		try {
			boolean selected = false;
			boolean displayed = false;
			TestReporter.info(listName + "...");
			Select dropDown = new Select(driver.findElement(By.id(id)));
			List<WebElement> optionsList = dropDown.getOptions();
			int i = 0;
			for (WebElement option : optionsList) {
				String sValue = option.getText();
				TestReporter.info(id + " value=" + sValue);
				i++;
				if (option.isSelected() && option.isDisplayed()) {
					selected = true;
					value = sValue;
				}
				if (option.isDisplayed()) {
					displayed = true;
//					value = sValue;
				}
			}
			TestReporter.info("Number of " + id + "s= " + i);
			if (selected == false && displayed == false) {
				verificationErrors.append("The " + id + "s dropdown has nothing selected or displayed.");
				TestReporter.fail("The " + id + "s dropdown has nothing selected or displayed.");
			} else {
				TestReporter.info("Selected/displayed value=" + value);
			}
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.toString());
		}
		return rVal;
	}
	
	/**
	 * 
	 * @param name
	 * @param listName
	 * @param verificationErrors
	 * @return
	 */
	public boolean isDropDownValidByName(String name, String listName, StringBuffer verificationErrors) {
		boolean rVal = true;
		String value = null;
		try {
			boolean selected = false;
			boolean displayed = false;
			TestReporter.info(listName + "...");
			Select dropDown = new Select(driver.findElement(By.name(name)));
			List<WebElement> optionsList = dropDown.getOptions();
			int i = 0;
			for (WebElement option : optionsList) {
				String sValue = option.getText();
				TestReporter.info(name + " value=" + sValue);
				i++;
				if (option.isSelected() && option.isDisplayed()) {
					selected = true;
					value = sValue;
				}
				if( option.isDisplayed() ) {
					displayed = true;
//					value = sValue;
				}
			}
			TestReporter.info("Number of " + name + "s= " + i);
			if (selected == false && displayed == false ) {
				verificationErrors.append("The " + name + "s dropdown has nothing selected or displayed.");
				TestReporter.fail("The " + name + "s dropdown has nothing selected or displayed.");
			} else {
				TestReporter.info("Selected/displayed value=" + value);
			}
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.toString());
		}
		return rVal;
	}

	/**
	 * 
	 * @param id
	 * @param listName
	 * @param verificationErrors
	 * @return
	 */
	public boolean isDropDownValidNotNull(String id, String listName, StringBuffer verificationErrors) {
		boolean rVal = true;
		String value = null;
		try {
			boolean selected = false;
			boolean displayed = false;
			TestReporter.info(listName + "...");
			Select dropDown = new Select(driver.findElement(By.id(id)));
			List<WebElement> optionsList = dropDown.getOptions();
			int i = 0;
			for (WebElement option : optionsList) {
				String sValue = option.getText();
				TestReporter.info(id + " value=" + sValue);
				i++;
				if (option.isSelected() && option.isDisplayed()) {
					selected = true;
					value = sValue;
				}
				if (option.isDisplayed()) {
					displayed = true;
//					value = sValue;
				}
			}
			TestReporter.info("Number of " + id + "s= " + i);
			if (selected == false && displayed == false) {
				verificationErrors.append("The " + id + "s dropdown has nothing selected or displayed.");
				TestReporter.fail("The " + id + "s dropdown has nothing selected or displayed.");
			} else {
				TestReporter.info("Selected/displayed value=" + value);
				if( value == null ) {
					rVal = false;
					verificationErrors.append("Display value for " + id + " is null");
					TestReporter.fail("Display value for " + id + " is null");
				}
			}
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.toString());
		}
		return rVal;
	}
	
	/**
	 * 
	 * @param name
	 * @param listName
	 * @param verificationErrors
	 * @return
	 */
	public boolean isDropDownValidByNameNotNull(String name, String listName, StringBuffer verificationErrors) {
		boolean rVal = true;
		String value = null;
		try {
			boolean selected = false;
			boolean displayed = false;
			TestReporter.info(listName + "...");
			Select dropDown = new Select(driver.findElement(By.name(name)));
			List<WebElement> optionsList = dropDown.getOptions();
			int i = 0;
			for (WebElement option : optionsList) {
				String sValue = option.getText();
				TestReporter.info(name + " value=" + sValue);
				i++;
				if (option.isSelected() && option.isDisplayed()) {
					selected = true;
					value = sValue;
				}
				if( option.isDisplayed() ) {
					displayed = true;
//					value = sValue;
				}
			}
			TestReporter.info("Number of " + name + "s= " + i);
			if (selected == false && displayed == false ) {
				verificationErrors.append("The " + name + "s dropdown has nothing selected or displayed.");
				TestReporter.fail("The " + name + "s dropdown has nothing selected or displayed.");
			} else {
				TestReporter.info("Selected/displayed value=" + value);
				if( value == null ) {
					rVal = false;
					verificationErrors.append("Display value for " + name + " is null");
					TestReporter.fail("Display value for " + name + " is null");
				}
			}
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.toString());
		}
		return rVal;
	}

	/**
	 * 
	 * @param xpath
	 * @param listName
	 * @param verificationErrors
	 * @return
	 */
	public boolean isDropDownValidByXpath(String xpath, String listName, StringBuffer verificationErrors) {
		boolean rVal = true;
		String value = null;
		try {
			boolean selected = false;
			boolean displayed = false;
			TestReporter.info(listName + "...");
			Select dropDown = new Select(driver.findElement(By.xpath(xpath)));
			List<WebElement> optionList = dropDown.getOptions();
			int i = 0;
			for (WebElement option : optionList) {
				String sValue = option.getText();
				TestReporter.info(xpath + " value=" + sValue);
				i++;
				if (option.isSelected() && option.isDisplayed()) {
					selected = true;
					value = sValue;
				}
				if( option.isDisplayed() ) {
					displayed = true;
//					value = sValue;
				}
			}
			TestReporter.info("Number of " + xpath + "s= " + i);
			if (selected == false && displayed == false ) {
				verificationErrors.append("The " + xpath + "s dropdown has nothing selected or displayed.");
				TestReporter.fail("The " + xpath + "s dropdown has nothing selected or displayed.");
			} else {
				TestReporter.info("Selected/displayed value=" + value);
			}
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.toString());
		}
		return rVal;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @param verificationErrors
	 * @return
	 */
	public boolean isNumeric(String name, String value, StringBuffer verificationErrors) {
		boolean rVal = true;
		if (StringUtils.isEmpty(value) == false && StringUtils.isNumeric(value) == false) {
			verificationErrors.append(name + " is not an isNumeric.");
			TestReporter.fail(name + " is not an isNumeric.");
			rVal = false;
		} else if (StringUtils.isEmpty(value)) {
			TestReporter.info(name + " is empty.");
		}
		return rVal;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @param verificationErrors
	 * @return
	 */
	public boolean isAlphanumericSpace(String name, String value, StringBuffer verificationErrors) {
		boolean rVal = true;
		if (StringUtils.isEmpty(value) == false && StringUtils.isAlphanumericSpace(value) == false) {
			verificationErrors.append(name + " is not an isAlphanumericSpace.");
			TestReporter.fail(name + " is not an isAlphanumericSpace.");
			rVal = false;
		} else if (StringUtils.isEmpty(value)) {
			TestReporter.info(name + " is empty.");
		}
		return rVal;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @param verificationErrors
	 * @return
	 */
	public boolean isAlpha(String name, String value, StringBuffer verificationErrors) {
		boolean rVal = true;
		if (StringUtils.isEmpty(value) == false && StringUtils.isAlpha(value) == false) {
			verificationErrors.append(name + " is not an alpha string.");
			TestReporter.fail(name + " is not an alpha string.");
			rVal = false;
		} else if (StringUtils.isEmpty(value) ) {
			TestReporter.info(name + " is empty.");			
		}	
		return rVal;
	}
	
	public boolean isAsciiPrintable( String name, String value, StringBuffer verificationErrors ) {
		boolean rVal = false;
		if(StringUtils.isEmpty(value) == true ) {

			TestReporter.info( name + " is empty");
			return true;
		}
		rVal = StringUtils.isAsciiPrintable(value);
		if( rVal == false ) {
			TestReporter.fail(name + " is invalid");
			verificationErrors.append(name + " is invalid");
		}
		return rVal;
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	
	public void clickItem(WebElement el, String name, StringBuffer verificationErrors, Integer stepNum) {
		try {
			clickWithWait(el);
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum, "Click " + name);	
		} catch (Error | WebDriverException | InterruptedException e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.getMessage(), this.getExcelReporter(), stepNum, "Click " + name);			
		}
	}
	
	public void validateTextEquals(String actualText, String text, StringBuffer verificationErrors, Integer stepNum) {
		try {
//			TestReporter.info("Testing for existence of Print Line Items button.");
			assertEquals(actualText, text);
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum, "Validate text '" + text +"'");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.fail(e.getMessage(), this.getExcelReporter(), stepNum, "Validate text '" + text +"'");		}		
	}
	
	public void isPresent(By value, String text, StringBuffer verificationErrors, Integer stepNum) {
		try {
			assertTrue(this.isElementPresent(value));
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum++, "Test for presence of " + text);									
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum++, "Test for presence of " + text);									
		}		
	}
	
	public void isNotPresent(By value, String text, StringBuffer verificationErrors, Integer stepNum) {
		try {
			assertFalse(this.isElementPresent(value));
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum++, "Test for no presence of " + text);									
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum++, "Test for no presence of " + text);									
		}		
	}
	
	public void enterText(WebElement el, String text, String value, StringBuffer verificationErrors, Integer stepNum) {
		this.clickItem(el, text, verificationErrors, stepNum);
		try {
			el.clear();
			el.sendKeys(value);
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum, "Entering text for " + text);									
		} catch (Error e) {
			verificationErrors.append(e.toString());
			TestReporter.step("SUCCESS", this.getExcelReporter(), stepNum, "Entering text for " + text);									
		}		
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public ExcelTestReporter getExcelReporter() {
		return excelReporter;
	}

	public void setExcelReporter(ExcelTestReporter excelR) {
		this.excelReporter = excelR;
	}

	/**
	 * @return the excelInput
	 */
	public ExcelTestInput getExcelInput() {
		return excelInput;
	}

	/**
	 * @param excelInput the excelInput to set
	 */
	public void setExcelInput(ExcelTestInput excelInput) {
		this.excelInput = excelInput;
	}

	/**
	 */
	public abstract void login();
	
}
