package main.java;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;


import static java.util.Objects.hash;

public class TestCasesClass {

    private static String getSaltedHash(String password){
        byte[] salt = null;
        String hashedPassword = null;

        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32); //$NON-NLS-1$
            hashedPassword = Base64.encodeBase64(salt) + "$" + hash(password, salt); //$NON-NLS-1$
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }

    public static void main(String[] args) {
        System.out.println(getSaltedHash("123456"));
    }

}
