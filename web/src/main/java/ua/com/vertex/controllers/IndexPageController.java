package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.LogInfo;

@Controller
public class IndexPageController {

    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(IndexPageController.class);

    private static final String ERROR = "error";
    private static final String INDEX = "index";

    @RequestMapping(value = "/")
    public String showIndexPage() {
        String view = INDEX;
        try {
            LOGGER.debug(logInfo.getId() + INDEX + " page accessed");
        } catch (Exception e) {
            LOGGER.debug(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    @Autowired
    public IndexPageController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
