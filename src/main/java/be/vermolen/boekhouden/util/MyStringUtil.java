package be.vermolen.boekhouden.util;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtil {

    public static String trim(String text, boolean capitalize) {
        if (text == null) {
            return null;
        }

        if (capitalize) {
            return StringUtils.capitalize(text.trim());
        } else {
            return text.trim();
        }
    }
}
