# Login TestFlows

Screen: Login · URL: `/`  
Code: `loginPage.yaml` → `LoginPage` → `LoginPageUtil` → `LoginTestFlow`

---

## TF1 — Verify the login page

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Open `https://www.saucedemo.com/` | Login page loads |
| 2 | Check Username field | Visible and enabled |
| 3 | Check Password field | Visible and enabled |
| 4 | Check Login button | Visible and enabled |
| 5 | Confirm not on inventory | Products page is not shown |

## TF2 — Verify user can login successfully

| Data | `standard_user` / `secret_sauce` |

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter username | Value accepted |
| 2 | Enter password | Value accepted |
| 3 | Click Login | Submit |
| 4 | Check URL | Contains `/inventory.html` |
| 5 | Check products UI | Products heading/list visible |
| 6 | Check errors | No login error shown |

## TF3 — Verify locked-out user cannot login

| Data | `locked_out_user` / `secret_sauce` |

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter username and password | Entered |
| 2 | Click Login | Attempted |
| 3 | Check page | Still on login |
| 4 | Check error | Message contains “locked out” |
| 5 | Check inventory | Products page not shown |

## TF4 — Verify invalid credentials cannot login

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter invalid credentials | Entered |
| 2 | Click Login | Attempted |
| 3 | Check page | Still on login |
| 4 | Check error | Credentials do not match |

## TF5 — Verify empty username cannot login

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Leave username empty; enter password | — |
| 2 | Click Login | Attempted |
| 3 | Check error | “Username is required” |

## TF6 — Verify empty password cannot login

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter username; leave password empty | — |
| 2 | Click Login | Attempted |
| 3 | Check error | “Password is required” |
