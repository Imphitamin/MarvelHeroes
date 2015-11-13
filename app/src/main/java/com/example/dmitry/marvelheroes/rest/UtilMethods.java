package com.example.dmitry.marvelheroes.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class UtilMethods {

    public static Long generateTimeStamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTimeInMillis() / 1000L;
    }

    public static String generateHash(long timeStamp) {
        return md5(String.valueOf(timeStamp) + Constants.API_PRIVATE_KEY + Constants.API_PUBLIC_KEY);
    }

    // Создаю md5 hash из входящего текста(String)
    public static String md5(final String inputString) {
        final String MD5 = "MD5";
        try {
            // Создаю MD5-Хэш
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(inputString.getBytes());
            byte messageDigest[] = digest.digest();

            // Создаю Hex-String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
