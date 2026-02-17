package utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static String randomEmail() {
        return "user_" + shortId() + "@testmail.com";
    }

    public static String randomPhoneZA() {
        // ZA-like mobile format (not validated strictly)
        int suffix = ThreadLocalRandom.current().nextInt(1000000, 9999999);
        return "07" + suffix;
    }

    public static String randomString(int len) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int idx = ThreadLocalRandom.current().nextInt(chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String shortId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public static int randomInt(int minInclusive, int maxInclusive) {
        return ThreadLocalRandom.current().nextInt(minInclusive, maxInclusive + 1);
    }
}