package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

@Component
@Aspect
public class CertificateDetailsPageControllerLogger {

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageControllerLogger.class);

    @Around("execution(* ua.com.vertex.controllers.CertificateDetailsPageController" +
            ".showCertificateDetailsPage(..)) && args(model)")
    public Object aspectForShowCertificateDetails(ProceedingJoinPoint jp, Model model) throws Throwable {

        LOGGER.debug(compose(jp) + " - Invoking certificate details form" + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - Certificate details form sent" + System.lineSeparator());
        return object;
    }

    @Around(value = "execution(* ua.com.vertex.controllers.CertificateDetailsPageController" +
            ".processCertificateDetails(..)) && args(certificate, result, model)",
            argNames = "jp, certificate, result, model")
    public Object aspectForProcessCertificateDetails(ProceedingJoinPoint jp, Certificate certificate,
                                                     BindingResult result, Model model) throws Throwable {

        LOGGER.debug(compose(jp) + " - Passing requested certificate ID=" + certificate.getCertificationId()
                + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - Certificate ID=" + certificate.getCertificationId() + " was (not) displayed"
                + System.lineSeparator());
        return object;
    }

    @Around(value = "execution(* ua.com.vertex.controllers.CertificateDetailsPageController.setModel(..)) " +
            "&& args(certificate, user, model)", argNames = "jp, certificate, user, model")
    public Object aspectForSetModel(ProceedingJoinPoint jp, Certificate certificate,
                                    User user, Model model) throws Throwable {
        LOGGER.debug(compose(jp) + " - Setting model attributes" + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - Model attributes set" + System.lineSeparator());
        return object;
    }

    @Around("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..))" +
            "&& args(model)")
    public Object aspectForShowUserPhoto(ProceedingJoinPoint jp, Model model) throws Throwable {

        LOGGER.debug(compose(jp) + " - Retrieving photo" + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - Photo sent to view" + System.lineSeparator());
        return object;
    }

    private String compose(ProceedingJoinPoint jp) {
        return "class: " + jp.getSignature().getDeclaringType() + "; method: " + jp.getSignature().getName();
    }
}
