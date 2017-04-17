package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.allure.annotations.Step;
import utils.TimeUtil;

public class MarketComputersPage extends MarketMainPage {

	private Logger logger = LoggerFactory.getLogger(MarketComputersPage.class);

	private static final By LAPTOPS_LOCATOR = By
			.xpath("//div[@class='catalog-menu__list']/a[text()='Ноутбуки']");
	private static final By TABLETS_LOCATOR = By
			.xpath("//div[@class='catalog-menu__list']/a[text()='Планшеты']");

	public MarketComputersPage(WebDriver driver) {
		TimeUtil.sleepTimeoutSec(2);
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
	}

	@Step("Выбрать раздел Ноутбуки")
	public MarketProductPage clickLaptops() {
		return clickProduct(LAPTOPS_LOCATOR);
	}

	@Step("Выбрать раздел Планшеты")
	public MarketProductPage clickTablets() {
		return clickProduct(TABLETS_LOCATOR);
	}

	private MarketProductPage clickProduct(By by) {
		try {
			driver.findElement(by).click();
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
		}
		return new MarketProductPage(driver);
	}
}
