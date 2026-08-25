package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Locale;

/**
 * LocatorFactory
 * --------------
 * Converts a YAML locator string into a Playwright Locator.
 * Supported YAML formats:
 *   placeholder=Username
 *   label=Password
 *   text=Products
 *   role=button[name='Login']
 *   testid=username
 *   [data-test='error']     (CSS fallback)
 */
public final class LocatorFactory {

    private LocatorFactory() {
        // Utility class — do not instantiate.
    }

    /** Turns one YAML value into a Playwright locator on the given page. */
    public static Locator fromYaml(Page page, String yamlLocator) {
        if (yamlLocator == null || yamlLocator.isBlank()) {
            throw new IllegalArgumentException("YAML locator value is empty.");
        }

        String value = yamlLocator.trim();

        if (value.startsWith("placeholder=")) {
            return page.getByPlaceholder(unquote(value.substring("placeholder=".length())));
        }
        if (value.startsWith("label=")) {
            return page.getByLabel(unquote(value.substring("label=".length())));
        }
        if (value.startsWith("text=")) {
            return page.getByText(unquote(value.substring("text=".length())));
        }
        if (value.startsWith("testid=")) {
            return page.getByTestId(unquote(value.substring("testid=".length())));
        }
        if (value.startsWith("data-test=")) {
            return page.getByTestId(unquote(value.substring("data-test=".length())));
        }
        if (value.startsWith("role=")) {
            return roleLocator(page, value.substring("role=".length()));
        }

        // CSS, id, or [data-test='...']
        return page.locator(value);
    }

    /**
     * Parses role=button[name='Login'] into getByRole(BUTTON, name="Login").
     */
    private static Locator roleLocator(Page page, String roleExpression) {
        String expression = roleExpression.trim();
        String roleName = expression;
        String accessibleName = null;

        int bracketStart = expression.indexOf('[');
        int bracketEnd = expression.lastIndexOf(']');
        if (bracketStart >= 0 && bracketEnd > bracketStart) {
            roleName = expression.substring(0, bracketStart).trim();
            String attributes = expression.substring(bracketStart + 1, bracketEnd);
            if (attributes.startsWith("name=")) {
                accessibleName = unquote(attributes.substring("name=".length()));
            }
        }

        AriaRole role = AriaRole.valueOf(roleName.toUpperCase(Locale.ROOT).replace('-', '_'));
        if (accessibleName == null || accessibleName.isBlank()) {
            return page.getByRole(role);
        }
        return page.getByRole(role, new Page.GetByRoleOptions().setName(accessibleName));
    }

    private static String unquote(String raw) {
        String value = raw.trim();
        if (value.length() >= 2) {
            boolean doubleQuoted = value.startsWith("\"") && value.endsWith("\"");
            boolean singleQuoted = value.startsWith("'") && value.endsWith("'");
            if (doubleQuoted || singleQuoted) {
                return value.substring(1, value.length() - 1);
            }
        }
        return value;
    }
}
