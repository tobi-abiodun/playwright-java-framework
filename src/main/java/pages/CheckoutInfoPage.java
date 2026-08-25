package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.YamlReader;

import java.util.Map;

/**
 * CheckoutInfoPage — Checkout: Your Information (step one).
 * Locators: checkoutInfoPage.yaml
 */
public class CheckoutInfoPage {

    private final Page page;
    private final Locator heading;
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator postalCodeField;
    private final Locator continueButton;
    private final Locator cancelButton;
    private final Locator errorMessage;
    private final Locator errorFirstNameRequired;

    public CheckoutInfoPage(Page page) {
        this.page = page;
        Map<String, String> locators = YamlReader.readLocators("checkoutInfoPage.yaml", "checkoutInfoPage");
        this.heading = LocatorFactory.fromYaml(page, locators.get("heading"));
        this.firstNameField = LocatorFactory.fromYaml(page, locators.get("firstNameField"));
        this.lastNameField = LocatorFactory.fromYaml(page, locators.get("lastNameField"));
        this.postalCodeField = LocatorFactory.fromYaml(page, locators.get("postalCodeField"));
        this.continueButton = LocatorFactory.fromYaml(page, locators.get("continueButton"));
        this.cancelButton = LocatorFactory.fromYaml(page, locators.get("cancelButton"));
        this.errorMessage = LocatorFactory.fromYaml(page, locators.get("errorMessage"));
        this.errorFirstNameRequired = LocatorFactory.fromYaml(page, locators.get("errorFirstNameRequired"));
    }

    public Page page() {
        return page;
    }

    public Locator heading() {
        return heading;
    }

    public Locator firstNameField() {
        return firstNameField;
    }

    public Locator lastNameField() {
        return lastNameField;
    }

    public Locator postalCodeField() {
        return postalCodeField;
    }

    public Locator continueButton() {
        return continueButton;
    }

    public Locator cancelButton() {
        return cancelButton;
    }

    public Locator errorMessage() {
        return errorMessage;
    }

    public Locator errorFirstNameRequired() {
        return errorFirstNameRequired;
    }
}
