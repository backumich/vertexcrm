package ua.com.vertex.util;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final static Logger LOGGER = LogManager.getLogger(ImageManager.class);

    public static byte[] convertBlobToBytes(Blob blob) {
        byte[] data = {};

        if (blob != null) {
            LOGGER.debug("Converting BLOB to byte[]");
            try (InputStream is = blob.getBinaryStream()) {
                data = IOUtils.toByteArray(is);
            } catch (SQLException | IOException e) {
                LOGGER.error(e, e);
            }
        }
        return data;
    }

    public static void saveImageToFileSystem(File file, Blob image) {
        byte[] data = convertBlobToBytes(image);

        LOGGER.debug("Saving image to file system");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (byte b : data) {
                fos.write(b);
            }
        } catch (IOException e) {
            LOGGER.error(e, e);
        }
    }

    public static Blob getImageFromFileSystem(File file) throws SQLException {
        byte[] bytes = {};
        Blob blob = new SerialBlob(bytes);

        LOGGER.debug("Retrieving image from file system");
        try {
            blob = new SerialBlob(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException | SQLException e) {
            LOGGER.error(e, e);
        }

        return blob;
    }
}
