package ch.hslu.mobpro.opendata.transport.util;

/**
 * Created by haubschueh & inux (2019)
 */
public class BooleanUtils {
    public static String toNumeralString(final Boolean input) {
        if (input == null) {
            return "null";
        } else {
            return input.booleanValue() ? "1" : "0";
        }
    }
}
