package entrata.website.object.homepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import entrata.website.object.BasePage;
import entrata.website.utils.Utils;

public class EntrataHomePage extends BasePage {

	@FindBy(css = "div[id='cookie-close']")
	public WebElement iconCloseCookieConsent;
	@FindBy(css = "button[aria-label='close']")
	public WebElement iconClose;
	@FindBy(xpath = "//*[@class='nav-button']//a[contains(@href,'sign-in')]")
	public WebElement btnSignIn;
	@FindBy(css = "[class^='button hover_black new-button']")
	public WebElement btnPMLogin;
	@FindBy(css = "[placeholder='Username']")
	public WebElement fdUsername;
	@FindBy(css = "[placeholder='Password']")
	public WebElement fdPassword;
	@FindBy(css = "[type='submit']")
	public WebElement btnSignInToSubmit;
	@FindBy(xpath = "//*[@class='footer-link black'][contains(text(),'ResidentPay')]")
	public WebElement txtResidentPay;
	@FindBy(css = "[id='product-banner-h1']")
	public WebElement txtResidentPayPage;
	@FindBy(css = "[class='footer-nav']")
	public WebElement txtFooterHeading;
	@FindBy(css = "[class^='white-button center-button hover_red']")
	public WebElement btnSeeHowItWorks;
	@FindBy(css = "[class^='nav-link last dropdown-menu-text']")
	public WebElement txtBasecamp;
	@FindBy(css = "[class='basecamp-landing-logo']")
	public WebElement txtBasecampPage;
	
	public EntrataHomePage(WebDriver driver) {
		super(driver);
	}

	/* This method close the cookie consent popup */
	public void closeCookieConsentPopup() {
		waitForElementVisibility(iconCloseCookieConsent);
		click(iconCloseCookieConsent);
		closeIcon();
	}

	/* This method click the cross icon visible on homepage */
	public void closeIcon() {
		waitForElementVisibility(iconClose);
		click(iconClose);
	}

	/* This method used to get the website title */
	public String getTitle() {
		return driver.getTitle();
	}

	/* This method validates the sign in option */
	public boolean validateSignIn() throws InterruptedException {
		sleep(3000);
		return isElementVisible(btnSignIn);
	}

	/* This method click the sign in option */
	public void clickSignIn() {
		javaScriptExecuteClick(btnSignIn);
	}

	/* This method validates the project manager login option */
	public boolean validateProjectManagerLogin() {
		return isElementVisible(btnPMLogin);
	}

	/* This method click the project manager login option */
	public void clickProjectManagerLogin() {
		click(btnPMLogin);
	}

	/* This method fill the data inside project manager login screen */
	public boolean enterDetailsForPMSignIn() {
		String username = Utils.readPropStart("username");
		String password = Utils.readPropStart("password");
		fillText(fdUsername, username);
		fillText(fdPassword, password);
		return isElementVisible(btnSignInToSubmit);
	}

	/* This method validates the footer section */
	public boolean verifyFooter() {
		String url = Utils.readPropStart("url");
		setUrl(url);
		autoScroll(txtFooterHeading);
		click(txtResidentPay);
		return isElementVisible(txtResidentPayPage);
	}

	/* This method validates the see how it works section */
	public boolean verifySeeHowItWorks() throws InterruptedException {
		String url = Utils.readPropStart("url");
		setUrl(url);
		sleep(4000);
		scrollDown(btnSeeHowItWorks);
		return isElementVisible(btnSeeHowItWorks);
	}

	/* This method validates basecamp section */
	public void verifyBasecamp() {
		String url = Utils.readPropStart("url");
		setUrl(url);
		javaScriptExecuteClick(txtBasecamp);
		moveToNewWindow();
		isElementVisible(txtBasecampPage);
		moveBackToMainWindow();
	}
}
