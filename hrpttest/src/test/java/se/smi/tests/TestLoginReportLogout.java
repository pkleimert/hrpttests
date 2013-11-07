/*
 * Note: Tests can be executed in any order and separately if wou want
 * Users in the test must exist as registered users with idcode and intake survey filled in.
 * Also test assumes users are adults ie. over 16 years.
 */
package se.smi.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

public class TestLoginReportLogout extends AbstractHrptTestCase{	
	@Test
	public void testReport_nosymptom() throws Exception {
		SELENIUM.open("/sv/survey/thanks/?gid="+GID);
		SELENIUM.click("link=Rapportera");
		SELENIUM.waitForPageToLoad(waitms);
		unClickSymptomsCBs();
		DRIVER.findElements(By.name("Q1a")).get(0).click();
		SELENIUM.click("id=commentsubmit");
		SELENIUM.waitForPageToLoad(waitms);
		assertEquals("Inga symptom",SELENIUM.getText("//div[@id='col1']/table[2]/tbody/tr/td[2]"));
	}
	
	@Test
	public void testReport_cold() throws Exception {
		SELENIUM.open("/sv/survey/thanks/?gid="+GID);
		SELENIUM.click("link=Rapportera");
		SELENIUM.waitForPageToLoad(waitms);
		
		unClickSymptomsCBs();
		
		DRIVER.findElements(By.name("Q1a")).get(1).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElements(By.name("Q1b")).get(0).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElement(By.name("Q1_3")).click();
		DRIVER.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_COLD_"+USER);	
		SELENIUM.click("id=commentsubmit");
		SELENIUM.waitForPageToLoad(waitms);
		assertEquals("Förkylning",SELENIUM.getText("//div[@id='col1']/table[2]/tbody/tr/td[2]"));
	}
	
	@Test
	public void testReport_ili() throws Exception {
		SELENIUM.open("/sv/survey/thanks/?gid="+GID);
		SELENIUM.click("link=Rapportera");
		SELENIUM.waitForPageToLoad(waitms);
		
		unClickSymptomsCBs();
		
		DRIVER.findElements(By.name("Q1a")).get(1).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElements(By.name("Q1b")).get(0).click();
		DRIVER.findElement(By.name("Q1_1")).click();
		DRIVER.findElement(By.name("Q1_5")).click();
		DRIVER.findElements(By.name("Q5")).get(0).click();
		DRIVER.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_ILI_"+USER);	
		SELENIUM.click("id=commentsubmit");
		SELENIUM.waitForPageToLoad(waitms);
		assertEquals("Influensaliknande symptom",SELENIUM.getText("//div[@id='col1']/table[2]/tbody/tr/td[2]"));
	}
	
	@Test
	public void testReport_iligastro() throws Exception {	
		SELENIUM.open("/sv/survey/thanks/?gid="+GID);
		SELENIUM.click("link=Rapportera");
		SELENIUM.waitForPageToLoad(waitms);
		
		unClickSymptomsCBs();
		
		DRIVER.findElements(By.name("Q1a")).get(1).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElements(By.name("Q1b")).get(0).click();
		DRIVER.findElement(By.name("Q1_1")).click();
		DRIVER.findElement(By.name("Q1_5")).click();
		DRIVER.findElement(By.name("Q1_16")).click();
		DRIVER.findElements(By.name("Q5")).get(0).click();
		DRIVER.findElements(By.name("Q6e")).get(3).click();
		DRIVER.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_ILIGASTRO_"+USER);	
		SELENIUM.click("id=commentsubmit");
		SELENIUM.waitForPageToLoad(waitms);
		assertEquals("Influensaliknande symptom + magsjuka",SELENIUM.getText("//div[@id='col1']/table[2]/tbody/tr/td[2]"));
	}
	
	@Test
	public void testReport_coldgastro() throws Exception {	
		SELENIUM.open("/sv/survey/thanks/?gid="+GID);
		SELENIUM.click("link=Rapportera");
		SELENIUM.waitForPageToLoad(waitms);
		
		unClickSymptomsCBs();
		
		DRIVER.findElements(By.name("Q1a")).get(1).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElements(By.name("Q1b")).get(0).click();
		DRIVER.findElement(By.name("Q1_3")).click();
		DRIVER.findElement(By.name("Q1_16")).click();
		DRIVER.findElement(By.name("Q1_17")).click();
		DRIVER.findElement(By.name("Q1_18")).click();
		DRIVER.findElements(By.name("Q6e")).get(3).click();
		DRIVER.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_COLDGASTRO_"+USER);	
		SELENIUM.click("id=commentsubmit");
		SELENIUM.waitForPageToLoad(waitms);
		assertEquals("Magsjuka + förkylning",SELENIUM.getText("//div[@id='col1']/table[2]/tbody/tr/td[2]"));
	}
    
	@Test
	public void testReport_gastro() throws Exception {	
		SELENIUM.open("/sv/survey/thanks/?gid="+GID);
		SELENIUM.click("link=Rapportera");
		SELENIUM.waitForPageToLoad(waitms);
		
		unClickSymptomsCBs();
		
		DRIVER.findElements(By.name("Q1a")).get(1).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElements(By.name("Q1b")).get(0).click();
		SELENIUM.waitForPageToLoad(waitms);
		DRIVER.findElement(By.name("Q1_16")).click();
		DRIVER.findElement(By.name("Q1_17")).click();
		DRIVER.findElement(By.name("Q1_18")).click();
		DRIVER.findElement(By.name("Q6g")).sendKeys(f.format(now)+"_GASTRO_"+USER);	
		SELENIUM.click("id=commentsubmit");
		SELENIUM.waitForPageToLoad(waitms);
		assertEquals("Magsjuka",SELENIUM.getText("//div[@id='col1']/table[2]/tbody/tr/td[2]"));
	}
	
//	@Test
//	public void testlogout() throws Exception {
//		SELENIUM.open("/sv/accounts/logout/");
//		assertEquals("Du är utloggad. Gå till startsidan", SELENIUM.getText("link=Du är utloggad. Gå till startsidan"));
//	}
}
