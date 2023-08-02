package org.akip.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {


    public static BigDecimal sum(BigDecimal... values) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < values.length; i++) {
            result = result.add(values[i]);
        }
        return result;
    }

    public static BigDecimal divide(BigDecimal n, BigDecimal d) {
        return n.divide(d, 4, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal divide(Integer n, BigDecimal d) {
        return divide(new BigDecimal(n), d);
    }
}
