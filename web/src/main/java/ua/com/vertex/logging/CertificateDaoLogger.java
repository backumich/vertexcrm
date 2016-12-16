package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@SuppressWarnings("ArgNamesWarningsInspection")
@Component
@Aspect
public class CertificateDaoLogger {

    private final static Logger LOGGER = LogManager.getLogger(CertificateDaoLogger.class);

    @Pointcut("execution(* ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(int))" +
            "&& args(certificateId))")
    public void getCertificateById(int certificateId) {
    }

    @Before("getCertificateById(certificateId)")
    public void before(int certificateId) {
        LOGGER.debug("ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(..) " +
                "- Retrieving certificate by certificateID=" + certificateId + System.lineSeparator());
    }

    @AfterReturning("getCertificateById(certificateId)")
    public void after(int certificateId) {
        LOGGER.debug("ua.com.vertex.dao.CertificateDaoImpl.getCertificateById(..) " +
                "- Exiting method" + System.lineSeparator());
    }
}
