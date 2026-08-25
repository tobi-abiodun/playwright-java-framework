package utils;

import java.util.Map;

/**
 * YamlLocatorReader
 * -----------------
 * Compatibility wrapper. New page objects should call YamlReader directly.
 */
public class YamlLocatorReader {

    public static Map<String, String> loadLocators(String yamlFileName, String pageKey) {
        return YamlReader.readLocators(yamlFileName, pageKey);
    }
}
