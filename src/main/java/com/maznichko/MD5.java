package com.maznichko;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class need for encryption string
 */

public class MD5 {
    private static final Logger log = Logger.getLogger(MD5.class);

    /**
     * Method return encryption string by input
     *
     * @param input - string
     * @return - hash key
     */

    public static String getMd5(String input) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage() + " encryption is failed");
            throw new RuntimeException(e);
        }
    }
}
