package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@SuppressWarnings("ArgNamesWarningsInspection")
@Component
@Aspect
public class CertDetailsPageLogicLogger {

    private final static Logger LOGGER = LogManager.getLogger(CertDetailsPageLogicLogger.class);

    @Pointcut("execution(* ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..))" +
            "&& args(certificationId))")
    public void aspectForGetCertificateDetails(int certificationId) {
    }

    @Before("aspectForGetCertificateDetails(certificationId)")
    public void beforeGetCertificateDetails(int certificationId) {
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..) " +
                "- Invoking logic to retrieve certificate ID=" + certificationId + System.lineSeparator());
    }

    @AfterReturning("aspectForGetCertificateDetails(certificationId)")
    public void afterReturningGetCertificateDetails(int certificationId) {
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..) " +
                "- Certificate ID=" + certificationId + " retrieved" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForGetCertificateDetails(certificationId)", throwing = "t")
    public void afterThrowingGetCertificateDetails(int certificationId, Throwable t) {
        LOGGER.error(t, t);
    }


    @Pointcut("execution(* ua.com.vertex.logic.CertDetailsPageLogicImpl.getUserDetails(int))" +
            "&& args(userId))")
    public void aspectForGetUserDetails(int userId) {
    }

    @Before("aspectForGetUserDetails(userId)")
    public void beforeGetUserDetails(int userId) {
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getUserDetails(..) " +
                "- Invoking logic to retrieve user ID=" + userId + System.lineSeparator());
    }

    @AfterReturning("aspectForGetUserDetails(userId)")
    public void afterReturningGetUserDetails(int userId) {
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getUserDetails(..) " +
                "- User ID=" + userId + " retrieved" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForGetUserDetails(userId)", throwing = "t")
    public void afterThrowingGetUserDetails(int userId, Throwable t) {
        LOGGER.error(t, t);
    }
}
