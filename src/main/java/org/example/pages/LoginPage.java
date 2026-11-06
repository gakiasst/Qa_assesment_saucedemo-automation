
package org.example.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

        private final Page page;

        // Locators
        private final String usernameInput = "[data-test='username']";
        private final String passwordInput = "[data-test='password']";
        private final String loginButton = "[data-test='login-button']";
        private final String errorMessage = "[data-test='error']";

        public LoginPage(Page page) {
            this.page = page;
        }

        public void navigateTo(String baseUrl) {
            page.navigate(baseUrl);
        }

        public void login(String username, String password) {
            page.locator(usernameInput).fill(username);
            page.locator(passwordInput).fill(password);
            page.locator(loginButton).click();
        }

    public String getErrorMessage() {
        return page.locator(errorMessage).textContent().trim();
    }
}