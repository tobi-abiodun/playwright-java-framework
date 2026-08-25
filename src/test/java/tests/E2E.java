package tests;

import core.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutInfoPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.OrderConfirmationPage;
import pages.OrderSummaryPage;
import testflow.CartTestFlow;
import testflow.CheckoutInfoTestFlow;
import testflow.InventoryTestFlow;
import testflow.LoginTestFlow;
import testflow.OrderConfirmationTestFlow;
import testflow.OrderSummaryTestFlow;
import utils.CartPageUtil;
import utils.CheckoutInfoPageUtil;
import utils.InventoryPageUtil;
import utils.LoginPageUtil;
import utils.OrderConfirmationPageUtil;
import utils.OrderSummaryPageUtil;
import utils.TestDataReader;

/**
 * E2E — one @Test per TS001–TS020. Tests only call TestFlows + TestDataReader.
 */
public class E2E extends BaseTest {

    private LoginTestFlow loginTestFlow;
    private InventoryTestFlow inventoryTestFlow;
    private CartTestFlow cartTestFlow;
    private CheckoutInfoTestFlow checkoutInfoTestFlow;
    private OrderSummaryTestFlow orderSummaryTestFlow;
    private OrderConfirmationTestFlow orderConfirmationTestFlow;

    @BeforeMethod
    public void setUp() {
        loginTestFlow = new LoginTestFlow(new LoginPageUtil(new LoginPage(page)));
        inventoryTestFlow = new InventoryTestFlow(new InventoryPageUtil(new InventoryPage(page)));
        cartTestFlow = new CartTestFlow(new CartPageUtil(new CartPage(page)));
        checkoutInfoTestFlow = new CheckoutInfoTestFlow(new CheckoutInfoPageUtil(new CheckoutInfoPage(page)));
        orderSummaryTestFlow = new OrderSummaryTestFlow(new OrderSummaryPageUtil(new OrderSummaryPage(page)));
        orderConfirmationTestFlow = new OrderConfirmationTestFlow(
                new OrderConfirmationPageUtil(new OrderConfirmationPage(page)));
    }

    private String user(String key) {
        return TestDataReader.getUsername(TestDataReader.LOGIN_USERS_FILE, key);
    }

    private String pass(String key) {
        return TestDataReader.getPassword(TestDataReader.LOGIN_USERS_FILE, key);
    }

    private String name(String productKey) {
        return TestDataReader.productName(productKey);
    }

    private String price(String productKey) {
        return TestDataReader.productPrice(productKey);
    }

    private String addBtn(String productKey) {
        return TestDataReader.productAddButton(productKey);
    }

    private String removeBtn(String productKey) {
        return TestDataReader.productRemoveButton(productKey);
    }

    private void loginStandardThroughInventory() {
        loginTestFlow.validateLoginPage(); // TF1
        loginTestFlow.validateLoginSuccess(user(TestDataReader.VALID_USER), pass(TestDataReader.VALID_USER)); // TF2
        inventoryTestFlow.validateScreen(); // TF7
    }

    private void addProductAndBadge(String productKey, String badge) {
        inventoryTestFlow.addProduct(name(productKey), addBtn(productKey), removeBtn(productKey)); // TF8
        inventoryTestFlow.validateCartBadge(badge); // TF10
    }

    private void openCartAndValidateItem(String productKey) {
        inventoryTestFlow.openCart(); // TF11
        cartTestFlow.validateScreen(); // TF14
        cartTestFlow.validateItemVisible(name(productKey)); // TF15
    }

