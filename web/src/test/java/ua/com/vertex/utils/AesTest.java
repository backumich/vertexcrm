package ua.com.vertex.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AesTest {

    @Test
    public void EncodeDecodeTest() {
        String toEncrypt = "TestStringToEncrypt";
        String password = "testPassword";
        String encrypted = Aes.encrypt(toEncrypt, password);
        String reverted = Aes.decrypt(encrypted, password);

        assertEquals(toEncrypt, reverted);
    }
}