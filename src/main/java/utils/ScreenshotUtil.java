package utils;

import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtil — captures failure screenshots under test-results/screenshots/.
 */
public final class ScreenshotUtil {

    private static final Path SCREENSHOT_DIR = Paths.get("test-results", "screenshots");

    private ScreenshotUtil() {
        // Utility class — do not instantiate.
    }

    /**
     * Takes a screenshot and returns the file path.
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

    /**
     * Captures a screenshot as PNG bytes (for Allure attachments).
     */
    public static byte[] captureScreenshotBytes(Page page) {
        return page.screenshot();
    }

    /**
     * Reads an existing screenshot file as bytes.
     */
    public static byte[] readBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}
