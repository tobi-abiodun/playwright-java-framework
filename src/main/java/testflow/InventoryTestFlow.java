package testflow;

import utils.InventoryPageUtil;

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
        inventoryPageUtil.assertInventoryUrl();
        inventoryPageUtil.assertProductsHeadingVisible();
        inventoryPageUtil.assertInventoryListVisible();
        inventoryPageUtil.assertShoppingCartVisible();
    }

    @TestFlow(description = "TF8: Verify user can add a product to cart")
    public void addProduct(String productName, String addButtonTestId, String removeButtonTestId) {
        inventoryPageUtil.assertItemNamedVisible(productName);
        inventoryPageUtil.clickAddToCart(addButtonTestId);
        inventoryPageUtil.assertRemoveButtonVisible(removeButtonTestId);
    }

    @TestFlow(description = "TF9: Verify user can add a second product to cart")
    public void addSecondProduct(String productName, String addButtonTestId, String removeButtonTestId) {
        inventoryPageUtil.assertItemNamedVisible(productName);
        inventoryPageUtil.clickAddToCart(addButtonTestId);
        inventoryPageUtil.assertRemoveButtonVisible(removeButtonTestId);
    }

    @TestFlow(description = "TF10: Verify cart badge count")
    public void validateCartBadge(String expectedCount) {
        inventoryPageUtil.assertCartBadgeCount(expectedCount);
    }

    @TestFlow(description = "TF11: Verify user can open cart from inventory")
    public void openCart() {
        inventoryPageUtil.clickShoppingCart();
    }

    @TestFlow(description = "TF12: Verify user can sort products")
    public void sortProducts(String optionValue, String expectedFirstName, String expectedLastName) {
        inventoryPageUtil.selectSortOption(optionValue);
        inventoryPageUtil.assertFirstItemName(expectedFirstName);
        inventoryPageUtil.assertLastItemName(expectedLastName);
    }

    @TestFlow(description = "TF13: Verify user can remove a product from inventory")
    public void removeProduct(String removeButtonTestId, String addButtonTestId) {
        inventoryPageUtil.clickRemove(removeButtonTestId);
        inventoryPageUtil.assertAddButtonVisible(addButtonTestId);
    }

    @TestFlow(description = "TF helper: assert cart badge hidden")
    public void validateCartBadgeHidden() {
        inventoryPageUtil.assertCartBadgeHidden();
    }

    public void validateProductVisible(String itemName) {
        inventoryPageUtil.assertItemNamedVisible(itemName);
    }
}
