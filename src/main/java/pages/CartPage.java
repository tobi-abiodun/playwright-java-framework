package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.YamlReader;

import java.util.Map;

/**
 * CartPage — Sauce Demo cart. Getters only.
 */
public class CartPage {

    private final Page page;
    private final Locator cartHeading;
    private final Locator cartList;
    private final Locator itemName;
    private final Locator itemQuantity;
    private final Locator checkoutButton;
    private final Locator continueShoppingButton;
    private final Locator cartBadge;

    public CartPage(Page page) {
        this.page = page;
        Map<String, String> locators = YamlReader.readLocators("cartPage.yaml", "cartPage");
        this.cartHeading = LocatorFactory.fromYaml(page, locators.get("cartHeading"));
        this.cartList = LocatorFactory.fromYaml(page, locators.get("cartList"));
        this.itemName = LocatorFactory.fromYaml(page, locators.get("itemName"));
        this.itemQuantity = LocatorFactory.fromYaml(page, locators.get("itemQuantity"));
        this.checkoutButton = LocatorFactory.fromYaml(page, locators.get("checkoutButton"));
        this.continueShoppingButton = LocatorFactory.fromYaml(page, locators.get("continueShoppingButton"));
        this.cartBadge = LocatorFactory.fromYaml(page, locators.get("cartBadge"));
    }

    public Page page() {
        return page;
    }

    public Locator cartHeading() {
        return cartHeading;
    }

    public Locator cartList() {
        return cartList;
    }

    public Locator itemName() {
        return itemName;
    }

    public Locator itemQuantity() {
        return itemQuantity;
    }

    public Locator checkoutButton() {
        return checkoutButton;
    }

    public Locator continueShoppingButton() {
        return continueShoppingButton;
    }

    public Locator cartBadge() {
        return cartBadge;
    }

    public Locator buttonByTestId(String testId) {
        return page.getByTestId(testId);
    }
}
