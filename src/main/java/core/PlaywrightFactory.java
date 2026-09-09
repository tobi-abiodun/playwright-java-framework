package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.Video;
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
    private static final Path VIDEO_DIR = Paths.get("test-results", "videos");

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private boolean tracingStarted;
    private boolean videoEnabled;

    /**
     * Starts Playwright, launches the configured browser, starts tracing (and video when enabled),
     * and returns a new Page.
     */
    public Page createPage() {
        playwright = Playwright.create();
        // Sauce Demo uses data-test, not the default data-testid
        playwright.selectors().setTestIdAttribute("data-test");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(ConfigReader.isHeadless());

        browser = selectBrowser(playwright).launch(launchOptions);

        videoEnabled = ConfigReader.isVideoEnabled();
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        if (videoEnabled) {
            try {
                Files.createDirectories(VIDEO_DIR);
            } catch (Exception exception) {
                throw new RuntimeException("Failed to create video directory: " + VIDEO_DIR, exception);
            }
            contextOptions.setRecordVideoDir(VIDEO_DIR);
        }
        context = browser.newContext(contextOptions);

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

    /**
     * Closes the page and context so Playwright finalizes the video file.
     * Keeps the Page reference so {@link #finalizeVideo(String)} can call saveAs.
     */
    public void closePageAndContext() {
        if (page != null) {
            try {
                page.close();
            } catch (Exception ignored) {
                // already closed
            }
        }
        if (context != null) {
            try {
                context.close();
            } catch (Exception ignored) {
                // already closed
            }
            context = null;
        }
    }

    /**
     * After page/context close, saves the recorded video as test-results/videos/&lt;testName&gt;.webm.
     *
     * @return path to the saved video, or null if video was disabled / unavailable
     */
    public Path finalizeVideo(String testName) {
        if (!videoEnabled || page == null) {
            return null;
        }
        try {
            Video video = page.video();
            if (video == null) {
                return null;
            }
            Files.createDirectories(VIDEO_DIR);
            String safeName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
            Path target = VIDEO_DIR.resolve(safeName + ".webm");
            video.saveAs(target);
            try {
                video.delete();
            } catch (Exception ignored) {
                // Original temp file may already be gone after saveAs
            }
            LoggerUtil.info("Saved Playwright video: " + target);
            return target;
        } catch (Exception exception) {
            LoggerUtil.error("Failed to finalize Playwright video for " + testName, exception);
            return null;
        }
    }

    /** Closes browser and Playwright and clears the page reference. */
    public void closeBrowser() {
        page = null;
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

    /** Full shutdown (used if teardown is interrupted). */
    public void close() {
        closePageAndContext();
        closeBrowser();
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
