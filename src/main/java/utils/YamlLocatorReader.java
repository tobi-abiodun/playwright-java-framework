package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * YamlLocatorReader
 * -----------------
 * A small utility that loads element selectors from YAML files on the classpath.
 * Page objects use this class so locators live in one place (YAML) instead of
 * being hard-coded in Java.
 */
public class YamlLocatorReader {

    /**
     * Loads a YAML locator file and returns the nested map for the given page key.
     *
     * @param yamlFileName file name only, e.g. "loginPage.yaml"
     * @param pageKey      top-level key inside the YAML, e.g. "loginPage"
     * @return map of locator name -> CSS selector string
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> loadLocators(String yamlFileName, String pageKey) {
        String resourcePath = "locators/" + yamlFileName;

        try (InputStream inputStream = YamlLocatorReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Could not find locator file on classpath: " + resourcePath);
            }

            Yaml yaml = new Yaml();
            Map<String, Object> root = yaml.load(inputStream);
            Object pageLocators = root.get(pageKey);

            if (!(pageLocators instanceof Map)) {
                throw new IllegalStateException("Page key not found in YAML: " + pageKey);
            }

            return (Map<String, String>) pageLocators;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to read locators from " + yamlFileName, exception);
        }
    }
}
