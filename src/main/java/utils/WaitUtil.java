package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import config.ConfigReader;

/**
 * WaitUtil
 * --------
 * Helper methods for waiting on elements before interacting or checking state.
 * Uses the timeout from config.properties.
 */
public final class WaitUtil {

    private WaitUtil() {
        // Utility class — do not instantiate.
    }

    /**
     * Waits until the selector is visible on the page.
     * Returns true if visible within the timeout; false if the wait times out.
     */
    public static boolean waitForVisible(Page page, String selector) {
        try {
            page.locator(selector).waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(ConfigReader.getTimeout())
            );
            return true;
        } catch (PlaywrightException exception) {
            return false;
        }
    }

    /**
     * Waits until the locator is hidden (or not present).
     * Returns true if hidden within the timeout; false if still visible.
     */
    public static boolean waitForHidden(Locator locator) {
        try {
            locator.waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.HIDDEN)
                            .setTimeout(ConfigReader.getTimeout())
            );
            return true;
        } catch (PlaywrightException exception) {
            return !locator.isVisible();
        }
    }

    /**
     * Waits until the locator is visible.
     * Returns true if visible within the timeout; false if the wait times out.
     */
    public static boolean waitForVisible(Locator locator) {
        try {
            locator.waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(ConfigReader.getTimeout())
            );
            return true;
        } catch (PlaywrightException exception) {
            return false;
        }
    }
}
