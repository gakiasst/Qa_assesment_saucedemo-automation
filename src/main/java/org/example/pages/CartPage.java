package org.example.pages;

import com.microsoft.playwright.Page;

import java.util.List;

public class CartPage {

    private final Page page;

    // Locators
    private final String title = "[data-test='title']";
    private final String cartItems = "[data-test='inventory-item']";
    private final String itemName = "[data-test='inventory-item-name']";
    private final String itemPrice = "[data-test='inventory-item-price']";
    private final String checkoutButton = "[data-test='checkout']";

    public CartPage(Page page) {
        this.page = page;
    }
    public void waitUntilLoaded() {
        page.locator(title).waitFor();
        page.locator(cartItems).first().waitFor();
    }
    public String getPageTitle() {
        return page.locator(title).textContent().trim();
    }

    public List<String> getProductNames() {
        return page.locator(cartItems)
                .locator(itemName)
                .allTextContents()
                .stream()
                .map(String::trim)
                .toList();
    }

    public List<String> getProductPrices() {
        return page.locator(cartItems)
                .locator(itemPrice)
                .allTextContents()
                .stream()
                .map(String::trim)
                .toList();
    }

    public void proceedToCheckout() {
        page.locator(checkoutButton).click();
    }

}