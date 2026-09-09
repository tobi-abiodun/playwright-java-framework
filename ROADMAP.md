# Framework build plan

> **Status (2026-09-09):** Sauce Demo 6-screen TestFlow architecture is complete
> (Login → Inventory → Cart → Checkout Info → Order Summary → Order Confirmation).
> TF1–TF28 are assertion-complete; E2E covers TS001–TS020. Failure evidence
> (screenshots + Allure + Playwright traces), **context video on every test**
> (`test-results/videos/`, `-Dvideo=false` to disable), LoggerUtil, CLI `-D`
> overrides, and GitHub Actions CI are in place. Suite entry: `test.xml`.
>
> Sections below for Steps 1–10 are **historical** and may be stale; trust this
> banner and `test-cases/README.md` for current layout.

This is the working plan we follow together.

**Goal:** Build a real Playwright + Java automation framework that:

- Automates a **real website** (not only dummy HTML)
- Uses **YAML locators**, **Page Objects**, **validation Utils**, and **TestFlow reusable scenarios**
- Has **multiple working test flows**
- Is clean, professional, and ready for **GitHub as a portfolio project**
- Shows **framework design**, not just “writing tests”

---

## How we work together

1. You confirm or pick the next step from this file.
2. I implement the code for that step.
3. You run it in IntelliJ (`test.xml` or `mvn test`) and share pass/fail.
4. We fix issues, then move to the next step.
5. We always keep this layering:

```
YAML locator  →  Page Object  →  Validation Util  →  TestFlow (scenario)  →  Test
```

Example for login:

| Layer | File | Job |
|-------|------|-----|
| Locators | `locators/loginPage.yaml` | Selectors only |
| Page Object | `pages/LoginPage.java` | Actions + element state |
| Validation Util | `utils/LoginPageUtil.java` | Assertions |
| TestFlow | `testflow/LoginTestFlow.java` | Reusable flows |
| Test | `tests/E2E.java` | Calls TestFlow only |

When we add a new page, we repeat that same 5-file pattern. We do **not** put Playwright clicks inside tests.

---

## Current snapshot (honest)

**Done:** 6-screen YAML → Page → Util → TestFlow layers; E2E TS001–TS020;
TF/TS docs under `test-cases/`; ScreenshotListener + Allure; traces on failure;
context video recording (every test); LoggerUtil; CLI overrides; GitHub Actions CI;
refreshed README.

**Next (optional polish):** BasePage inheritance for all pages; move YAML locators
under `src/main/resources/locators/`; Java package rename to `com.automation.framework.*`.

---

## Status legend

- `[x]` Done
- `[~]` Started, not finished
- `[ ]` Not started

---

## Step 1 – Choose the demo website

**Status:** `[x]` Done — **https://www.saucedemo.com**

Selected flows:

- Login success (`standard_user`)
- Login failure (`locked_out_user`)
- Later: inventory, cart, checkout

Config: `base.url` and `login.url` in `config.properties`.
Locators: Sauce Demo `data-test` attributes in `loginPage.yaml`.
Users: `testdata/loginUsers.yaml` (no hard-coded credentials in Java).

---

## Step 2 – Create the project skeleton

**Status:** `[x]` Done

**What we already have:**

- Maven project (`pom.xml`)
- Playwright Java
- TestNG + `test.xml`

**How to confirm:** Open the project in IntelliJ and reload Maven.

---

## Step 3 – Set up folder structure

**Status:** `[x]` Done

```
src/
  main/java/
    locators/
    pages/
    utils/
    testflow/
    config/
    core/
  main/resources/config/
  test/java/
    core/
    listeners/
    tests/
    utils/
  test/resources/testdata/
```

**Goal met:** Locators, page objects, utils, TestFlow, and tests are separated.

---

## Step 4 – Implement YAML locator system

**Status:** `[~]` Login YAML done; dashboard YAML started; more pages needed after a real site is chosen

**Done:** `src/main/java/locators/loginPage.yaml`

**Started:** `src/main/java/locators/dashboardPage.yaml` (built for the dummy HTML, not a real site yet)

**Later:** `inventoryPage.yaml`, `cartPage.yaml`, `checkoutPage.yaml`, etc. for the real site.

---

## Step 5 – Build YAML reader utility

**Status:** `[x]` Done

**File:** `src/main/java/utils/YamlLocatorReader.java`

Page objects load locators with:

```java
YamlLocatorReader.loadLocators("loginPage.yaml", "loginPage")
```

---

## Step 6 – Build the first Page Object (LoginPage.java)

**Status:** `[x]` Done (dummy site)

