package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CertificateDaoLogger {

    private final static Logger LOGGER = LogManager.getLogger(CertificateDaoLogger.class);

    @Around("execution(* ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(..)) " +
            "&& args(certificateId)")
    public Object aspectForGetCertificateById(ProceedingJoinPoint jp, int certificateId) throws Throwable {
        Object object;
        LOGGER.debug("ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(..) " +
                "- Retrieving certificate by certificateID=" + certificateId + System.lineSeparator());
        object = jp.proceed();
        LOGGER.debug("ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(..) " +
                "- CertificateID=" + certificateId + " retrieved" + System.lineSeparator());
        return object;
    }
}
