# Order Summary TestFlows

Screen: Checkout — **Order Summary** (Overview) · URL: `/checkout-step-two.html`  
Code: `orderSummaryPage.yaml` → `OrderSummaryPage` → `OrderSummaryPageUtil` → `OrderSummaryTestFlow`

This is the review screen **before** Finish (items, tax, total).  
It is **not** the thank-you page.

---

## TF23 — Verify the order summary screen

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Check URL | `checkout-step-two.html` |
| 2 | Check heading | Overview / summary heading visible |
| 3 | Check Finish | Finish button visible |
| 4 | Check Cancel | Cancel button visible |
| 5 | Check payment/shipping | Labels visible |

## TF24 — Verify order summary shows item and totals

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Check product name(s) | Expected item(s) listed |
| 2 | Check Item total | Matches sum of item prices |
| 3 | Check Tax | Tax amount visible |
| 4 | Check Total | Total = item total + tax |

## TF25 — Verify user can finish order from summary

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Finish | **Order confirmation** page opens |
| 2 | Check URL | `checkout-complete.html` |

## TF26 — Verify Cancel on order summary returns to inventory

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Cancel | Inventory page displayed |
