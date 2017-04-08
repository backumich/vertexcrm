package ua.com.vertex.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EncryptorTest {

    @Test
    public void testEncryption() {
        int idToEncrypt = 123456;
        String idEncrypted = Encryptor.encode(String.valueOf(idToEncrypt));
        int idReverted = Integer.parseInt(Encryptor.decode(idEncrypted));

        assertTrue(idToEncrypt == idReverted);
    }
}
