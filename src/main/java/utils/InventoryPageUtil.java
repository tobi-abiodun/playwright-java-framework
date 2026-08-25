package utils;

import com.microsoft.playwright.Locator;
import pages.InventoryPage;

/**
 * InventoryPageUtil — actions/asserts for products screen.
 */
public class InventoryPageUtil {

    private final InventoryPage inventoryPage;

    public InventoryPageUtil(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public void clickAddToCart(String addButtonTestId) {
        inventoryPage.buttonByTestId(addButtonTestId).click();
    }

    public void clickRemove(String removeButtonTestId) {
        inventoryPage.buttonByTestId(removeButtonTestId).click();
    }

    public void clickShoppingCart() {
        inventoryPage.shoppingCartLink().click();
    }

    public void selectSortOption(String optionValue) {
        inventoryPage.sortDropdown().selectOption(optionValue);
    }

    public String readCartBadgeText() {
        return inventoryPage.cartBadge().innerText();
    }

    public String firstItemName() {
        return inventoryPage.itemName().first().innerText();
    }

    public String lastItemName() {
        return inventoryPage.itemName().last().innerText();
    }

    public void assertProductsHeadingVisible() {
        assertVisible(inventoryPage.productsHeading(), "Products heading");
        String title = inventoryPage.productsHeading().innerText().trim();
        if (!"Products".equals(title)) {
            throw new AssertionError("Expected page title 'Products' but was '" + title + "'.");
        }
    }

    public void assertInventoryListVisible() {
        assertVisible(inventoryPage.inventoryList(), "inventory list");
    }

    public void assertShoppingCartVisible() {
        assertVisible(inventoryPage.shoppingCartLink(), "shopping cart link");
    }

    public void assertInventoryUrl() {
        String url = inventoryPage.page().url();
        if (!url.contains("/inventory.html")) {
            throw new AssertionError("Expected /inventory.html but URL was " + url);
        }
    }

    public void assertCartBadgeVisible() {
        assertVisible(inventoryPage.cartBadge(), "cart badge");
    }

    public void assertCartBadgeHidden() {
        if (inventoryPage.cartBadge().count() > 0 && inventoryPage.cartBadge().first().isVisible()) {
            throw new AssertionError("Expected cart badge to be hidden.");
        }
    }

    public void assertCartBadgeCount(String expectedCount) {
        assertCartBadgeVisible();
        String actual = readCartBadgeText();
        if (!expectedCount.equals(actual)) {
            throw new AssertionError("Expected cart badge '" + expectedCount + "' but was '" + actual + "'.");
        }
    }

    public void assertItemNamedVisible(String itemName) {
        Locator item = inventoryPage.itemName().filter(new Locator.FilterOptions().setHasText(itemName));
        assertVisible(item, "product named " + itemName);
    }

    public void assertRemoveButtonVisible(String removeButtonTestId) {
        assertVisible(inventoryPage.buttonByTestId(removeButtonTestId), "Remove button " + removeButtonTestId);
    }

    public void assertAddButtonVisible(String addButtonTestId) {
        assertVisible(inventoryPage.buttonByTestId(addButtonTestId), "Add to cart button " + addButtonTestId);
    }

    public void assertFirstItemName(String expected) {
        String actual = firstItemName();
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected first product '" + expected + "' but was '" + actual + "'.");
        }
    }

    public void assertLastItemName(String expected) {
        String actual = lastItemName();
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected last product '" + expected + "' but was '" + actual + "'.");
        }
    }

    private void assertVisible(Locator locator, String elementName) {
        if (!WaitUtil.waitForVisible(locator)) {
            throw new AssertionError("Expected " + elementName + " to be visible.");
        }
    }
}
