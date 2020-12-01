package com.ada.parsian.parsianmobilebank.Util;

import org.springframework.context.MessageSource;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ParsianUtil {

    public static String getMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, new Locale("fa", "IR"));
    }

    public static Timestamp getDateTimeFromPattern(String dateString) {

        String pattern;
        if (dateString.length() == 19) {
            pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

            try {
                return new Timestamp(dateFormat.parse(dateString).getTime());
            } catch (ParseException ignored) {
            }
        }

        return new java.sql.Timestamp(System.currentTimeMillis());
    }
}
