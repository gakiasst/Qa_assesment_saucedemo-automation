# SauceDemo Automation

## About the project

This project contains automated tests for the SauceDemo website.

The main test checks the purchase process, from login to order confirmation. There is also a test for the locked-out user.

Screenshots are automatically captured when a test fails, making it easier to investigate failures.

## Tools used

- Java 21
- Playwright
- Maven
- JUnit 5
- Page Object Model (POM)
- java-dotenv

## Project structure


src/
├── main/java/org/example/
│   ├── config/
│   │   └── Config.java
│   └── pages/
│       ├── LoginPage.java
│       ├── InventoryPage.java
│       ├── CartPage.java
│       └── CheckoutPage.java
└── test/java/org/example/
    ├── base/
    │   └── BaseTest.java
    └── tests/
        ├── PurchaseFlowTest.java
        └── LoginTest.java

.env.example
.gitignore
pom.xml
README.md


## Setup

The project requires:

- Java JDK 21
- IntelliJ IDEA
- Maven
- Playwright Chromium

Create a `.env` file in the project root by copying the values from `.env.example`.

Example:

```env
BASE_URL=https://www.saucedemo.com/
SAUCE_USERNAME=standard_user
LOCKED_OUT_USER=locked_out_user
PASSWORD=secret_sauce
FIRST_NAME=Test
LAST_NAME=User
POSTAL_CODE=12345
```

The `.env` file is not included in version control.

## Tests

### Purchase flow

The purchase test (`PurchaseFlowTest`) checks the following:

1. Login with `standard_user`.
2. Verify that the products page contains six products.
3. Sort products by price, from low to high.
4. Select the two cheapest products.
5. Add them to the cart.
6. Verify the cart contents and prices.
7. Enter checkout information.
8. Complete the purchase.
9. Verify the message `Thank you for your order!`.

### Locked-out user

A separate test (`LoginTest`) tries to log in with `locked_out_user` and checks that the expected error message is displayed.

## Running the tests

1. Open the project in IntelliJ IDEA.
2. Make sure the `.env` file is configured.
3. Open the Maven tool window.
4. Run the `test` goal under Lifecycle.

You can also run the tests directly from IntelliJ IDEA.

## Tech Stack and Assumptions

I chose Java in IntelliJ because I am already familiar with it and have experience working with it. I chose Playwright because I know it is also used in the company, so I wanted to gain more experience with a tool that is relevant to the working environment. I used JUnit 5 for the tests, Maven for dependency management and the Page Object Model to keep the code organized and easier to maintain.

The tests use the standard SauceDemo test accounts and sample customer information. The main purchase flow and the locked-out user scenario are covered.

## Notes

- The tests use the standard SauceDemo test accounts.
- Checkout details are test data.
- Browser resources are opened and closed for each test.
- Screenshots are saved in `target/screenshots` when a test fails.
- The `target` folder contains generated files and is not needed in the final submission.
