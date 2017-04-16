package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TimeUtil;

public class SearchResultPage extends MarketMainPage {

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

	public String getProductName() {
		return driver.findElement(NAME_LOCATOR).getText();
	}

}
