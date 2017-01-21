package ua.com.vertex.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static ua.com.vertex.context.WebAppInitializer.TEMP_DIR_PATH;

public class DeleteTempFiles {

    public static void cleanTempDir() throws IOException {
        FileUtils.cleanDirectory(new File(TEMP_DIR_PATH));
    }
}