**File:** `src/main/java/pages/LoginPage.java`

Methods:

- `goToLoginPage()`
- `enterUsername()`
- `enterPassword()`
- `clickLoginButton()`
- `isDashboardVisible()`
- `isErrorVisible()`

**Still needed:** Point `goToLoginPage()` at the real site URL (via config), then update selectors in YAML to match that site.

---

## Step 7 – Build validation util (LoginPageUtil.java)

**Status:** `[x]` Done

**File:** `src/main/java/utils/LoginPageUtil.java`

Methods:

- `validateLoginSuccess()`
- `validateLoginError()`

These call LoginPage state methods, not Playwright directly.

---

## Step 8 – Build TestFlow (LoginTestFlow.java)

**Status:** `[x]` Done

**File:** `src/main/java/testflow/LoginTestFlow.java`

Flows:

- `validateLoginSuccess(username, password)`
- `validateLoginFailure(username, password)`

---

## Step 9 – Build the first test (Login.java)

**Status:** `[x]` Done (dummy site)

**File:** `src/test/java/tests/Login.java`

- Extends `BaseTest` (browser setup is shared)
- Instantiates `LoginTestFlow`
- Calls success and failure flows
- Listed in `test.xml`

**Still needed after Step 1:** Replace values in `testdata/loginUsers.yaml` with that site’s real test users. Tests already read from YAML — do not put credentials in Java.

---

## Step 10 – Add more pages and flows

**Status:** `[~]` Dashboard YAML exists; the rest is not built yet

**Missing (must build):**

- `pages/DashboardPage.java`
- `utils/DashboardPageUtil.java`
- `testflow/DashboardTestFlow.java`
- `tests/Dashboard.java`
- Add Dashboard to `test.xml`

**After the real website is chosen, expand beyond dashboard**, for example on Sauce Demo:

1. Login (already have the pattern)
2. Inventory / products
3. Add to cart
4. Cart
5. Checkout

Each feature gets the same 5 files: YAML → Page → Util → TestFlow → Test.

**Goal:** Prove the framework scales past one login test.

---

## Step 11 – Add utilities

**Status:** `[x]` Done

| Utility | Status | File |
|---------|--------|------|
| Wait helper | Done | `utils/WaitUtil.java` |
| Screenshot helper | Done | `utils/ScreenshotUtil.java` |
| Screenshot on failure listener | Done | `listeners/ScreenshotListener.java` (wired in `test.xml`) |
| Test data YAML | Done | `testdata/*.yaml` + `TestDataReader.java` |
| Logger | Done | `utils/LoggerUtil.java` |

---

## Step 12 – Add Playwright config and environment handling

**Status:** `[x]` Done

**Done:**

- `src/main/resources/config/config.properties` (URL, browser, headless, timeout)
- `config/ConfigReader.java` (system properties override file values)
- `core/PlaywrightFactory.java` (explicit context + tracing)
- `core/BaseTest.java` (saves traces on failure)

---

## Step 13 – Create README for GitHub

**Status:** `[x]` Done — architecture, run, Allure, traces, CLI overrides, CI

**File:** `README.md`

---

## Step 14 – Publish to GitHub

**Status:** `[x]` Done — remote + CI workflow present

Remote:

`https://github.com/tobi-abiodun/playwright-java-framework.git`

---

## What we should do next (in order)

1. Run `mvn test` locally / via CI and keep the suite green.
2. Optional polish: BasePage inheritance, relocate YAML under resources, package rename.
3. Optional: video recording, multi-browser matrix in CI.

---

## Session checklist

DoD reporting/CI items are implemented. Preferred next message:

```
mvn test passed
```

or paste any failure.

---

## Recommended target (once site is chosen): Sauce Demo example

This is the portfolio-ready set we would build if you pick Sauce Demo.

1. Login success (`standard_user` / `secret_sauce`)
2. Login failure (`locked_out_user`)
3. View products / inventory
4. Add item to cart
5. Open cart and verify item
6. Checkout happy path

Each one uses YAML → Page → Util → TestFlow → Test.

---

## Already-started files (do not rebuild from scratch)

Keep and finish these; do not recreate:

- `pages/BasePage.java` — Page Objects should extend this (optional polish)
- `utils/ScreenshotUtil.java`
- `listeners/ScreenshotListener.java`
- `src/test/resources/testdata/loginUsers.yaml`
- `src/test/java/utils/TestDataReader.java`

Remaining optional polish:

- Page objects do **not** extend `BasePage` yet (getter + YamlReader pattern)
- YAML locators still under `src/main/java/locators/` (classpath resource include works)

