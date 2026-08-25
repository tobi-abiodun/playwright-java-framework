package utils;

import com.microsoft.playwright.Locator;
import pages.CheckoutInfoPage;

/**
 * CheckoutInfoPageUtil — Checkout: Your Information.
 */
public class CheckoutInfoPageUtil {

    private final CheckoutInfoPage page;

    public CheckoutInfoPageUtil(CheckoutInfoPage page) {
        this.page = page;
    }

    public void fillFirstName(String firstName) {
        page.firstNameField().fill(firstName == null ? "" : firstName);
    }

    public void fillLastName(String lastName) {
        page.lastNameField().fill(lastName == null ? "" : lastName);
    }

    public void fillPostalCode(String postalCode) {
        page.postalCodeField().fill(postalCode == null ? "" : postalCode);
    }

    public void fillCustomerInfo(String firstName, String lastName, String postalCode) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillPostalCode(postalCode);
    }

    public void clickContinue() {
        page.continueButton().click();
    }

    public void clickCancel() {
        page.cancelButton().click();
    }

    public void assertHeadingVisible() {
        assertVisible(page.heading(), "Checkout: Your Information heading");
        String title = page.heading().innerText().trim();
        if (!"Checkout: Your Information".equals(title)) {
            throw new AssertionError("Expected title 'Checkout: Your Information' but was '" + title + "'.");
        }
    }

    public void assertFieldsVisible() {
        assertVisible(page.firstNameField(), "First Name");
        assertVisible(page.lastNameField(), "Last Name");
        assertVisible(page.postalCodeField(), "Postal Code");
    }

    public void assertButtonsVisible() {
        assertVisible(page.continueButton(), "Continue");
        assertVisible(page.cancelButton(), "Cancel");
    }

    public void assertErrorVisible() {
        assertVisible(page.errorMessage(), "checkout info error");
    }

    public void assertErrorHidden() {
        if (page.errorMessage().count() > 0 && page.errorMessage().first().isVisible()) {
            throw new AssertionError("Expected checkout error to be hidden.");
        }
    }

    public void assertFirstNameRequiredErrorVisible() {
        assertVisible(page.errorFirstNameRequired(), "First Name is required error");
    }

    public void assertUrl() {
        String url = page.page().url();
        if (!url.contains("/checkout-step-one.html")) {
            throw new AssertionError("Expected checkout info URL but was " + url);
        }
    }

    private void assertVisible(Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
