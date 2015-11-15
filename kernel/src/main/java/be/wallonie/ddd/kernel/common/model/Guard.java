package be.wallonie.ddd.kernel.common.model;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Méthodes utilitaires facilitant la vérification des données.
 */
public class Guard {

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean equals(Integer value01, Integer value02) {
        return value01 != null && value02 != null && value01 == value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean smallerThan(Integer value01, Integer value02) {
        return value01 != null && value02 != null && value01 < value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean greaterThan(Integer value01, Integer value02) {
        return value01 != null && value02 != null && value01 > value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean smallerOrEqualThan(Integer value01, Integer value02) {
        return value01 != null && value02 != null && value01 <= value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean greaterOrEqualThan(Integer value01, Integer value02) {
        return value01 != null && value02 != null && value01 >= value02;
    }

    /**
     * @param value01
     * @param minimum
     * @param maximum
     * @return
     */
    public static boolean betweenNumber(Integer value01, Integer minimum, Integer maximum) {
        return value01 == null ? false : (minimum <= value01 && value01 <= maximum);
    }

    /**
     * @param value
     * @param domain
     * @return
     */
    public static boolean domain(Integer value, java.util.List<Integer> domain) {
        return value == null ? false : domain.contains((int) value);
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean equals(Double value01, Double value02) {
        return value01 != null && value02 != null && value01.equals(value02);
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean smallerThan(Double value01, Double value02) {
        return value01 != null && value02 != null && value01 < value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean greaterThan(Double value01, Double value02) {
        return value01 != null && value02 != null && value01 > value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean smallerOrEqualThan(Double value01, Double value02) {
        return value01 != null && value02 != null && value01 <= value02;
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static boolean greaterOrEqualThan(Double value01, Double value02) {
        return value01 != null && value02 != null && value01 >= value02;
    }

    /**
     * @param value01
     * @param minimum
     * @param maximum
     * @return
     */
    public static boolean betweenNumber(Double value01, Double minimum, Double maximum) {
        return value01 == null ? false : (minimum <= value01 && value01 <= maximum);
    }

    /**
     * @param value
     * @param domain
     * @return
     */
    public static boolean domain(Double value, List<Double> domain) {
        return value == null ? false : domain.contains(value);
    }

    /**
     * @param value
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param isNullAllowed
     * @return
     */
    public static boolean isFormat(Double value, int digitBeforeComma, int decimalPlaces, boolean isNullAllowed) {
        if (value == null) {
            return isNullAllowed;
        }
        BigDecimal bigDecimal = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
        double rounded = bigDecimal.doubleValue();
        if ((value) != rounded) {
            return false;
        }
        Double maxValue = (Double) Math.pow(10, digitBeforeComma);
        return value < maxValue;
    }

    /**
     * @param value
     * @param digitBeforeComma
     * @param decimalPlaces
     * @return
     */
    public static boolean isFormat(Double value, int digitBeforeComma, int decimalPlaces) {
        return isFormat(value, digitBeforeComma, decimalPlaces, false);
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static boolean before(Date value, Date date) {
        return value != null && date != null && value.compareTo(date) == -1;
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static boolean after(Date value, Date date) {
        return value != null && date != null && value.compareTo(date) == 1;
    }


    /**
     * @param value
     * @param date
     * @return
     */
    public static boolean beforeOrEqual(Date value, Date date) {
        return value != null && date != null && value.compareTo(date) <= 0;
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static boolean afterOrEqual(Date value, Date date) {
        return value != null && date != null && value.compareTo(date) >= 0;
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static boolean equals(Date value, Date date) {
        return value != null && date != null && value.compareTo(date) == 0;
    }

    /**
     * @param value
     * @return
     */
    public static boolean nullOrEmpty(String value) {
        return !StringUtils.isNoneEmpty(value);
    }

    /**
     * @param value1
     * @param value2
     * @return
     */
    public static boolean equals(String value1, String value2) {
        return (value1 == null && value2 == null) || (value1 != null && value1.equals(value2));
    }

    /**
     * @param str
     * @return
     */
    public static boolean digitOnly(String str) {
        return Guard.nullOrEmpty(str) ? false : (StringUtils.isNumeric(str) || NumberUtils.isNumber(str));
    }

    /**
     * @param value
     * @param minimum
     * @param maximum
     * @return
     */
    public static boolean betweenNumberString(String value, int minimum, int maximum) {
        return Guard.digitOnly(value) ? Guard.betweenNumber(Integer.parseInt(value), minimum, maximum) : false;
    }

    /**
     * @param value
     * @param minimum
     * @param maximum
     * @return
     */
    public static boolean betweenNumberString(String value, double minimum, double maximum) {
        return Guard.digitOnly(value) ? Guard.betweenNumber(Double.parseDouble(value), minimum, maximum) : false;
    }

    /**
     * @param value
     * @param length
     * @return
     */
    public static boolean length(String value, int length) {
        return Guard.nullOrEmpty(value) ? length == 0 : value.trim().length()
                == length;
    }

    /**
     * @param value
     * @param values
     * @return
     */
    public static boolean domain(String value, List<String> values) {
        if (Guard.nullOrEmpty(value)) {
            return false;
        } else {
            if (Guard.nbrOfElements(values, 1, Integer.MAX_VALUE, false)) {
                return values.contains(value);
            } else {
                return false;
            }
        }
    }

    /**
     * @param value
     * @return
     */
    public static boolean phoneNumber(String value) {
        if (Guard.nullOrEmpty(value)) {
            return false;
        } else {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(value, "BE");
                return true;
            } catch (NumberParseException e) {
                return false;
            }
        }
    }

    /**
     * @param value
     * @return
     */
    public static boolean email(String value) {
        return Guard.nullOrEmpty(value) ? false : EmailValidator.getInstance().isValid(value);
    }

    /**
     * @param objs
     * @param minElement
     * @param maxElement
     * @param nullCountAsElement
     * @param <T>
     * @return
     */
    public static <T> boolean nbrOfElements(List<T> objs, int minElement, int maxElement, boolean nullCountAsElement) {
        if (objs != null && !nullCountAsElement)
            objs = objs.stream().filter(x -> x != null).collect(Collectors.toList());
        return objs == null ? minElement == 0 : (minElement <= objs.size() && objs.size() <= maxElement);
    }

}
