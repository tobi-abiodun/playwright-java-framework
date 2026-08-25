package testflow;

import utils.CartPageUtil;

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
        cartPageUtil.assertCartUrl();
        cartPageUtil.assertCartHeadingVisible();
        cartPageUtil.assertCartListVisible();
        cartPageUtil.assertCheckoutButtonVisible();
        cartPageUtil.assertContinueShoppingVisible();
    }

    @TestFlow(description = "TF15: Verify cart contains expected product")
    public void validateItemVisible(String itemName) {
        cartPageUtil.assertItemVisible(itemName);
        cartPageUtil.assertItemQuantity("1");
    }

    @TestFlow(description = "TF15b: Verify cart contains product without quantity check")
    public void validateItemVisibleOnly(String itemName) {
        cartPageUtil.assertItemVisible(itemName);
    }

    @TestFlow(description = "TF16: Verify Continue Shopping returns to inventory")
    public void continueShopping() {
        cartPageUtil.assertContinueShoppingVisible();
        cartPageUtil.clickContinueShopping();
    }

    @TestFlow(description = "TF17: Verify user can proceed to checkout from cart")
    public void proceedToCheckout() {
        cartPageUtil.assertCheckoutButtonVisible();
        cartPageUtil.clickCheckout();
    }

    @TestFlow(description = "TF18: Verify user can remove product from cart")
    public void removeProduct(String removeButtonTestId, String itemName) {
        cartPageUtil.clickRemove(removeButtonTestId);
        cartPageUtil.assertItemHidden(itemName);
    }
}
