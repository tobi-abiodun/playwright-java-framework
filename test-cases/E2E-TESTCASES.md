# E2E Test Cases — Sauce Demo

Only **end-to-end** scenarios. Each **TS** chains **TestFlows** from [testflows/](./testflows/).

**Screens in a full purchase:**  
Login → Inventory → Cart → Checkout Info → **Order Summary** → **Order Confirmation**

---

## TS001 — Verify user can place an order (single product)

| | |
|--|--|
| Priority | High |
| Data | `standard_user`, Backpack, `validCustomer` |

| Step | TestFlow | What it validates |
|------|----------|-------------------|
| 1 | TF1 | Login page |
| 2 | TF2 | Successful login |
| 3 | TF7 | Inventory screen |
| 4 | TF8 | Add Backpack |
| 5 | TF10 | Badge = 1 |
| 6 | TF11 | Open cart |
| 7 | TF14 | Cart screen |
| 8 | TF15 | Backpack in basket |
| 9 | TF17 | Go to checkout info |
| 10 | TF19 | Checkout info screen |
| 11 | TF20 | Submit customer info |
| 12 | TF23 | **Order summary** screen |
| 13 | TF24 | Item + totals on summary |
| 14 | TF25 | Finish |
| 15 | TF27 | **Order confirmation** (thank you) |

**Pass:** Thank-you page shown after full path through order summary.

---

## TS002 — Verify user can place an order with two products

| Step | TestFlow |
|------|----------|
| 1–3 | TF1, TF2, TF7 |
| 4 | TF8 (Backpack) |
| 5 | TF9 (Bike Light) |
| 6 | TF10 (badge = 2) |
| 7–9 | TF11, TF14, TF15 (both items) |
| 10–15 | TF17, TF19, TF20, TF23, TF24, TF25, TF27 |

**Pass:** Both items on cart and summary; confirmation shown.

---

## TS003 — Verify locked-out user cannot place an order

| Step | TestFlow |
|------|----------|
| 1 | TF1 |
| 2 | TF3 |

**Pass:** Error shown; inventory/cart/checkout never reached.

---

## TS004 — Verify invalid credentials cannot start an order

| Step | TestFlow |
|------|----------|
| 1 | TF1 |
| 2 | TF4 |

**Pass:** Stay on login; no inventory.

---

## TS005 — Verify empty username cannot start an order

| Step | TestFlow |
|------|----------|
| 1 | TF1 |
| 2 | TF5 |

**Pass:** Username required error; no inventory.

---

## TS006 — Verify empty password cannot start an order

| Step | TestFlow |
|------|----------|
| 1 | TF1 |
| 2 | TF6 |

**Pass:** Password required error; no inventory.

---

## TS007 — Verify Continue Shopping then complete an order

| Step | TestFlow |
|------|----------|
| 1–6 | TF1, TF2, TF7, TF8, TF11, TF14 |
| 7 | TF16 (back to inventory) |
| 8 | TF9 (add second item) |
| 9–15 | TF11, TF14, TF15, TF17, TF19, TF20, TF23, TF24, TF25, TF27 |

**Pass:** Two items purchased after Continue Shopping detour.

---

## TS008 — Verify remove from cart then still place an order

| Step | TestFlow |
|------|----------|
| 1–6 | TF1, TF2, TF7, TF8, TF9, TF11 |
| 7 | TF14, TF18 (remove one item) |
| 8 | TF10 (badge = 1) |
| 9–14 | TF17, TF19, TF20, TF23, TF24, TF25, TF27 |

**Pass:** Remaining item ordered successfully.

---

## TS009 — Verify remove on inventory then re-add and place order

| Step | TestFlow |
|------|----------|
| 1–4 | TF1, TF2, TF7, TF8 |
| 5 | TF13 (remove on inventory) |
| 6 | TF8 (add again) |
| 7–14 | TF11, TF14, TF15, TF17, TF19, TF20, TF23, TF25, TF27 |

**Pass:** Order completes after remove/re-add.

---

## TS010 — Verify checkout info validation then recover and place order

