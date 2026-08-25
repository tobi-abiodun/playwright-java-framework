package testflow;

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
        orderConfirmationPageUtil.assertUrl();
        orderConfirmationPageUtil.assertHeadingVisible();
        orderConfirmationPageUtil.assertBackHomeVisible();
    }

    @TestFlow(description = "TF28: Verify Back Home from confirmation")
    public void backHome() {
        orderConfirmationPageUtil.clickBackHome();
    }
}
