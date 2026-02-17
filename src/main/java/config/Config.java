package config;

public final class Config {

    private Config(){}

    public static String baseUrl() {
        return ConfigLoader.get("baseUrl");
    }

    public static String browser() {
        return ConfigLoader.get("browser","chrome");
    }

    public static boolean headless() {
        return ConfigLoader.getBoolean("headless", false);
    }

    public static int explicitWaitSeconds() {
        return ConfigLoader.getInt("explicitWait", 20);
    }

    public static int implicitWaitSeconds() {
        return ConfigLoader.getInt("implicitWait", 0);
    }
}