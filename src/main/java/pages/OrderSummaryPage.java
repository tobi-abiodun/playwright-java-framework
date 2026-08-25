package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.LocatorFactory;
import utils.YamlReader;

import java.util.Map;

/**
 * OrderSummaryPage — Checkout Overview. Getters only.
 */
public class OrderSummaryPage {

    private final Page page;
    private final Locator heading;
    private final Locator cartList;
    private final Locator itemName;
    private final Locator finishButton;
    private final Locator cancelButton;
    private final Locator itemTotalLabel;
    private final Locator taxLabel;
    private final Locator totalLabel;
    private final Locator paymentInfoLabel;
    private final Locator shippingInfoLabel;

    public OrderSummaryPage(Page page) {
        this.page = page;
        Map<String, String> locators = YamlReader.readLocators("orderSummaryPage.yaml", "orderSummaryPage");
        this.heading = LocatorFactory.fromYaml(page, locators.get("heading"));
        this.cartList = LocatorFactory.fromYaml(page, locators.get("cartList"));
        this.itemName = LocatorFactory.fromYaml(page, locators.get("itemName"));
        this.finishButton = LocatorFactory.fromYaml(page, locators.get("finishButton"));
        this.cancelButton = LocatorFactory.fromYaml(page, locators.get("cancelButton"));
        this.itemTotalLabel = LocatorFactory.fromYaml(page, locators.get("itemTotalLabel"));
        this.taxLabel = LocatorFactory.fromYaml(page, locators.get("taxLabel"));
        this.totalLabel = LocatorFactory.fromYaml(page, locators.get("totalLabel"));
        this.paymentInfoLabel = LocatorFactory.fromYaml(page, locators.get("paymentInfoLabel"));
        this.shippingInfoLabel = LocatorFactory.fromYaml(page, locators.get("shippingInfoLabel"));
    }

    public Page page() {
        return page;
    }

    public Locator heading() {
        return heading;
    }

    public Locator cartList() {
        return cartList;
    }

    public Locator itemName() {
        return itemName;
    }

    public Locator finishButton() {
        return finishButton;
    }

    public Locator cancelButton() {
        return cancelButton;
    }

    public Locator itemTotalLabel() {
        return itemTotalLabel;
    }

    public Locator taxLabel() {
        return taxLabel;
    }

    public Locator totalLabel() {
        return totalLabel;
    }

    public Locator paymentInfoLabel() {
        return paymentInfoLabel;
    }

    public Locator shippingInfoLabel() {
        return shippingInfoLabel;
    }
}
