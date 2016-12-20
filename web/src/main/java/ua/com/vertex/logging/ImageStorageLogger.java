package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@SuppressWarnings("ArgNamesWarningsInspection")
@Component
@Aspect
public class ImageStorageLogger {

    private final static Logger LOGGER = LogManager.getLogger(ImageStorageLogger.class);

    @Pointcut("execution(* ua.com.vertex.beans.ImageStorage.getImageData())")
    public void aspectForGetImageData() {
    }

    @Before("aspectForGetImageData()")
    public void beforeGetImageData() {
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.getImageData(..) " +
                "- Retrieving byte array data" + System.lineSeparator());
    }

    @AfterReturning("aspectForGetImageData()")
    public void afterReturningGetImageData() {
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.getImageData(..) " +
                "- Image data retrieved" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForGetImageData()", throwing = "t")
    public void afterThrowingGetImageData(Throwable t) {
        LOGGER.error(t, t);
    }


    @Pointcut("execution(* ua.com.vertex.beans.ImageStorage.setImageData(..)) && args(imageData)")
    public void aspectForSetImageData(byte[] imageData) {
    }

    @Before("aspectForSetImageData(imageData)")
    public void beforeSetImageData(byte[] imageData) {
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.setImageData(..) " +
                "- Setting byte array data" + System.lineSeparator());
    }

    @AfterReturning("aspectForSetImageData(imageData)")
    public void afterReturningSetImageData(byte[] imageData) {
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.setImageData(..) " +
                "- Byte array data set" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForSetImageData(imageData)", throwing = "t")
    public void afterThrowingSetImageData(byte[] imageData, Throwable t) {
        LOGGER.error(t, t);
    }
}
