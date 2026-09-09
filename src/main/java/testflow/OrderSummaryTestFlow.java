package testflow;

import utils.LoggerUtil;
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
        LoggerUtil.step("TF23: Verify order summary screen");
        orderSummaryPageUtil.assertUrl();
        orderSummaryPageUtil.assertHeadingVisible();
        orderSummaryPageUtil.assertFinishVisible();
        orderSummaryPageUtil.assertCancelVisible();
        orderSummaryPageUtil.assertPaymentAndShippingVisible();
    }

    @TestFlow(description = "TF24: Verify order summary shows item and totals")
    public void validateItemAndTotals(String itemName, String expectedItemPrice) {
        LoggerUtil.step("TF24: Item " + itemName + " and totals (item total contains " + expectedItemPrice + ")");
        orderSummaryPageUtil.assertItemVisible(itemName);
        orderSummaryPageUtil.assertTotalsVisible();
        orderSummaryPageUtil.assertItemTotalContains(expectedItemPrice);
        orderSummaryPageUtil.assertTotalEqualsItemPlusTax();
    }

    @TestFlow(description = "TF24: Verify multiple items and combined totals")
    public void validateItemsAndTotals(String expectedItemTotal, String... itemNames) {
        LoggerUtil.step("TF24: Multiple items and totals " + expectedItemTotal);
        for (String itemName : itemNames) {
            orderSummaryPageUtil.assertItemVisible(itemName);
        }
        orderSummaryPageUtil.assertTotalsVisible();
        orderSummaryPageUtil.assertItemTotalContains(expectedItemTotal);
        orderSummaryPageUtil.assertTotalEqualsItemPlusTax();
    }

    @TestFlow(description = "TF24 item visible")
    public void validateItemVisible(String itemName) {
        LoggerUtil.step("TF24: Item visible " + itemName);
        orderSummaryPageUtil.assertItemVisible(itemName);
    }

    @TestFlow(description = "TF24b: Verify totals visible only")
    public void validateTotals() {
        LoggerUtil.step("TF24b: Totals visible");
        orderSummaryPageUtil.assertTotalsVisible();
        orderSummaryPageUtil.assertPaymentAndShippingVisible();
        orderSummaryPageUtil.assertTotalEqualsItemPlusTax();
    }

    @TestFlow(description = "TF24c: Verify summary has no line items")
    public void validateNoLineItems() {
        LoggerUtil.step("TF24c: No line items");
        orderSummaryPageUtil.assertNoLineItems();
    }

    @TestFlow(description = "TF25: Verify user can finish order from summary")
    public void finish() {
        LoggerUtil.step("TF25: Finish order");
        orderSummaryPageUtil.assertFinishVisible();
        orderSummaryPageUtil.clickFinish();
        orderSummaryPageUtil.assertConfirmationUrl();
    }

    @TestFlow(description = "TF26: Verify Cancel on order summary")
    public void cancel() {
        LoggerUtil.step("TF26: Cancel order summary");
        orderSummaryPageUtil.assertCancelVisible();
        orderSummaryPageUtil.clickCancel();
        orderSummaryPageUtil.assertInventoryUrl();
    }
}
