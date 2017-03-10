package ua.com.vertex.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class IdEncryptor {

    private static final Logger LOGGER = LogManager.getLogger(IdEncryptor.class);

    private static final String KEY = "ArgentinaJamaica";
    private static final String INIT_VECTOR = "ImpossibleTask11";

    public static String encode(int value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal((String.valueOf(value)).getBytes());

            return org.apache.commons.codec.binary.Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            LOGGER.debug(e);
        }

        return null;
    }

    public static int decode(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encrypted));

            return Integer.parseInt(new String(original));
        } catch (Exception e) {
            LOGGER.debug(e);
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(encode(1234987));
    }
}
