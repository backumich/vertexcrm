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

    private static final Logger LOGGER = LogManager.getLogger(ImageStorageLogger.class);

    @Around("execution(* ua.com.vertex.beans.ImageStorage.getImageData())")
    public Object aspectForGetImageData(ProceedingJoinPoint jp) throws Throwable {

        LOGGER.debug(compose(jp) + " - Retrieving image data" + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - Image data retrieved" + System.lineSeparator());

        return object;
    }

    @Around("execution(* ua.com.vertex.beans.ImageStorage.setImageData(..)) && args(imageData)")
    public Object aspectForSetImageData(ProceedingJoinPoint jp, byte[] imageData) throws Throwable {

        LOGGER.debug(compose(jp) + " - Setting image data" + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - Image data set" + System.lineSeparator());

        return object;
    }

    private String compose(ProceedingJoinPoint jp) {
        return "class: " + jp.getSignature().getDeclaringType() + "; method: " + jp.getSignature().getName();
    }
}
