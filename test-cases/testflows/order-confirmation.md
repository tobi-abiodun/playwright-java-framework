# Order Confirmation TestFlows

Screen: Checkout — **Order Confirmation** · URL: `/checkout-complete.html`  
Code: `orderConfirmationPage.yaml` → `OrderConfirmationPage` → `OrderConfirmationPageUtil` → `OrderConfirmationTestFlow`

Thank-you page **after** Finish.  
Different from **Order Summary** (`checkout-step-two.html`).

---

## TF27 — Verify the order confirmation (thank you) page

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Check URL | `checkout-complete.html` |
| 2 | Check message | Contains “Thank you for your order” |
| 3 | Check Back Home | Button visible |

## TF28 — Verify Back Home from confirmation returns to inventory

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Back Home | Inventory page displayed |
| 2 | Check cart badge | Empty / not visible after completed order |
