package testflow;

import utils.InventoryPageUtil;
import utils.LoggerUtil;

/**
 * InventoryTestFlow — TF7–TF13 for the products screen.
 */
public class InventoryTestFlow {

    private final InventoryPageUtil inventoryPageUtil;

    public InventoryTestFlow(InventoryPageUtil inventoryPageUtil) {
        this.inventoryPageUtil = inventoryPageUtil;
    }

    @TestFlow(description = "TF7: Verify the inventory screen")
    public void validateScreen() {
        LoggerUtil.step("TF7: Verify the inventory screen");
        inventoryPageUtil.assertInventoryUrl();
        inventoryPageUtil.assertProductsHeadingVisible();
        inventoryPageUtil.assertInventoryListVisible();
        inventoryPageUtil.assertShoppingCartVisible();
    }

    @TestFlow(description = "TF8: Verify user can add a product to cart")
    public void addProduct(String productName, String addButtonTestId, String removeButtonTestId) {
        LoggerUtil.step("TF8: Add product " + productName);
        inventoryPageUtil.assertItemNamedVisible(productName);
        inventoryPageUtil.clickAddToCart(addButtonTestId);
        inventoryPageUtil.assertRemoveButtonVisible(removeButtonTestId);
        inventoryPageUtil.assertCartBadgeCount("1");
    }

    @TestFlow(description = "TF8: Verify user can add a product to cart with expected badge")
    public void addProduct(String productName, String addButtonTestId, String removeButtonTestId, String expectedBadge) {
        LoggerUtil.step("TF8: Add product " + productName + " (badge " + expectedBadge + ")");
        inventoryPageUtil.assertItemNamedVisible(productName);
        inventoryPageUtil.clickAddToCart(addButtonTestId);
        inventoryPageUtil.assertRemoveButtonVisible(removeButtonTestId);
        inventoryPageUtil.assertCartBadgeCount(expectedBadge);
    }

    @TestFlow(description = "TF9: Verify user can add a second product to cart")
    public void addSecondProduct(String productName, String addButtonTestId, String removeButtonTestId) {
        LoggerUtil.step("TF9: Add second product " + productName);
        inventoryPageUtil.assertItemNamedVisible(productName);
        inventoryPageUtil.clickAddToCart(addButtonTestId);
        inventoryPageUtil.assertRemoveButtonVisible(removeButtonTestId);
        inventoryPageUtil.assertCartBadgeCount("2");
    }

    @TestFlow(description = "TF9: Verify user can add another product with expected badge")
    public void addSecondProduct(String productName, String addButtonTestId, String removeButtonTestId, String expectedBadge) {
        LoggerUtil.step("TF9: Add product " + productName + " (badge " + expectedBadge + ")");
        inventoryPageUtil.assertItemNamedVisible(productName);
        inventoryPageUtil.clickAddToCart(addButtonTestId);
        inventoryPageUtil.assertRemoveButtonVisible(removeButtonTestId);
        inventoryPageUtil.assertCartBadgeCount(expectedBadge);
    }

    @TestFlow(description = "TF10: Verify cart badge count")
    public void validateCartBadge(String expectedCount) {
        LoggerUtil.step("TF10: Cart badge = " + expectedCount);
        inventoryPageUtil.assertCartBadgeCount(expectedCount);
    }

    @TestFlow(description = "TF11: Verify user can open cart from inventory")
    public void openCart() {
        LoggerUtil.step("TF11: Open cart");
        inventoryPageUtil.clickShoppingCart();
        inventoryPageUtil.assertCartUrl();
    }

    @TestFlow(description = "TF12: Verify user can sort products")
    public void sortProducts(String optionValue, String expectedFirstName, String expectedLastName) {
        LoggerUtil.step("TF12: Sort products by " + optionValue);
        inventoryPageUtil.assertSortDropdownVisible();
        inventoryPageUtil.selectSortOption(optionValue);
        inventoryPageUtil.assertFirstItemName(expectedFirstName);
        inventoryPageUtil.assertLastItemName(expectedLastName);
    }

    @TestFlow(description = "TF13: Verify user can remove a product from inventory")
    public void removeProduct(String removeButtonTestId, String addButtonTestId) {
        LoggerUtil.step("TF13: Remove product on inventory");
        inventoryPageUtil.clickRemove(removeButtonTestId);
        inventoryPageUtil.assertAddButtonVisible(addButtonTestId);
        inventoryPageUtil.assertCartBadgeHidden();
    }

    @TestFlow(description = "TF helper: assert cart badge hidden")
    public void validateCartBadgeHidden() {
        LoggerUtil.step("Assert cart badge hidden");
        inventoryPageUtil.assertCartBadgeHidden();
    }

    public void validateProductVisible(String itemName) {
        inventoryPageUtil.assertItemNamedVisible(itemName);
    }
}
