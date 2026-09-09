package utils;

import com.microsoft.playwright.Locator;
import pages.OrderSummaryPage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OrderSummaryPageUtil — order summary / overview.
 */
public class OrderSummaryPageUtil {

    private static final Pattern MONEY = Pattern.compile("(-?\\d+(?:\\.\\d+)?)");

    private final OrderSummaryPage page;

    public OrderSummaryPageUtil(OrderSummaryPage page) {
        this.page = page;
    }

    public void clickFinish() {
        page.finishButton().click();
    }

    public void clickCancel() {
        page.cancelButton().click();
    }

    public void assertHeadingVisible() {
        assertVisible(page.heading(), "Checkout: Overview heading");
        String title = page.heading().innerText().trim();
        if (!"Checkout: Overview".equals(title)) {
            throw new AssertionError("Expected title 'Checkout: Overview' but was '" + title + "'.");
        }
    }

    public void assertCartListVisible() {
        assertVisible(page.cartList(), "order summary cart list");
    }

    public void assertFinishVisible() {
        assertVisible(page.finishButton(), "Finish button");
    }

    public void assertCancelVisible() {
        assertVisible(page.cancelButton(), "Cancel button");
    }

    public void assertTotalsVisible() {
        assertVisible(page.itemTotalLabel(), "item total");
        assertVisible(page.taxLabel(), "tax");
        assertVisible(page.totalLabel(), "total");
    }

    public void assertPaymentAndShippingVisible() {
        assertVisible(page.paymentInfoLabel(), "payment information");
        assertVisible(page.shippingInfoLabel(), "shipping information");
    }

    public void assertItemVisible(String itemName) {
        Locator item = page.itemName().filter(new Locator.FilterOptions().setHasText(itemName));
        assertVisible(item, "summary item " + itemName);
    }

    public void assertItemTotalContains(String priceText) {
        assertVisible(page.itemTotalLabel(), "item total");
        String actual = page.itemTotalLabel().innerText();
        if (actual == null || !actual.contains(priceText)) {
            throw new AssertionError("Expected item total to contain '" + priceText + "' but was '" + actual + "'.");
        }
    }

    /** Asserts Total label equals Item total + Tax (parsed from on-page labels). */
    public void assertTotalEqualsItemPlusTax() {
        assertTotalsVisible();
        double itemTotal = parseMoney(page.itemTotalLabel().innerText());
        double tax = parseMoney(page.taxLabel().innerText());
        double total = parseMoney(page.totalLabel().innerText());
        double expected = Math.round((itemTotal + tax) * 100.0) / 100.0;
        if (Math.abs(total - expected) > 0.001) {
            throw new AssertionError(
                    "Expected Total (" + total + ") to equal Item total (" + itemTotal + ") + Tax (" + tax
                            + ") = " + expected);
        }
    }

    public void assertNoLineItems() {
        if (page.itemName().count() > 0) {
            throw new AssertionError("Expected order summary to have no line items.");
        }
    }

    public void assertUrl() {
        String url = page.page().url();
        if (!url.contains("/checkout-step-two.html")) {
            throw new AssertionError("Expected order summary URL but was " + url);
        }
    }

    public void assertConfirmationUrl() {
        String url = page.page().url();
        if (!url.contains("/checkout-complete.html")) {
            throw new AssertionError("Expected /checkout-complete.html but URL was " + url);
        }
    }

    public void assertInventoryUrl() {
        String url = page.page().url();
        if (!url.contains("/inventory.html")) {
            throw new AssertionError("Expected /inventory.html but URL was " + url);
        }
    }

    private static double parseMoney(String labelText) {
        if (labelText == null) {
            throw new AssertionError("Money label text was null.");
        }
        Matcher matcher = MONEY.matcher(labelText.replace(",", ""));
        if (!matcher.find()) {
            throw new AssertionError("Could not parse money from: " + labelText);
        }
        return Double.parseDouble(matcher.group(1));
    }

    private void assertVisible(Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
