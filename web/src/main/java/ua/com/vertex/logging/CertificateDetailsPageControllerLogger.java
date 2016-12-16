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

    @Pointcut("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.doGet(..)) " +
            "&& args(requestedId, model))")
    public void doGet(String requestedId, Model model) {
    }

    @Before("doGet(requestedId, model)")
    public void before(String requestedId, Model model) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.doGet(..) " +
                "- Passing requested certificate ID=" + requestedId + System.lineSeparator());
    }

    @AfterReturning("doGet(requestedId, model)")
    public void afterReturning(String requestedId, Model model) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.doGet(..) " +
                "- Exiting method" + System.lineSeparator());
    }

    @AfterThrowing(value = "doGet(requestedId, model)", throwing = "e")
    public void afterThrowing(String requestedId, Model model, Exception e) {
        LOGGER.error(e, e);
    }


    @Pointcut("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..)) " +
            "&& args(response))")
    public void showUserPhoto(HttpServletResponse response) {
    }

    @Before("showUserPhoto(response)")
    public void before1(HttpServletResponse response) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..) " +
                "- Transferring image to jsp" + System.lineSeparator());
    }

    @AfterReturning("showUserPhoto(response)")
    public void afterReturning1(HttpServletResponse response) {
        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.showUserPhoto(..) " +
                "- Exiting method" + System.lineSeparator());
    }

    @AfterThrowing(value = "showUserPhoto(response)", throwing = "e")
    public void afterThrowing1(HttpServletResponse response, Exception e) {
        LOGGER.error(e, e);
    }

//    @Around("execution(* ua.com.vertex.controllers.CertificateDetailsPageController.doGet(..)) " +
//            "&& args(requestedId, model))")
//    public void doGet(ProceedingJoinPoint jp, String requestedId, Model model) {
//        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.doGet(..) " +
//                "- Passing requested certificate ID");
//        try {
//            jp.proceed();
//        } catch (Throwable throwable) {
////            LOGGER.error(e, e);
//        }
//        LOGGER.debug("ua.com.vertex.controllers.CertificateDetailsPageController.doGet(..) " +
//                "- Exiting method");
//    }
}
