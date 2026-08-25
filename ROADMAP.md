# Framework build plan

> **Status (2026-08-24):** Sauce Demo 6-screen TestFlow architecture is in place
> (Login → Inventory → Cart → Checkout Info → Order Summary → Order Confirmation).
> Specs use traditional TF/TS docs under `test-cases/` (not Gherkin). Combined
> `test.xml`, implement missing TF methods to match TS001–TS020, then reporting/CI.
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
| Test | `tests/Login.java` | Calls TestFlow only |

When we add a new page, we repeat that same 5-file pattern. We do **not** put Playwright clicks inside tests.

---

## Current snapshot (honest)

**Done:** 6-screen YAML → Page → Util → TestFlow layers for Sauce Demo; E2E class;
TF/TS docs under `test-cases/`.

**Next:** Run the suite, align Java TestFlows fully to TF1–TF28 / TS001–TS020,
then screenshots/Allure/traces/CI (Steps 15–17).

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

**Status:** `[~]` Partly done

| Utility | Status | File |
|---------|--------|------|
| Wait helper | Done | `utils/WaitUtil.java` |
| Screenshot helper | Started | `utils/ScreenshotUtil.java` |
| Screenshot on failure listener | Started | `listeners/ScreenshotListener.java` |
| Test data YAML | Done | `testdata/loginUsers.yaml` + `TestDataReader.java` |
| Logger | Not started | `LoggerUtil.java` |

**Still needed:**

- Wire `ScreenshotListener` into `test.xml` (it exists but is not attached yet)
- Add `LoggerUtil.java` so every flow logs steps clearly
- Add Allure to `pom.xml` so failure screenshots attach to a real report (listener already imports Allure)

---

## Step 12 – Add Playwright config and environment handling

**Status:** `[~]` Core config done; extras remaining

**Done:**

- `src/main/resources/config/config.properties` (URL, browser, headless, timeout)
- `config/ConfigReader.java`
- `core/PlaywrightFactory.java`
- `core/BaseTest.java`

**Still needed:**

- `base.url` for the real site (instead of dummy HTML path)
- Optional video / trace on failure
- Optional `dev` / `qa` / `stage` property files

---

## Step 13 – Create README for GitHub

**Status:** `[~]` First version exists; needs a refresh when the real site is live

**File:** `README.md`

Must later include:

- Project purpose
- Architecture diagram
- How to run tests (`mvn test`, IntelliJ + `test.xml`)
- Example flows
- Screenshots of passing runs / Allure report

---

## Step 14 – Publish to GitHub

**Status:** `[~]` Remote was set up; push / screenshots still needed

Remote:

`https://github.com/tobi-abiodun/playwright-java-framework.git`

**Still needed:**

- Commit remaining files
- Push to GitHub
- Add screenshots of test runs
- Optional: GitHub Actions so tests run on every push

---

## What we should do next (in order)

1. **You:** Run login tests (`test.xml` or `mvn test`) on Sauce Demo and share pass/fail.
2. If login passes, start **Step 10**: inventory, add to cart, checkout (same YAML → Page → Util → TestFlow → Test pattern).
3. Finish utilities: listener in `test.xml`, Allure, Logger.
4. Add Playwright traces on failure.
5. Add command-line overrides (`-Dbrowser`, `-Dheadless`).
6. Refresh README screenshots.
7. Add GitHub Actions CI.
8. Push to GitHub.

### Do not add extra pages until login works on Sauce Demo.

---

## Session checklist

Login against Sauce Demo is implemented. Next message can be:

```
Login tests passed
```

or paste any failure, then we add inventory / cart / checkout.

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

- `pages/BasePage.java` — Page Objects should extend this
- `utils/ScreenshotUtil.java`
- `listeners/ScreenshotListener.java`
- `src/test/resources/testdata/loginUsers.yaml`
- `src/test/java/utils/TestDataReader.java`
- `locators/dashboardPage.yaml`

