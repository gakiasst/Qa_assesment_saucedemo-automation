
package org.example.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import java.math.BigDecimal;
import java.util.List;


public class InventoryPage {

    private final Page page;

    // Locators
    private final String activeSortOption = "[data-test='active-option']";
    private final String title = "[data-test='title']";
    private final String inventoryItems = "[data-test='inventory-item']";
    private final String itemName = "[data-test='inventory-item-name']";
    private final String itemPrice = "[data-test='inventory-item-price']";
    private final String sortDropdown = "[data-test='product-sort-container']";
    private final String cartBadge = "[data-test='shopping-cart-badge']";
    private final String cartLink = "[data-test='shopping-cart-link']";

    public InventoryPage(Page page) {
        this.page = page;
    }

    public void waitUntilLoaded() {
        page.locator(title).waitFor();
        page.locator(inventoryItems).first().waitFor();
    }
    public String getTitle() {
        return page.locator(title).textContent().trim();
    }

    public int getProductCount() {
        return page.locator(inventoryItems).count();
    }

    public void sortByPriceLowToHigh() {
        page.locator(sortDropdown).selectOption("lohi");

        PlaywrightAssertions.assertThat(page.locator(activeSortOption))
                .hasText("Price (low to high)");
    }

    public List<String> getProductNames() {
        return page.locator(itemName)
                .allTextContents()
                .stream()
                .map(String::trim)
                .toList();
    }

    public List<BigDecimal> getProductPrices() {
        return page.locator(itemPrice)
                .allTextContents()
                .stream()
                .map(InventoryPage::parsePrice)
                .toList();
    }

    public BigDecimal getCheapestProductPrice() {
        return getProductPrices()
                .stream()
                .min(BigDecimal::compareTo)
                .orElseThrow();
    }

    public List<String> getFirstTwoProductNames() {
        return firstTwo(getProductNames());
    }

    public List<BigDecimal> getFirstTwoProductPrices() {
        return firstTwo(getProductPrices());
    }

    public void addFirstTwoProductsToCart() {
        page.locator(inventoryItems)
                .nth(0)
                .locator("button")
                .click();

        page.locator(inventoryItems)
                .nth(1)
                .locator("button")
                .click();
    }

    public String getCartBadgeCount() {
        return page.locator(cartBadge).textContent().trim();
    }

    public CartPage goToCart() {
        page.locator(cartLink).click();

        CartPage cartPage = new CartPage(page);
        cartPage.waitUntilLoaded();

        return cartPage;
    }

    // Convert price text to BigDecimal
    private static BigDecimal parsePrice(String text) {
        return new BigDecimal(text.replace("$", "").trim());
    }

    // Return first two elements from a list
    private static <T> List<T> firstTwo(List<T> list) {
        if (list.size() < 2) {
            throw new IllegalStateException(
                    "Expected at least 2 products but found " + list.size()
            );
        }

        return list.subList(0, 2);
    }
}
