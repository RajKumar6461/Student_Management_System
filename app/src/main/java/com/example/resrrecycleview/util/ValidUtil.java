package com.example.resrrecycleview.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for validation using Regex
 *
 * 2 method
 * @first for validation name
 * @second for Validation Id
 */
public final class ValidUtil {
    private final static String NAME_REGEX="^[a-zA-Z]{3,25}$";
    private final static String ID_REGEX="^[1-9][0-9]*$";

    public static boolean isValidName(final String NAME){

        final Pattern pattern = Pattern.compile(NAME_REGEX);;

        Matcher matcher = pattern.matcher(NAME);

        return matcher.matches();
    }

    public static boolean isValidId(final String ID){
       // String regex="^[1-9][0-9]*$";
        final Pattern pattern = Pattern.compile(ID_REGEX);;

        Matcher matcher = pattern.matcher(ID);

        return (matcher.matches());

    }
}
