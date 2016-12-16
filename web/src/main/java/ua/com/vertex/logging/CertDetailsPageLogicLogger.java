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

    @Pointcut("execution(* ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(int))" +
            "&& args(certificationId))")
    public void aspectForGetCertificateDetails(int certificationId) {
    }

    @Before("aspectForGetCertificateDetails(certificationId)")
    public void before(int certificationId) {
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..) " +
                "- Invoking logic to retrieve certificate ID=" + certificationId + System.lineSeparator());
    }

    @AfterReturning("aspectForGetCertificateDetails(certificationId)")
    public void afterReturning(int certificationId) {
        LOGGER.debug("ua.com.vertex.logic.CertDetailsPageLogicImpl.getCertificateDetails(..) " +
                "- Exiting method" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForGetCertificateDetails(certificationId)", throwing = "e")
    public void afterThrowing(int certificationId, Exception e) {
        LOGGER.error(e, e);
    }
}
