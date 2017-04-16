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
import utils.ReadPropertyData;

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

	@Test
	public void test01Laptops() {
		driver.get(ReadPropertyData.getBaseUrl());
		final HomePage homePage = new HomePage(driver);
		final MarketMainPage marketMainPage = homePage.clickMarket();
		final MarketComputersPage marketComputersPage = marketMainPage
				.clickComputers();
		final MarketProductPage marketLaptopsPage = marketComputersPage
				.clickLaptops();
		marketLaptopsPage.inputUpperPrice("30000");
		marketLaptopsPage.tickHP();
		marketLaptopsPage.tickLenovo();
		marketLaptopsPage.apply();
		assertEquals(12, marketLaptopsPage.countProducts());
		final String firstElementName = marketLaptopsPage.getProductName(1);
		final SearchResultPage searchResultPage = marketLaptopsPage
				.search(firstElementName);
		assertEquals(firstElementName, searchResultPage.getProductName());
	}

	@Test
	public void test02Tablets() {
		driver.get(ReadPropertyData.getBaseUrl());
		final HomePage homePage = new HomePage(driver);
		final MarketMainPage marketMainPage = homePage.clickMarket();
		final MarketComputersPage marketComputersPage = marketMainPage
				.clickComputers();
		final MarketProductPage marketTabletsPage = marketComputersPage
				.clickTablets();
		marketTabletsPage.inputLowerPrice("20000");
		marketTabletsPage.inputUpperPrice("25000");
		marketTabletsPage.tickAcer();
		marketTabletsPage.tickDell();
		marketTabletsPage.apply();
		assertEquals(12, marketTabletsPage.countProducts());
		final String firstElementName = marketTabletsPage.getProductName(1);
		final SearchResultPage searchResultPage = marketTabletsPage
				.search(firstElementName);
		assertEquals(firstElementName, searchResultPage.getProductName());
	}

	@After
	public void postconditions() {
		driver.quit();
	}
}
