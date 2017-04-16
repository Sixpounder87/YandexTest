package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TimeUtil;

public class HomePage {

	protected static final By MARKET_LOCATOR = By.cssSelector("a[data-id='market']");

	private static final String URL_MATCH = "https://yandex.ru/";
	
	private WebDriver driver;
	
	public HomePage(WebDriver driver) {
		TimeUtil.sleepTimeoutSec(2);
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
	}
	
	public MarketMainPage clickMarket() {
		driver.findElement(MARKET_LOCATOR).click();
		return new MarketMainPage(driver);
	}
}
