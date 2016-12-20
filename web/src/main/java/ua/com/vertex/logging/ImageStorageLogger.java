package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ImageStorageLogger {

    private final static Logger LOGGER = LogManager.getLogger(ImageStorageLogger.class);

    @Around("execution(* ua.com.vertex.beans.ImageStorage.getImageData())")
    public Object aroundForGetImageData(ProceedingJoinPoint jp) throws Throwable {
        Object object;
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.getImageData(..) " +
                "- Retrieving byte array data" + System.lineSeparator());
        object = jp.proceed();
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.getImageData(..) " +
                "- Image data retrieved" + System.lineSeparator());
        return object;
    }

    @Around("execution(* ua.com.vertex.beans.ImageStorage.setImageData(..)) && args(imageData)")
    public void aroundForGetImageData(ProceedingJoinPoint jp, byte[] imageData) throws Throwable {
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.setImageData(..) " +
                "- Setting byte array data" + System.lineSeparator());
        jp.proceed();
        LOGGER.debug("ua.com.vertex.beans.ImageStorage.setImageData(..) " +
                "- Byte array data set" + System.lineSeparator());
    }
}
