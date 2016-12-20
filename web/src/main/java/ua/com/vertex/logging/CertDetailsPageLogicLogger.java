package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CertDetailsPageLogicLogger {

    private final static Logger LOGGER = LogManager.getLogger(CertDetailsPageLogicLogger.class);

    @Around("execution(* ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..))" +
            "&& args(certificationId))")
    public Object aspectForGetCertificateDetails(ProceedingJoinPoint jp, int certificationId) throws Throwable {
        Object object;
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..) " +
                "- Invoking logic to retrieve certificate ID=" + certificationId + System.lineSeparator());
        object = jp.proceed();
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..) " +
                "- Certificate ID=" + certificationId + " retrieved" + System.lineSeparator());
        return object;
    }

    @Around("execution(* ua.com.vertex.logic.CertDetailsPageLogicImpl.getUserDetails(..))" +
            "&& args(userId))")
    public Object aspectForGetUserDetails(ProceedingJoinPoint jp, int userId) throws Throwable {
        Object object;
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getUserDetails(..) " +
                "- Invoking logic to retrieve user ID=" + userId + System.lineSeparator());
        object = jp.proceed();
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getUserDetails(..) " +
                "- User ID=" + userId + " retrieved" + System.lineSeparator());
        return object;
    }
}
