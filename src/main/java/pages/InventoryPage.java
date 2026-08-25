package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.YamlReader;

import java.util.Map;

/**
 * InventoryPage — Sauce Demo products screen. Getters only.
 */
public class InventoryPage {

    private final Page page;
    private final Locator productsHeading;
    private final Locator inventoryList;
    private final Locator shoppingCartLink;
    private final Locator cartBadge;
    private final Locator sortDropdown;
    private final Locator itemName;
    private final Locator openMenuButton;

    public InventoryPage(Page page) {
        this.page = page;
        Map<String, String> locators = YamlReader.readLocators("inventoryPage.yaml", "inventoryPage");
        this.productsHeading = LocatorFactory.fromYaml(page, locators.get("productsHeading"));
        this.inventoryList = LocatorFactory.fromYaml(page, locators.get("inventoryList"));
        this.shoppingCartLink = LocatorFactory.fromYaml(page, locators.get("shoppingCartLink"));
        this.cartBadge = LocatorFactory.fromYaml(page, locators.get("cartBadge"));
        this.sortDropdown = LocatorFactory.fromYaml(page, locators.get("sortDropdown"));
        this.itemName = LocatorFactory.fromYaml(page, locators.get("itemName"));
        this.openMenuButton = LocatorFactory.fromYaml(page, locators.get("openMenuButton"));
    }

    public Page page() {
        return page;
    }

    public Locator productsHeading() {
        return productsHeading;
    }

    public Locator inventoryList() {
        return inventoryList;
    }

    public Locator shoppingCartLink() {
        return shoppingCartLink;
    }

    public Locator cartBadge() {
        return cartBadge;
    }

    public Locator sortDropdown() {
        return sortDropdown;
    }

    public Locator itemName() {
        return itemName;
    }

    public Locator openMenuButton() {
        return openMenuButton;
    }

    /** Dynamic data-test button from product testdata (add/remove). */
    public Locator buttonByTestId(String testId) {
        return page.getByTestId(testId);
    }
}
