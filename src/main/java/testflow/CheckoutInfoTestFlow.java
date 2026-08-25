package testflow;

import utils.CheckoutInfoPageUtil;

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
        checkoutInfoPageUtil.assertUrl();
        checkoutInfoPageUtil.assertHeadingVisible();
        checkoutInfoPageUtil.assertFieldsVisible();
        checkoutInfoPageUtil.assertButtonsVisible();
    }

    @TestFlow(description = "TF20: Verify user can submit valid customer information")
    public void submitCustomerInfo(String firstName, String lastName, String postalCode) {
        checkoutInfoPageUtil.fillCustomerInfo(firstName, lastName, postalCode);
        checkoutInfoPageUtil.clickContinue();
        checkoutInfoPageUtil.assertErrorHidden();
    }

    @TestFlow(description = "TF21: Verify checkout info rejects empty first name")
    public void validateFirstNameRequiredError(String firstName, String lastName, String postalCode) {
        checkoutInfoPageUtil.fillCustomerInfo(firstName, lastName, postalCode);
        checkoutInfoPageUtil.clickContinue();
        checkoutInfoPageUtil.assertUrl();
        checkoutInfoPageUtil.assertFirstNameRequiredErrorVisible();
    }

    @TestFlow(description = "TF22: Verify Cancel on checkout info returns to cart")
    public void cancel() {
        checkoutInfoPageUtil.clickCancel();
    }
}
