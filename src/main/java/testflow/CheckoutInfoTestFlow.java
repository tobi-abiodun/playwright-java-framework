package testflow;

import utils.CheckoutInfoPageUtil;
import utils.LoggerUtil;

/**
 * CheckoutInfoTestFlow — TF19–TF22.
 */
public class CheckoutInfoTestFlow {

    private final CheckoutInfoPageUtil checkoutInfoPageUtil;

    public CheckoutInfoTestFlow(CheckoutInfoPageUtil checkoutInfoPageUtil) {
        this.checkoutInfoPageUtil = checkoutInfoPageUtil;
    }

    @TestFlow(description = "TF19: Verify the checkout information screen")
    public void validateScreen() {
        LoggerUtil.step("TF19: Verify checkout information screen");
        checkoutInfoPageUtil.assertUrl();
        checkoutInfoPageUtil.assertHeadingVisible();
        checkoutInfoPageUtil.assertFieldsVisible();
        checkoutInfoPageUtil.assertButtonsVisible();
    }

    @TestFlow(description = "TF20: Verify user can submit valid customer information")
    public void submitCustomerInfo(String firstName, String lastName, String postalCode) {
        LoggerUtil.step("TF20: Submit customer information");
        checkoutInfoPageUtil.fillCustomerInfo(firstName, lastName, postalCode);
        checkoutInfoPageUtil.clickContinue();
        checkoutInfoPageUtil.assertErrorHidden();
        checkoutInfoPageUtil.assertOrderSummaryUrl();
    }

    @TestFlow(description = "TF21: Verify checkout info rejects empty first name")
    public void validateFirstNameRequiredError(String firstName, String lastName, String postalCode) {
        LoggerUtil.step("TF21: Empty first name validation");
        checkoutInfoPageUtil.fillCustomerInfo(firstName, lastName, postalCode);
        checkoutInfoPageUtil.clickContinue();
        checkoutInfoPageUtil.assertUrl();
        checkoutInfoPageUtil.assertFirstNameRequiredErrorVisible();
    }

    @TestFlow(description = "TF22: Verify Cancel on checkout info returns to cart")
    public void cancel() {
        LoggerUtil.step("TF22: Cancel checkout info");
        checkoutInfoPageUtil.clickCancel();
        checkoutInfoPageUtil.assertCartUrl();
    }
}