    private void checkoutValidCustomer() {
        cartTestFlow.proceedToCheckout(); // TF17
        checkoutInfoTestFlow.validateScreen(); // TF19
        checkoutInfoTestFlow.submitCustomerInfo(
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "firstName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "lastName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "postalCode")
        ); // TF20
    }

    private void summaryFinishConfirm(String productKey) {
        orderSummaryTestFlow.validateScreen(); // TF23
        orderSummaryTestFlow.validateItemAndTotals(name(productKey), price(productKey)); // TF24
        orderSummaryTestFlow.finish(); // TF25
        orderConfirmationTestFlow.validateScreen(); // TF27
    }

    private void placeOrderForProduct(String productKey) {
        loginStandardThroughInventory();
        addProductAndBadge(productKey, "1");
        openCartAndValidateItem(productKey);
        checkoutValidCustomer();
        summaryFinishConfirm(productKey);
    }

    @Test(description = "TS001 — Verify user can place an order (single product)")
    public void ts001PlaceOrderSingleProduct() {
        placeOrderForProduct(TestDataReader.BACKPACK);
    }

    @Test(description = "TS002 — Verify user can place an order with two products")
    public void ts002PlaceOrderTwoProducts() {
        loginStandardThroughInventory();
        inventoryTestFlow.addProduct(name(TestDataReader.BACKPACK), addBtn(TestDataReader.BACKPACK), removeBtn(TestDataReader.BACKPACK)); // TF8
        inventoryTestFlow.addSecondProduct(name(TestDataReader.BIKE_LIGHT), addBtn(TestDataReader.BIKE_LIGHT), removeBtn(TestDataReader.BIKE_LIGHT)); // TF9
        inventoryTestFlow.validateCartBadge("2"); // TF10
        inventoryTestFlow.openCart(); // TF11
        cartTestFlow.validateScreen(); // TF14
        cartTestFlow.validateItemVisibleOnly(name(TestDataReader.BACKPACK)); // TF15
        cartTestFlow.validateItemVisibleOnly(name(TestDataReader.BIKE_LIGHT)); // TF15
        checkoutValidCustomer();
        orderSummaryTestFlow.validateScreen(); // TF23
        orderSummaryTestFlow.validateItemVisible(name(TestDataReader.BACKPACK));
        orderSummaryTestFlow.validateItemVisible(name(TestDataReader.BIKE_LIGHT));
        orderSummaryTestFlow.validateTotals(); // TF24
        orderSummaryTestFlow.validateItemAndTotals(name(TestDataReader.BACKPACK), "$39.98"); // Item total = both products
        orderSummaryTestFlow.finish(); // TF25
        orderConfirmationTestFlow.validateScreen(); // TF27
    }

    @Test(description = "TS003 — Verify locked-out user cannot place an order")
    public void ts003LockedOutCannotOrder() {
        loginTestFlow.validateLoginPage(); // TF1
        loginTestFlow.validateLockedOutLogin(user(TestDataReader.LOCKED_OUT_USER), pass(TestDataReader.LOCKED_OUT_USER)); // TF3
    }

    @Test(description = "TS004 — Verify invalid credentials cannot start an order")
    public void ts004InvalidCredentialsCannotOrder() {
        loginTestFlow.validateLoginPage(); // TF1
        loginTestFlow.validateInvalidCredentials(user(TestDataReader.UNKNOWN_USER), pass(TestDataReader.UNKNOWN_USER)); // TF4
    }

    @Test(description = "TS005 — Verify empty username cannot start an order")
    public void ts005EmptyUsernameCannotOrder() {
        loginTestFlow.validateLoginPage(); // TF1
        loginTestFlow.validateEmptyUsername(pass(TestDataReader.EMPTY_USERNAME)); // TF5
    }

    @Test(description = "TS006 — Verify empty password cannot start an order")
    public void ts006EmptyPasswordCannotOrder() {
        loginTestFlow.validateLoginPage(); // TF1
        loginTestFlow.validateEmptyPassword(user(TestDataReader.EMPTY_PASSWORD)); // TF6
    }

    @Test(description = "TS007 — Verify Continue Shopping then complete an order")
    public void ts007ContinueShoppingThenOrder() {
        loginStandardThroughInventory();
        addProductAndBadge(TestDataReader.BACKPACK, "1");
        inventoryTestFlow.openCart(); // TF11
        cartTestFlow.validateScreen(); // TF14
        cartTestFlow.continueShopping(); // TF16
        inventoryTestFlow.validateScreen(); // back on inventory
        inventoryTestFlow.addSecondProduct(name(TestDataReader.BIKE_LIGHT), addBtn(TestDataReader.BIKE_LIGHT), removeBtn(TestDataReader.BIKE_LIGHT)); // TF9
        inventoryTestFlow.validateCartBadge("2");
        inventoryTestFlow.openCart(); // TF11
        cartTestFlow.validateScreen(); // TF14
        cartTestFlow.validateItemVisibleOnly(name(TestDataReader.BACKPACK));
        cartTestFlow.validateItemVisibleOnly(name(TestDataReader.BIKE_LIGHT));
        checkoutValidCustomer();
        orderSummaryTestFlow.validateScreen();
        orderSummaryTestFlow.validateTotals();
        orderSummaryTestFlow.finish();
        orderConfirmationTestFlow.validateScreen();
    }

    @Test(description = "TS008 — Verify remove from cart then still place an order")
    public void ts008RemoveFromCartThenOrder() {
        loginStandardThroughInventory();
        inventoryTestFlow.addProduct(name(TestDataReader.BACKPACK), addBtn(TestDataReader.BACKPACK), removeBtn(TestDataReader.BACKPACK));
        inventoryTestFlow.addSecondProduct(name(TestDataReader.BIKE_LIGHT), addBtn(TestDataReader.BIKE_LIGHT), removeBtn(TestDataReader.BIKE_LIGHT));
        inventoryTestFlow.openCart();
        cartTestFlow.validateScreen();
        cartTestFlow.removeProduct(removeBtn(TestDataReader.BACKPACK), name(TestDataReader.BACKPACK)); // TF18
        cartTestFlow.validateItemVisible(name(TestDataReader.BIKE_LIGHT));
        checkoutValidCustomer();
        summaryFinishConfirm(TestDataReader.BIKE_LIGHT);
    }

    @Test(description = "TS009 — Verify remove on inventory then re-add and place order")
    public void ts009RemoveOnInventoryReAddOrder() {
        loginStandardThroughInventory();
        inventoryTestFlow.addProduct(name(TestDataReader.BACKPACK), addBtn(TestDataReader.BACKPACK), removeBtn(TestDataReader.BACKPACK)); // TF8
        inventoryTestFlow.removeProduct(removeBtn(TestDataReader.BACKPACK), addBtn(TestDataReader.BACKPACK)); // TF13
        inventoryTestFlow.validateCartBadgeHidden();
        addProductAndBadge(TestDataReader.BACKPACK, "1"); // TF8 + TF10
        openCartAndValidateItem(TestDataReader.BACKPACK);
        checkoutValidCustomer();
        orderSummaryTestFlow.validateScreen(); // TF23
        orderSummaryTestFlow.finish(); // TF25 (no TF24 in spec)
        orderConfirmationTestFlow.validateScreen(); // TF27
    }

    @Test(description = "TS010 — Verify checkout info validation then recover and place order")
    public void ts010CheckoutValidationThenRecover() {
        loginStandardThroughInventory();
        addProductAndBadge(TestDataReader.BACKPACK, "1");
        openCartAndValidateItem(TestDataReader.BACKPACK);
        cartTestFlow.proceedToCheckout(); // TF17
        checkoutInfoTestFlow.validateScreen(); // TF19
        checkoutInfoTestFlow.validateFirstNameRequiredError(
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.MISSING_FIRST_NAME, "firstName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.MISSING_FIRST_NAME, "lastName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.MISSING_FIRST_NAME, "postalCode")
        ); // TF21
        checkoutInfoTestFlow.submitCustomerInfo(
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "firstName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "lastName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "postalCode")
        ); // TF20
        orderSummaryTestFlow.validateScreen();
        orderSummaryTestFlow.finish();
        orderConfirmationTestFlow.validateScreen();
    }

    @Test(description = "TS011 — Verify Cancel on checkout info then complete order")
    public void ts011CancelCheckoutInfoThenOrder() {
        loginStandardThroughInventory();
        addProductAndBadge(TestDataReader.BACKPACK, "1");
        openCartAndValidateItem(TestDataReader.BACKPACK);
        cartTestFlow.proceedToCheckout();
        checkoutInfoTestFlow.validateScreen();
        checkoutInfoTestFlow.cancel(); // TF22
        cartTestFlow.validateScreen();
        cartTestFlow.validateItemVisible(name(TestDataReader.BACKPACK));
        checkoutValidCustomer();
        orderSummaryTestFlow.validateScreen();
        orderSummaryTestFlow.finish();
        orderConfirmationTestFlow.validateScreen();
    }

    @Test(description = "TS012 — Verify Cancel on order summary returns to inventory")
    public void ts012CancelOrderSummary() {
        loginStandardThroughInventory();
        addProductAndBadge(TestDataReader.BACKPACK, "1");
        openCartAndValidateItem(TestDataReader.BACKPACK);
        checkoutValidCustomer();
        orderSummaryTestFlow.validateScreen(); // TF23
        orderSummaryTestFlow.cancel(); // TF26
        inventoryTestFlow.validateScreen(); // TF7
    }

    @Test(description = "TS013 — Verify order confirmation and Back Home")
    public void ts013ConfirmationBackHome() {
        placeOrderForProduct(TestDataReader.BACKPACK);
        orderConfirmationTestFlow.backHome(); // TF28
        inventoryTestFlow.validateScreen(); // TF7
        inventoryTestFlow.validateCartBadgeHidden();
    }

    @Test(description = "TS014 — Verify sort then place order for lowest-price item")
    public void ts014SortThenOrderCheapest() {
        loginStandardThroughInventory();
        inventoryTestFlow.sortProducts(
                "lohi",
                name(TestDataReader.ONESIE),
                name(TestDataReader.FLEECE_JACKET)
        ); // TF12
        addProductAndBadge(TestDataReader.ONESIE, "1");
        openCartAndValidateItem(TestDataReader.ONESIE);
        checkoutValidCustomer();
        summaryFinishConfirm(TestDataReader.ONESIE);
    }

    @Test(description = "TS015 — Verify place order for Bike Light only")
    public void ts015PlaceOrderBikeLight() {
        placeOrderForProduct(TestDataReader.BIKE_LIGHT);
    }

    @Test(description = "TS016 — Verify place order for Fleece Jacket only")
    public void ts016PlaceOrderFleeceJacket() {
        placeOrderForProduct(TestDataReader.FLEECE_JACKET);
    }

    @Test(description = "TS017 — Verify place order for Onesie only")
    public void ts017PlaceOrderOnesie() {
        placeOrderForProduct(TestDataReader.ONESIE);
    }

    @Test(description = "TS018 — Verify place order for Bolt T-Shirt only")
    public void ts018PlaceOrderBoltTShirt() {
        placeOrderForProduct(TestDataReader.BOLT_T_SHIRT);
    }

    @Test(description = "TS019 — Verify place order for Red T-Shirt only")
    public void ts019PlaceOrderRedTShirt() {
        placeOrderForProduct(TestDataReader.RED_T_SHIRT);
    }

    @Test(description = "TS020 — Empty cart through checkout summary")
    public void ts020EmptyCartThroughSummary() {
        loginStandardThroughInventory();
        inventoryTestFlow.openCart(); // TF11
        cartTestFlow.validateScreen(); // TF14
        cartTestFlow.proceedToCheckout(); // TF17
        checkoutInfoTestFlow.validateScreen(); // TF19
        checkoutInfoTestFlow.submitCustomerInfo(
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "firstName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "lastName"),
                TestDataReader.getValue(TestDataReader.CHECKOUT_FILE, TestDataReader.VALID_CUSTOMER, "postalCode")
        ); // TF20
        orderSummaryTestFlow.validateScreen(); // TF23
        orderSummaryTestFlow.validateNoLineItems();
    }
}
