package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.allure.annotations.Step;
import utils.TimeUtil;

public class MarketProductPage extends MarketMainPage {

	private Logger logger = LoggerFactory.getLogger(MarketProductPage.class);

	private static final By DOWN_PRICE_BORDER_LOCATOR = By
			.id("glf-pricefrom-var");
	private static final By UP_PRICE_BORDER_LOCATOR = By.id("glf-priceto-var");
	private static final By HP_CHECKBOX_LOCATOR = By
			.xpath("//label[text()='HP']/..");
	private static final By LENOVO_CHECKBOX_LOCATOR = By
			.xpath("//label[text()='Lenovo']/..");
	private static final By ACER_CHECKBOX_LOCATOR = By
			.xpath("//label[text()='Acer']/..");
	private static final By DELL_CHECKBOX_LOCATOR = By
			.xpath("//label[text()='DELL']/..");
	private static final By BUTTON_APPLY_LOCATOR = By
			.cssSelector("button.button_action_n-filter-apply");
	private static final By PRODUCTS_LOCATOR = By
			.cssSelector("div.n-snippet-card");
	private static final By BUTTON_MORE_LOCATOR = By
			.xpath("//span[text()='Ещё']/..");
	private static final By INPUT_MANUFACTURER_LOCATOR = By
			.cssSelector("div:nth-child(4) > div.n-filter-block__body > div > span > span > input");

	private WebDriverWait wait;
	private final static int TIMEOUTsec = 5;

	public MarketProductPage(WebDriver driver) {
		TimeUtil.sleepTimeoutSec(2);
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
		wait = new WebDriverWait(driver, TIMEOUTsec);
	}

	@Step("Задать нижний ценовой диапазон {0}")
	public void inputLowerPrice(String price) {
		try {
			driver.findElement(DOWN_PRICE_BORDER_LOCATOR).sendKeys(price);
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
		}
	}

	@Step("Задать верхний ценовой диапазон {0}")
	public void inputUpperPrice(String price) {
		try {
			driver.findElement(UP_PRICE_BORDER_LOCATOR).sendKeys(price);
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
		}
	}

	public void tickHP() {
		tickManufacturer(HP_CHECKBOX_LOCATOR, "HP");
	}

	public void tickLenovo() {
		tickManufacturer(LENOVO_CHECKBOX_LOCATOR, "Lenovo");
	}

	public void tickAcer() {
		tickManufacturer(ACER_CHECKBOX_LOCATOR, "Acer");
	}

	public void tickDell() {
		tickManufacturer(DELL_CHECKBOX_LOCATOR, "Dell");
	}

	@Step("Выбрать марку {1}")
	private void tickManufacturer(By by, String manufacturer) {
		WebElement manufacturerElement;
		try {
			manufacturerElement = wait.until(ExpectedConditions
					.elementToBeClickable(by));
		} catch (TimeoutException e) {
			driver.findElement(BUTTON_MORE_LOCATOR).click();
			WebElement inputManufacturer = wait.until(ExpectedConditions
					.presenceOfElementLocated(INPUT_MANUFACTURER_LOCATOR));
			inputManufacturer.sendKeys(manufacturer);
			TimeUtil.sleepTimeoutSec(2);
			manufacturerElement = wait.until(ExpectedConditions
					.elementToBeClickable(by));
		}
		if (!manufacturerElement.isSelected()) {
			manufacturerElement.click();
		}
		TimeUtil.sleepTimeoutSec(2);
	}

	@Step("Нажать кнопку Применить")
	public void apply() {
		try {
			driver.findElement(BUTTON_APPLY_LOCATOR).click();
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
		}
		wait.until(ExpectedConditions
				.presenceOfElementLocated(PRODUCTS_LOCATOR));
	}

	@Step("Посчитать количество элементов поиска")
	public int countProducts() {
		List<WebElement> list = driver.findElements(PRODUCTS_LOCATOR);
		return list.size();
	}

	@Step("Получить название {0}-го элемента")
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

		try {
			WebElement product = driver.findElement(By
					.cssSelector(locatorString.toString()));
			return product.getAttribute("title").substring(8);
		} catch (NoSuchElementException e) {
			logger.error("Элемент не найден");
			return "";
		}
	}
}
