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

    private static final Logger LOGGER = LogManager.getLogger(CertificateDaoLogger.class);

    @Around("execution(* ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(..)) " +
            "&& args(certificateId)")
    public Object aspectForGetCertificateById(ProceedingJoinPoint jp, int certificateId) throws Throwable {

        LOGGER.debug(compose(jp) + " - Retrieving certificate by certificateID=" + certificateId
                + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - CertificateID=" + certificateId + " retrieved" + System.lineSeparator());

        return object;
    }

    private String compose(ProceedingJoinPoint jp) {
        return "class: " + jp.getSignature().getDeclaringType() + "; method: " + jp.getSignature().getName();
    }
}
