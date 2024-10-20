package entrata.website.test.homepage;

import org.testng.Assert;
import org.testng.annotations.Test;

import entrata.website.object.homepage.EntrataHomePage;
import entrata.website.test.BaseTest;
import io.qameta.allure.Description;

public class EntrataHomeTest extends BaseTest {

	@Test(description = "Navigate to Entrata landing page and get title")
	@Description("Navigate to Entrata landing page and get title")
	public void A01_VerifyTitle() throws InterruptedException {
		EntrataHomePage homePage = new EntrataHomePage(driver);
		homePage.closeCookieConsentPopup();
		Assert.assertTrue(homePage.getTitle().contains("Entrata"));
	}

	@Test(description = "Navigate to Sign In Option and Enter Required Details")
	@Description("Navigate to Sign In Option and Enter Required Details")
	public void A02_VerifySignInOption() throws InterruptedException {
		EntrataHomePage homePage = new EntrataHomePage(driver);
		Assert.assertTrue(homePage.validateSignIn());
		homePage.clickSignIn();
		Assert.assertTrue(homePage.validateProjectManagerLogin());
		homePage.clickProjectManagerLogin();
		Assert.assertTrue(homePage.enterDetailsForPMSignIn());
	}

	@Test(description = "Navigate to Entrata footer Section")
	@Description("Navigate to Entrata footer Section")
	public void A03_VerifyFooterSection() throws InterruptedException {
		EntrataHomePage homePage = new EntrataHomePage(driver);
		Assert.assertTrue(homePage.verifyFooter());
	}

	@Test(description = "Navigate to See How It Works")
	@Description("Navigate to See How It Works")
	public void A04_VerifySeeHowItWorks() throws InterruptedException {
		EntrataHomePage homePage = new EntrataHomePage(driver);
		Assert.assertTrue(homePage.verifySeeHowItWorks());
	}

	@Test(description = "Navigate to Basecamp")
	@Description("Navigate to Basecamp")
	public void A05_VerifyBasecamp() throws InterruptedException {
		EntrataHomePage homePage = new EntrataHomePage(driver);
		homePage.verifyBasecamp();
	}
}
