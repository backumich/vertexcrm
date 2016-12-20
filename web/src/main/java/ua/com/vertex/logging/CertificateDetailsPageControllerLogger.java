package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class CertificateDetailsPageControllerLogger {

    private final static Logger LOGGER = LogManager.getLogger(CertificateDetailsPageControllerLogger.class);

    @Around("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..)) " +
            "&& args(requestedId, model)")
    public Object aspectForShowCertificateDetails(ProceedingJoinPoint jp, String requestedId, Model model) {
        Object object = new Object();
        try {
            LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..) " +
                    "- Passing requested certificate ID=" + requestedId + System.lineSeparator());
            object = jp.proceed();
            LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..) " +
                    "- Certificate ID=" + requestedId + " was (not) displayed" + System.lineSeparator());
        } catch (Throwable t) {
            LOGGER.error(t, t);
        }
        return object;
    }

    @Around("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..)) " +
            "&& args(response)")
    public Object aspectForShowCertificateDetails(ProceedingJoinPoint jp, HttpServletResponse response) {
        Object object = new Object();
        try {
            LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..) " +
                    "- Retrieving photo" + System.lineSeparator());
            object = jp.proceed();
            LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..) " +
                    "- Photo sent to view" + System.lineSeparator());
        } catch (Throwable t) {
            LOGGER.error(t, t);
        }
        return object;
    }
}
