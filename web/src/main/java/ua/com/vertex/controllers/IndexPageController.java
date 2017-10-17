package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPageController {
    private static final Logger logger = LogManager.getLogger(IndexPageController.class);
    private static final String INDEX = "index";

    @RequestMapping(value = "/")
    public String showIndexPage() {
        logger.debug(INDEX + " page accessed");
        return INDEX;
    }
}
