package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import config.ConfigReader;
import utils.LoggerUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * PlaywrightFactory — creates browser sessions from config (with CLI -D overrides).
 */
public class PlaywrightFactory {

    private static final Path TRACE_DIR = Paths.get("test-results", "traces");

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private boolean tracingStarted;

    /**
     * Starts Playwright, launches the configured browser, starts tracing, and returns a new Page.
     */
    public Page createPage() {
        playwright = Playwright.create();
        // Sauce Demo uses data-test, not the default data-testid
        playwright.selectors().setTestIdAttribute("data-test");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(ConfigReader.isHeadless());

        browser = selectBrowser(playwright).launch(launchOptions);
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        tracingStarted = true;

        page = context.newPage();

        double timeout = ConfigReader.getTimeout();
        page.setDefaultTimeout(timeout);
        page.setDefaultNavigationTimeout(timeout);

        return page;
    }

    /**
     * Stops tracing: saves a zip on failure, discards on pass.
     */
    public void stopTracing(boolean save, String testName) {
        if (context == null || !tracingStarted) {
            return;
        }
        try {
            if (save) {
                if (!Files.exists(TRACE_DIR)) {
                    Files.createDirectories(TRACE_DIR);
                }
                String safeName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
                Path tracePath = TRACE_DIR.resolve(safeName + ".zip");
                context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
                LoggerUtil.info("Saved Playwright trace: " + tracePath);
            } else {
                context.tracing().stop();
            }
        } catch (Exception exception) {
            LoggerUtil.error("Failed to stop Playwright tracing", exception);
        } finally {
            tracingStarted = false;
        }
    }

    /** Closes page, context, browser, and Playwright. */
    public void close() {
        if (page != null) {
            try {
                page.close();
            } catch (Exception ignored) {
                // already closed
            }
            page = null;
        }
        if (context != null) {
            try {
                context.close();
            } catch (Exception ignored) {
                // already closed
            }
            context = null;
        }
        if (browser != null) {
            try {
                browser.close();
            } catch (Exception ignored) {
                // already closed
            }
            browser = null;
        }
        if (playwright != null) {
            try {
                playwright.close();
            } catch (Exception ignored) {
                // already closed
            }
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
