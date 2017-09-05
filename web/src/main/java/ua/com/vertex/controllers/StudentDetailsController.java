package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.NoSuchElementException;

public class StudentDetailsController {
    static final String STUDENT_DETAILS_JSP = "studentDetails";
    static final String USER_ID = "userId";
    static final String USER = "user";

    private static final Logger LOGGER = LogManager.getLogger(StudentDetailsController.class);

    private final UserLogic userLogic;

    @Autowired
    public StudentDetailsController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping(value = "studentDetails")
    @PreAuthorize("hasRole('TEACHER')")
    public ModelAndView studentDetails(@RequestParam(USER_ID) int userId) {
        LOGGER.debug(String.format("Call studentDetails(%s)", userId));
        return new ModelAndView(STUDENT_DETAILS_JSP).addObject(USER, userLogic.getUserById(userId)
                .orElseThrow(NoSuchElementException::new));
    }
}


/**
 LOGGER.debug(String.format("Call studentDetails(%s)", userId));
 ModelAndView result = new ModelAndView(STUDENT_DETAILS_JSP);

 LOGGER.debug(String.format("Try add user details by id - (%s).", userId));
 result.addObject(USER, userLogic.getUserById(userId));

 LOGGER.debug(String.format("Return new ModelAndView - %s", result));
 **/