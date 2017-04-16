package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.TimeUtil;

public class MarketProductPage extends MarketMainPage {

	private static final By DOWN_PRICE_BORDER_LOCATOR = By.id("glf-pricefrom-var");
	private static final By UP_PRICE_BORDER_LOCATOR = By.id("glf-priceto-var");
	private static final By HP_CHECKBOX_LOCATOR = By
			.xpath("//label[text()='HP']/..");
	private static final By LENOVO_CHECKBOX_LOCATOR = By
			.xpath("//label[text()='Lenovo']/..");
	private static final By BUTTON_APPLY_LOCATOR = By
			.cssSelector("button.button_action_n-filter-apply");
	private static final By PRODUCTS_LOCATOR = By
			.cssSelector("div.n-snippet-card");

	public MarketProductPage(WebDriver driver) {
		TimeUtil.sleepTimeoutSec(2);
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
	}

	public void inputLowerPrice(String price) {
		driver.findElement(DOWN_PRICE_BORDER_LOCATOR).sendKeys(price);
	}

	public void inputUpperPrice(String price) {
		driver.findElement(UP_PRICE_BORDER_LOCATOR).sendKeys(price);
	}

	public void tickHP() {
		tickManufacturer(HP_CHECKBOX_LOCATOR);
	}

	public void tickLenovo() {
		tickManufacturer(LENOVO_CHECKBOX_LOCATOR);
	}

	private void tickManufacturer(By by) {
		WebElement manufacturer = driver.findElement(by);
		if (!manufacturer.isSelected()) {
			manufacturer.click();
		}
	}

	public void apply() {
		driver.findElement(BUTTON_APPLY_LOCATOR).click();
		TimeUtil.sleepTimeoutSec(3);
	}

	public int countProducts() {
		List<WebElement> list = driver.findElements(PRODUCTS_LOCATOR);
		return list.size();
	}

	public String getProductName(int serialNumber) {

		StringBuilder locatorString = new StringBuilder();
		String firstPartLocatorString;
		String lastPartLocatorString;

		if (serialNumber < 4) {
			firstPartLocatorString = "div.metrika_js_inited > div:nth-child(";
			lastPartLocatorString = ") > div.snippet-card__view > a";

			locatorString.append(firstPartLocatorString).append(serialNumber)
					.append(lastPartLocatorString);
		} else {
			firstPartLocatorString = "div:nth-child(3) > div:nth-child(";
			lastPartLocatorString = ") > div.snippet-card__content > div > div:nth-child(1) > div > h3 > a";

			locatorString.append(firstPartLocatorString)
					.append(serialNumber - 3).append(lastPartLocatorString);
		}

		WebElement product = driver.findElement(By.cssSelector(locatorString
				.toString()));
		return product.getAttribute("title").substring(8);
	}
}
