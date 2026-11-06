
package org.example.pages;

import com.microsoft.playwright.Page;

public class CheckoutPage {

    private final Page page;

    // Locators
    private final String title = "[data-test='title']";
    private final String firstNameInput = "[data-test='firstName']";
    private final String lastNameInput = "[data-test='lastName']";
    private final String postalCodeInput = "[data-test='postalCode']";
    private final String continueButton = "[data-test='continue']";
    private final String finishButton = "[data-test='finish']";
    private final String successMessage = "[data-test='complete-header']";

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public void waitUntilInformationStepLoaded() {
        page.locator(title).waitFor();
    }

    public String getPageTitle() {
        return page.locator(title).textContent().trim();
    }

    public void fillCustomerInformation(
            String firstName,
            String lastName,
            String postalCode) {

        page.locator(firstNameInput).fill(firstName);
        page.locator(lastNameInput).fill(lastName);
        page.locator(postalCodeInput).fill(postalCode);
    }

    public void continueToOverview() {
        page.locator(continueButton).click();

        page.locator(title).waitFor();
    }

    public void finish() {
        page.locator(finishButton).click();
        page.locator(successMessage).waitFor();
    }

    public String getSuccessMessage() {
        return page.locator(successMessage).textContent().trim();
    }
}
