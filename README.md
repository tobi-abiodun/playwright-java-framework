# Playwright Java Automation Framework

A layered Playwright + Java test automation framework using Page Object Model, YAML locators, validation utilities, and reusable scenario flows.

**Build plan:** see [ROADMAP.md](ROADMAP.md) for the step-by-step plan, current progress, and what we will do next together.

## Architecture

```
config/config.properties     → URLs, browser, timeouts
        ↓
core/PlaywrightFactory       → Creates browser and page
core/BaseTest                → Shared setup/teardown for all tests
        ↓
locators/*.yaml              → Element selectors
pages/*.java                 → Page actions and state checks
utils/*Util.java             → Validations and helpers
testflow/*TestFlow.java      → Reusable test flows (scenarios)
tests/*.java                 → Test classes
```

| Layer | Example | Purpose |
|-------|---------|---------|
| Locators | `loginPage.yaml` | Stores selectors |
| Page Object | `LoginPage.java` | Browser actions + element state |
| Validation Util | `LoginPageUtil.java` | Assertion / validation methods |
| TestFlow | `LoginTestFlow.java` | Reusable screen/scenario flows |
| Test | `E2E.java` | Ten screen-by-screen end-to-end tests |

## Project structure

```
src/
  main/java/
    config/ConfigReader.java
    core/PlaywrightFactory.java
    locators/          # login, inventory, cart, checkoutInfo, orderSummary, orderConfirmation
    pages/             # one Page Object per screen (+ BasePage)
    utils/             # *PageUtil + WaitUtil, YamlReader, LocatorFactory, …
    testflow/          # *TestFlow per screen
  main/resources/config/config.properties
  test/java/
    core/BaseTest.java
    tests/E2E.java
    listeners/, utils/
  test/resources/testdata/
test-cases/
  README.md
  E2E-TESTCASES.md
  testflows/           # TF specs per screen
test.xml
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
timeout=30000                 # milliseconds
```

## Run tests

Tests are defined in **`test.xml`** at the project root.

From the project root:

```bash
mvn test
```

Maven reads `test.xml` and runs every test class listed there.

Run only the E2E tests (without test.xml):

```bash
mvn test -Dtest=tests.E2E
```

### Run from IntelliJ

1. **Using test.xml:** Right-click `test.xml` → **Run 'test.xml'**
2. **Single class:** Open `tests/E2E.java` → click the green run icon

Case list (E2E + TestFlows): [test-cases/README.md](test-cases/README.md)

### Add a new test to the suite

Open `test.xml` and add your class:

```xml
<test name="My New Tests">
    <classes>
        <class name="tests.MyNewTest"/>
    </classes>
</test>
```

## Application under test

Tests run against **https://www.saucedemo.com** (Swag Labs).

Users are stored in `src/test/resources/testdata/loginUsers.yaml` (not in Java):

- **validUser:** `standard_user` / `secret_sauce` → products page
- **invalidUser:** `locked_out_user` / `secret_sauce` → error message

## Test data

All usernames, passwords, and similar inputs must come from YAML files under:

`src/test/resources/testdata/`

Example (`loginUsers.yaml`):

```yaml
validUser:
  username: standard_user
  password: secret_sauce
invalidUser:
  username: locked_out_user
  password: secret_sauce
```

Tests load them with `TestDataReader`. Change the YAML only — never hard-code credentials in tests.

## Adding a new page

Follow the same pattern:

1. Create `locators/newPage.yaml`
2. Create `pages/NewPage.java`
3. Create `utils/NewPageUtil.java` (validations)
4. Create `testflow/NewTestFlow.java` (reusable flows)
5. Create `tests/NewTest.java` extending `BaseTest`

## Tech stack

- Playwright Java
- TestNG (suite via test.xml)
- Maven
- SnakeYAML

## Author

Tobi Abiodun
