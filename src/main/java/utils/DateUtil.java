package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private DateUtil(){}

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final DateTimeFormatter HUMAN = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");

    public static String timestamp() {
        return LocalDateTime.now().format(TS);
    }

    public static String nowHuman() {
        return LocalDateTime.now().format(HUMAN);
    }
}