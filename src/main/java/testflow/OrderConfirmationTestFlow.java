package testflow;

import utils.LoggerUtil;
import utils.OrderConfirmationPageUtil;

/**
 * OrderConfirmationTestFlow — TF27–TF28.
 */
public class OrderConfirmationTestFlow {

    private final OrderConfirmationPageUtil orderConfirmationPageUtil;

    public OrderConfirmationTestFlow(OrderConfirmationPageUtil orderConfirmationPageUtil) {
        this.orderConfirmationPageUtil = orderConfirmationPageUtil;
    }

    @TestFlow(description = "TF27: Verify the order confirmation page")
    public void validateScreen() {
        LoggerUtil.step("TF27: Verify order confirmation");
        orderConfirmationPageUtil.assertUrl();
        orderConfirmationPageUtil.assertHeadingVisible();
        orderConfirmationPageUtil.assertThankYouMessageContains("Thank you for your order");
        orderConfirmationPageUtil.assertBackHomeVisible();
    }

    @TestFlow(description = "TF28: Verify Back Home from confirmation")
    public void backHome() {
        LoggerUtil.step("TF28: Back Home");
        orderConfirmationPageUtil.clickBackHome();
        orderConfirmationPageUtil.assertInventoryUrl();
        orderConfirmationPageUtil.assertCartBadgeHidden();
    }
}
