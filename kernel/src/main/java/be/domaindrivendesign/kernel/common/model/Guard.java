package be.domaindrivendesign.kernel.common.model;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Méthodes utilitaires facilitant la vérification des données.
 */
public class Guard {

    //region Numeric

    /**
     * @param value01
     * @param value02
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equals(T value01, T value02) {
        return value01 != null && value02 != null && value01.equals(value02);
    }


    public static <T extends Number & Comparable<T>> boolean smallerThan(T value01, T value02) {
        return value01 != null && value02 != null && value01.compareTo(value02) == -1;
    }

    /**
     * @param value01
     * @param value02
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThan(T value01, T value02) {
        return value01 != null && value02 != null && value01.compareTo(value02) == 1;
    }

    /**
     * @param value01
     * @param value02
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThan(T value01, T value02) {
        return value01 != null && value02 != null && value01.compareTo(value02) <= 0;
    }

    /**
     *
     * @param value01
     * @param value02
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThan(T value01, T value02) {
        return value01 != null && value02 != null && value01.compareTo(value02) >= 0;
    }

    /**
     *
     * @param value01
     * @param minimum
     * @param maximum
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean betweenNumber(T value01, T minimum, T maximum) {
        return value01 == null ? false : (value01.compareTo(minimum) >= 0 && value01.compareTo(maximum) <= 0);
    }

    /**
     *
     * @param value
     * @param domain
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean domain(T value, java.util.List<T> domain) {
        return value == null ? false : domain.contains(value);
    }

    //endregion

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
        Double maxValue = Math.pow(10, digitBeforeComma);
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

    //endregion

    //region Temporal

    /**
     * @param value
     * @param date
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean before(T value, T date) {
        return value != null && date != null && value.compareTo(date) == -1;
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean after(T value, T date) {
        return value != null && date != null && value.compareTo(date) == 1;
    }


    /**
     * @param value
     * @param date
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeOrEqual(T value, T date) {
        return value != null && date != null && value.compareTo(date) <= 0;
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterOrEqual(T value, T date) {
        return value != null && date != null && value.compareTo(date) >= 0;
    }

    /**
     * @param value
     * @param date
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean equals(T value, T date) {
        return value != null && date != null && value.compareTo(date) == 0;
    }

    /**
     * @param value
     * @param domain
     * @param <T>
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean domain(T value, java.util.List<T> domain) {
        return value == null ? false : domain.contains(value);
    }

    //endregion

    //region String

    /**
     * @param value
     * @return
     */
    public static boolean nullOrEmpty(String value) {
        return !StringUtils.isNoneEmpty(value);
    }

    /**
     * @param value01
     * @param value02
     * @return
     */
    public static <T extends String> boolean equals(T value01, T value02) {
        return value01 != null && value02 != null && value01.compareTo(value02) == 0;
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
    public static <T extends String> boolean domain(T value, List<T> values) {
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

    //endregion

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
