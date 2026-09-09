package testflow;

import utils.CartPageUtil;
import utils.LoggerUtil;

/**
 * CartTestFlow — TF14–TF18 for the cart screen.
 */
public class CartTestFlow {

    private final CartPageUtil cartPageUtil;

    public CartTestFlow(CartPageUtil cartPageUtil) {
        this.cartPageUtil = cartPageUtil;
    }

    @TestFlow(description = "TF14: Verify the cart screen")
    public void validateScreen() {
        LoggerUtil.step("TF14: Verify the cart screen");
        cartPageUtil.assertCartUrl();
        cartPageUtil.assertCartHeadingVisible();
        cartPageUtil.assertCartListVisible();
        cartPageUtil.assertCheckoutButtonVisible();
        cartPageUtil.assertContinueShoppingVisible();
    }

    @TestFlow(description = "TF15: Verify cart contains expected product")
    public void validateItemVisible(String itemName) {
        LoggerUtil.step("TF15: Cart contains " + itemName);
        cartPageUtil.assertItemVisible(itemName);
        cartPageUtil.assertItemQuantity(itemName, "1");
    }

    @TestFlow(description = "TF16: Verify Continue Shopping returns to inventory")
    public void continueShopping() {
        LoggerUtil.step("TF16: Continue Shopping");
        cartPageUtil.assertContinueShoppingVisible();
        cartPageUtil.clickContinueShopping();
        cartPageUtil.assertInventoryUrl();
    }

    @TestFlow(description = "TF16: Verify Continue Shopping returns to inventory with badge")
    public void continueShopping(String expectedBadge) {
        LoggerUtil.step("TF16: Continue Shopping (badge " + expectedBadge + ")");
        cartPageUtil.assertContinueShoppingVisible();
        cartPageUtil.clickContinueShopping();
        cartPageUtil.assertInventoryUrl();
        cartPageUtil.assertCartBadgeCount(expectedBadge);
    }

    @TestFlow(description = "TF17: Verify user can proceed to checkout from cart")
    public void proceedToCheckout() {
        LoggerUtil.step("TF17: Proceed to checkout");
        cartPageUtil.assertCheckoutButtonVisible();
        cartPageUtil.clickCheckout();
        cartPageUtil.assertCheckoutStepOneUrl();
    }

    @TestFlow(description = "TF18: Verify user can remove product from cart")
    public void removeProduct(String removeButtonTestId, String itemName) {
        LoggerUtil.step("TF18: Remove " + itemName + " from cart");
        cartPageUtil.clickRemove(removeButtonTestId);
        cartPageUtil.assertItemHidden(itemName);
    }

    @TestFlow(description = "TF18: Verify user can remove product from cart with badge update")
    public void removeProduct(String removeButtonTestId, String itemName, String expectedBadge) {
        LoggerUtil.step("TF18: Remove " + itemName + " from cart (badge " + expectedBadge + ")");
        cartPageUtil.clickRemove(removeButtonTestId);
        cartPageUtil.assertItemHidden(itemName);
        if (expectedBadge == null || expectedBadge.isBlank()) {
            cartPageUtil.assertCartBadgeHidden();
        } else {
            cartPageUtil.assertCartBadgeCount(expectedBadge);
        }
    }
}