| Step | TestFlow |
|------|----------|
| 1–9 | TF1 → TF2 → TF7 → TF8 → TF11 → TF14 → TF17 → TF19 |
| 10 | TF21 (empty first name → error) |
| 11 | TF20 (submit valid info) |
| 12–14 | TF23, TF25, TF27 |

**Pass:** Error first, then successful order through summary + confirmation.

---

## TS011 — Verify Cancel on checkout info then complete order

| Step | TestFlow |
|------|----------|
| 1–9 | Through TF17, TF19 |
| 10 | TF22 (Cancel → cart) |
| 11 | TF17 (Checkout again) |
| 12–15 | TF19, TF20, TF23, TF25, TF27 |

**Pass:** Cancel does not lose cart; order still completes.

---

## TS012 — Verify Cancel on order summary returns to inventory (no confirmation)

| Step | TestFlow |
|------|----------|
| 1–12 | Through TF20, TF23 |
| 13 | TF26 (Cancel on **order summary**) |

**Pass:** Land on inventory; thank-you page **not** shown.

---

## TS013 — Verify order confirmation and Back Home

| Step | TestFlow |
|------|----------|
| 1–14 | Same as TS001 through TF25, TF27 |
| 15 | TF28 (Back Home) |
| 16 | TF7 (inventory again) |

**Pass:** After confirmation, Back Home shows empty cart badge / inventory.

---

## TS014 — Verify sort then place order for lowest-price item

| Step | TestFlow |
|------|----------|
| 1–3 | TF1, TF2, TF7 |
| 4 | TF12 (Price low to high) |
| 5 | TF8 (add first listed / Onesie) |
| 6–14 | TF11 → … → TF23 → TF24 → TF25 → TF27 |

**Pass:** Order completes for sorted selection; summary totals match item.

---

## TS015 — Verify place order for Bike Light only

Same path as TS001 with product = Bike Light (TF8/TF15/TF24 data change).

**Pass:** Confirmation; summary shows Bike Light / $9.99 item total.

---

## TS016 — Verify place order for Fleece Jacket only

Same path as TS001 with Fleece Jacket.

**Pass:** Confirmation; summary shows $49.99 item total.

---

## TS017 — Verify place order for Onesie only

Same path as TS001 with Onesie.

**Pass:** Confirmation; summary shows $7.99 item total.

---

## TS018 — Verify place order for Bolt T-Shirt only

Same path as TS001 with Bolt T-Shirt.

**Pass:** Confirmation shown.

---

## TS019 — Verify place order for Red T-Shirt only

Same path as TS001 with Test.allTheThings() T-Shirt (Red).

**Pass:** Confirmation shown.

---

## TS020 — Verify empty cart still reaches checkout info but summary has no items

| Step | TestFlow |
|------|----------|
| 1–3 | TF1, TF2, TF7 |
| 4 | TF11 (open cart with no adds) |
| 5 | TF14 |
| 6 | TF17 |
| 7 | TF19, TF20 |
| 8 | TF23 |

**Pass:** Overview/summary opens with **no** line items (Sauce Demo allows this). Document as known app behavior.

---

## Catalog

| ID | Title | Path highlight |
|----|--------|----------------|
| TS001 | Place order — single product (Backpack) | Full path incl. summary + confirmation |
| TS002 | Place order — two products | Badge 2 + both on summary |
| TS003 | Locked-out cannot order | Stops at login |
| TS004 | Invalid credentials cannot order | Stops at login |
| TS005 | Empty username cannot order | Stops at login |
| TS006 | Empty password cannot order | Stops at login |
| TS007 | Continue Shopping then order | Cart detour |
| TS008 | Remove from cart then order | Cart edit |
| TS009 | Remove on inventory, re-add, order | Inventory edit |
| TS010 | Info validation then recover + order | Negative then happy |
| TS011 | Cancel info then order | Checkout cancel |
| TS012 | Cancel on **order summary** | No confirmation |
| TS013 | Confirmation + Back Home | Post-purchase |
| TS014 | Sort + order cheapest | Inventory sort |
| TS015–TS019 | Place order — each other catalog product | Data variants of TS001 |
| TS020 | Empty cart through summary | Edge / app behavior |

Next new E2E case = **TS021**.
