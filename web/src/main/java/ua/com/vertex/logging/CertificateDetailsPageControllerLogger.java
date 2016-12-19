package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("ArgNamesWarningsInspection")
@Component
@Aspect
public class CertificateDetailsPageControllerLogger {

    private final static Logger LOGGER = LogManager.getLogger(CertificateDetailsPageControllerLogger.class);

    @Pointcut("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..)) " +
            "&& args(requestedId, model))")
    public void aspectForShowCertificateDetails(String requestedId, Model model) {
    }

    @Before("aspectForShowCertificateDetails(requestedId, model)")
    public void beforeShowCertificateDetails(String requestedId, Model model) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..) " +
                "- Passing requested certificate ID=" + requestedId + System.lineSeparator());
    }

    @AfterReturning("aspectForShowCertificateDetails(requestedId, model)")
    public void afterShowCertificateDetails(String requestedId, Model model) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..) " +
                "- Exiting method" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForShowCertificateDetails(requestedId, model)", throwing = "t")
    public void afterThrowingShowCertificateDetails(String requestedId, Model model, Throwable t) {
        LOGGER.error(t, t);
    }



    @Pointcut("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..)) " +
            "&& args(response))")
    public void aspectForShowUserPhoto(HttpServletResponse response) {
    }

    @Before("aspectForShowUserPhoto(response)")
    public void beforeShowUserPhoto(HttpServletResponse response) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..) " +
                "- Transferring image to jsp" + System.lineSeparator());
    }

    @AfterReturning("aspectForShowUserPhoto(response)")
    public void afterReturningShowUserPhoto(HttpServletResponse response) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..) " +
                "- Exiting method" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForShowUserPhoto(response)", throwing = "t")
    public void afterThrowingShowUserPhoto(HttpServletResponse response, Throwable t) {
        LOGGER.error(t, t);
    }
}
