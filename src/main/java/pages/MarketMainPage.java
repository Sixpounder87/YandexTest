package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.allure.annotations.Step;
import utils.TimeUtil;

public class MarketMainPage {

	private Logger logger = LoggerFactory.getLogger(MarketMainPage.class);

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

	@Step("Выбрать раздел Компьютеры")
	public MarketComputersPage clickComputers() {
		try {
			driver.findElement(COMPUTERS_LOCATOR).click();
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
		}
		return new MarketComputersPage(driver);
	}

	@Step("Найти в поисковой строке \"{0}\"")
	public SearchResultPage search(String request) {
		try {
			driver.findElement(INPUT_SEARCH_LOCATOR).sendKeys(request);
			driver.findElement(BUTTON_SEARCH_LOCATOR).click();
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
		}
		return new SearchResultPage(driver);
	}
}
