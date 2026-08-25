# Sauce Demo — Test documentation

Traditional QA format (Step | Action | Expected Result). **Not** Gherkin.

## Concepts

| Term | Meaning |
|------|---------|
| **TF** | Reusable independent steps for one screen/action |
| **TS** | E2E-only case that composes TFs in order |

## Six screens

| # | Screen | Locators | Page | Util | TestFlow | Spec |
|---|--------|----------|------|------|----------|------|
| 1 | Login | `loginPage.yaml` | `LoginPage` | `LoginPageUtil` | `LoginTestFlow` | [testflows/login.md](testflows/login.md) |
| 2 | Inventory | `inventoryPage.yaml` | `InventoryPage` | `InventoryPageUtil` | `InventoryTestFlow` | [testflows/inventory.md](testflows/inventory.md) |
| 3 | Cart | `cartPage.yaml` | `CartPage` | `CartPageUtil` | `CartTestFlow` | [testflows/cart.md](testflows/cart.md) |
| 4 | Checkout Info | `checkoutInfoPage.yaml` | `CheckoutInfoPage` | `CheckoutInfoPageUtil` | `CheckoutInfoTestFlow` | [testflows/checkout-info.md](testflows/checkout-info.md) |
| 5 | Order Summary | `orderSummaryPage.yaml` | `OrderSummaryPage` | `OrderSummaryPageUtil` | `OrderSummaryTestFlow` | [testflows/order-summary.md](testflows/order-summary.md) |
| 6 | Order Confirmation | `orderConfirmationPage.yaml` | `OrderConfirmationPage` | `OrderConfirmationPageUtil` | `OrderConfirmationTestFlow` | [testflows/order-confirmation.md](testflows/order-confirmation.md) |

Order Summary (`checkout-step-two`) ≠ Order Confirmation (`checkout-complete`).

## E2E cases

[E2E-TESTCASES.md](E2E-TESTCASES.md) — TS001–TS020

## Locator notes

[Checkout-Locators.md](Checkout-Locators.md)
