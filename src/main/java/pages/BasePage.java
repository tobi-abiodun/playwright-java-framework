package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.WaitUtil;
import utils.YamlLocatorReader;

import java.util.Map;

/**
 * BasePage
 * --------
 * Parent class for all Page Objects.
 * Reads locators from YAML and converts each string into a Playwright Locator
 * via LocatorFactory (role, placeholder, label, text, testid, or CSS).
 */
public abstract class BasePage {

    protected final Page page;
    protected final Map<String, String> locators;

    protected BasePage(Page page, String yamlFileName, String pageKey) {
        this.page = page;
        this.locators = YamlLocatorReader.loadLocators(yamlFileName, pageKey);
    }

    /**
     * Reads the YAML key and converts the stored string into a Playwright locator.
     * Example YAML: usernameField: "placeholder=Username"
     */
    protected Locator locator(String locatorName) {
        String yamlValue = locators.get(locatorName);
        if (yamlValue == null) {
            throw new IllegalArgumentException("Locator '" + locatorName + "' is not defined in the YAML file.");
        }
        return LocatorFactory.fromYaml(page, yamlValue);
    }

    /** Clicks an element defined in YAML. */
    protected void click(String locatorName) {
        locator(locatorName).click();
    }

    /** Types text into an input defined in YAML. */
    protected void fill(String locatorName, String text) {
        locator(locatorName).fill(text);
    }

    /** Waits until the YAML element is visible; returns true if visible, false on timeout. */
    protected boolean isVisible(String locatorName) {
        return WaitUtil.waitForVisible(locator(locatorName));
    }

    public Page getPage() {
        return page;
    }
}
