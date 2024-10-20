package entrata.website.object;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;

public class BasePage {

	public final static int TIMEOUT = 60;
	private static String mainWindow;
	public WebDriverWait wait;
	@FindBy(css = "[name='footer']")
	public WebElement footer;
	@FindBy(css = "#content")
	public WebElement permisMsg;
	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
	}

	/*
	 * Call this method with your element and a color like (red,green,orange etc...)
	 */
	protected void highlightElement(WebElement element, String color) {
		// keep the old style to change it back
		String originalStyle = element.getAttribute("style");
		String newStyle = "border: 2px solid " + color + ";" + originalStyle;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Change the style
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ newStyle + "');},0);", element);

		// Change the style back after few miliseconds
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ originalStyle + "');},400);", element);

	}

	// This is ScrollDown JavaScript Method
	public void scrollDown(WebElement value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", value);
	}

	public void click(WebElement el) {
		try {
			waitForElementLoad(el);
			highlightElement(el, "orange");
			el.click();
		} catch (StaleElementReferenceException e1) {
			sleep(3000);
			highlightElement(el, "orange");
			el.click();
		}
	}

	public void waitForElementLoad(List<WebElement> webElement) {
		wait.until(ExpectedConditions.visibilityOfAllElements(webElement));
	}

	public void waitForElementLoadByTime(List<WebElement> webElement, int time) {
		new WebDriverWait(driver, Duration.ofSeconds(time))
				.until(ExpectedConditions.visibilityOfAllElements(webElement));
	}

	public WebElement waitForElementLoad(List<WebElement> webElement, int elemetIndex) {
		wait.until(ExpectedConditions.visibilityOfAllElements(webElement));
		int count = 0;
		while (count < 20) {
			if (webElement.size() < (elemetIndex + 1)) {
				sleep(500);
				count++;
				continue;
			}
			break;
		}
		return webElement.get(elemetIndex);
	}

	public boolean waitForElementAttributeToBe(WebElement webElement, int seconds, String attribute, String value) {
		try {

			new WebDriverWait(driver, Duration.ofSeconds(seconds))
					.until(ExpectedConditions.attributeToBe(webElement, attribute, value));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForElementLoad(WebElement webElement) {
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void waitForElementVisibility(WebElement webElement) {
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public boolean isElementVisible(WebElement webElement) {
		return webElement.isDisplayed();
	}

	public void waitForElementInVisibility(WebElement webElement) {
		wait.until(ExpectedConditions.invisibilityOf((webElement)));
	}

	public void waitForElementVisibility(WebElement webElement, String text) {
		wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
	}

	public void fillText(WebElement el, String text) {
		int count = 0;
		while (count < 10) {
			try {
				waitForElementVisibility(el);
				highlightElement(el, "orange");
				el.clear();
				sendKeys(el, text);
				sleep(2000); // This is only for user view
			} catch (StaleElementReferenceException e) {
				count++;
				sleep(4000);
				if (count == 10)
					throw e;
				continue;
			}
			break;
		}
	}

	public void fillTextNowait(WebElement el, String text) {
		highlightElement(el, "orange");
		el.clear();
		sendKeys(el, text);
		sleep(1000); // This is only for user view
	}

	public void sendKeys(WebElement el, String text) {
		int count = 0;
		while (count < 10) {
			try {
				el.sendKeys(text);
			} catch (StaleElementReferenceException e) {
				count++;
				sleep(200);
				if (count == 10)
					throw e;
				continue;
			}
			break;
		}
	}

	public void fillEmptyText(WebElement el) {
		waitForElementVisibility(el);
		highlightElement(el, "orange");
		el.clear();
		sleep(300); // This is only for user view
	}

	public void fillNoText(WebElement el) {
		waitForElementLoad(el);
		highlightElement(el, "orange");
		el.sendKeys(Keys.CONTROL, Keys.chord("a")); // select all text in textbox
		el.sendKeys(Keys.BACK_SPACE); // delete it
	}

	public String getText(WebElement el) {

		try {
			waitForElementVisibility(el);
			highlightElement(el, "green");
			return el.getText();
		} catch (StaleElementReferenceException e) {
			waitForElementLoad(el);
			sleep(3000);
			return el.getText();
		}
	}

	public String getTextWithNoWait(WebElement el) {
		highlightElement(el, "green");
		return el.getText();
	}

	protected void submit(WebElement el) {
		highlightElement(el, "orange");
		el.submit();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void setUrl(String URL) {
		driver.get(URL);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void maxWindow() {
		driver.manage().window().maximize();
	}

	public void sleep(long milsec) {
		try {
			Thread.sleep(milsec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void switchToFrame(WebElement frameName) {
		driver.switchTo().frame(frameName);
		sleep(1500);
	}

	public void goToFooter() {
		driver.switchTo().defaultContent();
		sleep(1500);
		driver.switchTo().frame(footer);
		sleep(1500);
	}

	public void afterSwitchFrame() {
		driver.switchTo().defaultContent();
		sleep(1500);
	}

	public void moveToNewWindow() {
		mainWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		String lastWindow = "";
		for (String currentWindow : allWindows) {
			lastWindow = currentWindow;
		}
		driver.switchTo().window(lastWindow);
		sleep(2000);
	}

	protected void closeWindow() {
		driver.close();
		sleep(2000);
	}

	public void moveBackToMainWindow() {
		driver.close();
		driver.switchTo().window(mainWindow);
		sleep(2000);
	}

	public void changeFocus(WebElement element) {
		highlightElement(element, "orange");
		Actions actions = new Actions(driver);
		actions.moveToElement(element);

	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void navigateForward() {
		driver.navigate().forward();
	}

	public void browserRefresh() {
		driver.navigate().refresh();
	}

	public List<String> getText(List<WebElement> elementList) {
		List<String> stringList = new ArrayList<String>();
		waitForElementVisibility(elementList.get(0));
		for (WebElement element : elementList) {
			highlightElement(element, "green");
			stringList.add(element.getText());
		}
		return stringList;
	}

	public void javaScriptExecuteClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public String getAttribute(WebElement element, String str) {
		return element.getAttribute(str);
	}

	public void autoScroll(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public void deleteCookies() {
		driver.manage().deleteAllCookies();
	}

	public void closeTabs() {
		Set<String> allWindows = driver.getWindowHandles();
		for (String windowHandle : allWindows) {
			if (!windowHandle.equals(mainWindow)) {
				driver.switchTo().window(windowHandle);
				driver.close();
			}
		}
	}
}
