package testflow;

import utils.LoginPageUtil;
import utils.LoggerUtil;

/**
 * LoginTestFlow — TF1–TF6 for the login screen.
 */
public class LoginTestFlow {

    private final LoginPageUtil loginPageUtil;

    public LoginTestFlow(LoginPageUtil loginPageUtil) {
        this.loginPageUtil = loginPageUtil;
    }

    @TestFlow(description = "TF1: Verify the login page")
    public void validateLoginPage() {
        LoggerUtil.step("TF1: Verify the login page");
        loginPageUtil.openLoginPage();
        loginPageUtil.assertUsernameFieldVisible();
        loginPageUtil.assertUsernameFieldEnabled();
        loginPageUtil.assertPasswordFieldVisible();
        loginPageUtil.assertPasswordFieldEnabled();
        loginPageUtil.assertLoginButtonVisible();
        loginPageUtil.assertLoginButtonEnabled();
        loginPageUtil.assertProductsHeadingHidden();
    }

    @TestFlow(description = "TF2: Verify user can login successfully")
    public void validateLoginSuccess(String username, String password) {
        LoggerUtil.step("TF2: Login successfully as " + username);
        loginPageUtil.openLoginPage();
        loginPageUtil.fillUsername(username);
        loginPageUtil.fillPassword(password);
        loginPageUtil.clickLoginButton();
        loginPageUtil.assertInventoryUrl();
        loginPageUtil.assertProductsHeadingVisible();
        loginPageUtil.assertInventoryListVisible();
        loginPageUtil.assertErrorHidden();
    }

    @TestFlow(description = "TF3: Verify locked-out user cannot login")
    public void validateLockedOutLogin(String username, String password) {
        LoggerUtil.step("TF3: Locked-out login as " + username);
        loginPageUtil.openLoginPage();
        loginPageUtil.fillUsername(username);
        loginPageUtil.fillPassword(password);
        loginPageUtil.clickLoginButton();
        loginPageUtil.assertStillOnLoginPage();
        loginPageUtil.assertLockedOutErrorVisible();
        loginPageUtil.assertProductsHeadingHidden();
    }

    @TestFlow(description = "TF4: Verify invalid credentials cannot login")
    public void validateInvalidCredentials(String username, String password) {
        LoggerUtil.step("TF4: Invalid credentials as " + username);
        loginPageUtil.openLoginPage();
        loginPageUtil.fillUsername(username);
        loginPageUtil.fillPassword(password);
        loginPageUtil.clickLoginButton();
        loginPageUtil.assertStillOnLoginPage();
        loginPageUtil.assertBadCredentialsErrorVisible();
        loginPageUtil.assertProductsHeadingHidden();
    }

    @TestFlow(description = "TF5: Verify empty username cannot login")
    public void validateEmptyUsername(String password) {
        LoggerUtil.step("TF5: Empty username");
        loginPageUtil.openLoginPage();
        loginPageUtil.fillUsername("");
        loginPageUtil.fillPassword(password);
        loginPageUtil.clickLoginButton();
        loginPageUtil.assertStillOnLoginPage();
        loginPageUtil.assertUsernameRequiredErrorVisible();
    }

    @TestFlow(description = "TF6: Verify empty password cannot login")
    public void validateEmptyPassword(String username) {
        LoggerUtil.step("TF6: Empty password for " + username);
        loginPageUtil.openLoginPage();
        loginPageUtil.fillUsername(username);
        loginPageUtil.fillPassword("");
        loginPageUtil.clickLoginButton();
        loginPageUtil.assertStillOnLoginPage();
        loginPageUtil.assertPasswordRequiredErrorVisible();
    }
}
