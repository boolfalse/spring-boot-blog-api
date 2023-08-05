package am.github.springbootblogapi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Helper {

    public static String getNowDateFormatted() {
        LocalDateTime ldt = LocalDateTime.now();
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd h:m:s", Locale.ENGLISH)
                .format(ldt);
    }
}
