package com.example.project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
    public static byte[] encryptSHA(byte[] data, String shaN) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(shaN);
        sha.update(data);
        return sha.digest();
    }
}
