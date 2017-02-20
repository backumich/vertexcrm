package ua.com.vertex.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.Arrays;

public class TransformId {
    private static String key = "what a pain";

    public static String encode(int id) {
        String toConvert = id + key;
        byte[] array1 = toConvert.getBytes();
        System.out.println(Arrays.toString(array1));

        String result = Base64.encode(array1);
        byte[] array2 = result.getBytes();

        return Base64.encode(array2);
    }

    public static int decode(String toDecode) {
        byte[] arrayReversed1 = Base64.decode(toDecode);
        String retrievedString = new String(arrayReversed1);
        byte[] arrayReversed2 = Base64.decode(retrievedString);
        String retrievedString2 = new String(arrayReversed2);

        return Integer.parseInt(retrievedString2.replace(key, ""));
    }
}
