package entrata.website.test;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import entrata.website.utils.Utils;

public class BaseTest {

	protected WebDriver driver;

	@BeforeClass
	// Key sensitive use: Edge Chrome FireFox
	public void Setup(ITestContext testContext, @Optional("Edge") String browser) {
		driver = Utils.getDriver(browser);
		testContext.setAttribute("WebDriver", this.driver);
		driver.get(Utils.readPropStart("url"));
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();

	}

	@BeforeSuite
	public void readProperties(@Optional("Edge") String browser) throws IOException {
		Utils.readProperties();
	}
}
