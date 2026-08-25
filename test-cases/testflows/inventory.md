# Inventory TestFlows

Screen: Inventory / Products · URL: `/inventory.html`  
Code: `inventoryPage.yaml` → `InventoryPage` → `InventoryPageUtil` → `InventoryTestFlow`

---

## TF7 — Verify the inventory (products) screen

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Observe heading | Products heading visible |
| 2 | Observe list | Inventory list visible |
| 3 | Observe cart link | Shopping cart icon visible |
| 4 | Check URL | Contains `/inventory.html` |

## TF8 — Verify user can add a product to cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Find product by name | Product visible |
| 2 | Click Add to cart | Button changes to Remove |
| 3 | Check cart badge | Badge shows count (e.g. `1`) |

## TF9 — Verify user can add a second product to cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Add to cart for second product | Added |
| 2 | Check cart badge | Badge shows `2` (or prior + 1) |

## TF10 — Verify cart badge count

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Read cart badge | Matches expected count |

## TF11 — Verify user can open cart from inventory

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click shopping cart icon | Navigates to cart page |

## TF12 — Verify user can sort products

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Open sort dropdown | Options visible |
| 2 | Select a sort option | List reorders |
| 3 | Confirm first/last item match sort | Order is correct |

## TF13 — Verify user can remove a product from inventory

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click Remove for that product | Button returns to Add to cart |
| 2 | Check badge | Decremented or hidden if zero |
