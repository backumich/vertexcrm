package ua.com.vertex.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static ua.com.vertex.context.WebAppInitializer.TEMP_DIR_PATH;

@Component
public class DeleteTempFiles {

    private Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(DeleteTempFiles.class);

    public void cleanTempDir() {
        try {
            FileUtils.cleanDirectory(new File(TEMP_DIR_PATH));
        } catch (IOException e) {
            LOGGER.debug(storage.getId(), e, e);
        }
    }

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
