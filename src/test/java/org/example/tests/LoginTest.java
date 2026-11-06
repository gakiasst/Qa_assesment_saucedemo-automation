package org.example.tests;

import org.example.base.BaseTest;
import org.example.config.Config;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends BaseTest {

    // Verifies that a locked-out user cannot log in.
    @Test
    public void shouldDisplayErrorWhenLockedOutUserTriesToLogin() {

        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo(Config.baseUrl());

        loginPage.login(
                Config.lockedOutUser(),
                Config.password()
        );

        String expectedError =
                "Epic sadface: Sorry, this user has been locked out.";

        assertEquals(
                expectedError,
                loginPage.getErrorMessage(),
                "A locked-out user should not be able to log in"
        );
    }
}
