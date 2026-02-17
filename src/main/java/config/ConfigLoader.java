package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {

    private static final Logger log = LogManager.getLogger(ConfigLoader.class);
    private static final Properties props = new Properties();
    private static boolean loaded = false;

    private ConfigLoader() {}

    // Load once (lazy)
    private static synchronized void loadIfNeeded() {
        if (loaded) return;

        try (InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("config.properties not found in src/main/resources");
            }
            props.load(is);
            loaded = true;
            log.info("Loaded config.properties successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /** Get value by key (no default). Throws if missing/blank. */
    public static String get(String key) {
        loadIfNeeded();

        String sys = System.getProperty(key);
        if (sys != null && !sys.trim().isEmpty()) return sys.trim();

        String env = System.getenv(key);
        if (env != null && !env.trim().isEmpty()) return env.trim();

        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Missing required config key: " + key);
        }
        return value.trim();
    }

    /** Get value by key with default. */
    public static String get(String key, String defaultValue) {
        loadIfNeeded();

        String sys = System.getProperty(key);
        if (sys != null && !sys.trim().isEmpty()) return sys.trim();

        String env = System.getenv(key);
        if (env != null && !env.trim().isEmpty()) return env.trim();

        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) return defaultValue;
        return value.trim();
    }

    /** Get boolean with default. Accepts true/false/yes/no/1/0 */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String raw = get(key, String.valueOf(defaultValue)).toLowerCase().trim();
        return raw.equals("true") || raw.equals("yes") || raw.equals("1");
    }

    /** Get int with default. */
    public static int getInt(String key, int defaultValue) {
        String raw = get(key, String.valueOf(defaultValue)).trim();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid int value for key: " + key + " = " + raw, e);
        }
    }
}