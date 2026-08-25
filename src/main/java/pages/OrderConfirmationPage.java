package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.YamlReader;

import java.util.Map;

/**
 * OrderConfirmationPage — Checkout Complete / thank you.
 * Locators: orderConfirmationPage.yaml
 */
public class OrderConfirmationPage {

    private final Page page;
    private final Locator heading;
    private final Locator completeHeader;
    private final Locator completeText;
    private final Locator backHomeButton;
    private final Locator ponyExpressImage;

    public OrderConfirmationPage(Page page) {
        this.page = page;
        Map<String, String> locators = YamlReader.readLocators("orderConfirmationPage.yaml", "orderConfirmationPage");
        this.heading = LocatorFactory.fromYaml(page, locators.get("heading"));
        this.completeHeader = LocatorFactory.fromYaml(page, locators.get("completeHeader"));
        this.completeText = LocatorFactory.fromYaml(page, locators.get("completeText"));
        this.backHomeButton = LocatorFactory.fromYaml(page, locators.get("backHomeButton"));
        this.ponyExpressImage = LocatorFactory.fromYaml(page, locators.get("ponyExpressImage"));
    }

    public Page page() {
        return page;
    }

    public Locator heading() {
        return heading;
    }

    public Locator completeHeader() {
        return completeHeader;
    }

    public Locator completeText() {
        return completeText;
    }

    public Locator backHomeButton() {
        return backHomeButton;
    }

    public Locator ponyExpressImage() {
        return ponyExpressImage;
    }
}
