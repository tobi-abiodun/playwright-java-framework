# Checkout screens — locators (3 separate layers)

| # | Screen | URL | YAML | Page | Util | TestFlow |
|---|--------|-----|------|------|------|----------|
| 4 | Your Information | `/checkout-step-one.html` | `checkoutInfoPage.yaml` | `CheckoutInfoPage` | `CheckoutInfoPageUtil` | `CheckoutInfoTestFlow` |
| 5 | Order Summary | `/checkout-step-two.html` | `orderSummaryPage.yaml` | `OrderSummaryPage` | `OrderSummaryPageUtil` | `OrderSummaryTestFlow` |
| 6 | Order Confirmation | `/checkout-complete.html` | `orderConfirmationPage.yaml` | `OrderConfirmationPage` | `OrderConfirmationPageUtil` | `OrderConfirmationTestFlow` |

The old combined `checkoutPage.yaml` / `CheckoutPage` / `CheckoutPageUtil` was removed.
