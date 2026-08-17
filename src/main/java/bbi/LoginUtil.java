package bbi;

import com.microsoft.playwright.Page;
import pages.LoginPage;
import utils.LoginPageUtil;

/**
 * LoginUtil (BBI - Scenario Util)
 * -------------------------------
 * Reusable login scenario builder.
 * Combines LoginPage actions with LoginPageUtil validations into ready-made flows
 * that tests can call with one line instead of repeating steps.
 */
public class LoginUtil {

    private final LoginPage loginPage;
    private final LoginPageUtil loginPageUtil;

    public LoginUtil(Page page) {
        this.loginPage = new LoginPage(page);
        this.loginPageUtil = new LoginPageUtil(loginPage);
    }

    /**
     * Full happy-path login flow:
     * open page -> enter credentials -> click login -> validate success.
     */
    public void loginFlow(String username, String password) {
        loginPage.goToLoginPage();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        loginPageUtil.validateLoginSuccess();
    }

    /**
     * Negative login flow:
     * open page -> enter bad credentials -> click login -> validate error.
     */
    public void loginFlowExpectingFailure(String username, String password) {
        loginPage.goToLoginPage();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        loginPageUtil.validateLoginError();
    }
}
