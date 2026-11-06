
package org.example.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    // Captures a screenshot when a test method fails,
    @RegisterExtension
    protected final TestExecutionExceptionHandler screenshotOnFailure =
            (extensionContext, exception) -> {

                try {
                    if (page != null && !page.isClosed()) {

                        Path screenshotDirectory =
                                Path.of("target", "screenshots");

                        Files.createDirectories(screenshotDirectory);

                        String testName = extensionContext
                                .getRequiredTestMethod()
                                .getName();

                        String timestamp = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

                        Path screenshotPath = screenshotDirectory.resolve(
                                testName + "-" + timestamp + ".png"
                        );

                        page.screenshot(
                                new Page.ScreenshotOptions()
                                        .setPath(screenshotPath)
                        );

                        System.out.println(
                                "Failure screenshot saved at: " + screenshotPath
                        );
                    }

                } catch (Exception screenshotException) {
                    exception.addSuppressed(screenshotException);
                }

                throw exception;
            };

    // new browser session before each test.
    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1280, 720)
        );

        context.setDefaultTimeout(10_000);

        page = context.newPage();
    }

    // Closes browser resources after each test
    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}
