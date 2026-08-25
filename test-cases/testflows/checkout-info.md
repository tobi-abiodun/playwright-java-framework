# Checkout Info TestFlows

Screen: Checkout — Your Information · URL: `/checkout-step-one.html`  
Code: `checkoutInfoPage.yaml` → `CheckoutInfoPage` → `CheckoutInfoPageUtil` → `CheckoutInfoTestFlow`

---

## TF19 — Verify the checkout information screen

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Check URL | `checkout-step-one.html` |
| 2 | Check heading | Information heading visible |
| 3 | Check fields | First Name, Last Name, Postal Code visible |
| 4 | Check buttons | Continue and Cancel visible |

## TF20 — Verify user can submit valid customer information

| Data | `validCustomer` from `checkout.yaml` |

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter First Name | Accepted |
| 2 | Enter Last Name | Accepted |
| 3 | Enter Postal Code | Accepted |
| 4 | Click Continue | **Order summary** opens |
| 5 | Check URL | `checkout-step-two.html` |
| 6 | Check errors | No error banner |

## TF21 — Verify checkout info rejects empty first name

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Leave First Name empty | — |
| 2 | Click Continue | Stay on info page |
| 3 | Check error | “First Name is required” |

## TF22 — Verify Cancel on checkout info returns to cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Cancel | Cart page displayed |
| 2 | Check items | Prior cart items still present |
