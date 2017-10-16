package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/403")
public class AccessDeniedController {
    private static final Logger Logger = LogManager.getLogger(AccessDeniedController.class);
    private static final String ACCESS_DENIED = "403";

    @GetMapping
    public String show403Page() {
        Logger.debug("HTTP Status 403 - Access is denied");
        return ACCESS_DENIED;
    }
}
