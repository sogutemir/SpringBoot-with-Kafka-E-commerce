package com.food.ordering.system.springwork3.common.mapper;

import java.math.BigDecimal;

public class BigDecimalMapper {

    public static BigDecimal fromDouble(double value) {
        return BigDecimal.valueOf(value);
    }

    public static double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }

    public static BigDecimal fromString(String value) {
        return value != null && !value.isEmpty() ? new BigDecimal(value) : BigDecimal.ZERO;
    }

    public static String toString(BigDecimal value) {
        return value != null ? value.toString() : "0.0";
    }
}
