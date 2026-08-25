package utils;

import com.microsoft.playwright.Locator;
import pages.OrderConfirmationPage;

/**
 * OrderConfirmationPageUtil — actions/asserts for Checkout Complete / thank you only.
 */
public class OrderConfirmationPageUtil {

    private final OrderConfirmationPage page;

    public OrderConfirmationPageUtil(OrderConfirmationPage page) {
        this.page = page;
    }

    public void clickBackHome() {
        page.backHomeButton().click();
    }

    public void assertHeadingVisible() {
        assertVisible(page.heading(), "Thank you for your order heading");
    }

    public void assertCompleteHeaderVisible() {
        assertVisible(page.completeHeader(), "complete header");
    }

    public void assertBackHomeVisible() {
        assertVisible(page.backHomeButton(), "Back Home button");
    }

    public void assertUrl() {
        String url = page.page().url();
        if (!url.contains("/checkout-complete.html")) {
            throw new AssertionError("Expected order confirmation URL but was " + url);
        }
    }

    private void assertVisible(Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
