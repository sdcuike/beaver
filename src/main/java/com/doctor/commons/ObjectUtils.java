package com.doctor.commons;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年11月1日
 *         <p>
 */
public final class ObjectUtils {
    public static final int    Initial_Hash            = 7;
    public static final int    Multiplier_             = 31;

    public static final String Empty_String            = "";
    public static final String Null_String             = "null";
    public static final String Array_Start             = "{";
    public static final String Array_End               = "}";
    public static final String Empty_Array             = Array_Start + Array_End;
    public static final String Array_Element_Separator = ", ";

    public static boolean isArray(Object object) {
        return (object != null && object.getClass().isArray());
    }

    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }

        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }

        if (object instanceof Collection) {
            return ((Collection<?>) object).isEmpty();
        }

        if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    public static boolean contains(Object[] array, Object element) {
        if (array == null) {
            return false;
        }

        for (Object object : array) {
            if (nullSafeEquals(object, element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Enum<?>[] enumValues, String enumConstantName) {
        return contains(enumValues, enumConstantName, false);
    }

    public static boolean contains(Enum<?>[] enumValues, String enumConstantName, boolean caseSensitive) {
        for (Enum<?> element : enumValues) {
            if (caseSensitive ? element.toString().equals(enumConstantName) : element.toString().equalsIgnoreCase(enumConstantName)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues, String enumConstantName) {
        for (E e : enumValues) {
            if (e.toString().equalsIgnoreCase(enumConstantName)) {
                return e;
            }
        }
        return null;
    }

    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }

        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }

        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            return arrayEquals(o1, o2);
        }

        return false;
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }
}
