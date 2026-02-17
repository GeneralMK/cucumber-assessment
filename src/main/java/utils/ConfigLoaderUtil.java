package utils;

import config.ConfigLoader;

public final class ConfigLoaderUtil {

    private ConfigLoaderUtil(){

    }

    public static String username() {

        return ConfigLoader.get("username");
    }

    public static String password() {
        return ConfigLoader.get("password");
    }
}

