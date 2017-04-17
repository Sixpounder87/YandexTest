package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.allure.annotations.Step;
import utils.TimeUtil;

public class HomePage {

	private Logger logger = LoggerFactory.getLogger(HomePage.class);
	
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
	
	@Step("ѕерейти в €ндекс маркет")
	public MarketMainPage clickMarket() {
		try {
		driver.findElement(MARKET_LOCATOR).click();
		} catch (NoSuchElementException e) {
			logger.error("Ёлемент не найден");
		}
		return new MarketMainPage(driver);
	}
}
