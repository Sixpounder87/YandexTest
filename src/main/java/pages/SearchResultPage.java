package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.allure.annotations.Step;
import utils.TimeUtil;

public class SearchResultPage extends MarketMainPage {

	private Logger logger = LoggerFactory.getLogger(SearchResultPage.class);

	private static final By NAME_LOCATOR = By
			.cssSelector("h1[itemprop='name']");

	public SearchResultPage(WebDriver driver) {
		TimeUtil.sleepTimeoutSec(2);
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
	}

	@Step("Получить название найденного продукта")
	public String getProductName() {
		try {
			return driver.findElement(NAME_LOCATOR).getText();
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
			return "";
		}
	}

}
