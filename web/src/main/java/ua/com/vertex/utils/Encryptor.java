package ua.com.vertex.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
    private static final String KEY = "ArgentinaJamaica";
    private static final String INIT_VECTOR = "ImpossibleTask11";

    public static String encode(String value) {

        String toReturn;

        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            toReturn = Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return toReturn;
    }

    public static String decode(String encrypted) {

        String toReturn;

        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            toReturn = new String(original);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return toReturn;
    }
}