Gaps to close:

- `LoginPage` does **not** extend `BasePage` yet
- `BaseTest` has no `getPage()` (needed by `ScreenshotListener`)
- `pom.xml` has **no Allure** yet (listener already tries to use it)
- No `DashboardPage` / `DashboardUtil` / dashboard test yet

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

A failing test must leave proof. The original plan mentioned screenshots. A Playwright framework also needs **traces**.

| Artifact | Why it is necessary |
|----------|---------------------|
| Screenshot on failure | Shows the UI at the moment of failure |
| Playwright trace on failure | Lets you replay the test (clicks, network, DOM) |
| Allure (or similar) report | Portfolio-friendly HTML report with attachments |

Screenshots exist as files. They are not wired yet. Traces and Allure are not in the 14-step list as first-class steps.

### Configuration you can change without editing Java

Necessary flags (Maven / command line), not only `config.properties`:

```bash
mvn test -Dbrowser=chromium -Dheadless=false
```

Interviewers often ask: “How do you switch browser or environment without changing code?”

### YAML files belong in resources, not under `src/main/java`

Necessary Maven convention:

- Keep locator YAML in `src/main/resources/locators/`
- Keep Java only in `src/main/java/`

Today locators sit under `src/main/java/locators/` because that was your original structure. We can keep the package idea, but professionally they should live as resources. The reader already loads them from the classpath, so this is a move, not a rewrite.

### Proper Java packages

Necessary for a portfolio repo. Flat names like `pages` and `tests` work, but look unfinished.

Target style:

```
com.automation.framework.pages
com.automation.framework.testflow
com.automation.framework.config
```

Do this **once**, after login works on the real site — not before.

### Java 17 vs your IntelliJ JDK

You have (or had) **OpenJDK 24** in IntelliJ and the project compiles as **Java 17**. That is fine if the IDE language level stays 17. Document it so runs do not fail because of mixed JDKs.

### Definition of Done (when the framework is “finished”)

The original 14 steps never say when we stop. For this project, Done means all of the following:

- [ ] Real public site is automated (not only `demo/login.html`)
- [ ] Login success + login failure tests pass
- [ ] At least 3 more flows pass (example: inventory, cart, checkout)
- [ ] Tests read data from YAML testdata files, not hard-coded strings
- [ ] Failure screenshot + Allure report works
- [ ] Playwright trace saved on failure
- [ ] `test.xml` is the single place that launches the suite
- [ ] README explains architecture and how to run
- [ ] Code is on GitHub
- [ ] GitHub Actions runs `mvn test` on push

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

**Status:** `[~]` Listener already imports Allure; `pom.xml` has no Allure dependency/plugin

**Why necessary:** A portfolio without a report looks like “I ran tests in the IDE.” Allure shows architecture + evidence.

**Action:**

- Add Allure TestNG dependency and Maven plugin
- Attach `ScreenshotListener` in `test.xml`
- Document: `mvn test` then `mvn allure:serve`

---

## Step 16 – Playwright trace on failure

**Status:** `[ ]` Not started

**Why necessary:** This is the Playwright-specific skill interviewers expect.

**Action:**

- Start tracing in `BaseTest` before each test
- On failure, save `test-results/traces/<testName>.zip`
- Document how to open it: `mvn exec:java ... show-trace path.zip`

---

## Step 17 – GitHub Actions CI

**Status:** `[ ]` Not started (mentioned as optional under Step 14; it should be required)

**Why necessary:** Shows the framework runs without IntelliJ.

**Action:**

- Add `.github/workflows/tests.yml`
- Install JDK 17, Maven, Playwright browsers
- Run `mvn test`
- Upload Allure results or screenshots as artifacts

---

## Step 18 – Command-line config overrides

**Status:** `[ ]` Not started

**Why necessary:** Same suite, different browser/headless/url, no Java edits.

**Action:**

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
