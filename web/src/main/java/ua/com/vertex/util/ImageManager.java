package ua.com.vertex.util;

import org.apache.commons.io.IOUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

public class ImageManager {
    public static byte[] convertBlobToBytes(Blob blob) {
        byte[] data = {};

        if (blob != null) {
            try (InputStream is = blob.getBinaryStream()) {
                data = IOUtils.toByteArray(is);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void saveImageToFileSystem(File file, Blob image) {
        byte[] data = convertBlobToBytes(image);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (byte b : data) {
                fos.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Blob getImageFromFileSystem(File file) throws SQLException {
        byte[] bytes = {};
        Blob blob = new SerialBlob(bytes);

        try {
            blob = new SerialBlob(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return blob;
    }
}
