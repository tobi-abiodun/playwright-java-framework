package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.YamlReader;

import java.util.Map;

/**
 * LoginPage
 * ---------
 * Page Object for the Sauce Demo login screen.
 * Reads locators from loginPage.yaml and exposes ONLY element getters.
 * Clicks, fills, and assertions belong in LoginPageUtil — not here.
 */
public class LoginPage {

    private final Page page;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;
    private final Locator productsHeading;
    private final Locator inventoryList;
    private final Locator acceptedUsersHeading;
    private final Locator passwordHintHeading;
    private final Locator errorLockedOut;
    private final Locator errorBadCredentials;
    private final Locator errorUsernameRequired;
    private final Locator errorPasswordRequired;

    public LoginPage(Page page) {
        this.page = page;
        Map<String, String> locators = YamlReader.readLocators("loginPage.yaml", "loginPage");

        this.usernameField = LocatorFactory.fromYaml(page, locators.get("usernameField"));
        this.passwordField = LocatorFactory.fromYaml(page, locators.get("passwordField"));
        this.loginButton = LocatorFactory.fromYaml(page, locators.get("loginButton"));
        this.errorMessage = LocatorFactory.fromYaml(page, locators.get("errorMessage"));
        this.productsHeading = LocatorFactory.fromYaml(page, locators.get("productsHeading"));
        this.inventoryList = LocatorFactory.fromYaml(page, locators.get("inventoryList"));
        this.acceptedUsersHeading = LocatorFactory.fromYaml(page, locators.get("acceptedUsersHeading"));
        this.passwordHintHeading = LocatorFactory.fromYaml(page, locators.get("passwordHintHeading"));
        this.errorLockedOut = LocatorFactory.fromYaml(page, locators.get("errorLockedOut"));
        this.errorBadCredentials = LocatorFactory.fromYaml(page, locators.get("errorBadCredentials"));
        this.errorUsernameRequired = LocatorFactory.fromYaml(page, locators.get("errorUsernameRequired"));
        this.errorPasswordRequired = LocatorFactory.fromYaml(page, locators.get("errorPasswordRequired"));
    }

    /** Playwright page — used by LoginPageUtil for navigate and URL checks only. */
    public Page page() {
        return page;
    }

    public Locator usernameField() {
        return usernameField;
    }

    public Locator passwordField() {
        return passwordField;
    }

    public Locator loginButton() {
        return loginButton;
    }

    public Locator errorMessage() {
        return errorMessage;
    }

    public Locator productsHeading() {
        return productsHeading;
    }

    public Locator inventoryList() {
        return inventoryList;
    }

    public Locator acceptedUsersHeading() {
        return acceptedUsersHeading;
    }

    public Locator passwordHintHeading() {
        return passwordHintHeading;
    }

    public Locator errorLockedOut() {
        return errorLockedOut;
    }

    public Locator errorBadCredentials() {
        return errorBadCredentials;
    }

    public Locator errorUsernameRequired() {
        return errorUsernameRequired;
    }

    public Locator errorPasswordRequired() {
        return errorPasswordRequired;
    }
}
