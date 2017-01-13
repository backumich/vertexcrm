package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogOutController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogOutController.class);

    private static final String LOG_ENTRY = " page entered";
    private static final String LOG_LOGOUT_REFUSE = "logging out refused";

    private static final String LOGOUT = "logOut";
    private static final String LOGGED_OUT = "loggedOut";
    private static final String ERROR = "error";
    private static final String INDEX = "index";

    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public String showLogOutPage(HttpServletRequest request) {
        LOGGER.debug(storage.getId() + LOGOUT + LOG_ENTRY);

        String view = LOGOUT;
        try {
            if (request.getUserPrincipal() == null) {
                view = LOGGED_OUT;
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    public String processLogOut() {
        LOGGER.info(storage.getId() + LOGGED_OUT);
        return LOGGED_OUT;
    }

    @RequestMapping(value = "/logOutRefuse", method = RequestMethod.GET)
    public String rejectLogOut() {
        LOGGER.debug(storage.getId() + LOG_LOGOUT_REFUSE);
        return INDEX;
    }

    @Autowired
    public LogOutController(Storage storage) {
        this.storage = storage;
    }
}
