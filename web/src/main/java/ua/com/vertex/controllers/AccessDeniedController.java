package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.LogInfo;

@Controller
@RequestMapping(value = "/403")
public class AccessDeniedController {

    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(AccessDeniedController.class);

    private static final String LOG_DENIED = "HTTP Status 403 - Access is denied";

    private static final String ACCESS_DENIED = "403";

    @RequestMapping
    public String show403Page() {
        LOGGER.debug(logInfo.getId() + LOG_DENIED);
        return ACCESS_DENIED;
    }

    @Autowired
    public AccessDeniedController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
