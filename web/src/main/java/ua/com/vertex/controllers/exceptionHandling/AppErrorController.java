package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;


@Controller
public class AppErrorController {
    //        implements ErrorController{
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String NOT_FOUND = "404 — unfortunately, the page you requested has not been found";
    private static final Logger LOGGER = LogManager.getLogger(AppErrorController.class);

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }

//    @RequestMapping(value = "/error")
//    public String handleError(HttpServletRequest request, Model model) {
//        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//
//        if (UNKNOWN_ERROR.equals(throwable.getMessage())) {
//            LOGGER.warn(UNKNOWN_ERROR, throwable);
//            model.addAttribute(ERROR_MESSAGE, UNKNOWN_ERROR);
//        } else {
//            LOGGER.warn(INTERNAL_SERVER_ERROR, throwable);
//            model.addAttribute(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
//        }
//        return ERROR;
//    }
//    @RequestMapping(value = "/404")
//    public String handle404(Model model) {
//        model.addAttribute(ERROR_MESSAGE, NOT_FOUND);
//        return ERROR;
//    }

}
