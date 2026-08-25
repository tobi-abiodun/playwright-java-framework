package utils;

import config.ConfigReader;
import pages.LoginPage;

/**
 * LoginPageUtil — actions/asserts for login screen.
 */
public class LoginPageUtil {

    private final LoginPage loginPage;

    public LoginPageUtil(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void openLoginPage() {
        loginPage.page().navigate(ConfigReader.getLoginUrl());
    }

    public void fillUsername(String username) {
        loginPage.usernameField().fill(username == null ? "" : username);
    }

    public void fillPassword(String password) {
        loginPage.passwordField().fill(password == null ? "" : password);
    }

    public void clickLoginButton() {
        loginPage.loginButton().click();
    }

    public String readErrorText() {
        return loginPage.errorMessage().innerText();
    }

    public String readCurrentUrl() {
        return loginPage.page().url();
    }

    public void assertUsernameFieldVisible() {
        assertVisible(loginPage.usernameField(), "username field");
    }

    public void assertPasswordFieldVisible() {
        assertVisible(loginPage.passwordField(), "password field");
    }

    public void assertLoginButtonVisible() {
        assertVisible(loginPage.loginButton(), "Login button");
    }

    public void assertUsernameFieldEnabled() {
        if (!loginPage.usernameField().isEnabled()) {
            throw new AssertionError("Expected username field to be enabled.");
        }
    }

    public void assertPasswordFieldEnabled() {
        if (!loginPage.passwordField().isEnabled()) {
            throw new AssertionError("Expected password field to be enabled.");
        }
    }

    public void assertLoginButtonEnabled() {
        if (!loginPage.loginButton().isEnabled()) {
            throw new AssertionError("Expected Login button to be enabled.");
        }
    }

    public void assertErrorVisible() {
        assertVisible(loginPage.errorMessage(), "error message");
    }

    public void assertErrorHidden() {
        if (loginPage.errorMessage().isVisible()) {
            throw new AssertionError("Expected error message to be hidden.");
        }
    }

    public void assertLockedOutErrorVisible() {
        assertVisible(loginPage.errorLockedOut(), "locked-out error");
    }

    public void assertBadCredentialsErrorVisible() {
        assertVisible(loginPage.errorBadCredentials(), "bad-credentials error");
    }

    public void assertUsernameRequiredErrorVisible() {
        assertVisible(loginPage.errorUsernameRequired(), "username-required error");
    }

    public void assertPasswordRequiredErrorVisible() {
        assertVisible(loginPage.errorPasswordRequired(), "password-required error");
    }

    public void assertProductsHeadingVisible() {
        assertVisible(loginPage.productsHeading(), "Products heading");
        String title = loginPage.productsHeading().innerText().trim();
        if (!"Products".equals(title)) {
            throw new AssertionError("Expected page title 'Products' but was '" + title + "'.");
        }
    }

    public void assertInventoryListVisible() {
        assertVisible(loginPage.inventoryList(), "inventory list");
    }

    public void assertProductsHeadingHidden() {
        if (loginPage.page().url().contains("/inventory.html")) {
            throw new AssertionError("Expected Products page to stay hidden.");
        }
    }

    public void assertInventoryUrl() {
        String url = readCurrentUrl();
        if (!url.contains("/inventory.html")) {
            throw new AssertionError("Expected URL to contain /inventory.html but was " + url);
        }
    }

    public void assertStillOnLoginPage() {
        assertLoginButtonVisible();
        String url = readCurrentUrl();
        if (url.contains("/inventory.html")) {
            throw new AssertionError("Expected to stay on the login page but URL was " + url);
        }
    }

    private void assertVisible(com.microsoft.playwright.Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
