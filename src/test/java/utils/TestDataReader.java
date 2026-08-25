package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * Loads test data from YAML under src/test/resources/testdata/.
 */
public final class TestDataReader {

    public static final String LOGIN_USERS_FILE = "loginUsers.yaml";
    public static final String VALID_USER = "validUser";
    /** Locked-out user (TS003 / TF3). */
    public static final String LOCKED_OUT_USER = "invalidUser";
    /** @deprecated use LOCKED_OUT_USER */
    public static final String INVALID_USER = LOCKED_OUT_USER;
    public static final String UNKNOWN_USER = "unknownUser";
    public static final String WRONG_PASSWORD_USER = "wrongPasswordUser";
    public static final String EMPTY_USERNAME = "emptyUsername";
    public static final String EMPTY_PASSWORD = "emptyPassword";
    public static final String EMPTY_BOTH = "emptyBoth";

    public static final String PRODUCTS_FILE = "products.yaml";
    public static final String BACKPACK = "backpack";
    public static final String BIKE_LIGHT = "bikeLight";
    public static final String BOLT_T_SHIRT = "boltTShirt";
    public static final String FLEECE_JACKET = "fleeceJacket";
    public static final String ONESIE = "onesie";
    public static final String RED_T_SHIRT = "redTShirt";

    public static final String CHECKOUT_FILE = "checkout.yaml";
    public static final String VALID_CUSTOMER = "validCustomer";
    public static final String MISSING_FIRST_NAME = "missingFirstName";

    private TestDataReader() {
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> load(String fileName) {
        String resourcePath = "testdata/" + fileName;
        try (InputStream inputStream = TestDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Test data file not found: " + resourcePath);
            }
            Yaml yaml = new Yaml();
            Object loaded = yaml.load(inputStream);
            if (!(loaded instanceof Map)) {
                throw new IllegalStateException("Test data file must contain a YAML map: " + fileName);
            }
            return (Map<String, Object>) loaded;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to read test data file: " + fileName, exception);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRecord(String fileName, String recordKey) {
        Object record = load(fileName).get(recordKey);
        if (!(record instanceof Map)) {
            throw new IllegalStateException("Record '" + recordKey + "' not found in " + fileName);
        }
        return (Map<String, Object>) record;
    }

    public static String getValue(String fileName, String recordKey, String fieldName) {
        Object value = getRecord(fileName, recordKey).get(fieldName);
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    public static String getUsername(String fileName, String userKey) {
        return getValue(fileName, userKey, "username");
    }

    public static String getPassword(String fileName, String userKey) {
        return getValue(fileName, userKey, "password");
    }

    public static String productName(String productKey) {
        return getValue(PRODUCTS_FILE, productKey, "name");
    }

    public static String productPrice(String productKey) {
        return getValue(PRODUCTS_FILE, productKey, "price");
    }

    public static String productAddButton(String productKey) {
        return getValue(PRODUCTS_FILE, productKey, "addButton");
    }

    public static String productRemoveButton(String productKey) {
        return getValue(PRODUCTS_FILE, productKey, "removeButton");
    }
}
