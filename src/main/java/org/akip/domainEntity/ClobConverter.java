package org.akip.domainEntity;

import java.sql.Clob;
import java.sql.SQLException;

public class ClobConverter {

    public static Object convertClobToString(Object value, int maxLength) {
        if (!(value instanceof Clob)) {
            return value;
        }
        Clob clob = (Clob) value;
        try {
            String clobAsString = clob.getSubString(1, maxLength > 0 ? maxLength: (int) clob.length());
            if (maxLength> 0 && clob.length() > maxLength) {
                clobAsString += "...";
            }
            return clobAsString;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
