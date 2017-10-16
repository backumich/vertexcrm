package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    static final String ADMIN_JSP = "admin";

    private static final Logger Logger = LogManager.getLogger(AdminController.class);

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView admin() {
        Logger.debug("Request to '/admin' redirect to page - " + ADMIN_JSP);
        return new ModelAndView(ADMIN_JSP);
    }

}
