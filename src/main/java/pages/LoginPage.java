package pages;

import com.microsoft.playwright.Page;
import utils.YamlLocatorReader;

import java.nio.file.Paths;
import java.util.Map;

/**
 * LoginPage
 * ---------
 * Page Object for the login screen.
 * - Reads selectors from loginPage.yaml
 * - Exposes action methods (navigate, type, click)
 * - Exposes state methods (is element visible?)
 * Tests and BBI classes should not use Playwright directly for login; they call this class.
 */
public class LoginPage {

    private final Page page;
    private final Map<String, String> locators;

    // Demo login page used by the sample tests (served from the demo/ folder)
    private static final String DEMO_LOGIN_URL = Paths.get("demo", "login.html")
            .toAbsolutePath()
            .toUri()
            .toString();

    public LoginPage(Page page) {
        this.page = page;
        // Load all selectors for the login page from YAML once when the object is created
        this.locators = YamlLocatorReader.loadLocators("loginPage.yaml", "loginPage");
    }

    // -------------------- ACTION METHODS --------------------

    /** Opens the login page in the browser. */
    public void goToLoginPage() {
        page.navigate(DEMO_LOGIN_URL);
    }

    /** Types the username into the username field. */
    public void enterUsername(String username) {
        page.locator(locators.get("usernameField")).fill(username);
    }

    /** Types the password into the password field. */
    public void enterPassword(String password) {
        page.locator(locators.get("passwordField")).fill(password);
    }

    /** Clicks the login button. */
    public void clickLoginButton() {
        page.locator(locators.get("loginButton")).click();
    }

    // -------------------- STATE METHODS --------------------

    /** Returns true when the dashboard element is visible (successful login). */
    public boolean isDashboardVisible() {
        return page.locator(locators.get("dashboard")).isVisible();
    }

    /** Returns true when the error message element is visible (failed login). */
    public boolean isErrorVisible() {
        return page.locator(locators.get("errorMessage")).isVisible();
    }
}
