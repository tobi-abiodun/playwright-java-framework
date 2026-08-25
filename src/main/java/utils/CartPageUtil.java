package utils;

import com.microsoft.playwright.Locator;
import pages.CartPage;

/**
 * CartPageUtil — actions/asserts for cart screen.
 */
public class CartPageUtil {

    private final CartPage cartPage;

    public CartPageUtil(CartPage cartPage) {
        this.cartPage = cartPage;
    }

    public void clickCheckout() {
        cartPage.checkoutButton().click();
    }

    public void clickContinueShopping() {
        cartPage.continueShoppingButton().click();
    }

    public void clickRemove(String removeButtonTestId) {
        cartPage.buttonByTestId(removeButtonTestId).click();
    }

    public void assertCartHeadingVisible() {
        assertVisible(cartPage.cartHeading(), "Your Cart heading");
        String title = cartPage.cartHeading().innerText().trim();
        if (!"Your Cart".equals(title)) {
            throw new AssertionError("Expected page title 'Your Cart' but was '" + title + "'.");
        }
    }

    public void assertCartListVisible() {
        assertVisible(cartPage.cartList(), "cart list");
    }

    public void assertCheckoutButtonVisible() {
        assertVisible(cartPage.checkoutButton(), "Checkout button");
    }

    public void assertContinueShoppingVisible() {
        assertVisible(cartPage.continueShoppingButton(), "Continue Shopping button");
    }

    public void assertCartUrl() {
        String url = cartPage.page().url();
        if (!url.contains("/cart.html")) {
            throw new AssertionError("Expected /cart.html but URL was " + url);
        }
    }

    public void assertItemVisible(String itemName) {
        Locator item = cartPage.itemName().filter(new Locator.FilterOptions().setHasText(itemName));
        assertVisible(item, "cart item " + itemName);
    }

    public void assertItemHidden(String itemName) {
        Locator item = cartPage.itemName().filter(new Locator.FilterOptions().setHasText(itemName));
        if (item.count() > 0 && item.first().isVisible()) {
            throw new AssertionError("Expected cart item '" + itemName + "' to be hidden.");
        }
    }

    public void assertItemQuantity(String expectedQuantity) {
        assertVisible(cartPage.itemQuantity().first(), "item quantity");
        String actual = cartPage.itemQuantity().first().innerText().trim();
        if (!expectedQuantity.equals(actual)) {
            throw new AssertionError("Expected quantity '" + expectedQuantity + "' but was '" + actual + "'.");
        }
    }

    public void assertCartBadgeCount(String expectedCount) {
        assertVisible(cartPage.cartBadge(), "cart badge");
        String actual = cartPage.cartBadge().innerText();
        if (!expectedCount.equals(actual)) {
            throw new AssertionError("Expected cart badge '" + expectedCount + "' but was '" + actual + "'.");
        }
    }

    public void assertCartBadgeHidden() {
        if (cartPage.cartBadge().count() > 0 && cartPage.cartBadge().first().isVisible()) {
            throw new AssertionError("Expected cart badge to be hidden.");
        }
    }

    private void assertVisible(Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
