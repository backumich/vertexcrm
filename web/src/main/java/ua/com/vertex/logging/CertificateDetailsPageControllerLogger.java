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
    public void aspectForDoGet(String requestedId, Model model) {
    }

    @Before("aspectForDoGet(requestedId, model)")
    public void beforeDoGet(String requestedId, Model model) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..) " +
                "- Passing requested certificate ID=" + requestedId + System.lineSeparator());
    }

    @AfterReturning("aspectForDoGet(requestedId, model)")
    public void afterReturningDoGet(String requestedId, Model model) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showCertificateDetails(..) " +
                "- Exiting method" + System.lineSeparator());
    }

    @AfterThrowing(value = "aspectForDoGet(requestedId, model)", throwing = "e")
    public void afterThrowingDoGet(String requestedId, Model model, Exception e) {
        LOGGER.error(e, e);
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

    @AfterThrowing(value = "aspectForShowUserPhoto(response)", throwing = "e")
    public void afterThrowingShowUserPhoto(HttpServletResponse response, Exception e) {
        LOGGER.error(e, e);
    }
}
