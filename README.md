# Playwright Java Automation Framework

A layered Playwright + Java test automation framework using Page Object Model, YAML locators, validation utilities, and reusable scenario flows.

**Build plan:** see [ROADMAP.md](ROADMAP.md) for status and history.

## Architecture

```
config/config.properties     → URLs, browser, timeouts (overridable via -D)
        ↓
core/PlaywrightFactory       → Browser + context + tracing
core/BaseTest                → Shared setup/teardown; saves traces on failure
        ↓
locators/*.yaml              → Element selectors
pages/*.java                 → Page actions and state checks
utils/*Util.java             → Validations and helpers (+ LoggerUtil, ScreenshotUtil)
testflow/*TestFlow.java      → Reusable test flows (scenarios TF1–TF28)
tests/*.java                 → Test classes (TS001–TS020)
listeners/ScreenshotListener → Failure screenshots + Allure attachments
```

| Layer | Example | Purpose |
|-------|---------|---------|
| Locators | `loginPage.yaml` | Stores selectors |
| Page Object | `LoginPage.java` | Browser actions + element state |
| Validation Util | `LoginPageUtil.java` | Assertion / validation methods |
| TestFlow | `LoginTestFlow.java` | Reusable screen/scenario flows |
| Test | `E2E.java` | End-to-end cases TS001–TS020 |

## Project structure

```
src/
  main/java/
    config/ConfigReader.java
    core/PlaywrightFactory.java
    locators/          # login, inventory, cart, checkoutInfo, orderSummary, orderConfirmation
    pages/             # one Page Object per screen (+ BasePage)
    utils/             # *PageUtil + WaitUtil, YamlReader, LoggerUtil, ScreenshotUtil
    testflow/          # *TestFlow per screen (TF1–TF28)
  main/resources/config/config.properties
  test/java/
    core/BaseTest.java
    tests/E2E.java
    listeners/ScreenshotListener.java
    utils/TestDataReader.java
  test/resources/
    testdata/
    allure.properties
test-cases/            # TF/TS documentation
test.xml               # TestNG suite (+ ScreenshotListener)
.github/workflows/tests.yml
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Git

## Setup

1. Clone the repository:

```bash
git clone https://github.com/tobi-abiodun/playwright-java-framework.git
cd playwright-java-framework
```

2. Install Playwright browsers (first time only):

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

3. Import the project in IntelliJ as a Maven project and let dependencies download.

## Configuration

Edit `src/main/resources/config/config.properties`:

```properties
base.url=https://www.saucedemo.com
login.url=https://www.saucedemo.com/
browser=chromium              # chromium | firefox | webkit
headless=true                 # false to see the browser
video=true                    # false to skip .webm recording
timeout=30000                 # milliseconds
```

### CLI overrides

System properties override `config.properties` (no Java edits):

```bash
mvn test -Dbrowser=firefox -Dheadless=false
mvn test -Dbase.url=https://www.saucedemo.com -Dheadless=true
mvn test -Dvideo=false
```

## Run tests

Tests are defined in **`test.xml`** at the project root.

```bash
mvn test
```

Run only the E2E class:

```bash
mvn test -Dtest=tests.E2E
```

### Run from IntelliJ

1. **Using test.xml:** Right-click `test.xml` → **Run 'test.xml'**
2. **Single class:** Open `tests/E2E.java` → click the green run icon

Case list (E2E + TestFlows): [test-cases/README.md](test-cases/README.md)

## Evidence (pass + fail)

| Artifact | When | Location |
|----------|------|----------|
| Video (`.webm`) | Every test (when `video=true`) | `test-results/videos/<test>.webm` |
| Screenshot | Failure only | `test-results/screenshots/<test>_<timestamp>.png` |
| Playwright trace | Failure only | `test-results/traces/<test>.zip` |
| Allure attachments | Video every run; PNG on failure | `mvn allure:serve` |

Disable video (faster / less disk):

```bash
mvn test -Dvideo=false
```

Open a failure trace:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace test-results/traces/<test>.zip"
```

Play a video in any player that supports WebM (Chrome, VLC, etc.), or open it from the Allure report attachment.
## Allure report

```bash
mvn test
mvn allure:serve
```

Results are written to `allure-results/` (gitignored).

## Application under test

Tests run against **https://www.saucedemo.com** (Swag Labs).

Users are stored in `src/test/resources/testdata/loginUsers.yaml` (not in Java):

- **validUser:** `standard_user` / `secret_sauce` → products page
- **invalidUser:** `locked_out_user` / `secret_sauce` → error message

Products and checkout data live in `products.yaml` and `checkout.yaml`.

## CI

GitHub Actions (`.github/workflows/tests.yml`) runs on push/PR to `main`:

- JDK 17 + Maven
- Installs Chromium
- `mvn test -Dheadless=true`
- Uploads `test-results/` and `allure-results/` as artifacts

## Adding a new page

Follow the same pattern:

1. Create `locators/newPage.yaml`
2. Create `pages/NewPage.java`
3. Create `utils/NewPageUtil.java` (validations)
4. Create `testflow/NewTestFlow.java` (reusable flows)
5. Create or extend a test in `tests/` extending `BaseTest`

## Tech stack

- Playwright Java
- TestNG (suite via test.xml)
- Maven
- SnakeYAML
- Allure

## Author

Tobi Abiodun
