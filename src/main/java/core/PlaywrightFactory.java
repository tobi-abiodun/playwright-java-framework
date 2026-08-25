package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.ConfigReader;

/**
 * PlaywrightFactory
 * -----------------
 * Creates and closes Playwright browser sessions using values from config.properties.
 * All tests should get their Page from here (via BaseTest) instead of creating Playwright manually.
 */
public class PlaywrightFactory {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    /**
     * Starts Playwright, launches the configured browser, and returns a new Page.
     * Applies default timeouts from config.
     */
    public Page createPage() {
        playwright = Playwright.create();
        // Sauce Demo uses data-test, not the default data-testid
        playwright.selectors().setTestIdAttribute("data-test");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(ConfigReader.isHeadless());

        browser = selectBrowser(playwright).launch(launchOptions);
        page = browser.newPage();

        double timeout = ConfigReader.getTimeout();
        page.setDefaultTimeout(timeout);
        page.setDefaultNavigationTimeout(timeout);

        return page;
    }

    /** Closes page, browser, and Playwright. Safe to call even if createPage() was not called. */
    public void close() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    private BrowserType selectBrowser(Playwright playwrightInstance) {
        String browserName = ConfigReader.getBrowser().toLowerCase();

        switch (browserName) {
            case "firefox":
                return playwrightInstance.firefox();
            case "webkit":
                return playwrightInstance.webkit();
            case "chromium":
            default:
                return playwrightInstance.chromium();
        }
    }
}
