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

    public void assertThankYouMessageContains(String expectedText) {
        assertVisible(page.completeHeader(), "complete header");
        String header = page.completeHeader().innerText();
        if (header == null || !header.contains(expectedText)) {
            // Fall back to complete text body if header wording differs
            String body = page.completeText().count() > 0 ? page.completeText().innerText() : "";
            if ((header == null || !header.contains(expectedText))
                    && (body == null || !body.contains(expectedText))) {
                throw new AssertionError(
                        "Expected thank-you text to contain '" + expectedText + "' but header was '"
                                + header + "' and body was '" + body + "'.");
            }
        }
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

    public void assertInventoryUrl() {
        String url = page.page().url();
        if (!url.contains("/inventory.html")) {
            throw new AssertionError("Expected /inventory.html but URL was " + url);
        }
    }

    public void assertCartBadgeHidden() {
        Locator badge = page.page().getByTestId("shopping-cart-badge");
        if (badge.count() > 0 && badge.first().isVisible()) {
            throw new AssertionError("Expected cart badge to be hidden after Back Home.");
        }
    }

    private void assertVisible(Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
