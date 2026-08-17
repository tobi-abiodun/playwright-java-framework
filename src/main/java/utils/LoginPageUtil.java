package utils;

import pages.LoginPage;

/**
 * LoginPageUtil
 * -------------
 * Validation helper for login scenarios.
 * Contains ONLY assertion/validation methods.
 * Each method checks page state by calling LoginPage state methods — not Playwright directly.
 */
public class LoginPageUtil {

    private final LoginPage loginPage;

    public LoginPageUtil(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    /** Validates that login succeeded and the dashboard is shown. */
    public void validateLoginSuccess() {
        if (!loginPage.isDashboardVisible()) {
            throw new AssertionError("Expected dashboard to be visible after a successful login.");
        }
    }

    /** Validates that login failed and an error message is shown. */
    public void validateLoginError() {
        if (!loginPage.isErrorVisible()) {
            throw new AssertionError("Expected error message to be visible after a failed login.");
        }
    }
}
