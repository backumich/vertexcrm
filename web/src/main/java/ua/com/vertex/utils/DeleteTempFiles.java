package ua.com.vertex.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static ua.com.vertex.context.WebAppInitializer.TEMP_DIR_PATH;

@Component
public class DeleteTempFiles {

    private LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(DeleteTempFiles.class);

    public void cleanTempDir() {
        try {
            FileUtils.cleanDirectory(TEMP_DIR_PATH.toFile());
        } catch (IOException e) {
            LOGGER.debug(logInfo.getId(), e, e);
        }
    }

    @Autowired
    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
