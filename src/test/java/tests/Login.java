package tests;

import bbi.LoginUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Login
 * -----
 * Playwright Java test class.
 * Creates a browser, instantiates LoginUtil, and runs the reusable login flows.
 */
public class Login {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginUtil loginUtil;

    @BeforeEach
    void setUp() {
        // Start Playwright and open a Chromium browser for each test
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        page = browser.newPage();
        loginUtil = new LoginUtil(page);
    }

    @AfterEach
    void tearDown() {
        // Close browser resources after each test
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    void testSuccessfulLogin() {
        loginUtil.loginFlow("admin", "password123");
    }

    @Test
    void testFailedLogin() {
        loginUtil.loginFlowExpectingFailure("wrongUser", "wrongPass");
    }
}