---

## Necessary items that were missing from the original 14 steps

These are **required** for this framework to be professional. They are not optional extras.

### Design rules (must follow on every page)

Without these, the layers will slowly mix and the framework becomes scripts again.

| Layer | Allowed | Not allowed |
|-------|---------|-------------|
| YAML | Selectors only | Waits, clicks, asserts |
| Page Object | Actions + state (`isVisible`) | Assertions, test data, browser launch |
| Validation Util | Assertions using page state methods | Playwright clicks/fills |
| TestFlow | Combine actions + validations into a flow | Browser setup, hard-coded locators |
| Test | Call TestFlow + load test data | Playwright locators or YAML maps |

Also:

- One test = one independent run. Tests must not depend on another test having run first.
- Browser is started in `BaseTest`, never inside a Page or TestFlow.
- Credentials and product names live in `testdata/`, not in test methods.
- Prefer stable selectors (`id`, `data-test`) in YAML. Avoid long CSS/XPath unless needed.

### Failure evidence (must have)

A failing test must leave proof.

| Artifact | Why it is necessary | Status |
|----------|---------------------|--------|
| Screenshot on failure | Shows the UI at the moment of failure | Done |
| Playwright trace on failure | Lets you replay the test (clicks, network, DOM) | Done |
| Allure (or similar) report | Portfolio-friendly HTML report with attachments | Done |

### Configuration you can change without editing Java

```bash
mvn test -Dbrowser=chromium -Dheadless=false
```

### YAML files belong in resources, not under `src/main/java`

Today locators sit under `src/main/java/locators/` with a Maven resource include. Optional move to `src/main/resources/locators/` remains polish.

### Proper Java packages

Target style (optional polish):

```
com.automation.framework.pages
com.automation.framework.testflow
com.automation.framework.config
```

### Java 17 vs your IntelliJ JDK

Project compiles as **Java 17**. Document it so runs do not fail because of mixed JDKs.

### Definition of Done (when the framework is “finished”)

- [x] Real public site is automated (not only `demo/login.html`)
- [x] Login success + login failure tests pass
- [x] At least 3 more flows pass (example: inventory, cart, checkout)
- [x] Tests read data from YAML testdata files, not hard-coded strings
- [x] Failure screenshot + Allure report works
- [x] Playwright trace saved on failure
- [x] `test.xml` is the single place that launches the suite
- [x] README explains architecture and how to run
- [x] Code is on GitHub
- [x] GitHub Actions runs `mvn test` on push

### What we will not build (keeps the framework focused)

These are **not** necessary for this framework. Skip them unless a later job requires them:

- Cucumber / Gherkin
- API + UI hybrid (unless one checkout assertion needs an API check)
- Database setup/teardown
- Parallel execution with `ThreadLocal` (add only after tests are stable)
- Retry-flaky-test plugins (hide failures; do not use at the start)
- Dockerized browsers
- Multiple websites in one repo

---

## Step 15 – Reporting (Allure)

**Status:** `[x]` Done

- `allure-testng` + `allure-maven` plugin in `pom.xml`
- `ScreenshotListener` attached in `test.xml` and attaches PNGs to Allure
- Documented: `mvn test` then `mvn allure:serve`

---

## Step 16 – Playwright trace on failure

**Status:** `[x]` Done

- Tracing started in `PlaywrightFactory` on an explicit `BrowserContext`
- On failure, `BaseTest` saves `test-results/traces/<testName>.zip`
- Documented in README (`show-trace`)

---

## Step 17 – GitHub Actions CI

**Status:** `[x]` Done

- `.github/workflows/tests.yml` — JDK 17, Maven, Chromium install, `mvn test`, artifact upload

---

## Step 18 – Command-line config overrides

**Status:** `[x]` Done

- `ConfigReader` reads system property first, then `config.properties`
- Example: `mvn test -Dheadless=false -Dbrowser=firefox`

---

## Target folder structure (end state)

```
src/
  main/java/
    locators/          YAML selectors per page
    pages/             Page Objects (extend BasePage)
    utils/             Validations, waits, YAML reader, logger, screenshots
    testflow/               Reusable scenario flows
    config/            ConfigReader
    core/              PlaywrightFactory
  main/resources/
    config/            config.properties
  test/java/
    core/              BaseTest
    listeners/         Failure screenshots
    tests/             Test classes only
    utils/             TestDataReader
  test/resources/
    testdata/          JSON users and flow data
test.xml               TestNG suite
README.md              How to run + architecture
ROADMAP.md             This plan
```
