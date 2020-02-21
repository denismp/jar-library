package com.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppWebDriver {
//	private static AppWebDriver instance = null;
	private RemoteWebDriver dr = null;

//	public AppWebDriver(String browser) throws InterruptedException {
//
//		if (browser == null) {
//			browser = "firefox";
//		}
//		if (browser.equals("chrome")) {
//			// TODO need to parameterize the driver path.
//			// Download from here:
//			// https://sites.google.com/a/chromium.org/chromedriver/downloads
////			String driverPath = "C:\\Users\\dennis.putman.ctr\\Downloads\\chromedriver_win32\\";
//			String driverPath = "resources/";
//			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
//			dr = new ChromeDriver();
//		} else if (browser.equals("firefox")) {
//			String driverPath = "resources/";
//			System.setProperty("webdriver.firefox.driver", driverPath + "geckodriver.exe");
//			try {
//				dr = new FirefoxDriver();
//			} catch (WebDriverException e) {
//				Thread.sleep(5000);
//				dr = new FirefoxDriver();
//			}
//		} else if (browser.contains("internet explorer")) {
//			// Download form here: https://www.seleniumhq.org/download/
//			// TODO need to parameterize the driver path.
////			String driverPath = "C:\\Users\\dennis.putman.ctr\\Downloads\\IEDriverServer_x64_3.14.0\\";
//			String driverPath = "resources/";
//			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
//			dr = new InternetExplorerDriver();
//		} else if (browser.contains("edge")) {
//			// Download form here: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/#downloads
//			// TODO need to parameterize the driver path.
////			String driverPath = "C:\\Users\\dennis.putman.ctr\\Downloads\\IEDriverServer_x64_3.14.0\\";
//			String driverPath = "resources/";
//			System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
//			dr = new EdgeDriver();
//		}
//
//		dr.manage().window().maximize();
//		dr.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
//	}
	
	public AppWebDriver(String browser, boolean local) throws InterruptedException, MalformedURLException {
		
//        URL hubURL = new  URL("http://localhost:4444/wd/hub");
//        URL hubURL = new  URL("http://selenium.sandbox.group.com:4444/wd/hub");
		URL hubURL = null;
		if( local ) {
			hubURL = new  URL("http://localhost:4444/wd/hub");
		} else {
			hubURL = new  URL("http://selenium.sanbox.group.com:4444/wd/hub");
		}

		if (browser == null) {
			browser = "firefox";
		}
		if (browser.equals("chrome")) {
	        ChromeOptions option = new ChromeOptions();
	        dr = new RemoteWebDriver(hubURL, option);
		} else if (browser.equals("firefox")) {
	        FirefoxOptions option = new FirefoxOptions();
	        dr = new RemoteWebDriver(hubURL, option);
		} else if (browser.contains("internet explorer")) {
	        InternetExplorerOptions option = new InternetExplorerOptions();
	        dr = new RemoteWebDriver(hubURL, option);
		} else if (browser.contains("edge")) {
	        EdgeOptions option = new EdgeOptions();
	        dr = new RemoteWebDriver(hubURL, option);
		}

		dr.manage().window().maximize();
		dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

//	public static AppWebDriver getInstance(String browser) throws InterruptedException {
//		if (instance == null) {
//			instance = new AppWebDriver(browser);
//		}
//		return instance;
//
//	}

	public RemoteWebDriver getDriver() {
		return dr;
	}
}
