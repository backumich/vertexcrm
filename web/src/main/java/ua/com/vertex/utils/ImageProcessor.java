package ua.com.vertex.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ImageProcessor {

    static public String encodeImage(byte[] image) {
        return Base64.encode(image);
    }

    static public byte[] decodeImage(String image) {
        return Base64.decode(image);
    }
}
