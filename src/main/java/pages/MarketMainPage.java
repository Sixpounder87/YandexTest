package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TimeUtil;

public class MarketMainPage {

	protected static final String URL_MATCH = "https://market.yandex.ru/";
	protected static final By COMPUTERS_LOCATOR = By
			.xpath("//li/a[text()='Компьютеры']");
	protected static final By INPUT_SEARCH_LOCATOR = By.id("header-search");
	protected static final By BUTTON_SEARCH_LOCATOR = By
			.cssSelector("button[type='submit']");

	protected WebDriver driver;

	public MarketMainPage() {
	}

	public MarketMainPage(WebDriver driver) {
		TimeUtil.sleepTimeoutSec(2);
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
	}

	public MarketComputersPage clickComputers() {
		driver.findElement(COMPUTERS_LOCATOR).click();
		return new MarketComputersPage(driver);
	}

	public SearchResultPage search(String request) {
		driver.findElement(INPUT_SEARCH_LOCATOR).sendKeys(request);
		driver.findElement(BUTTON_SEARCH_LOCATOR).click();
		return new SearchResultPage(driver);
	}
}
