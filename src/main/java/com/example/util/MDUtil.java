package com.example.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MDUtil {
    public static void main(String[] args) {
        System.out.println(encode("@Rajab1202"));
    }

    public static String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
