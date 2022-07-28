package com.agilekip.service.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5SumUtil {

    private final Logger log = LoggerFactory.getLogger(MD5SumUtil.class);

    private MessageDigest md;

    public MD5SumUtil() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("Error getting a messageDigest instance: {}", e);
        }
    }

    public String calculateMD5Sum(String input) {
        if (md == null) {
            return input;
        }

        return (new BigInteger(1, md.digest(input.getBytes()))).toString(16);
    }
}
