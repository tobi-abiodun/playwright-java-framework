package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * YamlReader
 * ----------
 * Reads locator YAML files from the classpath (src/main/java/locators).
 * Page Objects call this so selectors stay in YAML instead of Java.
 */
public final class YamlReader {

    private YamlReader() {
        // Utility class — do not instantiate.
    }

    /**
     * Loads a YAML locator file and returns the map for one page.
     *
     * @param yamlFileName file name only, e.g. "loginPage.yaml"
     * @param pageKey      top-level key, e.g. "loginPage"
     * @return locator name → locator string
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> readLocators(String yamlFileName, String pageKey) {
        String resourcePath = "locators/" + yamlFileName;

        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Could not find locator file on classpath: " + resourcePath);
            }

            Yaml yaml = new Yaml();
            Map<String, Object> root = yaml.load(inputStream);
            Object pageLocators = root.get(pageKey);

            if (!(pageLocators instanceof Map)) {
                throw new IllegalStateException("Page key not found in YAML: " + pageKey);
            }

            Map<String, Object> raw = (Map<String, Object>) pageLocators;
            LinkedHashMap<String, String> locators = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : raw.entrySet()) {
                locators.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return locators;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to read locators from " + yamlFileName, exception);
        }
    }
}
