package ua.com.vertex.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class IdTransformer {
    private static final String KEY = "what a pain";

    public static String encode(int id) {
        String interim = encodingStep(id + KEY);
        return encodingStep(interim);
    }

    private static String encodingStep(String toEncode) {
        return Base64.encode(toEncode.getBytes());
    }

    public static int decode(String toDecode) {
        String interim = decodingStep(toDecode);
        String result = decodingStep(interim);

        return Integer.parseInt(result.replace(KEY, ""));
    }

    private static String decodingStep(String toDecode) {
        return new String(Base64.decode(toDecode));
    }
}
