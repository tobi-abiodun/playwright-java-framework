package utils;

import com.microsoft.playwright.Page;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtil
 * --------------
 * Captures a screenshot when a test fails (or on demand).
 * Files are saved under test-results/screenshots/.
 */
public final class ScreenshotUtil {

    private static final Path SCREENSHOT_DIR = Paths.get("test-results", "screenshots");

    private ScreenshotUtil() {
        // Utility class — do not instantiate.
    }

    /**
     * Takes a screenshot and returns the file path.
     *
     * @param page     active Playwright page
     * @param testName name used in the screenshot file name
     */
    public static String captureScreenshot(Page page, String testName) {
        try {
            if (!Files.exists(SCREENSHOT_DIR)) {
                Files.createDirectories(SCREENSHOT_DIR);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
            Path screenshotPath = SCREENSHOT_DIR.resolve(safeName + "_" + timestamp + ".png");

            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            return screenshotPath.toString();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to capture screenshot for test: " + testName, exception);
        }
    }
}
