package testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import pages.HomePage;
import pages.MarketComputersPage;
import pages.MarketMainPage;
import pages.MarketProductPage;
import pages.SearchResultPage;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;
import utils.ReadPropertyData;

@Title("Yandex Market test suite")
public final class YandexTest {

	private WebDriver driver;

	@Before
	public void preconditions() {
		System.setProperty("webdriver.chrome.driver",
				ReadPropertyData.getDriverPath());
		final ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
	}

	@Title("Тест 01. Тестирование выбора ноутбуков")
	@Test
	public void test01Laptops() {
		final MarketComputersPage marketComputersPage = getComputersMarketPage();
		final MarketProductPage marketLaptopsPage = marketComputersPage
				.clickLaptops();
		marketLaptopsPage.inputUpperPrice("30000");
		marketLaptopsPage.tickHP();
		marketLaptopsPage.tickLenovo();
		marketLaptopsPage.apply();
		makeVerification(marketLaptopsPage, 12, 1);
	}

	@Title("Тест 02. Тестирование выбора планшетов")
	@Test
	public void test02Tablets() {
		final MarketComputersPage marketComputersPage = getComputersMarketPage();
		final MarketProductPage marketTabletsPage = marketComputersPage
				.clickTablets();
		marketTabletsPage.inputLowerPrice("20000");
		marketTabletsPage.inputUpperPrice("25000");
		marketTabletsPage.tickAcer();
		marketTabletsPage.tickDell();
		marketTabletsPage.apply();
		makeVerification(marketTabletsPage, 12, 1);
	}

	@After
	public void postconditions() {
		driver.quit();
	}

	@Step("Зайти на страницу {0}")
	private void openPage(String url) {
		driver.get(url);
	}

	@Step("Проверить, что количество элементов на странице {0}")
	private void checkNumberOfElements(int number, MarketProductPage page) {
		assertEquals(number, page.countProducts());
	}

	@Step("Найти и проверить, что наименование товара соответствует {1}")
	private void verifyProductName(MarketProductPage page, String productName) {
		final SearchResultPage searchResultPage = page.search(productName);
		assertEquals(productName, searchResultPage.getProductName());
	}

	private MarketComputersPage getComputersMarketPage() {
		openPage(ReadPropertyData.getBaseUrl());
		final HomePage homePage = new HomePage(driver);
		final MarketMainPage marketMainPage = homePage.clickMarket();
		return marketMainPage.clickComputers();
	}

	private void makeVerification(MarketProductPage page, int numberOfElements,
			int elementInSearchNumber) {
		checkNumberOfElements(numberOfElements, page);
		final String firstElementName = page
				.getProductName(elementInSearchNumber);
		verifyProductName(page, firstElementName);
	}
}
