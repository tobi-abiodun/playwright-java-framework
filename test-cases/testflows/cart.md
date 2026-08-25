# Cart TestFlows

Screen: Cart (basket) · URL: `/cart.html`  
Code: `cartPage.yaml` → `CartPage` → `CartPageUtil` → `CartTestFlow`

---

## TF14 — Verify the cart (basket) screen

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Check URL | Contains `/cart.html` |
| 2 | Check heading | Cart heading visible |
| 3 | Check cart list | Cart list visible |
| 4 | Check Checkout | Checkout button visible |
| 5 | Check Continue Shopping | Continue Shopping visible |

## TF15 — Verify cart contains expected product

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Look for product name in cart | Product is listed |
| 2 | Check quantity | Quantity is `1` (unless specified) |

## TF16 — Verify Continue Shopping returns to inventory

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Continue Shopping | Inventory page displayed |
| 2 | Check cart badge | Still reflects items in cart |

## TF17 — Verify user can proceed to checkout from cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Checkout | Checkout information page opens |
| 2 | Check URL | Contains `checkout-step-one.html` |

## TF18 — Verify user can remove product from cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Remove for the product | Item no longer in cart |
| 2 | Check badge | Updated / hidden if empty |
