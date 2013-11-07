package se.smi.tests;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;

public abstract class AbstractHrptTestCase{
	protected static String USER = "survey_test_vuxen";
	protected static String PASSWORD = "password";
	protected static String GID = "04d73c01-f4f1-47e4-9525-47cb6b1b70ae";
	protected static Selenium SELENIUM;
	protected static WebDriver DRIVER;
	private Selenium selenium;
	private WebDriver driver;
	protected String user;
	protected String password;
	protected String gid;
	Date now = new Date();
	SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	protected static String waitms = "90000";
	protected String baseurl;
	
	@BeforeClass
	public static void setUp() throws Exception {
		DRIVER = new FirefoxDriver();
		String baseUrl = "http://127.0.0.1:8000";
		System.out.println("running test with baseurl " + baseUrl);
		SELENIUM = new WebDriverBackedSelenium(DRIVER, baseUrl);
		SELENIUM.open("/sv/");
		SELENIUM.click("link=Logga in");
		SELENIUM.waitForPageToLoad(waitms);
		SELENIUM.type("id=id_username", USER);
		SELENIUM.type("id=id_password", PASSWORD);
		SELENIUM.click("id=boxsubmit");
		SELENIUM.waitForPageToLoad(waitms);
	}
	
	public void setUpInstance() throws Exception {
		driver = new FirefoxDriver();
		selenium = new WebDriverBackedSelenium(driver, baseurl);
		driver.manage().timeouts().pageLoadTimeout(360, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(360, TimeUnit.SECONDS);
	}
	
	public void loginInstance() throws Exception {
		selenium.open("/sv/");
		selenium.click("link=Logga in");
		selenium.waitForPageToLoad(waitms);
		selenium.type("id=id_username", user);
		selenium.type("id=id_password", password);
		selenium.click("id=boxsubmit");
		selenium.waitForPageToLoad(waitms);
	}
	
	public void unClickSymptomsCBs() throws Exception{
		WebDriver wd = getDriver();
		
		//Clear comment
		WebElement we = wd.findElement(By.name("Q6g"));
		if(we.isDisplayed()){
			we.clear();
		}
		
		//Special clear for no of vomits
		we = wd.findElement(By.name("Q1_16"));
		if(we.isDisplayed() && !we.isSelected()){
			we.click();
		}
		we = wd.findElements(By.name("Q6e")).get(1);
		if(we.isDisplayed()){
			we.click();
		}
		
		List<WebElement> el = new ArrayList<WebElement>();
		el.addAll(wd.findElements(By.name("Q5")));
		el.add(wd.findElement(By.name("Q1_16")));
		el.add(wd.findElement(By.name("Q1_17")));
		el.add(wd.findElement(By.name("Q1_18")));
		el.add(wd.findElement(By.name("Q1_5")));
		el.add(wd.findElement(By.name("Q1_3")));
		el.add(wd.findElement(By.name("Q1_1")));
		
		for(WebElement e :el){
			if(e.isSelected() && e.isDisplayed()){
				e.click();
			}
		}
	}
	
	private WebDriver getDriver(){
		if(DRIVER == null){
			return driver;
		}
		else{
			return DRIVER;
		}
	}
	
	public void reportNoSymptomInstance() throws Exception {
		selenium.open("/sv/survey/thanks/?gid="+gid);
		selenium.click("link=Rapportera");
		selenium.waitForPageToLoad(waitms);
		unClickSymptomsCBs();
		driver.findElements(By.name("Q1a")).get(0).click();
		selenium.click("id=commentsubmit");
		selenium.waitForPageToLoad(waitms);
	}
	
	public void reportIliInstance() throws Exception {
		selenium.open("/sv/survey/thanks/?gid="+gid);
		selenium.click("link=Rapportera");
		selenium.waitForPageToLoad(waitms);
		unClickSymptomsCBs();
		driver.findElements(By.name("Q1a")).get(1).click();
		selenium.waitForPageToLoad(waitms);
		driver.findElements(By.name("Q1b")).get(0).click();
		selenium.waitForPageToLoad(waitms);
		driver.findElement(By.name("Q1_1")).click();
		driver.findElement(By.name("Q1_5")).click();
		driver.findElements(By.name("Q5")).get(0).click();
		driver.findElement(By.name("Q6g")).clear();
		driver.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_ILI_"+user);	
		selenium.click("id=commentsubmit");
		selenium.waitForPageToLoad(waitms);
	}
	
	public void reportColdInstance() throws Exception {
		selenium.open("/sv/survey/thanks/?gid="+gid);
		selenium.click("link=Rapportera");
		selenium.waitForPageToLoad(waitms);
		unClickSymptomsCBs();
		driver.findElements(By.name("Q1a")).get(1).click();
		selenium.waitForPageToLoad(waitms);
		driver.findElements(By.name("Q1b")).get(0).click();
		selenium.waitForPageToLoad(waitms);
		driver.findElement(By.name("Q1_3")).click();
		//if(driver.findElement(By.name("Q6g")).isDisplayed()){
		driver.findElement(By.name("Q6g")).clear();
		driver.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_COLD_"+user);
		//}
		selenium.click("id=commentsubmit");
		selenium.waitForPageToLoad(waitms);
	}
	
	public void logoutInstance() throws Exception {
		selenium.open("/sv/accounts/logout");
	}
		
	@AfterClass
	public static void tearDown() throws Exception {
		SELENIUM.open("/sv/accounts/logout/");
		assertEquals("Du är utloggad. Gå till startsidan", SELENIUM.getText("link=Du är utloggad. Gå till startsidan"));
		SELENIUM.stop();
	}
	
	public void tearDownInstance() throws Exception {
		selenium.stop();
	}
}
