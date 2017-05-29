package ua.com.vertex.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Aes {

    private static String pad2(String n) {
        if (n.length() < 2) {
            return "0" + n;
        } else {
            return n;
        }
    }

    private static String hex(byte[] bytes) {
        String r = "";
        for (byte aByte : bytes) {
            r = r + pad2(Integer.toHexString(aByte + 128));
        }
        return r;
    }

    private static String safePassword(String unsafe) {
        String safe = unsafe;
        if (safe.length() > 16) {
            safe = safe.substring(0, 16);
        }
        int nn = safe.length();
        for (int i = nn - 1; i < 15; i++) {
            safe = safe + "*";
        }
        return safe;
    }

    public static String encrypt(String value, String password) {
        byte[] encrypted;
        try {
            SecretKey key = new SecretKeySpec(safePassword(password).getBytes("UTF-8"), "Aes");
            Cipher cipher = Cipher.getInstance("Aes/ECB/PKCS5Padding", "SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(value.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hex(encrypted);
    }

    private static int parseInt2(String s) {
        return (new java.math.BigInteger(s, 16)).intValue();
    }

    private static byte[] fromHex(String enc) {
        byte[] r = new byte[enc.length() / 2];
        for (int i = 0; i < r.length; i++) {
            int n = parseInt2(enc.substring(i * 2, i * 2 + 2)) - 128;
            r[i] = (byte) n;
        }
        return r;
    }

    public static String decrypt(String value, String password) {
        String toReturn;
        try {
            byte[] encypted = fromHex(value);
            SecretKey key = new SecretKeySpec(safePassword(password).getBytes("UTF-8"), "Aes");
            Cipher cipher = Cipher.getInstance("Aes/ECB/PKCS5Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encypted);
            toReturn = new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }
}
