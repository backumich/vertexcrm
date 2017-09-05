package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeacherController {

    static final String TEACHER_JSP = "teacher";

    private static final Logger LOGGER = LogManager.getLogger(TeacherController.class);

    @GetMapping(value = "/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public ModelAndView teacher() {
        LOGGER.debug("Request to '/teacher' redirect to page - " + TEACHER_JSP);
        return new ModelAndView(TEACHER_JSP);
    }
}
