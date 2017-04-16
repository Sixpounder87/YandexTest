package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TimeUtil;

public class MarketComputersPage extends MarketMainPage {

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

	public MarketProductPage clickLaptops() {
		return clickProduct(LAPTOPS_LOCATOR);
	}

	public MarketProductPage clickTablets() {
		return clickProduct(TABLETS_LOCATOR);
	}

	private MarketProductPage clickProduct(By by) {
		driver.findElement(by).click();
		return new MarketProductPage(driver);
	}
}
