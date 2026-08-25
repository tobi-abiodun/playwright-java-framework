package testflow;

import utils.OrderSummaryPageUtil;

/**
 * OrderSummaryTestFlow — TF23–TF26.
 */
public class OrderSummaryTestFlow {

    private final OrderSummaryPageUtil orderSummaryPageUtil;

    public OrderSummaryTestFlow(OrderSummaryPageUtil orderSummaryPageUtil) {
        this.orderSummaryPageUtil = orderSummaryPageUtil;
    }

    @TestFlow(description = "TF23: Verify the order summary screen")
    public void validateScreen() {
        orderSummaryPageUtil.assertUrl();
        orderSummaryPageUtil.assertHeadingVisible();
        orderSummaryPageUtil.assertFinishVisible();
        orderSummaryPageUtil.assertCancelVisible();
        orderSummaryPageUtil.assertPaymentAndShippingVisible();
    }

    @TestFlow(description = "TF24: Verify order summary shows item and totals")
    public void validateItemAndTotals(String itemName, String expectedItemPrice) {
        orderSummaryPageUtil.assertItemVisible(itemName);
        orderSummaryPageUtil.assertTotalsVisible();
        orderSummaryPageUtil.assertItemTotalContains(expectedItemPrice);
    }

    @TestFlow(description = "TF24 item visible")
    public void validateItemVisible(String itemName) {
        orderSummaryPageUtil.assertItemVisible(itemName);
    }

    @TestFlow(description = "TF24b: Verify totals visible only")
    public void validateTotals() {
        orderSummaryPageUtil.assertTotalsVisible();
        orderSummaryPageUtil.assertPaymentAndShippingVisible();
    }

    @TestFlow(description = "TF24c: Verify summary has no line items")
    public void validateNoLineItems() {
        orderSummaryPageUtil.assertNoLineItems();
    }

    @TestFlow(description = "TF25: Verify user can finish order from summary")
    public void finish() {
        orderSummaryPageUtil.assertFinishVisible();
        orderSummaryPageUtil.clickFinish();
    }

    @TestFlow(description = "TF26: Verify Cancel on order summary")
    public void cancel() {
        orderSummaryPageUtil.assertCancelVisible();
        orderSummaryPageUtil.clickCancel();
    }
}
