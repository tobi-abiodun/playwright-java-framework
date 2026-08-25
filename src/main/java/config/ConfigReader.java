package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader
 * ------------
 * Loads settings from config/config.properties on the classpath.
 * Use this class instead of hard-coding URLs, browser type, or timeouts in tests and page objects.
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config/config.properties";
    private static final Properties PROPERTIES = new Properties();

    private ConfigReader() {
        // Utility class — do not instantiate.
    }

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Config file not found on classpath: " + CONFIG_FILE);
            }
            PROPERTIES.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load config file: " + CONFIG_FILE, exception);
        }
    }

    /** Returns a property value, or null if the key is missing. */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    /** Returns a property value, or the default if the key is missing. */
    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    /**
     * Returns the login page URL.
     * Uses login.url when set, otherwise base.url.
     */
    public static String getLoginUrl() {
        String loginUrl = getProperty("login.url", "").trim();
        if (!loginUrl.isEmpty()) {
            return loginUrl;
        }

        String baseUrl = getProperty("base.url", "").trim();
        if (!baseUrl.isEmpty()) {
            return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        }

        throw new IllegalStateException("Set login.url or base.url in config.properties");
    }

    /** Application base URL, e.g. https://www.saucedemo.com */
    public static String getBaseUrl() {
        String baseUrl = getProperty("base.url", "").trim();
        if (!baseUrl.isEmpty()) {
            return baseUrl;
        }
        return getLoginUrl();
    }

    /** Browser name: chromium, firefox, or webkit. */
    public static String getBrowser() {
        return getProperty("browser", "chromium");
    }

    /** Whether the browser runs without a visible window. */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "true"));
    }

    /** Default timeout in milliseconds. */
    public static double getTimeout() {
        return Double.parseDouble(getProperty("timeout", "30000"));
    }
}
