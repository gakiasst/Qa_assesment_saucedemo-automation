
package org.example.tests;

import org.example.base.BaseTest;
import org.example.config.Config;
import org.example.pages.LoginPage;
import org.example.pages.InventoryPage;
import org.example.pages.CartPage;
import org.example.pages.CheckoutPage;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseFlowTest extends BaseTest {

    @Test
    public void completePurchase() {

        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateTo(Config.baseUrl());
        loginPage.login(Config.standardUser(), Config.password());

        InventoryPage inventoryPage = new InventoryPage(page);

        inventoryPage.waitUntilLoaded();

        assertEquals(
                "Products",
                inventoryPage.getTitle(),
                "Inventory page should be displayed after login"
        );

        assertEquals(
                6,
                inventoryPage.getProductCount(),
                "Inventory page should display exactly 6 products"
        );

        inventoryPage.sortByPriceLowToHigh();

        List<BigDecimal> displayedPrices =
                inventoryPage.getProductPrices();

        List<BigDecimal> sortedPrices = displayedPrices.stream()
                .sorted()
                .toList();

        assertEquals(
                sortedPrices,
                displayedPrices,
                "Products should be sorted by price from low to high"
        );

        assertEquals(
                inventoryPage.getCheapestProductPrice(),
                displayedPrices.get(0),
                "The first displayed product should have the cheapest price"
        );

        List<String> expectedProductNames =
                inventoryPage.getFirstTwoProductNames();

        List<BigDecimal> expectedProductPrices =
                inventoryPage.getFirstTwoProductPrices();

        inventoryPage.addFirstTwoProductsToCart();

        assertEquals(
                "2",
                inventoryPage.getCartBadgeCount(),
                "Cart badge should display 2 items after adding two products"
        );

        CartPage cartPage = inventoryPage.goToCart();

        assertEquals(
                "Your Cart",
                cartPage.getPageTitle(),
                "Cart page should be displayed"
        );

        assertEquals(
                2,
                cartPage.getProductNames().size(),
                "Cart should contain exactly two products"
        );

        assertEquals(
                expectedProductNames,
                cartPage.getProductNames(),
                "Cart should contain the two selected products"
        );

        assertEquals(
                expectedProductPrices.stream()
                        .map(BigDecimal::toPlainString)
                        .toList(),
                cartPage.getProductPrices().stream()
                        .map(price -> new BigDecimal(
                                price.replace("$", "").trim()
                        ).toPlainString())
                        .toList(),
                "Cart should display the correct prices for the selected products"
        );

        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(page);

        checkoutPage.waitUntilInformationStepLoaded();

        assertEquals(
                "Checkout: Your Information",
                checkoutPage.getPageTitle(),
                "Checkout information page should be displayed"
        );

        checkoutPage.fillCustomerInformation(
                Config.firstName(),
                Config.lastName(),
                Config.postalCode()
        );

        checkoutPage.continueToOverview();

        assertEquals(
                "Checkout: Overview",
                checkoutPage.getPageTitle(),
                "Checkout overview page should be displayed"
        );

        checkoutPage.finish();

        assertEquals(
                "Thank you for your order!",
                checkoutPage.getSuccessMessage(),
                "Purchase should be completed successfully"
        );

    }

}
